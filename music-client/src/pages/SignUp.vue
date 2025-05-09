<template>
  <div class="signup-container">
    <el-card class="signup-card">
      <div slot="header">
        <span>注册账号</span>
      </div>
      <el-form :model="registerForm" :rules="rules" ref="registerForm" label-width="100px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="registerForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="registerForm.password"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input type="password" v-model="registerForm.confirmPassword"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="registerForm.email"></el-input>
        </el-form-item>
        <el-form-item label="用户类型" prop="userType">
          <el-select v-model="registerForm.userType" placeholder="请选择用户类型">
            <el-option label="听众" value="Listener"></el-option>
            <el-option label="音乐人" value="Musician"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-select v-model="registerForm.gender" placeholder="请选择性别">
            <el-option label="男" value="Male"></el-option>
            <el-option label="女" value="Female"></el-option>
            <el-option label="其他" value="Other"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出生日期" prop="dateOfBirth">
          <el-date-picker
            v-model="registerForm.dateOfBirth"
            type="date"
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="国籍" prop="nationality">
          <el-input v-model="registerForm.nationality"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleRegister">注册</el-button>
          <el-button @click="$router.push('/login')">返回登录</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
import { mixin } from '../mixins'
import { SignUp } from '../api/index'

export default {
  name: 'SignUp',
  mixins: [mixin],
  data () {
    return {
      registerForm: {
        username: '',
        password: '',
        confirmPassword: '',
        email: '',
        userType: 'Listener',
        gender: '',
        dateOfBirth: '',
        nationality: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          {
            validator: (rule, value, callback) => {
              if (value !== this.registerForm.password) {
                callback(new Error('两次输入密码不一致'))
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ],
        email: [
          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
        ],
        userType: [
          { required: true, message: '请选择用户类型', trigger: 'change' }
        ],
        gender: [
          { required: true, message: '请选择性别', trigger: 'change' }
        ],
        dateOfBirth: [
          { required: true, message: '请选择出生日期', trigger: 'change' }
        ],
        nationality: [
          { required: true, message: '请输入国籍', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    ...mapMutations([
      'setLoginIn',
      'setIsActive',
      'setUserType'
    ]),
    handleRegister () {
      this.$refs.registerForm.validate(async (valid) => {
        if (valid) {
          try {
            let params = new URLSearchParams();
            params.append('username', this.registerForm.username);
            params.append('password', this.registerForm.password);
            params.append('email', this.registerForm.email);
            params.append('sex', this.registerForm.gender === 'Male' ? '1' : '0');
            params.append('phoneNum', '');
            // 格式化日期为 yyyy-MM-dd
            const date = new Date(this.registerForm.dateOfBirth);
            const formattedDate = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;
            params.append('birth', formattedDate);
            params.append('location', this.registerForm.nationality);
            params.append('introduction', '');
            params.append('avator', '');
            
            console.log('提交的注册参数:', Object.fromEntries(params));
            
            const result = await SignUp(params)
            if (result.code === 1) {
              this.$message.success('注册成功')
              this.setLoginIn(true)
              this.setIsActive(true)
              this.setUserType(this.registerForm.userType)
              this.$router.push('/login')
            } else {
              this.$message.error(result.message || '注册失败')
            }
          } catch (error) {
            console.error('注册错误:', error)
            this.$message.error('注册失败，请稍后重试')
          }
        }
      })
    }
  }
}
</script>

<style scoped>
.signup-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f5f5;
}
.signup-card {
  width: 500px;
}
</style>