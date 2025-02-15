import {FileRecordQuery, FileRecordVo} from "@/api/system/file/types";
import {AxiosPromise} from 'axios';
import request, {upload} from '@/utils/request';

export const getFileRecordPageList = (param: FileRecordQuery): AxiosPromise<FileRecordVo[]> => {
  return request({
    url: '/store/file/list',
    method: 'get',
    params: param
  })
}

export const listByIds = (ids: Array<number>): AxiosPromise<FileRecordVo> => {
  return request({
    url: '/store/file/listByIds',
    method: 'get',
    params: {ids: ids}
  })
}

export const deleteFile = (ids: Array<number | string>) => {
  return request({
    url: '/store/file/delete?ids=' + ids,
    method: 'delete',
  })
}

// 上传文件
export const updateFile = (data: { file: File }) => {
  const formData = new FormData()
  formData.append('file', data.file)
  return upload({url: '/store/file/upload', data: formData})
}

