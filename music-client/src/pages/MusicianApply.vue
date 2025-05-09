<template>
  <div class="musician-apply">
    <div class="apply-container">
      <h2>音乐人申请</h2>
      <el-form :model="applyForm" ref="applyForm" label-width="100px" :rules="rules">
        <el-form-item label="艺名" prop="artistName">
          <el-input v-model="applyForm.artistName" placeholder="请输入您的艺名"></el-input>
        </el-form-item>
        <el-form-item label="创作角色" prop="creativeRoles">
          <el-checkbox-group v-model="applyForm.creativeRoles">
            <el-checkbox label="composer">作曲</el-checkbox>
            <el-checkbox label="lyricist">作词</el-checkbox>
            <el-checkbox label="vocalist">演唱</el-checkbox>
            <el-checkbox label="arranger">编曲</el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="音乐风格" prop="genres">
          <el-select v-model="applyForm.genres" multiple placeholder="请选择音乐风格">
            <el-option label="流行" value="pop"></el-option>
            <el-option label="摇滚" value="rock"></el-option>
            <el-option label="民谣" value="folk"></el-option>
            <el-option label="电子" value="electronic"></el-option>
            <el-option label="古典" value="classical"></el-option>
            <el-option label="爵士" value="jazz"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="个人简介" prop="introduction">
          <el-input type="textarea" v-model="applyForm.introduction" rows="4" placeholder="请介绍您的音乐经历和创作理念"></el-input>
        </el-form-item>
        <el-form-item label="作品集" prop="portfolio">
          <el-upload
            class="upload-demo"
            action="/api/upload"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :before-remove="beforeRemove"
            multiple
            :limit="3"
            :file-list="fileList">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">请上传您的音乐作品（支持mp3格式）</div>
          </el-upload>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitApply">提交申请</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { mixin } from '../mixins'
import { HttpManager } from '../api/index'

export default {
  name: 'musician-apply',
  mixins: [mixin],
  data() {
    return {
      applyForm: {
        artistName: '',
        creativeRoles: [],
        genres: [],
        introduction: '',
        portfolio: []
      },
      fileList: [],
      rules: {
        artistName: [
          { required: true, message: '请输入艺名', trigger: 'blur' },
          { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
        ],
        creativeRoles: [
          { type: 'array', required: true, message: '请至少选择一个创作角色', trigger: 'change' }
        ],
        genres: [
          { type: 'array', required: true, message: '请至少选择一个音乐风格', trigger: 'change' }
        ],
        introduction: [
          { required: true, message: '请输入个人简介', trigger: 'blur' },
          { min: 50, max: 500, message: '长度在 50 到 500 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitApply() {
      this.$refs.applyForm.validate((valid) => {
        if (valid) {
          const params = {
            artistName: this.applyForm.artistName,
            creativeRoles: this.applyForm.creativeRoles,
            genres: this.applyForm.genres,
            introduction: this.applyForm.introduction,
            portfolio: this.fileList.map(file => file.url)
          }

          HttpManager.applyMusician(params)
            .then(res => {
              if (res.code === 1) {
                this.notify('申请提交成功，请等待审核', 'success')
                setTimeout(() => {
                  this.$router.push('/')
                }, 2000)
              } else {
                this.notify('申请提交失败', 'error')
              }
            })
            .catch(err => {
              this.notify('申请提交失败', 'error')
            })
        }
      })
    },
    resetForm() {
      this.$refs.applyForm.resetFields()
      this.fileList = []
    },
    handleRemove(file, fileList) {
      this.fileList = fileList
    },
    handlePreview(file) {
      console.log(file)
    },
    beforeRemove(file) {
      return this.$confirm(`确定移除 ${file.name}？`)
    }
  }
}
</script>

<style lang="scss" scoped>
.musician-apply {
  padding: 20px;
  .apply-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
    
    h2 {
      text-align: center;
      margin-bottom: 30px;
      color: #409EFF;
    }
  }
}
</style> 