<script setup lang="ts">
import {useMessage} from "@/hooks/useMessage";
import {FormRules} from "element-plus";
import {updateStatus} from "@/api/account/ids";

defineOptions({name: 'AppAccountBatchStatusDialog'})
const message = useMessage() // 消息弹窗
const {proxy} = getCurrentInstance() as ComponentInternalInstance
const {app_account_status} = toRefs<any>(
  proxy?.useDict('app_account_status')
)

const dialogVisible = ref(false) // 弹窗的是否展示

const idsRef = ref<number[]>([])

/** 打开弹窗 */
const open = async (ids: number[]) => {
  dialogVisible.value = true
  idsRef.value = ids
}
defineExpose({open}) // 提供 open 方法，用于打开弹窗
interface Form {
  status: number | undefined
}

const formLoading = ref(false) // 表单的加载中
const formRef = ref()
const formData = reactive<Form>({
  status: undefined
})
const formRules = reactive<FormRules>({
  status: [
    {required: true, message: "需要选择一个状态", trigger: "blur"},
  ]
})

const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调

const submit = async () => {
  if (!formRef) {
    return
  }
  try {
    const valid = await formRef.value.validate()
    if (!valid) {
      return
    }
    if (!idsRef.value || idsRef.value.length == 0) {
      message.error("请至少选择一条数据")
      return
    }
  } catch (e) {
    return
  }

  formLoading.value = true
  try {
    const response = await updateStatus(idsRef.value, formData.status!!)
    if (response.code == 200) {
      message.success("修改成功")
      dialogVisible.value = false
      emit('success')
    } else {
      message.error(response.msg)
    }
  } finally {
    formLoading.value = false
  }
}

</script>

<template>
  <Dialog v-model="dialogVisible" title="批量修改状态">
    <el-form ref="formRef" :inline="true" v-loading="formLoading" :model="formData" :rules="formRules" label-width="70px">
      <el-form-item label="状态" prop="status">
        <el-select v-model="formData.status" placeholder="请选择状态" clearable style="width: 120px">
          <el-option v-for="item in app_account_status" :key="item.value" :label="item.label" :value="parseInt(item.value)"/>
        </el-select>
      </el-form-item>

    </el-form>
    <template #footer>
      <el-button :disabled="formLoading" type="primary" @click="submit">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>

