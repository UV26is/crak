<template>
  <div class="admin">
    <div class="admin-container">
      <h2>系统管理</h2>
      
      <!-- 用户管理 -->
      <el-card class="user-manage-card">
        <div slot="header">
          <span>用户管理</span>
        </div>
        <el-tabs v-model="activeTab">
          <el-tab-pane label="听众管理" name="listener">
            <el-table :data="listenerList" style="width: 100%">
              <el-table-column prop="username" label="用户名"></el-table-column>
              <el-table-column prop="email" label="邮箱"></el-table-column>
              <el-table-column prop="registerDate" label="注册时间"></el-table-column>
              <el-table-column prop="status" label="状态">
                <template slot-scope="scope">
                  <el-tag :type="scope.row.status === 'active' ? 'success' : 'danger'">
                    {{scope.row.status === 'active' ? '正常' : '禁用'}}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="200">
                <template slot-scope="scope">
                  <el-button size="mini" @click="handleUserStatus(scope.row)">
                    {{scope.row.status === 'active' ? '禁用' : '启用'}}
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
          
          <el-tab-pane label="音乐人管理" name="musician">
            <el-table :data="musicianList" style="width: 100%">
              <el-table-column prop="username" label="用户名"></el-table-column>
              <el-table-column prop="artistName" label="艺名"></el-table-column>
              <el-table-column prop="email" label="邮箱"></el-table-column>
              <el-table-column prop="status" label="状态">
                <template slot-scope="scope">
                  <el-tag :type="getStatusType(scope.row.status)">
                    {{getStatusText(scope.row.status)}}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="250">
                <template slot-scope="scope">
                  <el-button size="mini" type="success" 
                    v-if="scope.row.status === 'pending'"
                    @click="handleMusicianApprove(scope.row)">通过</el-button>
                  <el-button size="mini" type="danger" 
                    v-if="scope.row.status === 'pending'"
                    @click="handleMusicianReject(scope.row)">拒绝</el-button>
                  <el-button size="mini" type="warning" 
                    v-if="scope.row.status === 'active'"
                    @click="handleMusicianDisable(scope.row)">禁用</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
        </el-tabs>
      </el-card>

      <!-- 广告管理 -->
      <el-card class="ad-manage-card">
        <div slot="header">
          <span>广告管理</span>
          <el-button style="float: right; padding: 3px 0" type="text" @click="showAddAdDialog">添加广告</el-button>
        </div>
        <el-table :data="adList" style="width: 100%">
          <el-table-column prop="title" label="标题"></el-table-column>
          <el-table-column prop="type" label="类型"></el-table-column>
          <el-table-column prop="startDate" label="开始时间"></el-table-column>
          <el-table-column prop="endDate" label="结束时间"></el-table-column>
          <el-table-column prop="status" label="状态">
            <template slot-scope="scope">
              <el-tag :type="scope.row.status === 'active' ? 'success' : 'info'">
                {{scope.row.status === 'active' ? '进行中' : '已结束'}}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="200">
            <template slot-scope="scope">
              <el-button size="mini" @click="handleEditAd(scope.row)">编辑</el-button>
              <el-button size="mini" type="danger" @click="handleDeleteAd(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>

      <!-- 添加广告对话框 -->
      <el-dialog title="添加广告" :visible.sync="adDialogVisible">
        <el-form :model="adForm" ref="adForm" label-width="100px" :rules="adRules">
          <el-form-item label="标题" prop="title">
            <el-input v-model="adForm.title"></el-input>
          </el-form-item>
          <el-form-item label="类型" prop="type">
            <el-select v-model="adForm.type" placeholder="请选择广告类型">
              <el-option label="首页轮播" value="banner"></el-option>
              <el-option label="歌曲播放页" value="player"></el-option>
              <el-option label="侧边栏" value="sidebar"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="时间范围" prop="dateRange">
            <el-date-picker
              v-model="adForm.dateRange"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="广告内容" prop="content">
            <el-input type="textarea" v-model="adForm.content" rows="4"></el-input>
          </el-form-item>
          <el-form-item label="广告图片" prop="image">
            <el-upload
              class="upload-demo"
              action="/api/upload"
              :on-success="handleAdImageSuccess"
              :on-error="handleAdImageError"
              :before-upload="beforeAdImageUpload"
              :limit="1"
              :file-list="adImageList">
              <el-button size="small" type="primary">点击上传</el-button>
              <div slot="tip" class="el-upload__tip">请上传jpg/png格式的图片</div>
            </el-upload>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="adDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitAd">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { mixin } from '../mixins'
import { HttpManager } from '../api/index'

