import request from '@/utils/request';
import {AxiosPromise} from 'axios';

import {FileConfigForm, FileConfigQuery, FileConfigVo} from "@/api/system/fileConfig/types";

// 分页查询列表
export function getFileConfigPageList(query: FileConfigQuery): AxiosPromise<FileConfigVo[]> {
  return request({
    url: '/store/file/config/list',
    method: 'get',
    params: query
  });
}

// 获取单条数据
export function getFileConfig(configId: string | number): AxiosPromise<FileConfigVo> {
  return request({
    url: `/store/file/config/${configId}`,
    method: 'get',
  });
}

export function addFileConfig(data: FileConfigForm) {
  return request({
    url: '/store/file/config',
    method: 'post',
    data: data
  });
}

export function updateFileConfig(data: FileConfigForm) {
  return request({
    url: '/store/file/config',
    method: 'put',
    data: data
  });
}

export function delFileConfig(configId: string | number | Array<string | number>) {
  return request({
    url: `/store/file/config/${configId}`,
    method: 'delete'
  });
}

export function updateMaster(configId: string | number) {
  return request({
    url: `/store/file/config/updateMaster`,
    method: 'put',
    params: {configId}
  });
}
