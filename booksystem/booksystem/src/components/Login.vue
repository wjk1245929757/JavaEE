<template>
  <div class="back">
    <div id="bg"></div>
    <div class="back2">
      <div class="box1">
        登录
      </div>
      <hr>
      <div class="box3">
        没有账号，点击<router-link id="Register" to="/Register">注册</router-link>
      </div>
      <div class="box2">
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" id="block" :label-position="labelPosition" >
          <el-form-item label="邮箱" prop="account" id="ei">
            <el-input prefix-icon="el-icon-user-solid" v-model="ruleForm.account"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="pass" id="eis">
            <el-input prefix-icon="el-icon-key" type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item id="but">
            <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import qs from 'qs'

export default {
  name: 'Login',
  data () {
    var checkAccount = (rule, value, callback) => {
      const mailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/
      if (!value) {
        return callback(new Error('邮箱不能为空'))
      }
      setTimeout(() => {
        if (mailReg.test(value)) {
          callback()
        } else {
          callback(new Error('请输入正确的邮箱格式'))
        }
      }, 100)
    }
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass')
        }
        /* if(!/^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$)([^\u4e00-\u9fa5\s]){10,16}$/.test(value)) {
          callback(new Error('请输入10-16位英文字母、数字或者符号(除空格)，且字母、数字和标点符号至少包含两种'))

        } */
        callback()
      }
    }

    return {
      labelPosition: 'right',
      ruleForm: {
        pass: '',
        account: ''
      },
      zhud: false,
      loading: false,
      rules: {
        pass: [
          { validator: validatePass, trigger: 'blur' }
        ],
        account: [
          { validator: checkAccount, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // alert('submit!')
          console.log(this.ruleForm.account + this.ruleForm.pass)
          let _data = {
            email: this.ruleForm.account,
            password: this.ruleForm.pass
          }
          axios.post(
            'http://localhost:8081/login',
            qs.stringify(_data)
          ).then(res => {
            console.log(res.data)
            if (res.data.data === 'Success') {
              alert('Success')
              this.$message.success('登录成功')
              localStorage.setItem('jwtToken', res.data.jwtToken)
              this.$router.replace('/Home')
            } else if (res.data.data === '账号不存在') {
              alert('账号不存在')
            } else if (res.data.data === '密码错误') {
              alert('密码错误')
            } else {
              alert('?????')
            }
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    }
  }
}
</script>

<style scoped>

.back{
  width: 100%;
  height: auto;
  /*position: absolute;*/
  /*background: url("../assets/background.jpeg");*/
}
#bg{
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  position: absolute;
  z-index: -1;
  background-size: cover;
  background: url("../assets/background.png");
}
.back2{
  width: 30%;
  height: auto;
  margin: 0 auto;
}
.box1{
  width: 100%;
  height: 30%;
  text-align: center;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 2rem;
  color: black;
}
.box2{
  padding-top: 100px;
  display: flex;
  justify-content: left;
}
.box3{
  padding-right: 1rem;
  text-align: right;
  color: #ececec;
}
</style>
