import {AppLinksForm, AppLinksQuery, AppLinksVo} from "@/api/account/links/types";
import {AxiosPromise} from "axios";
import request, {download} from "@/utils/request";
import {AppAccountQuery} from "@/api/account/ids/types";

/**
 * 分页列表
 * @param query
 */
export const pageList = (query?: AppLinksQuery): AxiosPromise<AppLinksVo[]> => {
  return request({
    url: "/app/links/list",
    method: "get",
    params: query
  })
}
/**
 * 查询详细
 * @param id
 */
export const getInfo = (id: number): AxiosPromise<AppLinksVo> => {
  return request({
    url: "/app/links/" + id,
    method: "get"
  })
}
/**
 * 新增
 * @param data
 */
export const add = (data: AppLinksForm) => {
  return request({
    url: "/app/links",
    method: "post",
    data: data
  })
}

export const update = (data: AppLinksForm) => {
  return request({
    url: "/app/links",
    method: "put",
    data: data
  })
}

export const del = (id: number) => {
  return request({
    url: "/app/links/" + id,
    method: "delete"
  })
}

export const delIds = (ids: Array<number>) => {
  return request({
    url: "/app/links/" + ids,
    method: "delete",
  })
}

export const exportData = (query?: AppLinksQuery) => {
  return download("app/link/export", query, `链接数据_${new Date().getTime()}.xlsx`)
}
