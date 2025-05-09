<template>
  <div class="user-role-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>用户角色管理</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="handleAdd">添加角色</el-button>
      </div>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="userId" label="用户ID" width="100"></el-table-column>
        <el-table-column prop="roleName" label="角色名称"></el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
        <el-table-column prop="updateTime" label="更新时间" width="180"></el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 添加/编辑对话框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible">
      <el-form :model="form" :rules="rules" ref="form" label-width="100px">
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="form.userId"></el-input>
        </el-form-item>
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="form.roleName"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getAllUserRoles, addUserRole, updateUserRole, deleteUserRole } from '@/api/userRole'

export default {
  name: 'UserRole',
  data() {
    return {
      tableData: [],
      dialogVisible: false,
      dialogTitle: '',
      form: {
        id: '',
        userId: '',
        roleName: ''
      },
      rules: {
        userId: [
          { required: true, message: '请输入用户ID', trigger: 'blur' }
        ],
        roleName: [
          { required: true, message: '请输入角色名称', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      getAllUserRoles().then(response => {
        this.tableData = response
      })
    },
    handleAdd() {
      this.dialogTitle = '添加角色'
      this.form = {
        id: '',
        userId: '',
        roleName: ''
      }
      this.dialogVisible = true
    },
    handleEdit(row) {
      this.dialogTitle = '编辑角色'
      this.form = Object.assign({}, row)
      this.dialogVisible = true
    },
    handleDelete(row) {
      this.$confirm('确认删除该角色?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteUserRole(row.id).then(response => {
          if (response) {
            this.$message.success('删除成功')
            this.getList()
          }
        })
      })
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          if (this.form.id) {
            updateUserRole(this.form).then(response => {
              if (response.code === 1) {
                this.$message.success('修改成功')
                this.dialogVisible = false
                this.getList()
              }
            })
          } else {
            addUserRole(this.form).then(response => {
              if (response.code === 1) {
                this.$message.success('添加成功')
                this.dialogVisible = false
                this.getList()
              }
            })
          }
        }
      })
    }
  }
}
</script>

<style scoped>
.user-role-container {
  padding: 20px;
}
</style> 