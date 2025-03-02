<script setup lang="ts">
import {AppAccountRecordQuery, AppAccountRecordVo} from "@/api/account/ids/types";
import {recordPageList} from "@/api/account/ids";
import {useMessage} from "@/hooks/useMessage";

defineOptions({name: "CommonIdsRecordIndex"})

interface Props {
  classify: {
    type: Number,
    required: true
  },
}

const props = defineProps<Props>()
const {proxy} = getCurrentInstance() as ComponentInternalInstance;
const {
  app_account_status,
} = toRefs<any>(proxy?.useDict(
  "app_account_status",
))
type Query = AppAccountRecordQuery
type VO = AppAccountRecordVo
const showSearch = ref(true)
const loading = ref(false)
const message = useMessage();

const classify = computed(() => props.classify as unknown as number)

/**
 * 列表查询部分
 */
const dateRangeCreateTime = ref<[DateModelType, DateModelType]>(['', '']);
const queryParams = reactive<Query>({
  pageNum: 0,
  pageSize: 10,
  account: undefined,
  classify: undefined,
  used: undefined,
  createUser: undefined
})
const queryFormRef = ref<ElFormInstance>()
const handleQuery = () => {
  queryParams.pageNum = 1;
  getList();
}
const resetQuery = () => {
  queryFormRef.value?.resetFields();
  handleQuery();
}
/**
 * 表格列表
 */
const dataList = ref<VO[]>([])
const total = ref(0)
const getList = async () => {
  if (classify.value < 0) {
    message.error("页面错误，关闭标签页重试")
    return
  }
  loading.value = true;
  try {
    queryParams.classify = classify.value
    const response = await recordPageList(
      proxy?.addDateRange(queryParams, dateRangeCreateTime.value)
    );
    dataList.value = response.rows
    total.value = response.total
  } finally {
    loading.value = false;
  }
}

onMounted(() => {
  getList();
})
</script>

<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="mb-[10px]" v-show="showSearch">
        <el-card shadow="hover">
          <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
            <el-form-item label="账号" prop="account">
              <el-input
                v-model="queryParams.account"
                placeholder="请输入账号关键字"
                clearable
                @keyup.enter="handleQuery"
                class="!w-240px"
              />
            </el-form-item>

            <el-form-item label="状态" prop="status">
              <el-select v-model="queryParams.used" placeholder="请选择状态" clearable>
                <el-option v-for="item in app_account_status" :key="item.value" :label="item.label"
                           :value="item.value"/>
              </el-select>
            </el-form-item>
            <el-form-item label="上传人" prop="createUser">
              <el-input
                v-model="queryParams.createUser"
                placeholder="请输入上传人关键字"
                clearable
                @keyup.enter="handleQuery"
                class="!w-240px"
              />
            </el-form-item>
            <el-form-item label="创建时间">
              <el-date-picker
                v-model="dateRangeCreateTime"
                value-format="YYYY-MM-DD HH:mm:ss"
                type="daterange"
                range-separator="-"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :default-time="[new Date(2000, 1, 1, 0, 0, 0), new Date(2000, 1, 1, 23, 59, 59)]"
              ></el-date-picker>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" icon="search" @click="handleQuery">搜索</el-button>
              <el-button icon="Refresh" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>
        </el-card>

      </div>

    </transition>

    <el-table v-loading="loading" :data="dataList">
      <el-table-column label="记录ID" align="center" prop="id" width="100px"/>
      <el-table-column label="账号数据ID" align="center" prop="accountId" width="150px" fixed="left"/>
      <el-table-column label="账号" align="center" prop="account" min-width="150px" fixed="left"/>
      <el-table-column label="数据状态" align="center" prop="used">
        <template #default="scope">
          <dict-tag :options="app_account_status" :value="scope.row.used"/>
        </template>
      </el-table-column>
      <el-table-column label="分配人" align="center" prop="bindUser"/>
      <el-table-column label="上传人" align="center" prop="createUser"/>
      <el-table-column label="上传时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ scope.row.createTime || parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize"
                @pagination="getList"/>
  </div>
</template>

<style scoped lang="scss">

</style>