export default {
  name: 'admin',
  mixins: [mixin],
  data() {
    return {
      activeTab: 'listener',
      listenerList: [],
      musicianList: [],
      adList: [],
      adDialogVisible: false,
      adForm: {
        title: '',
        type: '',
        dateRange: [],
        content: '',
        image: ''
      },
      adImageList: [],
      adRules: {
        title: [
          { required: true, message: '请输入广告标题', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请选择广告类型', trigger: 'change' }
        ],
        dateRange: [
          { required: true, message: '请选择广告时间范围', trigger: 'change' }
        ],
        content: [
          { required: true, message: '请输入广告内容', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getUserList()
    this.getAdList()
  },
  methods: {
    getUserList() {
      HttpManager.getUserList({ type: 'listener' }).then(res => {
        if (res.code === 1) {
          this.listenerList = res.data
        }
      })
      HttpManager.getMusicianList().then(res => {
        if (res.code === 1) {
          this.musicianList = res.data
        }
      })
    },
    getAdList() {
      HttpManager.getAdList().then(res => {
        if (res.code === 1) {
          this.adList = res.data
        }
      })
    },
    handleUserStatus(row) {
      const newStatus = row.status === 'active' ? 'disabled' : 'active'
      HttpManager.updateUserInfo({ id: row.id, status: newStatus }).then(res => {
        if (res.code === 1) {
          this.notify('状态更新成功', 'success')
          this.getUserList()
        }
      })
    },
    handleMusicianApprove(row) {
      HttpManager.approveMusician(row.id).then(res => {
        if (res.code === 1) {
          this.notify('已通过音乐人申请', 'success')
          this.getUserList()
        }
      })
    },
    handleMusicianReject(row) {
      HttpManager.rejectMusician(row.id).then(res => {
        if (res.code === 1) {
          this.notify('已拒绝音乐人申请', 'success')
          this.getUserList()
        }
      })
    },
    handleMusicianDisable(row) {
      HttpManager.updateUserInfo({ id: row.id, status: 'disabled' }).then(res => {
        if (res.code === 1) {
          this.notify('已禁用该音乐人账号', 'success')
          this.getUserList()
        }
      })
    },
    getStatusType(status) {
      const types = {
        'pending': 'warning',
        'active': 'success',
        'disabled': 'danger'
      }
      return types[status] || 'info'
    },
    getStatusText(status) {
      const texts = {
        'pending': '待审核',
        'active': '正常',
        'disabled': '已禁用'
      }
      return texts[status] || status
    },
    showAddAdDialog() {
      this.adDialogVisible = true
      this.adForm = {
        title: '',
        type: '',
        dateRange: [],
        content: '',
        image: ''
      }
      this.adImageList = []
    },
    handleEditAd(row) {
      this.adDialogVisible = true
      this.adForm = {
        ...row,
        dateRange: [row.startDate, row.endDate]
      }
    },
    handleDeleteAd(row) {
      this.$confirm('确认删除该广告？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        HttpManager.deleteAd(row.id).then(res => {
          if (res.code === 1) {
            this.notify('删除成功', 'success')
            this.getAdList()
          }
        })
      })
    },
    handleAdImageSuccess(response, file, fileList) {
      this.adForm.image = response.data
      this.notify('图片上传成功', 'success')
    },
    handleAdImageError() {
      this.notify('图片上传失败', 'error')
    },
    beforeAdImageUpload(file) {
      const isImage = file.type.startsWith('image/')
      if (!isImage) {
        this.notify('只能上传图片文件', 'error')
      }
      return isImage
    },
    submitAd() {
      this.$refs.adForm.validate((valid) => {
        if (valid) {
          const params = {
            title: this.adForm.title,
            type: this.adForm.type,
            startDate: this.adForm.dateRange[0],
            endDate: this.adForm.dateRange[1],
            content: this.adForm.content,
            image: this.adForm.image
          }
          
          if (this.adForm.id) {
            HttpManager.updateAd(this.adForm.id, params).then(res => {
              if (res.code === 1) {
                this.notify('更新成功', 'success')
                this.adDialogVisible = false
                this.getAdList()
              }
            })
          } else {
            HttpManager.addAd(params).then(res => {
              if (res.code === 1) {
                this.notify('添加成功', 'success')
                this.adDialogVisible = false
                this.getAdList()
              }
            })
          }
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.admin {
  padding: 20px;
  .admin-container {
    max-width: 1200px;
    margin: 0 auto;
    
    h2 {
      text-align: center;
      margin-bottom: 30px;
      color: #409EFF;
    }
    
    .user-manage-card,
    .ad-manage-card {
      margin-bottom: 20px;
    }
  }
}
</style> 