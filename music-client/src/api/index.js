import Axios from "axios";
import {get,post} from "./http";
import axios from 'axios'
import { baseURL } from '../config/index'

//============歌手相关================
//查询歌手
export const getAllSinger =() => get(`singer/allSinger`);
//根据性别查询歌手
export const getSingerOfSex = (sex) => get(`singer/singerOfSex?sex=${sex}`);

//============歌曲相关================
//根据歌手id查询歌曲
export const songOfSingerId =(id) => get(`song/singer/detail?singerId=${id}`);
//根据歌曲id查询歌曲对象
export const songOfSongId =(id) => get(`song/detail?songId=${id}`);
//根据歌手名字模糊查询歌曲
export const likeSongOfName =(keywords) => get(`song/likeSongOfName?songName=${keywords}`);

//============歌单相关================
//查询歌单
export const getAllSongList =() => get(`songList/allSongList`);
//返回标题包含文字的歌单列表
export const getSongListOfLikeTitle = (keywords) => get(`songList/likeTitle?title=${keywords}`);
//根据风格模糊查询歌单列表
export const getSongListOfLikeStyle = (style) => get(`songList/likeStyle?style=${style}`);


//============歌单的歌曲相关============
//根据歌单id查询歌曲列表
export const listSongDetail = (songListId) => get(`listSong/detail?songListId=${songListId}`);

//============用户相关================
//查询用户
export const getAllConsumer =() => get(`consumer/allConsumer`);

//注册
export const SignUp = (params) => {
  return post('/consumer/add', params);
}

//登录
export const loginIn =(params) => post(`/consumer/login`,params);

//根据用户id查询该用户的详细信息
export const getUserOfId =(id) => get(`/consumer/selectByPrimaryKey?id=${id}`);
//更新用户信息
export const updateUserMsg =(params) => post(`/consumer/update`,params);

//下载音乐
export const download = (url)  => Axios({
    method: 'get',
    url: url,
    responseType: 'blob'
});

//===========评价======================
//提交评分
export const setRank =(params) => post(`/rank/add`,params);
//获取指定歌单的平均分
export const getRankOfSongListId = (songListId) => get(`/rank?songListId=${songListId}`);

//===========评论======================
//提交评论
export const setComment =(params) => post(`/comment/add`,params);
//点赞
export const setLike =(params) => post(`/comment/like`,params);
//返回当前歌单或歌曲的评论列表
export const getAllComment = (type,id) => {
    if(type == 0){              //歌曲
        return get(`/comment/commentOfSongId?songId=${id}`);
    }else{                      //歌单
        return get(`/comment/commentOfSongListId?songListId=${id}`);
    }
}

//===============收藏===================
//新增收藏
export const setCollect =(params) => post(`/collect/add`,params);
//指定用户的收藏列表
export const getCollectOfUserId = (userId) => get(`/collect/collectOfUserId?userId=${userId}`);

// 创建axios实例
const instance = axios.create({
  baseURL: baseURL,
  timeout: 5000
})

// 请求拦截器
instance.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
instance.interceptors.response.use(
  response => {
    return response.data
  },
  error => {
    return Promise.reject(error)
  }
)

export const HttpManager = {
  // 用户相关接口
  register: (params) => instance.post('/user/register', params),
  login: (params) => instance.post('/user/login', params),
  getUserInfo: () => instance.get('/user/info'),
  updateUserInfo: (params) => instance.put('/user/info', params),
  updatePassword: (params) => instance.put('/user/password', params),
  uploadAvatar: (formData) => instance.post('/user/avatar', formData),

  // 音乐人相关接口
  applyMusician: (params) => instance.post('/musician/apply', params),
  getMusicianInfo: () => instance.get('/musician/info'),
  updateMusicianInfo: (params) => instance.put('/musician/info', params),
  getMusicianList: (params) => instance.get('/musician/list', { params }),
  approveMusician: (id) => instance.put(`/musician/${id}/approve`),
  rejectMusician: (id) => instance.put(`/musician/${id}/reject`),

  // 歌曲相关接口
  uploadSong: (formData) => instance.post('/song/upload', formData),
  getSongList: (params) => instance.get('/song/list', { params }),
  getSongDetail: (id) => instance.get(`/song/${id}`),
  updateSong: (id, params) => instance.put(`/song/${id}`, params),
  deleteSong: (id) => instance.delete(`/song/${id}`),
  addSongCreator: (id, params) => instance.post(`/song/${id}/creator`, params),
  removeSongCreator: (id, creatorId) => instance.delete(`/song/${id}/creator/${creatorId}`),

  // 用户偏好相关接口
  getUserPreference: () => instance.get('/preference'),
  updateUserPreference: (params) => instance.put('/preference', params),
  getRecommendedSongs: () => instance.get('/preference/recommended'),
  getUserHistory: () => instance.get('/preference/history'),
  clearUserHistory: () => instance.delete('/preference/history'),

  // 广告相关接口
  getAdList: (params) => instance.get('/ad/list', { params }),
  addAd: (params) => instance.post('/ad', params),
  updateAd: (id, params) => instance.put(`/ad/${id}`, params),
  deleteAd: (id) => instance.delete(`/ad/${id}`),
  uploadAdImage: (formData) => instance.post('/ad/image', formData)
}