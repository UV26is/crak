import request from '../utils/request'

// 获取广告列表
export function getAdList() {
  return request({
    url: '/ad/list',
    method: 'get'
  })
}

// 添加广告
export function addAd(data) {
  return request({
    url: '/ad/add',
    method: 'post',
    data
  })
}

// 更新广告
export function updateAd(data) {
  return request({
    url: '/ad/update',
    method: 'put',
    data
  })
}

// 删除广告
export function deleteAd(id) {
  return request({
    url: `/ad/delete/${id}`,
    method: 'delete'
  })
}

// 获取广告详情
export function getAdDetail(id) {
  return request({
    url: `/ad/detail/${id}`,
    method: 'get'
  })
} 