<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle">
    <el-form
      ref="formRef"
      v-loading="formLoading"
      :model="formData"
      :rules="formRules"
      label-width="120px"
    >
      <el-form-item label="配置名" prop="name">
        <el-input v-model="formData.name" placeholder="请输入配置名"/>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="formData.remark" placeholder="请输入备注"/>
      </el-form-item>
      <el-form-item label="存储器" prop="storage">
        <el-select
          v-model="formData.storage"
          :disabled="formData.id !== undefined"
          placeholder="请选择存储器"
        >
          <el-option
            v-for="dict in store_file_storage_type"
            :key="dict.value"
            :label="dict.label"
            :value="parseInt(dict.value)"
          />
        </el-select>
      </el-form-item>
      <!-- DB -->
      <!-- Local / FTP / SFTP -->
      <el-form-item
        v-if="formData.storage >= 10 && formData.storage <= 12"
        label="基础路径"
        prop="config.basePath"
      >
        <el-input v-model="formData.config.basePath" placeholder="请输入基础路径"/>
      </el-form-item>
      <!-- S3 -->
      <el-form-item v-if="formData.storage === 20" label="节点地址" prop="config.endpoint">
        <el-input v-model="formData.config.endpoint" placeholder="请输入节点地址"/>
      </el-form-item>
      <el-form-item v-if="formData.storage === 20" label="存储 bucket" prop="config.bucket">
        <el-input v-model="formData.config.bucket" placeholder="请输入 bucket"/>
      </el-form-item>
      <el-form-item v-if="formData.storage === 20" label="accessKey" prop="config.accessKey">
        <el-input v-model="formData.config.accessKey" placeholder="请输入 accessKey"/>
      </el-form-item>
      <el-form-item v-if="formData.storage === 20" label="accessSecret" prop="config.accessSecret">
        <el-input v-model="formData.config.accessSecret" placeholder="请输入 accessSecret"/>
      </el-form-item>
      <!-- 通用 -->
      <el-form-item v-if="formData.storage === 20" label="自定义域名">
        <!-- 无需参数校验，所以去掉 prop -->
        <el-input v-model="formData.config.domain" placeholder="请输入自定义域名"/>
      </el-form-item>
      <el-form-item v-else-if="formData.storage" label="自定义域名" prop="config.domain">
        <el-input v-model="formData.config.domain" placeholder="请输入自定义域名，如：http://localhost:8080"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button :disabled="formLoading" type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script lang="ts" setup>
import {Dialog} from "@/components/Dialog";
import {FileConfigForm, FileConfigType, FileConfigVo,} from "@/api/system/fileConfig/types";
import {FormRules} from 'element-plus'
import {addFileConfig, getFileConfig, updateFileConfig} from "@/api/system/fileConfig";

const {proxy} = getCurrentInstance() as ComponentInternalInstance;
const {store_file_storage_type} = toRefs<any>(proxy?.useDict("store_file_storage_type"));
defineOptions({name: 'FileConfigFormDialog'})

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const initSubmitForm = {
  id: undefined,
  name: '',
  storage: store_file_storage_type.value[0]?.label,
  master: false,
  remark: '',
  config: {}
}

const formData = ref<FileConfigVo>({...initSubmitForm})
const formRules = reactive<FormRules>({
  name: [{required: true, message: '配置名不能为空', trigger: 'blur'}],
  storage: [{required: true, message: '存储器不能为空', trigger: 'change'}],
  config: {
    basePath: [{required: true, message: '基础路径不能为空', trigger: 'blur'}],
    endpoint: [{required: true, message: '节点地址不能为空', trigger: 'blur'}],
    bucket: [{required: true, message: '存储 bucket 不能为空', trigger: 'blur'}],
    accessKey: [{required: true, message: 'accessKey 不能为空', trigger: 'blur'}],
    accessSecret: [{required: true, message: 'accessSecret 不能为空', trigger: 'blur'}],
    domain: [{required: true, message: '自定义域名不能为空', trigger: 'blur'}]
  } as FormRules
})
const formRef = ref() // 表单 Ref

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = type
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      const resp = await getFileConfig(id)
      console.log(resp)
      const storageTypes = store_file_storage_type.value as unknown as DictDataOption[]
      const label = storageTypes.find(item => item.label === resp.data.storage)?.label
      formData.value = {
        ...resp.data,
        storage: label || resp.data.storage
      }
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({open}) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as FileConfigForm
    if (formType.value === 'create') {
      await addFileConfig(data)
      proxy?.$modal?.msgSuccess("创建成功")
    } else {
      await updateFileConfig(data)
      proxy?.$modal?.msgSuccess("更新成功")
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {...initSubmitForm}
  formRef.value?.resetFields()
}
</script>
