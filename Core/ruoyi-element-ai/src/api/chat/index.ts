import type {ChatMessageVo, CompletionsDTO, GetChatListParams, SendDTO} from './types';
import { get, post } from '@/utils/request';

// 发送消息
export const completions = (data: CompletionsDTO) => post<null>('/chat/completions', data);

export const send = (data: SendDTO) => post<null>('/chat/send', data);

// 新增对应会话聊天记录
export function addChat(data: ChatMessageVo) {
  return post('/system/message', data).json();
}

// 获取当前会话的聊天记录
export function getChatList(params: GetChatListParams) {
  return get<ChatMessageVo[]>('/system/message/list', params).json();
}
