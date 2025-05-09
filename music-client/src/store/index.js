import Vue from 'vue'
import Vuex from 'vuex'
import configure from './configure'
import user from './user'
import song from './song'

Vue.use(Vuex)

const store = new Vuex.Store({
    modules: {
        configure,
        user,
        song
    },
    state: {
        loginIn: false, // 是否登录
        isActive: false, // 是否显示歌曲信息
        activeName: '首页', // 当前选中的导航
        showAside: false, // 是否显示侧边栏
        avator: '', // 用户头像
        userType: '', // 用户类型：Listener/Musician/Admin
    },
    getters: {
        loginIn: state => state.loginIn,
        isActive: state => state.isActive,
        activeName: state => state.activeName,
        showAside: state => state.showAside,
        avator: state => state.avator,
        userType: state => state.userType,
    },
    mutations: {
        setLoginIn: (state, loginIn) => {
            state.loginIn = loginIn
        },
        setIsActive: (state, isActive) => {
            state.isActive = isActive
        },
        setActiveName: (state, activeName) => {
            state.activeName = activeName
        },
        setShowAside: (state, showAside) => {
            state.showAside = showAside
        },
        setAvator: (state, avator) => {
            state.avator = avator
        },
        setUserType: (state, userType) => {
            state.userType = userType
        },
    }
})

export default store