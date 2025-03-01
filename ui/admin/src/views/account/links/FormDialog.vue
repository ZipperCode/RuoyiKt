<script setup lang="ts">
import {AppLinksForm} from '@/api/account/links/types';
import {FormRules} from "element-plus";
import {add, getInfo, update} from "@/api/account/links";


const {proxy} = getCurrentInstance() as ComponentInternalInstance
const {app_links_classify} = toRefs<any>(proxy?.useDict("app_links_classify"))
defineOptions({name: "AppLinksFormDialog"})
let classifyValue = -1
const dialogVisible = ref(false)
const dialogTitle = ref("")
const formLoading = ref(false)
const formType = ref("")
const initForm: AppLinksForm = {
  id: 0,
  classify: undefined,
  link: "",
  remark: ""
}

const formRef = ref()
const formData = ref<AppLinksForm>({...initForm})
const formRules = reactive<FormRules>({
  link: [
    {required: true, message: "链接不能为空", trigger: "blur"},
  ],
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
    const data = formData.value as unknown as AppLinksForm
    data.classify = classifyValue;
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
</script>
<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle">
    <el-form ref="formRef" v-loading="formLoading" :model="formData" :rules="formRules" label-width="80px">
      <el-form-item label="链接" prop="link">
        <el-input v-model="formData.link" placeholder="请输入链接" clearable/>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="formData.remark" placeholder="请输入备注" clearable/>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="submitForm" :disabled="formLoading">确 定</el-button>
      <el-button @click="resetForm">重 置</el-button>
    </template>
  </Dialog>
</template>
<style scoped lang="scss">

</style>
