export interface AppLinksVo {
  /**
   * 数据id
   */
  id: number

  /**
   * 链接
   */
  link: string

  /**
   * 分类
   */
  classify: number

  /**
   * 备注
   */
  remark?: string
  /**
   * 创建用户
   */
  createUser?: string
  /**
   * 创建时间
   */
  createTime?: string
}

export interface AppLinksForm {
  /**
   * 数据id
   */
  id?: number

  /**
   * 链接
   */
  link: string

  /**
   * 分类
   */
  classify?: number

  /**
   * 备注
   */
  remark?: string
}

export interface AppLinksQuery extends PageQuery {
  /**
   * 链接
   */
  link?: string

  /**
   * 分类
   */
  classify?: number

  /**
   * 备注
   */
  remark?: string
  /**
   * 上传人
   */
  uploader?: string
}
