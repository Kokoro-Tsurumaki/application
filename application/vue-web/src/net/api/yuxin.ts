// src/net/api/yuxin.ts
import request from '@/net/utils/request.js'
import type { ApiResponse } from '@/types/api'

export interface User {
  user_id: number
  name: string
  username: string
  password: String
}

//获取所有用户
export const getUsersApi = ():Promise<ApiResponse<User[]>> => {
  return request({
    url: `/yuxin/users`,
    method: 'get'
  })
}

export const addUserApi = (data:User):Promise<ApiResponse<number>> => {
  return request({
    url: `/yuxin/addUser`,
    method: 'post',
    data
  })
}

export const deleteUserApi = (user_id:number):Promise<ApiResponse<unknown>> => {
  return request({
    url: `/yuxin/deleteUser/${user_id}`,
    method: 'delete',
  })
}

