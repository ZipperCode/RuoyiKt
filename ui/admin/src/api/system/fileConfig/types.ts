export interface FileConfigVo extends BaseEntity {
  id: number | undefined
  name: string
  storage: string | number
  master: boolean
  remark: string
  config: Record<string, any>
}

export interface LocalFileConfig{
  // 根路径
  basePath: string
  // 自定义域名
  domain: string
}

export interface DataBaseFileConfig{
  // 自定义域名
  domain: string
}

export interface OssFileConfig{
  // 节点地址
  endpoint: string
  // 自定义域名
  domain: string
  // 存储空间
  bucket: string
  // key
  accessKey: string
  // secret
  accessSecret: string
  // 区域
  region: string
  // 前缀
  prefix: string
  // 是否https
  useHttps: string
  // 桶类型
  accessPolicy: string
  // 服务提供商
  service?: string
}
export interface FileConfigType extends DataBaseFileConfig, OssFileConfig, LocalFileConfig{

}

export interface FileConfigQuery extends PageQuery {
  name?: string
  storage?: number
}

export interface FileConfigForm {
  id: number | string | undefined
  name: string
  storage: number
  config: Record<string, any>
  remark: string
}
