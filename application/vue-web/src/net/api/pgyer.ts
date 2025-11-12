import request from '@/net/utils/request.js'
import type { ApiResponse } from '@/types/api'

export interface APPInfo{
  info:Config,
  version:PgyerBuild
}

export interface Config {
  config_id: number
  name: string
  apikey: string
  appkey: string
  buildBuildVersion?: string
  downloadURL?: string
  buildVersion?: string
  buildVersionNo?: string
  buildUpdateDescription?: string
  buildFileKey?: string
}
//获取所有配置
export const getConfigsApi = ():Promise<ApiResponse<Config[]>> => {
  return request({
    url: `/pgyer/configs`,
    method: 'get'
  })
}

export const addConfigApi = (data:Config):Promise<ApiResponse<number>> => {
  return request({
    url: `/pgyer/addConfig`,
    method: 'post',
    data
  })
}

export const deleteConfigApi = (config_id:number):Promise<ApiResponse<unknown>> => {
  return request({
    url: `/pgyer/deleteConfig/${config_id}`,
    method: 'delete',
  })
}

interface PgyerRequestParams {
  _api_key: string
  appKey: string
}

interface PgyerBuild {
  buildBuildVersion: string
  downloadURL: string
  buildVersion: string
  buildVersionNo: string
  buildUpdateDescription: string
  buildFileKey: string
}

export const checkVersionApi = (data:PgyerRequestParams):Promise<ApiResponse<PgyerBuild>> => {
  return request({
    url: `https://api.pgyer.com/apiv2/app/check`,
    method: 'post',
    data: new URLSearchParams(Object.entries(data)).toString(),
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  })
}

export const getPgyerAppDetailApi = ():Promise<ApiResponse<unknown>> => {
  return request({
    url: `https://api.pgyer.com/apiv2/app/updateApp`,
    method: 'post',
  })
}

// 下载apk
export const downApkApi = (url: string,fileName:string) => {
  return request({
    url :url,
    method:`get`,
    responseType: "blob",
  })
}

