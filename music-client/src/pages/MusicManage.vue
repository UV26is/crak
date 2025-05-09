<template>
  <div class="music-manage">
    <div class="manage-container">
      <h2>音乐管理</h2>
      
      <!-- 上传新歌曲 -->
      <el-card class="upload-card">
        <div slot="header">
          <span>上传新歌曲</span>
        </div>
        <el-form :model="uploadForm" ref="uploadForm" label-width="100px" :rules="rules">
          <el-form-item label="歌曲名称" prop="name">
            <el-input v-model="uploadForm.name" placeholder="请输入歌曲名称"></el-input>
          </el-form-item>
          <el-form-item label="创作角色" prop="creativeRoles">
            <el-checkbox-group v-model="uploadForm.creativeRoles">
              <el-checkbox label="composer">作曲</el-checkbox>
              <el-checkbox label="lyricist">作词</el-checkbox>
              <el-checkbox label="vocalist">演唱</el-checkbox>
              <el-checkbox label="arranger">编曲</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item label="音乐风格" prop="genre">
            <el-select v-model="uploadForm.genre" placeholder="请选择音乐风格">
              <el-option label="流行" value="pop"></el-option>
              <el-option label="摇滚" value="rock"></el-option>
              <el-option label="民谣" value="folk"></el-option>
              <el-option label="电子" value="electronic"></el-option>
              <el-option label="古典" value="classical"></el-option>
              <el-option label="爵士" value="jazz"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="歌词" prop="lyric">
            <el-input type="textarea" v-model="uploadForm.lyric" rows="4" placeholder="请输入歌词"></el-input>
          </el-form-item>
          <el-form-item label="歌曲文件" prop="file">
            <el-upload
              class="upload-demo"
              action="/api/upload"
              :on-success="handleUploadSuccess"
              :on-error="handleUploadError"
              :before-upload="beforeUpload"
              :limit="1"
              :file-list="fileList">
              <el-button size="small" type="primary">点击上传</el-button>
              <div slot="tip" class="el-upload__tip">请上传mp3格式的音频文件</div>
            </el-upload>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitUpload">提交</el-button>
            <el-button @click="resetForm">重置</el-button>
          </el-form-item>
        </el-form>
      </el-card>

      <!-- 已上传歌曲列表 -->
      <el-card class="song-list-card">
        <div slot="header">
          <span>已上传歌曲</span>
        </div>
        <el-table :data="songList" style="width: 100%">
          <el-table-column prop="name" label="歌曲名称"></el-table-column>
          <el-table-column prop="genre" label="风格"></el-table-column>
          <el-table-column prop="playCount" label="播放次数"></el-table-column>
          <el-table-column prop="downloadCount" label="下载次数"></el-table-column>
          <el-table-column prop="commentCount" label="评论数"></el-table-column>
          <el-table-column label="操作" width="200">
            <template slot-scope="scope">
              <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
              <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </div>
  </div>
</template>

<script>
import { mixin } from '../mixins'
import { HttpManager } from '../api/index'

export default {
  name: 'music-manage',
  mixins: [mixin],
  data() {
    return {
      uploadForm: {
        name: '',
        creativeRoles: [],
        genre: '',
        lyric: '',
        file: ''
      },
      fileList: [],
      songList: [],
      rules: {
        name: [
          { required: true, message: '请输入歌曲名称', trigger: 'blur' }
        ],
        creativeRoles: [
          { type: 'array', required: true, message: '请至少选择一个创作角色', trigger: 'change' }
        ],
        genre: [
          { required: true, message: '请选择音乐风格', trigger: 'change' }
        ],
        lyric: [
          { required: true, message: '请输入歌词', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getSongList()
  },
  methods: {
    getSongList() {
      HttpManager.getSongList()
        .then(res => {
          if (res.code === 1) {
            this.songList = res.data
          }
        })
    },
    submitUpload() {
      this.$refs.uploadForm.validate((valid) => {
        if (valid) {
          const formData = new FormData()
          formData.append('name', this.uploadForm.name)
          formData.append('creativeRoles', JSON.stringify(this.uploadForm.creativeRoles))
          formData.append('genre', this.uploadForm.genre)
          formData.append('lyric', this.uploadForm.lyric)
          formData.append('file', this.uploadForm.file)

          HttpManager.uploadSong(formData)
            .then(res => {
              if (res.code === 1) {
                this.notify('上传成功', 'success')
                this.resetForm()
                this.getSongList()
              } else {
                this.notify('上传失败', 'error')
              }
            })
        }
      })
    },
    resetForm() {
      this.$refs.uploadForm.resetFields()
      this.fileList = []
    },
    handleUploadSuccess(response, file, fileList) {
      this.uploadForm.file = response.data
      this.notify('文件上传成功', 'success')
    },
    handleUploadError() {
      this.notify('文件上传失败', 'error')
    },
    beforeUpload(file) {
      const isMP3 = file.type === 'audio/mpeg'
      if (!isMP3) {
        this.notify('只能上传MP3格式的音频文件', 'error')
      }
      return isMP3
    },
    handleEdit(row) {
      // 实现编辑功能
    },
    handleDelete(row) {
      this.$confirm('确认删除该歌曲？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        HttpManager.deleteSong(row.id)
          .then(res => {
            if (res.code === 1) {
              this.notify('删除成功', 'success')
              this.getSongList()
            } else {
              this.notify('删除失败', 'error')
            }
          })
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.music-manage {
  padding: 20px;
  .manage-container {
    max-width: 1200px;
    margin: 0 auto;
    
    h2 {
      text-align: center;
      margin-bottom: 30px;
      color: #409EFF;
    }
    
    .upload-card {
      margin-bottom: 20px;
    }
    
    .song-list-card {
      margin-top: 20px;
    }
  }
}
</style> 