export interface AppAccountVo extends BaseEntity {
  /**
   * 数据id
   */
  id: number
  /**
   * 账号
   */
  account?: string

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
   * 链接备注
   */
  linkRemark?: string
  /**
   * 截图
   */
  screenshot?: string
  /**
   * 绑定用户状态
   */
  record?: AppAccountRecord
}

export interface AppAccountForm {
  /**
   * 数据id
   */
  id?: number
  /**
   * 账号
   */
  account?: string
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
   * 链接备注
   */
  linkRemark?: string
  /**
   * 截图
   */
  screenshot?: string

}

export interface AppAccountQuery extends PageQuery {
  /**
   * 账号
   */
  account?: string

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


export interface AppAccountUploadVo {
  successCount: number
  failureCount: number
}

export interface AppAccountRecord {
  bindUser?: string
  createUser?: string
}

export interface AppAccountRecordQuery extends PageQuery {
  /**
   * 数据id
   */
  id?: number
  classify?: number
  account?: string
  bindUser?: string
  used?: number
  createUser?: string
}

export interface AppAccountRecordVo extends BaseEntity {
  /**
   * 数据id
   */
  id: number
  classify: number
  /**
   * id
   */
  accountId: number
  /**
   * 账号
   */
  account: string
  /**
   * 绑定用户
   */
  bindUserId: number
  /**
   * 绑定用户
   */
  bindUser?: string
  /**
   * 使用状态
   */
  used: number
  /**
   * 创建用户
   */
  createUser: string
}
