<template>
  <div class="back">
    <div id="bg"></div>
    <div class="back2">
      <div id="box1">
        <el-form :model="updateBookForm" class="demo-form-inline">
          <el-form-item label="书名">
            <el-input v-model="updateBookForm.bname" placeholder="请输入书名"></el-input>
          </el-form-item>
          <el-form-item label="作者">
            <el-input v-model="updateBookForm.author" placeholder="请输入作者"></el-input>
          </el-form-item>
          <el-form-item label="内容">
            <el-input
              v-model="updateBookForm.content"
              placeholder="请输入内容"
              type="textarea"
              :autosize="{ minRows: 4, maxRows: 8}">
            </el-input>
          </el-form-item>
          <div v-if="fileIsNull">
            <el-form-item label="源文件">
              <el-input
                v-model="updateBookForm.path"
                :placeholder="this.updateBookForm.path"
                :readonly="true">
              </el-input>
              <el-button type="button" v-on:click="clickLoad()">选择上传文件</el-button>
              <input type="file" id="files" ref="refFile" style="display: none"  v-on:change="fileLoad()">
            </el-form-item>
          </div>
          <div v-else>
            <div>{{this.updateBookForm.path}}</div>
            <el-button type="button" v-on:click="downloadFile()">下载文件</el-button>
            <el-button type="button" v-on:click="deleteFile()">删除文件</el-button>
          </div>
        </el-form>
        <br>
        <el-button type="button" id="fileCommit" v-on:click="updateBook()">提交</el-button>
        <el-button type="button" v-on:click="back()">返回</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'BookDetail',
  data () {
    return {
      fileIsNull: false,
      formData: new FormData(),
      fileData: null,
      updateBookForm: {
        bid: '',
        bname: '',
        author: '',
        content: '',
        path: ''
      }
    }
  },
  methods: {
    getBook () {
      if (this.$route.params.bid === undefined) {
        console.log(this.$route.params.bid)
        this.fileIsNull = true
      } else {
        this.updateBookForm.bid = this.$route.params.bid
        let dataTemp = {
          bid: this.updateBookForm.bid
        }
        let urlTemp = 'http://localhost:8081/books/' + this.updateBookForm.bid
        axios({
          method: 'get',
          url: urlTemp,
          headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('jwtToken')
          },
          data: dataTemp
        }).then(res => {
          console.log(res)
          this.updateBookForm = res.data
          console.log(res.data.path)
          if (this.updateBookForm.path === null) {
            this.fileIsNull = true
          }
        })
      }
    },
    updateBook () {
      let _this = this
      console.log(this.updateBookForm.path)
      if (this.$route.params.bid === undefined) {
        axios({
          method: 'post',
          url: 'http://localhost:8081/books',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('jwtToken')
          },
          data: _this.updateBookForm
        }).then(res => {
          console.log(res)
          this.updateBookForm.bid = res.data
          if (this.updateBookForm.path !== null && this.fileIsNull === true) {
            this.uploadFile()
          }
          this.$router.push('/Home')
        })
      } else {
        axios({
          method: 'put',
          url: 'http://localhost:8081/book/' + this.updateBookForm.bid,
          headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('jwtToken')
          },
          data: _this.updateBookForm
        }).then(res => {
          console.log(res)
          if (res.data === 'Success') {
            if (this.updateBookForm.path !== null && this.fileIsNull === true) {
              this.uploadFile()
            }
            this.$router.push('/Home')
          }
        })
      }
    },
    back () {
      this.$router.push('/Home')
    },
    fileLoad () {
      let _this = this
      const selectedFile = this.$refs.refFile.files[0]
      _this.formData.append('file', selectedFile)
      console.log(this.$refs.refFile.files)
      _this.updateBookForm.path = this.$refs.refFile.files[0].name
      let reader = new FileReader()
      reader.readAsText(selectedFile)
      reader.onload = function () {
        _this.fileData = this.result
        console.log(_this.fileData)
      }
    },
    clickLoad () {
      let _this = this
      _this.clear_data()
      this.$refs.refFile.dispatchEvent(new MouseEvent('click'))
    },
    clear_data () {
      let _this = this
      _this.fileData = null
    },
    uploadFile () {
      axios({
        method: 'post',
        url: 'http://localhost:8081/book/' + this.updateBookForm.bid + '/upload',
        data: this.formData,
        headers: {
          'Content-Type': 'multipart/form-data',
          'Authorization': localStorage.getItem('jwtToken')
        }
      }).then(res => {
        console.log(res)
      })
    },
    downloadFile () {
      let a = document.createElement('a')
      a.href = 'http://localhost:8081/' + this.updateBookForm.path
      a.click()
    },
    deleteFile () {
      axios({
        method: 'delete',
        url: 'http://localhost:8081/book/' + this.updateBookForm.bid + '/delete',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem('jwtToken')
        }
      }).then(res => {
        console.log(res)
        this.$router.go(0)
      })
    }
  },
  mounted () {
    this.getBook()
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
  width: 70%;
  height: auto;
  margin: 0 auto;
}
#leftbox{
  width: 50%;
  height: auto;
  background: white;
  padding: 5px 15px 10px 15px;
  border-radius:40px 0 0 40px;
}
</style>
