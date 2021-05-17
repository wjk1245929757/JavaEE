import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld'
import Home from '../components/Home'
import Register from '../components/Register'
import Login from '../components/Login'
import BookDetail from '../components/BookDetail'
import Admin from '../components/Admin'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/Home',
      name: 'Home',
      component: Home
    },
    {
      path: '/Login',
      name: 'Login',
      component: Login
    },
    {
      path: '/Register',
      name: 'Register',
      component: Register
    },
    {
      path: '/book/:bid',
      name: 'BookDetail',
      component: BookDetail
    },
    {
      path: '/BookDetail',
      name: 'BookDetail',
      component: BookDetail
    },
    {
      path: '/Admin',
      name: 'Admin',
      component: Admin
    }
  ]
})
