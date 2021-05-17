<template>
  <div class="back">
    <div id="bg"></div>
    <div class="back2">
      <div class="box1">
        注册
      </div>
      <hr>
      <div class="box3">
        没有账号，点击<router-link id="Login" to="/Login">登录</router-link>
      </div>
      <div class="box2">
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm" id="block" :label-position="labelPosition" >
          <el-form-item label="邮箱" prop="mail" id="ei">
            <el-input prefix-icon="el-icon-user-solid"  v-model="ruleForm.mail"></el-input>
          </el-form-item>
          <el-form-item :inline="true" label="验证码" prop="code" id="eissss">
            <el-col :span="11">
              <el-input prefix-icon="el-icon-s-promotion"  v-model.number="ruleForm.code" style="width: 100%;"></el-input>
            </el-col>
            <el-col :span="13">
              <el-button type="info"  @click="getCode()" icon="el-icon-message" circle></el-button>
            </el-col>
          </el-form-item>
          <el-form-item label="密码" prop="pass" id="eis">
            <el-input prefix-icon="el-icon-key" type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="checkPass" id="eiss">
            <el-input prefix-icon="el-icon-key" type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item id="but">
            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'Register',
  data () {
    var checkMail = (rule, value, callback) => {
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
        if (!/^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$)([^\u4e00-\u9fa5\s]){10,16}$/.test(value)) {
          callback(new Error('请输入10-16位英文字母、数字或者符号(除空格)，且字母、数字和标点符号至少包含两种'))
        }
        callback()
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    var checkCode = (rule, value, callback) => {
      let data = sessionStorage.getItem('checkCode')
      console.log(data)
      if (!value) {
        return callback(new Error('验证码不能为空'))
      } else if (data !== value) {
        return callback(new Error('验证码错误'))
      } else {
        callback()
      }
    }
    return {
      labelPosition: 'right',
      ruleForm: {
        pass: '',
        checkPass: '',
        mail: '',
        code: ''
      },
      rules: {
        pass: [
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePass2, trigger: 'blur' }
        ],
        mail: [
          { validator: checkMail, trigger: 'blur' }
        ],
        code: [
          {validator: checkCode, trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    getCode () {
      let _this = this
      if (this.ruleForm.mail === '') {
        _this.$message.error('请先输入邮箱再点击获取验证码')
      } else {
        axios({
          method: 'post',
          url: 'http://localhost:8081/sendCheckCode',
          data: {
            'email': this.ruleForm.mail
          }
        }).then(res => {
          sessionStorage.setItem('checkCode', res.data)
          console.log(res)
        })
      }
    },
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.post('http://localhost:8081/register', {
            email: this.ruleForm.mail,
            password: this.ruleForm.pass
          }).then(res => {
            console.log(res)
            if (res.data === '注册成功') {
              this.$router.back()
            } else if (res.data === '账号已存在') {
              alert('账号已存在')
            } else {

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
  width: 100%;
  height: 100%;
  position: absolute;
  z-index: -1;
  background-size: cover;
  background: url("../assets/background.png");
  top: 0;
  left: 0;
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
  padding-top: 50px;
  display: flex;
  justify-content: left;
}
.box3{
  padding-right: 1rem;
  text-align: right;
  color: #ececec;
}
</style>
