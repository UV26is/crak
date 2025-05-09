//左侧导航栏
const navMsg = [
    {name: '首页',path: '/'},
    {name: '歌单',path: '/song-list'},
    {name: '歌手',path: '/singer'},
    {name: '我的音乐',path: '/my-music'}
]

//右侧导航栏
const loginMsg = [
    {name: '登录',path: '/login-in'},
    {name: '注册',path: '/sign-up'}
]

//用户下拉菜单
const menuList = [
    {name: '音乐人申请',path: '/musician-apply', show: 'Listener'},  // 仅听众可见
    {name: '音乐管理',path: '/music-manage', show: 'Musician'},      // 仅音乐人可见
    {name: '系统管理',path: '/admin', show: 'Admin'},                // 仅管理员可见
    {name: '设置',path: '/setting'},
    {name: '退出',path: 0}
]

export {
    navMsg,
    loginMsg,
    menuList
}