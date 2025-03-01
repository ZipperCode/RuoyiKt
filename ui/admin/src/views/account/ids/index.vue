<script setup lang="ts">
import {AppAccountForm, AppAccountQuery, AppAccountVo} from "@/api/account/ids/types";
import {delIds, dispatch, exportData, pageList, unbind} from "@/api/account/ids";
import {Permission} from "@/views/account/ids/types";
import AppAccountFormDialog from "@/views/account/ids/FormDialog.vue";
import AppAccountBatchUploadDialog from "@/views/account/ids/BatchUploadDialog.vue";
import {useMessage} from "@/hooks/useMessage";
import AppAccountDispatchFormDialog from "@/views/account/ids/DispatchDialog.vue";
import {checkPermi} from "@/utils/permission";
import AppAccountBatchStatusDialog from "@/views/account/ids/BatchStatusDialog.vue";
import AppAccountScreenshotDialog from "@/views/account/ids/ScreenshotDialog.vue";

const {proxy} = getCurrentInstance() as ComponentInternalInstance;
const {
  app_account_classify, app_account_status, app_account_country
} = toRefs<any>(proxy?.useDict(
  "app_account_classify", "app_account_status", 'app_account_country'
))
type Query = AppAccountQuery
type Form = AppAccountForm
type VO = AppAccountVo
const showSearch = ref(true)
const loading = ref(false)
const formRef = ref();
const uploadFormRef = ref();
const fullscreenLoading = ref(false)

const message = useMessage();

const route = useRoute()
const classify = computed(() => {
  try {
    return parseInt(route.path.slice(route.path.lastIndexOf('/') + 1))
  } catch (e) {
    return -1
  }
})

/**
 * 列表查询部分
 */
const dateRangeCreateTime = ref<[DateModelType, DateModelType]>(['', '']);
const queryParams = reactive<Query>({
  pageNum: 0,
  pageSize: 10,
  account: undefined,
  country: undefined,
  accountType: undefined,
  classify: undefined,
  status: undefined,
  remark: undefined,
  uploader: undefined
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
    const response = await pageList(
      proxy?.addDateRange(queryParams, dateRangeCreateTime.value)
    );
    dataList.value = response.rows
    total.value = response.total
  } finally {
    loading.value = false;
  }
}

/**
 * 多选处理
 */
const selectIds = ref<Array<number>>([]);
const single = ref(false);
const multiple = computed(() => selectIds.value.length == 0)
const handleSelectionChange = (selection: VO[]) => {
  console.log("handleSelection ", selection)
  selectIds.value = selection.map(item => item.id)
  single.value = selection.length != 1
}

/**
 * 删除
 */
const handleDelete = async (row?: VO) => {
  const deleteIds = row?.id == undefined ? selectIds.value : [row.id];
  await proxy?.$modal.confirm('是否确认删除"' + deleteIds.length + '"条数据项?');
  loading.value = true;
  await delIds(deleteIds).finally(() => loading.value = false);
  await getList();
  proxy?.$modal.msgSuccess("删除成功");
}

const handleAdd = async () => {
  formRef.value.open("create", classify.value)
}

const handleEdit = async (row: VO) => {
  formRef.value.open("update", classify.value, row.id)
}

const handleUpload = async () => {
  uploadFormRef.value.open(classify.value)
}

const handleCopy = async (row: VO) => {
  const link = row.account
  if (!link) {
    proxy?.$modal.msgError("复制失败");
    return
  }
  // 复制到剪贴板
  navigator.clipboard.writeText(link!!).then(() => {
    proxy?.$modal.msgSuccess("复制成功: link = " + link);
  }).catch((error) => {
    proxy?.$modal.msgError("复制失败");
  });
}

const dispatchFormRef = ref()
const handleDispatch = async () => {
  fullscreenLoading.value = true
  try {
    const response = await dispatch(classify.value)
    if (response.code === 200 && response.data) {
      message.success("分配成功")
    } else {
      message.error(response.msg)
    }
  } finally {
    fullscreenLoading.value = false;
  }
  // dispatchFormRef.value.open(classify.value)
}

const handleUnbind = async (id: number) => {
  await message.confirm("是否确认解除绑定?")
  const response = await unbind(id)
  if (response.code === 200 && response.data) {
    proxy?.$modal.msgSuccess("解除绑定成功");
    getList().then();
  } else {
    proxy?.$modal.msgError(response.msg);
  }
}

