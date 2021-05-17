<template>
  <div class = "back">
    <div id="bg"></div>
    <div class="box2">
      <el-table
        @cell-dblclick="tableDbEdit"
        :data="tableData"
        style="width: 100%">
        <el-table-column
          type="index"
          label="序号"
          width="50">
        </el-table-column>
        <el-table-column
          prop="email"
          label="邮箱">
        </el-table-column>
        <el-table-column
          prop="password"
          label="密码">
        </el-table-column>
        <el-table-column
          prop="role"
          label="角色">
        </el-table-column>
        <el-table-column label="操作" width="160">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" @click="handleClick1(scope.row)">修改</el-button>
            <el-button size="mini" type="danger" @click="handleClick2(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div>
      <br/>
      <el-button type="button" @click="handleClick3()">刷新</el-button>
    </div>
    <div v-if="isAdmin">
      <br/>
      <el-button type="button" @click="handleClick4()">添加</el-button>
    </div>
    <div>
      <br/>
      <el-button type="button" @click="handleClick5()">退出</el-button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Admin',
  data () {
    return {
      isAdmin: false,
      tableData: [{
        email: '??',
        password: '??',
        role: '??'
      }]
    }
  },
  methods: {
    handleClick1 (row) {
      console.log(row)
      let _this = this
      let index = -1
      for (let j = 0; j < _this.tableData.length; j++) {
        // console.log(_this.tableData[j])
        if (_this.tableData[j].email === row.email) {
          index = j
        }
      }
      axios({
        method: 'put',
        url: 'http://localhost:8081/admin/user/' + row.email,
        headers: {
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem('jwtToken')
        },
        data: _this.tableData[index]
      }).then(res => {
        console.log(res)
        if (res.data.status === '401') {
          console.log(res.data.status)
          this.updateUser()
        }
        // this.$router.go(0)
        this.$message.success('修改成功')
      })
    },
    handleClick2 (row) {
      console.log(row)
      axios({
        method: 'delete',
        url: 'http://localhost:8081/admin/user/' + row.email,
        headers: {
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem('jwtToken')
        }
      }).then(res => {
        console.log(res)
        this.$router.go(0)
        if (res.data.status === '401') {
          console.log(res.data.status)
          this.deleteUser()
        }
      })
    },
    handleClick3 () {
      this.$router.go(0)
    },
    handleClick4 () {
      this.$prompt('请输入邮箱', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
        inputErrorMessage: '邮箱格式不正确'
      }).then(({ value }) => {
        let _data = {
          email: value,
          password: '123456',
          role: 'ROLE_USER'
        }
        this.tableData.push(_data)
        axios({
          method: 'post',
          url: 'http://localhost:8081/admin/users',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('jwtToken')
          },
          data: _data
        }).then(res => {
          console.log(res)
          this.$router.go(0)
        })
        this.$message({
          type: 'success',
          message: '你的邮箱是: ' + value
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        })
      })
    },
    handleClick5 () {
      localStorage.clear()
      this.$router.push('/Login')
    },
    tableDbEdit (row, column, cell, event) {
      console.log(row, column, cell, event)
      let _this = this
      if (column.label === '密码' || (column.label === '角色' && this.isAdmin)) {
        event.target.innerHTML = ''
        let cellInput = document.createElement('input')
        cellInput.value = ''
        cellInput.setAttribute('type', 'text')
        cellInput.style.width = '80%'
        cellInput.style.padding = '0px'
        cellInput.style.border = '1px solid #288EFE'
        cellInput.style.borderRadius = '3px'
        cell.appendChild(cellInput)
        // 当鼠标失去焦点时，判断输入是否为空，为空的话可以删除input
        cellInput.onblur = function () {
          if (cellInput.value === '' && column.label === '字段名') {
            this.$message.warning('字段名不能为空')
            cellInput.style.border = '1px solid #FD1B1E'
          } else {
            cell.removeChild(cellInput)
            event.target.innerHTML = cellInput.value
            if (column.label === '密码') {
              for (let j = 0; j < _this.tableData.length; j++) {
                // console.log(_this.tableData[j])
                if (_this.tableData[j].email === row.email) {
                  _this.tableData[j].password = cellInput.value
                }
              }
            } else {
              for (let j = 0; j < _this.tableData.length; j++) {
                // console.log(_this.tableData[j])
                if (_this.tableData[j].email === row.email) {
                  _this.tableData[j].role = cellInput.value
                }
              }
            }
          }
        }
      }
    },
    getUserList () {
      axios({
        method: 'get',
        url: 'http://localhost:8081/admin/users',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem('jwtToken')
        }
      }).then(res => {
        console.log(res)
        if (res.data.status === '401') {
          console.log(res.data.status)
          this.getUser()
        } else {
          this.isAdmin = true
          this.tableData = res.data
        }
      })
    },
    getUser () {
      axios({
        method: 'get',
        url: 'http://localhost:8081/user/user',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem('jwtToken')
        }
      }).then(res => {
        console.log(res)
        if (res.data.status === '401') {
          console.log(res.data.status)
          this.$router.push('/Login')
        } else {
          this.tableData = []
          this.tableData.push(res.data)
        }
      })
    },
    updateUser () {
      console.log(this.tableData[0])
      axios({
        method: 'put',
        url: 'http://localhost:8081/user/user',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem('jwtToken')
        },
        data: this.tableData[0]
      }).then(res => {
        console.log(res)
      })
    },
    deleteUser () {
      axios({
        method: 'delete',
        url: 'http://localhost:8081/user/user',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem('jwtToken')
        }
      }).then(res => {
        console.log(res)
      })
    }
  },
  mounted () {
    console.log(localStorage.getItem('jwtToken'))
    this.getUserList()
  }
}
</script>

<style scoped>

</style>
