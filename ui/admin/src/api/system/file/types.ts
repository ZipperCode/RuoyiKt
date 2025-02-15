export interface FileRecordVo extends BaseEntity {
  id: string | number
  configId: string | number
  path: string
  url: string
  mimeType: string
  service?: string
  hash?: string
  fileSize: number
  createBy?: string
  createTime?: string
}

export interface FileRecordQuery extends PageQuery {
  service?: string
  path?: string
  type?: string
  hash?: string
  createTime?: Date[]
}
