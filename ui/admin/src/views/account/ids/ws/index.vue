<script setup lang="ts">
import {AppAccountVo, SearchQuery} from "@/api/account/ids/types";
import {searchList} from "@/api/account/ids";
import {useMessage} from "@/hooks/useMessage";

const {proxy} = getCurrentInstance() as ComponentInternalInstance;
const {
  app_account_classify, app_account_status, app_account_country
} = toRefs<any>(proxy?.useDict(
  "app_account_classify", "app_account_status", 'app_account_country'
))
type Query = SearchQuery
type VO = AppAccountVo
const showSearch = ref(true)
const loading = ref(false)

const message = useMessage();

/**
 * 列表查询部分
 */
const dateRangeCreateTime = ref<[DateModelType, DateModelType]>(['', '']);
const queryParams = reactive<Query>({
  pageNum: 0,
  pageSize: 10,
  id: undefined,
  account: undefined,
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
  loading.value = true;
  try {
    const response = await searchList(
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
            <el-form-item label="编号" prop="id">
              <el-input
                v-model="queryParams.id"
                placeholder="请输入编号"
                clearable
                @keyup.enter="handleQuery"
                class="!w-240px"
              />
            </el-form-item>
            <el-form-item label="账号" prop="account">
              <el-input
                v-model="queryParams.account"
                placeholder="请输入账号关键字"
                clearable
                @keyup.enter="handleQuery"
                class="!w-240px"
              />
            </el-form-item>
            <el-form-item label="上传人" prop="uploader">
              <el-input
                v-model="queryParams.uploader"
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
      <el-table-column label="数据id" align="center" prop="id" width="80px" fixed="left"/>
      <el-table-column label="账号" align="center" prop="account" min-width="150px" fixed="left"/>
      <el-table-column label="分类" align="center" prop="classify">
        <template #default="scope">
          <dict-tag :options="app_account_classify" :value="scope.row.classify"/>
        </template>
      </el-table-column>
      <el-table-column label="绑定状态" align="center" prop="record">
        <template #default="scope">
          <div>
            <el-tooltip :content="scope.row.record?.bindUser" placement="top" v-if="scope.row.record !== null">
              <el-tag type="success">已绑定</el-tag>
            </el-tooltip>
            <el-tag type="success" v-else>未绑定</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="数据状态" align="center" prop="bindUser">
        <template #default="scope">
          <dict-tag :options="app_account_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
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
