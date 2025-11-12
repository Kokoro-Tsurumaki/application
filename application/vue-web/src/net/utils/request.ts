// src/net/utils/request.ts
import axios, { type AxiosError, type AxiosResponse } from 'axios'

const service = axios.create({
  baseURL: import.meta.env.VITE_API_BASEURL,
  timeout: 10000,
  validateStatus: (status) => status < 500,
})

// 请求拦截器
service.interceptors.request.use((config) => {
  config.headers['Authorization'] = 'Bearer ' + localStorage.getItem('token')
  return config
})

// 响应拦截器
service.interceptors.response.use(
  (response) => response.data,
  (error) => {
    console.error('API Error:', error)
    return Promise.reject(error)
  },
)

export default service
