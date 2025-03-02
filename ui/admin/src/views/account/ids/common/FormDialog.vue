<script setup lang="ts">
import type {UploadProps, UploadUserFile} from 'element-plus'
import {FormRules} from "element-plus";
import {add, getInfo, update} from "@/api/account/ids";
import {AppAccountForm} from "@/api/account/ids/types";
import {Delete, ZoomIn} from '@element-plus/icons-vue'
import {UploadRequestOptions} from "element-plus/es/components/upload/src/upload";
import {useMessage} from "@/hooks/useMessage";
import * as FileApi from '@/api/system/file'
import {FileRecordVo} from "@/api/system/file/types";

const {proxy} = getCurrentInstance() as ComponentInternalInstance
const {app_account_country, app_account_status} = toRefs<any>(
  proxy?.useDict('app_account_country', 'app_account_status')
)
const message = useMessage();
const baseUrl = import.meta.env.VITE_APP_BASE_API;
defineOptions({name: "AppAccountFormDialog"});
type Form = AppAccountForm
const dialogVisible = ref(false)
const dialogTitle = ref("")
const formLoading = ref(false)
let classifyValue = -1
const formType = ref("")
const initForm: Form = {
  id: undefined,
  account: undefined,
  country: undefined,
  work: undefined,
  income: undefined,
  age: undefined,
  accountType: undefined,
  classify: undefined,
  status: undefined,
  screenshot: undefined,
  remark: undefined
}

const formRef = ref()
const formData = ref<Form>({...initForm})
const formRules = reactive<FormRules>({
  account: [
    {required: true, message: "链接不能为空", trigger: "blur"},
  ],
  classify: [
    {required: true, message: "分类不能为空", trigger: "blur"},
  ]
})
/**
 * 打开弹窗
 * @param type 弹窗类型（新增create，修改update）
 * @param classify 分类
 * @param id 数据id
 * @param title 标题
 */
const open = async (type: string, classify: number, id?: number, title?: string) => {
  dialogVisible.value = true;
  formType.value = type;
  classifyValue = classify;
  dialogTitle.value = title ?? formType.value == 'create' ? '新增' : '修改';
  resetForm();
  if (id) {
    formLoading.value = true;
    try {
      const resp = await getInfo(id);
      formData.value = {...resp.data};
      if (resp.data.screenshot) {
        uploadFileUrlPath.push(...resp.data.screenshot.split(","))
        fileList.value = uploadFileUrlPath.map(url => {
          return {
            url: baseUrl + url,
            name: url.substring(url.lastIndexOf('/') + 1)
          } as UploadUserFile
        })
        console.log("uploadFileUrlPath = ", uploadFileUrlPath)
        console.log("fileList = ", fileList.value)
      }
    } finally {
      formLoading.value = false;
    }
  }
}
defineExpose({open})

const emit = defineEmits(['success'])
const submitForm = async () => {
  if (!formRef) {
    return
  }
  const valid = await formRef.value.validate()
  if (!valid) {
    return
  }
  formLoading.value = true
  try {
    const data = formData.value as unknown as Form
    if (uploadFileUrlPath.length != 0) {
      data.screenshot = uploadFileUrlPath.join(",")
    }
    console.log("data = ", data)
    if (formType.value === 'create') {
      const resp = await add(data)
      if (resp.code == 200) {
        proxy?.$modal?.msgSuccess("创建成功")
      } else {
        proxy?.$modal?.msgError(resp.msg)
        return
      }
    } else {
      const resp = await update(data)
      if (resp.code == 200) {
        proxy?.$modal?.msgSuccess("更新成功")
      } else {
        proxy?.$modal?.msgError(resp.msg)
        return
      }
    }
    dialogVisible.value = false
    emit('success')
  } finally {
    formLoading.value = false
  }
}
/**
 * 重置表单
 */
const resetForm = () => {
  formData.value = {...initForm, classify: classifyValue}
  formRef.value?.resetFields()
}
/**
 * 上传截图
 */
const previewDialogVisible = ref(false)
const previewFileUrl = ref()
const uploadRef = ref()
const fileList = ref<UploadUserFile[]>([])
let uploadFileUrlPath: Array<string> = []
const onDialogClose = () => {
  uploadFileUrlPath = []
  fileList.value = []
}
const handlePaste = async (event: ClipboardEvent) => {
  // console.log("handlePaste", event)
  // console.log("event.clipboardData.items", event.clipboardData?.items)
  const items = event.clipboardData?.items
  if (items) {
    const firstItem = items[0]
    if (firstItem.type.indexOf('image') === 0) {
      const blob = firstItem.getAsFile();
      console.log("blob = ", blob)
      if (blob && blob.type) {
        if (!blob.type.startsWith("image")) {
          message.error('粘贴的内容不是图片，请您重新粘贴！')
          return
        }
        try {
          const resp = await handleUploadRequest(blob)
          uploadFileUrlPath.push(resp.url)
          const url = baseUrl + resp.url
          fileList.value = [...fileList.value, {
            url: url,
            name: url.substring(url.lastIndexOf('/') + 1)
          } as UploadUserFile]
        } catch (e) {
          message.error('上传失败，请您重新上传！')
        }
      }
    }
  }
}

