<template>
  <div class="the-header">
    <div class="header-logo" @click="goHome">
        <svg class="icon">
            <use xlink:href = "#icon-erji"></use>
        </svg>
        <span>music</span>
    </div>
    <ul class="navbar">
        <li :class="{active: item.name == activeName}" v-for="item in navMsg" :key="item.path" @click="goPage(item.path,item.name)">
            {{item.name}}
        </li>
        <li>
            <div class="header-search">
                <input type="text" placeholder="搜索音乐" @keyup.enter="goSearch()" v-model="keywords">
                <div class="search-btn" @click="goSearch()">
                    <svg class="icon">
                        <use xlink:href = "#icon-sousuo"></use>
                    </svg>
                </div>
            </div>
        </li>
        <li v-show="!loginIn" :class="{active: item.name == activeName}" v-for="item in loginMsg" :key="item.path" @click="goPage(item.path,item.name)">
            {{item.name}}
        </li>
    </ul>
    <div class="header-right" v-show="loginIn">
        <div id='user'>
            <img :src='attachImageUrl(avator)'>
        </div>
        <ul class="menu">
            <li v-for="(item,index) in filteredMenuList" :key="index" @click="goMenuList(item.path)">{{item.name}}</li> 
        </ul>
    </div>
  </div>
</template>

<script>
import {mapGetters} from 'vuex';
import {navMsg,loginMsg,menuList} from '../assets/data/header';
import { resourcePath } from '../config/index';

export default {
  name: 'the-header',
  data() {
      return {
          navMsg: [],    //左侧导航栏
          keywords: '',  //搜索关键字       
          loginMsg: [],  //右侧导航栏
          menuList: [], //用户下拉菜单
      }
  },
  computed:{
      ...mapGetters([
          'activeName',
          'loginIn',
          'avator',
          'userType'  // 从vuex中获取用户类型
      ]),
      filteredMenuList() {
          return this.menuList.filter(item => {
              // 如果没有show属性，则所有用户都可见
              if (!item.show) return true;
              // 否则只显示对应用户类型的菜单项
              return item.show === this.userType;
          });
      }
  },
  created() {
      this.navMsg = navMsg;
      this.loginMsg = loginMsg;
      this.menuList = menuList;
  },
  mounted(){
      document.querySelector('#user').addEventListener('click',function(e){
          document.querySelector('.menu').classList.add("show");
          e.stopPropagation()           //关键在于阻止冒泡
      },false);
      document.querySelector('.menu').addEventListener('click',function(e){
          e.stopPropagation()           //点击菜单内部时，阻止时间冒泡，这样，点击内部时，菜单不会关闭
      },false);
      document.addEventListener('click',function(){
        document.querySelector('.menu').classList.remove('show');
      },false);
  },
  
  methods: {
       //提示信息
        notify(title,type) {
            this.$notify({
                title: title,
                type: type
            })
        },
      goHome() {
          this.$router.push({path: "/"});
      },
      goPage(path,name) {
          if(!this.loginIn && path=='/my-music'){
              this.notify('请先登录','warning');
          }else{
            this.$store.commit('setActiveName',name);
            this.$router.push({path: path});
          }          
      },
      goSearch(){
          this.$router.push({path:'/search',query:{keywords: this.keywords}})
      },
      attachImageUrl(srcUrl) {
        console.log('原始图片URL:', srcUrl);
        
        if (!srcUrl) {
          return require('../assets/img/user.jpg');
        }
        
        if (srcUrl.startsWith('http')) {
          return srcUrl;
        }
        
        // 获取基础URL
        const baseUrl = this.$store.state.configure.HOST;
        
        // 构建完整的资源URL
        let fullUrl;
        if (srcUrl.startsWith('/avatorImages/')) {
          fullUrl = `${baseUrl}${srcUrl}`;
        } else if (srcUrl.startsWith('/img/')) {
          fullUrl = `${baseUrl}${srcUrl}`;
        } else if (srcUrl.startsWith('/song/')) {
          fullUrl = `${baseUrl}${srcUrl}`;
        } else {
          // 默认处理头像
          fullUrl = `${baseUrl}/avatorImages/${srcUrl}`;
        }
        
        console.log('完整资源URL:', fullUrl);
        return fullUrl;
      },
      goMenuList(path){
          if(path == 0){
              this.$store.commit('setLoginIn',false);
              this.$store.commit('setIsActive',false);
              this.$router.go(0);
          }else{
              this.$router.push({path:path});
          }
      }
  }

}
</script>

<style lang="scss" scoped>
@import '../assets/css/the-header.scss';
</style>
