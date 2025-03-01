export interface AppQrVo extends BaseEntity {
  /**
   * 数据id
   */
  id: number
  /**
   * 链接内容
   */
  qrContent?: string
  /**
   * 二维码
   */
  qrPath?: string

  /**
   * 地区
   */
  country?: string

  /**
   * 工作
   */
  work?: string

  /**
   * 收入
   */
  income?: number

  /**
   * 年龄
   */
  age?: number

  /**
   * 账号类型[AccountType]
   */
  accountType?: number

  /**
   * 数据分类[DataClassify]
   */
  classify?: number

  /**
   * 数据状态[DataStatus]
   */
  status?: number

  /**
   * 备注
   */
  remark?: string

  /**
   * 截图
   */
  screenshot?: string
}

export interface AppQrForm {
  /**
   * 数据id
   */
  id?: number
  /**
   * 链接内容
   */
  qrContent?: string
  /**
   * 二维码
   */
  qrPath?: string
  /**
   * 地区
   */
  country?: string
  /**
   * 工作
   */
  work?: string
  /**
   * 收入
   */
  income?: number
  /**
   * 年龄
   */
  age?: number
  /**
   * 账号类型[AccountType]
   */
  accountType?: number
  /**
   * 数据分类[DataClassify]
   */
  classify?: number
  /**
   * 数据状态[DataStatus]
   */
  status?: number
  /**
   * 备注
   */
  remark?: string
  /**
   * 截图
   */
  screenshot?: string

}

export interface AppQrQuery extends PageQuery {
  /**
   * 链接内容
   */
  qrContent?: string

  /**
   * 地区
   */
  country?: string

  /**
   * 账号类型[AccountType]
   */
  accountType?: number
  /**
   * 数据分类[DataClassify]
   */
  classify?: number
  /**
   * 数据状态[DataStatus]
   */
  status?: number
  /**
   * 备注
   */
  remark?: string

  /**
   * 上传人
   */
  uploader?: string
}
