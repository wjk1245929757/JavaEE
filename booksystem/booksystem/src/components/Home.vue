<template>
  <div class = "back">
    <div id="bg"></div>
    <div class="box2">
      <el-table
        :data="tableData"
        style="width: 100%">
        <el-table-column
          type="index"
          width="50">
        </el-table-column>
        <el-table-column
          prop="bname"
          label="书名"
          width="180">
        </el-table-column>
        <el-table-column
          prop="author"
          label="作者"
          width="180">
        </el-table-column>
        <el-table-column
          prop="content"
          label="内容简介">
        </el-table-column>
        <el-table-column label="操作" width="160">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" @click="handleClick1(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleClick2(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <br/>
    <el-button type="button" @click="handleClick3()">添加</el-button>
    <el-button type="button" @click="handleClick4()">个人中心</el-button>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Home',
  data () {
    return {
      tableData: [{
        bid: '??',
        bname: '??',
        author: '??',
        content: '??'
      }]
    }
  },
  methods: {
    handleClick1 (row) {
      console.log(row)
      this.$router.push({
        path: `/book/${row.bid}`
      })
    },
    handleClick2 (row) {
      console.log(row)
      axios({
        method: 'delete',
        url: 'http://localhost:8081/book/' + row.bid,
        headers: {
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem('jwtToken')
        }
      }).then(res => {
        console.log(res)
        this.$router.go(0)
      })
    },
    handleClick3 () {
      this.$router.push('/BookDetail')
    },
    handleClick4 () {
      this.$router.push('/Admin')
    },
    getBookList () {
      axios({
        method: 'get',
        url: 'http://localhost:8081/books',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem('jwtToken')
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data
      })
    }
  },
  mounted () {
    console.log(localStorage.getItem('jwtToken'))
    this.getBookList()
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
.box2{
  width: 80%;
  margin:0 auto;
  display: flex;
  justify-content: center;
}
</style>
