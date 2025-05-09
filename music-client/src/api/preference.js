import request from '../utils/request'

// 获取用户偏好设置
export function getUserPreference() {
  return request({
    url: '/preference/get',
    method: 'get'
  })
}

// 更新用户偏好设置
export function updateUserPreference(data) {
  return request({
    url: '/preference/update',
    method: 'put',
    data
  })
}

// 获取推荐歌曲
export function getRecommendedSongs() {
  return request({
    url: '/preference/recommend',
    method: 'get'
  })
}

// 获取用户历史记录
export function getUserHistory() {
  return request({
    url: '/preference/history',
    method: 'get'
  })
}

// 清除用户历史记录
export function clearUserHistory() {
  return request({
    url: '/preference/history/clear',
    method: 'delete'
  })
} 