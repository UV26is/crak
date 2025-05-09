import request from '../utils/request'

export function addUserRole(data) {
  return request({
    url: '/userRole/add',
    method: 'post',
    data
  })
}

export function updateUserRole(data) {
  return request({
    url: '/userRole/update',
    method: 'post',
    data
  })
}

export function deleteUserRole(id) {
  return request({
    url: '/userRole/delete',
    method: 'get',
    params: { id }
  })
}

export function getUserRoleDetail(id) {
  return request({
    url: '/userRole/detail',
    method: 'get',
    params: { id }
  })
}

export function getAllUserRoles() {
  return request({
    url: '/userRole/allUserRole',
    method: 'get'
  })
}

export function getUserRolesByUserId(userId) {
  return request({
    url: '/userRole/userRoleOfUserId',
    method: 'get',
    params: { userId }
  })
} 