//获取简历数据
import request from '@/net/utils/request.js'
import type {ApiResponse} from "@/types/api"

/**
* 个人信息类型
*/
export interface PersonalInfo {
  name: string
  title: string
  avatar: string
  contacts: Contact[]
}

/**
* 联系方式
*/
export interface Contact {
  icon: string
  text: string
}

/**
* 技能项
*/
export interface Skill {
  name: string
  level: number
}

/**
* 语言能力（已注释）
*/
// export interface Language {
//   name: string
//   level: number
// }

/**
* 工作经历
*/
export interface Experience {
  period: string
  company: string
  position: string
  items: string[]
}

/**
* 教育背景
*/
export interface Education {
  period: string
  school: string
  major: string
  items: string[]
}

/**
* 项目经验
*/
export interface Project {
  title: string
  description: string
  tags: string[]
}

/**
* 完整简历数据类型
*/
export interface ResumeData {
  personalInfo: PersonalInfo
  skills: Skill[]
  // languages?: Language[]  // 可选字段
  profile: string
  experiences: Experience[]
  education: Education
  projects: Project[]
}

/**
* 用户基础信息（根据需求补充）
*/
export interface User {
  id: number
  name: string
  username: string
  password: string
  resume?: ResumeData // 关联简历数据
}


export const getResumeDataApi = ():Promise<ApiResponse<ResumeData>> => {
  return request({
    url: `/resume/张先杰`,
    method: 'get'
  })
}
