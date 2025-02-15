import * as FileApi from '@/api/system/file'
import CryptoJS from 'crypto-js'
import {UploadRawFile, UploadRequestOptions} from 'element-plus/es/components/upload/src/upload'
import axios from 'axios'

export const useUpload = () => {
  // 后端上传地址
  const uploadUrl = import.meta.env.VITE_UPLOAD_URL
  // 重写ElUpload上传方法
  const httpRequest = async (options: UploadRequestOptions) => {
    // 重写 el-upload httpRequest 文件上传成功会走成功的钩子，失败走失败的钩子
    return new Promise((resolve, reject) => {
      console.log("httpRequest  upload = options = ", options)
      FileApi.updateFile({file: options.file})
        .then((res) => {
          if (res.code === 200) {
            resolve(res)
          } else {
            reject(res)
          }
        })
        .catch((res) => {
          reject(res)
        })
    })
  }
  return {
    uploadUrl,
    httpRequest
  }
}

/**
 * 生成文件名称（使用算法SHA256）
 * @param file 要上传的文件
 */
async function generateFileName(file: File) {
  // 读取文件内容
  const data = await file.arrayBuffer()
  const wordArray = CryptoJS.lib.WordArray.create(data)
  // 计算SHA256
  const sha256 = CryptoJS.SHA256(wordArray).toString()
  // 拼接后缀
  const ext = file.name.substring(file.name.lastIndexOf('.'))
  return `${sha256}${ext}`
}

/**
 * 上传类型
 */
enum UPLOAD_TYPE {
  // 客户端直接上传（只支持S3服务）
  CLIENT = 'client',
  // 客户端发送到后端上传
  SERVER = 'server'
}