const handleRemove = (uploadFile: UploadFile) => {
  // console.log("handleRemove", uploadFile)
  const uploadFiles = unref(fileList)
  const index = uploadFiles.indexOf(uploadFile)
  uploadFiles.splice(index, 1)
  uploadFileUrlPath.splice(index, 1)
}

const handlePictureCardPreview: UploadProps['onPreview'] = (uploadFile) => {
  // console.log("handlePictureCardPreview", uploadFile)
  previewDialogVisible.value = true
  previewFileUrl.value = uploadFile.url
}
const handleExceed = (): void => {
  message.error('最多只能上传5个文件！')
}
const handleFileChange = (file: UploadFile) => {
  console.log("handleFileChange", file)
}
const submitUpload = () => {
  console.log("submitUpload")
  unref(uploadRef)?.submit()
}
const submitUploadSuccess = (resp: FileRecordVo) => {
  // console.log("submitUploadSuccess", resp)
  if (resp.url) {
    uploadFileUrlPath.push(resp.url)
  }
}
const submitFormError = (): void => {
  message.error('上传失败，请您重新上传！')
}
const httpRequest = async (options: UploadRequestOptions) => {
  // console.log("httpRequest", options)
  return await handleUploadRequest(options.file);
}
const handleUploadRequest = async (file: File) => {
  formLoading.value = true;
  try {
    const response = await FileApi.uploadFile({file: file,})
    if (response.code == 200) {
      message.success('上传成功')
      return response.data
    }
  } catch (e) {
    console.log("submitUploadError", e)
  } finally {
    formLoading.value = false;
  }
  throw new Error('上传失败，请您重新上传！')
}
</script>
<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle" @paste="handlePaste" @close="onDialogClose">
    <el-form ref="formRef" :inline="true" v-loading="formLoading" :model="formData" :rules="formRules" label-width="70px">
      <el-form-item label="链接" prop="account" style="width: 100%">
        <el-input v-model="formData.account" placeholder="请输入账号" :disabled="formType==='update' && formData.id !== undefined"
                  clearable/>
      </el-form-item>
      <el-form-item label="年龄" prop="age">
        <el-input-number v-model="formData.age" placeholder="请输入年龄" clearable/>
      </el-form-item>
      <el-form-item label="收入" prop="income">
        <el-input-number v-model="formData.income" placeholder="请输入收入" clearable/>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="formData.status" placeholder="请选择状态" clearable style="width: 120px">
          <el-option v-for="item in app_account_status" :key="item.value" :label="item.label" :value="parseInt(item.value)"/>
        </el-select>
      </el-form-item>
      <el-form-item label="工作" prop="work">
        <el-input v-model="formData.work" placeholder="请输入工作" clearable/>
      </el-form-item>
      <el-form-item label="国家" prop="country">
        <el-select v-model="formData.country" placeholder="请选择城市" clearable>
          <el-option v-for="item in app_account_country" :key="item.value" :label="item.label" :value="item.value"/>
        </el-select>
      </el-form-item>

      <el-form-item label="链接备注" prop="linkRemark" style="width: 100%">
        <el-input v-model="formData.linkRemark" placeholder="请输入链接备注" clearable/>
      </el-form-item>
      <el-form-item label="备注" prop="remark" style="width: 100%">
        <el-input v-model="formData.remark" placeholder="请输入备注" clearable/>
      </el-form-item>
      <el-form-item label="截图" prop="remark" style="width: 100%">
        <!--        <el-input v-model="formData.remark" placeholder="请输入备注" clearable/>-->
        <el-upload
          ref="uploadRef"
          :limit="5"
          v-model:file-list="fileList"
          action=""
          list-type="picture-card"
          :on-change="handleFileChange"
          :on-preview="handlePictureCardPreview"
          :on-remove="handleRemove"
          :on-exceed="handleExceed"
          :on-error="submitFormError"
          :on-success="submitUploadSuccess"
          :http-request="httpRequest"
          accept=".jpg, .png, .webp"
          drag
        >
          <span>点击或者快捷键上传</span>

          <template #file="{ file }">
            <div>
              <img class="el-upload-list__item-thumbnail" :src="file.url" alt=""/>
              <span class="el-upload-list__item-actions">
                <span class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
                  <el-icon><ZoomIn></ZoomIn></el-icon>
                </span>
                <span class="el-upload-list__item-delete" @click="handleRemove(file)">
                  <el-icon><Delete/></el-icon>
                </span>
              </span>
            </div>
          </template>
        </el-upload>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="submitForm" :disabled="formLoading">确 定</el-button>
      <!--      <el-button @click="resetForm">重 置</el-button>-->
    </template>

    <el-dialog v-model="previewDialogVisible">
      <img w-full :src="previewFileUrl" alt="Preview Image"/>
    </el-dialog>
  </Dialog>
</template>
<style scoped lang="scss">

</style>
