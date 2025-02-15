<template>
  <div>
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="mb-[10px]" v-show="showSearch">
        <el-card shadow="hover">
          <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
            <el-form-item label="文件路径" prop="path">
              <el-input
                v-model="queryParams.path"
                placeholder="请输入文件路径"
                clearable
                @keyup.enter="handleQuery"
                class="!w-240px"
              />
            </el-form-item>
            <el-form-item label="文件类型" prop="type">
              <el-input
                v-model="queryParams.path"
                placeholder="请输入文件类型"
                clearable
                @keyup.enter="handleQuery"
                class="!w-240px"
              />
            </el-form-item>
            <el-form-item label="Hash" prop="hash">
              <el-input
                v-model="queryParams.hash"
                placeholder="请输入Hash"
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
            <el-form-item label="服务商" prop="service">
              <el-input
                v-model="queryParams.service"
                placeholder="请输入云存储服务商"
                clearable
                @keyup.enter="handleQuery"
                class="!w-240px"
              />
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
            <el-button type="primary" plain icon="Upload" @click="handleUploadFile" v-hasPermi="[Permission.Upload]">上传文件</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="primary" plain icon="Upload" @click="handleUploadImage" v-hasPermi="[Permission.Upload]">上传图片</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="[Permission.Delete]">
              多选删除
            </el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column label="配置Id" align="center" prop="configId" width="100"/>
        <el-table-column label="文件路径" align="center" prop="path"/>
        <el-table-column label="文件类型" align="center" prop="mimeType"/>
        <el-table-column label="文件大小" align="center" prop="fileSize"/>
        <el-table-column label="链接" align="center" prop="url"/>
        <el-table-column label="Hash" align="center" prop="hash"/>
        <el-table-column label="创建时间" align="center" prop="createTime" width="180">
          <template #default="scope">
            <span>{{ scope.row.createTime || parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
          <template #default="scope">
            <el-tooltip content="下载" placement="top">
              <el-link type="primary" download  @click="handleDownload(scope.row.url)" :underline="false"  v-hasPermi="[Permission.Download]">
                下载
              </el-link>
            </el-tooltip>
            <el-tooltip content="删除" placement="top">
              <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="[Permission.Delete]"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize"
                  @pagination="getList"/>
    </el-card>

    <FileUploadForm ref="uploadFileRef" @success="getList"/>
  </div>
</template>
<script setup lang="ts">
import {Permission} from "@/views/store/file/types";
import {FileRecordQuery, FileRecordVo} from "@/api/system/file/types";
import {deleteFile, getFileRecordPageList} from "@/api/system/file";
import FileUploadForm from "@/views/store/file/FileUploadForm.vue";

const {proxy} = getCurrentInstance() as ComponentInternalInstance;

const initQueryForm: FileRecordQuery = {
  pageNum: 0,
  pageSize: 10,
  service: undefined,
  path: undefined,
  type: undefined,
  createTime: []
}

const uploadFileRef = ref()

/// 获取列表
const showSearch = ref(true)
const loading = ref(false)
const total = ref(0)
const dataList = ref<FileRecordVo[]>([])
const dateRangeCreateTime = ref<[DateModelType, DateModelType]>(['', '']);

const getList = async () => {
  loading.value = true
  try {
    const response = await getFileRecordPageList(
      proxy?.addDateRange(queryParams, dateRangeCreateTime.value, "CreateTime")
    )
    dataList.value = response.rows
    total.value = response.total
  } finally {
    loading.value = false
  }
}
/// 查询列表
const queryParams = reactive<FileRecordQuery>({
  ...initQueryForm
})
const queryFormRef = ref<ElFormInstance>()
const handleQuery = () => {
  queryParams.pageNum = 1;
  getList()
}
const resetQuery = () => {
  console.log("resetQuery")
  queryFormRef.value?.resetFields()
  handleQuery()
}

/// 表格选择
const selectIds = ref<Array<number | string>>([]);
const single = ref(false);
const multiple = computed(() => selectIds.value.length > 0)
const handleSelectionChange = (selection: FileRecordVo[]) => {
  selectIds.value = selection.map(item => item.id)
  single.value = selection.length != 1
}

/// 文件上传
const handleUploadFile = () => {
  uploadFileRef.value.open()
}

const handleUploadImage = () => {

}

const handleDownload = (url: string) => {
  // window.open(url, '_blank')
  proxy?.$download.download(url)
}

/// 删除文件
const handleDelete = async (row?: FileRecordVo) => {
  const deleteIds = row?.id == undefined ? selectIds.value : [row.id];
  await proxy?.$modal.confirm('是否确认删除"' + deleteIds + '"的数据项?');
  loading.value = true;
  await deleteFile(deleteIds).finally(() => loading.value = false);
  await getList();
  proxy?.$modal.msgSuccess("删除成功");
}

onMounted(() => {
  getList();
})

</script>
<style scoped lang="scss">

</style>
