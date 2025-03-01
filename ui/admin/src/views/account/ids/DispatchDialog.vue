<script setup lang="ts">
import {AppAccountForm} from "@/api/account/ids/types";
import {useMessage} from "@/hooks/useMessage";

const {proxy} = getCurrentInstance() as ComponentInternalInstance
const {app_account_classify, app_account_country, app_account_status} = toRefs<any>(
  proxy?.useDict("app_account_classify", 'app_account_country', 'app_account_status')
)
const message = useMessage();
const baseUrl = import.meta.env.VITE_APP_BASE_API;
defineOptions({name: "AppAccountDispatchFormDialog"});
type Form = AppAccountForm
const dialogVisible = ref(false)
const dialogTitle = ref("")
const formLoading = ref(false)
let classifyValue = -1

const formRef = ref()

/**
 * 打开弹窗
 * @param classify 分类
 */
const open = async (classify: number) => {
  dialogVisible.value = true;
  classifyValue = classify;
  dialogTitle.value = "分配数据"

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

    dialogVisible.value = false
    emit('success')
  } finally {
    formLoading.value = false
  }
}

const getUserList = async () => {

}

const getAccountList = async () => {

}

onMounted(() => {
  getAccountList()
  getUserList()
})

</script>
<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle" width="70%">
    <el-card shadow="hover">
      <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" @click="submitForm">提交</el-button>
      </el-col>
      </el-row>
    </el-card>
    <el-table>

    </el-table>

    <template #footer>
      <el-button type="primary" @click="submitForm" :disabled="formLoading">确 定</el-button>
      <!--      <el-button @click="resetForm">重 置</el-button>-->
    </template>

  </Dialog>
</template>
<style scoped lang="scss">

</style>
