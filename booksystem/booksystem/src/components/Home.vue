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
          width="100">
        </el-table-column>
        <el-table-column
          prop="author"
          label="作者"
          width="180">
        </el-table-column>
        <el-table-column
          prop="price"
          label="价格"
          width="100">
        </el-table-column>
        <el-table-column
          prop="content"
          label="内容简介">
        </el-table-column>
        <el-table-column label="操作" width="160">
          <template slot-scope="scope">
            <div v-if="isAdmin">
              <el-button size="mini" type="primary" @click="handleClick1(scope.row)">编辑</el-button>
              <el-button size="mini" type="danger" @click="handleClick2(scope.row)">删除</el-button>
            </div>
            <div v-else>
              <el-button size="mini" type="primary" @click="handleClick1(scope.row)">查看</el-button>
              <el-button size="mini" type="danger" @click="handleClick5(scope.row)">购买</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <br/>
    <el-button type="button" @click="handleClick4()">个人中心</el-button>
    <div v-if="isAdmin">
      <br/>
      <el-button type="button" @click="handleClick3()">添加</el-button>
    </div>
    <div v-else>
      <br>
      <div>已选书籍：{{this.temp_book}}</div>
      <div>价格：{{this.total_price}}</div>
      <br>
      <el-button type="button" @click="clearCart()">清空购物车</el-button>
      <el-button type="button" @click="buy()">确认购买</el-button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'Home',
  data () {
    return {
      temp_book: '',
      // choose_book: '',
      total_price: 0,
      isAdmin: false,
      tableData: [{
        bid: '??',
        bname: '??',
        author: '??',
        price: '??',
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
    handleClick5 (row) {
      // this.temp_book = localStorage.clear()
      if (this.temp_book === null || this.temp_book === undefined || this.temp_book === '') {
        this.temp_book = row.bname
      } else {
        this.temp_book += ',' + row.bname
      }
      this.total_price += row.price
      console.log(localStorage.getItem('buy'))
      this.$message.success(row.bname + '成功添加至购物车')
    },
    clearCart () {
      this.temp_book = ''
      this.total_price = 0
    },
    buy () {
      const weiXinUrl = require('../assets/wechat.jpg')
      const aLiUrl = require('../assets/zhifubao.jpg')
      this.$alert('<strong><img src=' + weiXinUrl + ' width=150px height=200px><img src=' + aLiUrl + ' width=150px height=200px></strong>', '请扫描下方二维码进行支付：', {
        dangerouslyUseHTMLString: true
      }).then(action => {
        this.$message.success('购买成功')
        this.clearCart()
      })
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
    },
    getRole () {
      axios({
        method: 'get',
        url: 'http://localhost:8081/getrole',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': localStorage.getItem('jwtToken')
        }
      }).then(res => {
        console.log(res)
        if (res.data === 'ROLE_ADMIN') {
          this.isAdmin = true
        }
        console.log('admin:' + this.isAdmin)
      })
    }
  },
  mounted () {
    console.log(localStorage.getItem('jwtToken'))
    this.getRole()
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