const updateStatusDialogRef = ref()
const openUpdateStatus = async () => {
  const deleteIds = selectIds.value
  if (deleteIds.length == 0) {
    message.error("请至少选中一条数据")
    return
  }
  updateStatusDialogRef.value.open(deleteIds)
}

const handleExport = async () => {
  fullscreenLoading.value = true
  try {
    queryParams.classify = classify.value
    await exportData(queryParams)
  } finally {
    fullscreenLoading.value = false;
  }
}

const screenshotDialogRef = ref()

const handleScreenshot = async (row: VO) => {
  screenshotDialogRef.value.open(row)
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
            <el-form-item label="备注" prop="remark">
              <el-input
                v-model="queryParams.remark"
                placeholder="请输入备注关键字"
                clearable
                @keyup.enter="handleQuery"
                class="!w-240px"
              />
            </el-form-item>
            <el-form-item label="国家" prop="country">
              <el-select v-model="queryParams.country" placeholder="请选择国家" clearable>
                <el-option v-for="item in app_account_country" :key="item.value" :label="item.label"
                           :value="parseInt(item.value)"/>
              </el-select>
            </el-form-item>
            <el-form-item label="状态" prop="status">
              <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
                <el-option v-for="item in app_account_status" :key="item.value" :label="item.label"
                           :value="item.value"/>
              </el-select>
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
    <el-card shadow="hover">
      <template #header>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="Plus" @click="handleAdd()" v-hasPermi="[Permission.Add]">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="primary" plain icon="Upload" @click="handleUpload()" v-hasPermi="[Permission.Upload]">
              批量上传
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="[Permission.Delete]">
              多选删除
            </el-button>
          </el-col>

          <el-col :span="1.5">
            <el-button type="primary" plain icon="Edit" :disabled="multiple" @click="openUpdateStatus()" v-hasPermi="[Permission.Edit]">
              批量修改状态
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="primary" v-loading.fullscreen.lock="fullscreenLoading" plain icon="Share" @click="handleDispatch()"
                       v-hasPermi="[Permission.Dispatch]">
              数据分配
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="primary" v-loading.fullscreen.lock="fullscreenLoading" plain icon="Share" @click="handleExport()"
                       v-hasPermi="[Permission.Export]">
              导出数据
            </el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>
    </el-card>

    <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
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
              <el-button size="small" type="danger" v-if="checkPermi([Permission.Unbind])" @click="handleUnbind(scope.row.id)">解绑
              </el-button>
              <el-tag v-else type="success">已绑定</el-tag>
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
      <el-table-column label="截图" align="center" prop="screenshot">
        <template #default="scope">
          <el-button size="small" type="primary" @click="handleScreenshot(scope.row)">截图</el-button>
        </template>
      </el-table-column>
      <el-table-column label="上传人" align="center" prop="createUser"/>
      <el-table-column label="国家" align="center" prop="country" width="100px">
        <template #default="scope">
          <dict-tag :options="app_account_country" :value="scope.row.country"/>
        </template>
      </el-table-column>
      <el-table-column label="收入" align="center" prop="income" width="100px"/>
      <el-table-column label="工作" align="center" prop="work" width="150px" show-overflow-tooltip/>
      <el-table-column label="备注" align="center" prop="remark" width="200px" show-overflow-tooltip/>
      <el-table-column label="年龄" align="center" prop="age" width="100px"/>
      <el-table-column label="上传时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ scope.row.createTime || parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right" min-width="120px">
        <template #default="scope">
          <el-tooltip content="复制到剪贴板" placement="top">
            <el-button link type="primary" icon="CopyDocument" download @click="handleCopy(scope.row)" :underline="false">
            </el-button>
          </el-tooltip>
          <el-tooltip content="修改" placement="top">
            <el-button
              link type="primary" icon="Edit" @click="handleEdit(scope.row)" v-hasPermi="[Permission.Edit]">
            </el-button>
          </el-tooltip>

          <el-tooltip content="删除" placement="top">
            <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="[Permission.Delete]"></el-button>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize"
                @pagination="getList"/>

    <AppAccountFormDialog ref="formRef" @success="getList"/>
    <AppAccountBatchUploadDialog ref="uploadFormRef" @success="getList"/>
    <AppAccountDispatchFormDialog ref="dispatchFormRef" @success="getList"/>
    <AppAccountBatchStatusDialog ref="updateStatusDialogRef" @success="getList"/>
    <AppAccountScreenshotDialog ref="screenshotDialogRef"/>
  </div>
</template>

<style scoped lang="scss">

</style>
