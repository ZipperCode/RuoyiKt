<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="mb-[10px]" v-show="showSearch">
        <el-card shadow="hover">
          <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
            <el-form-item label="链接" prop="link">
              <el-input
                v-model="queryParams.link"
                placeholder="请输入链接关键字"
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
            <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete()" v-hasPermi="[Permission.Delete]">
              多选删除
            </el-button>
          </el-col>
          <el-col :span="2"></el-col>
          <el-row v-hasPermi="[Permission.Check]">
            <el-form ref="uploadFormRef" :inline="true" v-loading="uploadFormLoading" :model="uploadFormData"
                     :rules="uploadFormRules">
              <el-form-item label="链接" prop="link" style="width: 500px">
                <el-input v-model="uploadFormData.link" placeholder="请输入您要检测上传的链接" clearable/>
              </el-form-item>
            </el-form>
            <el-button type="primary" @click="submitUploadForm" :disabled="uploadFormLoading">检测并上传</el-button>
          </el-row>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>
    </el-card>

    <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="数据id" align="center" prop="id" width="100px"/>
      <el-table-column label="链接" align="center" prop="link"/>
      <el-table-column label="分类" align="center" prop="classify">
        <template #default="scope">
          <dict-tag :options="app_links_classify" :value="scope.row.classify"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="创建用户" align="center" prop="createUser"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ scope.row.createTime || parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
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

    <AppLinksFormDialog ref="formRef" @success="getList"/>
  </div>
</template>
<script setup lang="ts">
import {AppLinksForm, AppLinksQuery, AppLinksVo} from "@/api/account/links/types";
import {add, delIds, pageList} from "@/api/account/links";
import {Permission} from "@/views/account/links/types";
import AppLinksFormDialog from "@/views/account/links/FormDialog.vue";
import {FormRules} from "element-plus";

const {proxy} = getCurrentInstance() as ComponentInternalInstance;
const {app_links_classify} = toRefs<any>(proxy?.useDict("app_links_classify"))
const showSearch = ref(true)
const loading = ref(false)
const formRef = ref();
const route = useRoute()
const classify = computed(() => {
  try {
    return parseInt(route.path.slice(route.path.lastIndexOf('/') + 1))
  } catch (e) {
    return -1
  }
})
/**
 * 检查上传
 */
const initForm: AppLinksForm = {
  id: 0,
  classify: undefined,
  link: "",
  remark: ""
}

const uploadFormLoading = ref(false)
const uploadFormRef = ref()
const uploadFormData = ref<AppLinksForm>({...initForm})
const uploadFormRules = reactive<FormRules>({
  link: [
    {required: true, message: "链接不能为空", trigger: "blur"},
  ]
})
const submitUploadForm = async () => {
  uploadFormLoading.value = true;
  try {
    if (!uploadFormRef) {
      return
    }
    const valid = await uploadFormRef.value.validate()
    if (!valid) {
      return
    }
    const data = uploadFormData.value as unknown as AppLinksForm
    data.classify = classify.value;
    const resp = await add(data)
    if (resp.code == 200) {
      proxy?.$modal?.msgSuccess("创建成功")
      getList().then()
    } else {
      proxy?.$modal?.msgError(resp.msg)
    }
  } finally {
    uploadFormLoading.value = false;
  }
}

/**
 * 列表查询部分
 */
const dateRangeCreateTime = ref<[DateModelType, DateModelType]>(['', '']);
const queryParams = reactive<AppLinksQuery>({
  pageNum: 0,
  pageSize: 10,
  link: undefined,
  classify: undefined,
  remark: undefined,
  uploader: undefined
})
const queryFormRef = ref<ElFormInstance>()
const handleQuery = () => {
  queryParams.pageNum = 1;
  queryParams.classify = classify.value;
  getList();
}
const resetQuery = () => {
  queryFormRef.value?.resetFields();
  handleQuery();
}
/**
 * 表格列表
 */
const dataList = ref<AppLinksVo[]>([])
const total = ref(0)
const getList = async () => {
  loading.value = true;
  try {
    queryParams.classify = classify.value;
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
const handleSelectionChange = (selection: AppLinksVo[]) => {
  console.log("handleSelection ", selection)
  selectIds.value = selection.map(item => item.id)
  single.value = selection.length != 1
}
/**
 * 删除
 */
const handleDelete = async (row?: AppLinksVo) => {
  const deleteIds = row?.id == undefined ? selectIds.value : [row.id];
  await proxy?.$modal.confirm('是否确认删除"' + deleteIds + '"的数据项?');
  loading.value = true;
  await delIds(deleteIds).finally(() => loading.value = false);
  await getList();
  proxy?.$modal.msgSuccess("删除成功");
}

const handleAdd = async () => {
  formRef.value.open("create", classify.value)
}

const handleEdit = async (row: AppLinksVo) => {
  formRef.value.open("update", classify.value, row.id)
}

const handleCopy = async (row: AppLinksVo) => {
  const link = row.link
  // 复制到剪贴板
  navigator.clipboard.writeText(link).then(() => {
    proxy?.$modal.msgSuccess("复制成功: link = " + link);
  }).catch((error) => {
    proxy?.$modal.msgError("复制失败");
  });
}

onMounted(() => {
  getList();
})
</script>
<style scoped lang="scss">

</style>
