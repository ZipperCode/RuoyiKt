import {AxiosPromise} from "axios";
import request, {download, upload} from "@/utils/request";
import {
  AppAccountForm,
  AppAccountQuery,
  AppAccountRecordQuery,
  AppAccountRecordVo,
  AppAccountUploadVo,
  AppAccountVo
} from "@/api/account/ids/types";

export const add = (data: AppAccountForm): AxiosPromise<AppAccountVo> => {
  return request({
    url: "/app/account",
    method: "post",
    data: data
  })
}

export const update = (data: AppAccountForm): AxiosPromise<AppAccountVo> => {
  return request({
    url: "/app/account",
    method: "put",
    data: data
  })
}

export const delIds = (ids: Array<number>) => {
  return request({
    url: "/app/account/" + ids,
    method: "delete",
  })
}

export const getInfo = (id: number): AxiosPromise<AppAccountVo> => {
  return request({
    url: "/app/account/" + id,
    method: "get",
  })
}

export const pageList = (query?: AppAccountQuery): AxiosPromise<AppAccountVo[]> => {
  return request({
    url: "/app/account/list",
    method: "get",
    params: query
  })
}


export const uploadTxt = (file: File, classify: number): AxiosPromise<AppAccountUploadVo> => {
  const formData = new FormData()
  formData.append('file', file)
  formData.append('classify', classify + "")
  return upload({
    url: "/app/account/upload",
    data: formData
  })
}

export const unbind = (id: number): AxiosPromise<Boolean> => {
  return request({
    url: "/app/account/unbind/" + id,
    method: "delete"
  })
}

export const recordPageList = (query?: AppAccountRecordQuery): AxiosPromise<AppAccountRecordVo[]> => {
  return request({
    url: "/app/account/recordList",
    method: "get",
    params: query
  })
}

export const dispatch = (classify: number): AxiosPromise<number> => {
  return request({
    url: "/app/account/dispatch",
    method: "post",
    data: {
      classify: classify
    }
  })
}

export const updateStatus = (ids: number[], status: number): AxiosPromise<number> => {
  return request({
    url: "/app/account/updateStatus",
    method: "put",
    data: {
      ids: ids,
      status: status
    }
  })
}

export const exportData = (query?: AppAccountQuery)=> {
  return download("app/account/export", query, `账号数据_${new Date().getTime()}.xlsx`)
}
