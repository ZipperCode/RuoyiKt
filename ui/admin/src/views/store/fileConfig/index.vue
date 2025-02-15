<template>
  <div class="p-2">
    <transition :enter-active-class="proxy?.animate.searchAnimate.enter" :leave-active-class="proxy?.animate.searchAnimate.leave">
      <div class="mb-[10px]" v-show="showSearch">
        <el-card shadow="hover">
          <el-form :model="queryParams" ref="queryFormRef" :inline="true" label-width="68px">
            <el-form-item label="配置名" prop="name">
              <el-input
                v-model="queryParams.name"
                placeholder="请输入配置名"
                clearable
                @keyup.enter="handleQuery"
                class="!w-240px"
              />
            </el-form-item>
            <el-form-item label="存储器" prop="storage">
              <el-select
                v-model="queryParams.storage"
                placeholder="请选择存储器"
                clearable
                class="!w-240px"
              >
                <el-option
                  v-for="dict in store_file_storage_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
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
            <el-button type="primary" plain icon="Plus" @click="handleAdd()" v-hasPermi="[FileConfigPerm.Add]">新增</el-button>
          </el-col>
          <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
      </template>

      <el-table
        v-loading="loading" :data="fileConfigList" @selection-change="handleSelectionChange"
        :header-cell-class-name="handleHeaderClass"
      >
        <el-table-column label="编号" align="center" prop="id"/>
        <el-table-column label="配置名" align="center" prop="name" width="150"/>
        <el-table-column label="存储器" align="center" prop="storage" width="150">
          <template #default="scope">
            <dict-tag :options="store_file_storage_type" :value="scope.row.storage"/>
          </template>
        </el-table-column>

        <el-table-column label="主配置" align="center" prop="master">
          <template #default="scope">
            <dict-tag :options="sys_yes_no" :value="scope.row.master"/>
          </template>
        </el-table-column>
        <el-table-column label="备注" align="center" prop="remark"/>
        <el-table-column label="创建时间" align="center" prop="createTime" width="180" sortable="custom">
          <template #default="scope">
            <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="240px" fixed="right">
          <template #default="scope">
            <el-button
              link type="primary" @click="openForm('update', scope.row.id)" v-hasPermi="[FileConfigPerm.Edit]">
              编辑
            </el-button>
            <el-button
              link type="primary" :disabled="scope.row.master" @click="handleMaster(scope.row.id)"
              v-hasPermi="[FileConfigPerm.Edit]">
              主配置
            </el-button>
            <el-button
              link type="danger"
              @click="handleDelete(scope.row)" v-hasPermi="[FileConfigPerm.Delete]">
              删除
            </el-button>
          </template>
        </el-table-column>

      </el-table>

      <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize"
                  @pagination="getList"/>
    </el-card>

    <!-- 表单弹窗：添加/修改 -->
    <FileConfigFormDialog ref="formRef" @success="getList"/>
  </div>
</template>

<script setup name="FileConfig" lang="ts">
import {FileConfigForm, FileConfigQuery, FileConfigVo} from "@/api/system/fileConfig/types";
import {delFileConfig, getFileConfigPageList, updateMaster} from "@/api/system/fileConfig";
import FileConfigFormDialog from './FileConfigFormDialog.vue'
import {FileConfigPerm} from "@/views/store/fileConfig/types";

const {proxy} = getCurrentInstance() as ComponentInternalInstance;
const {store_file_storage_type} = toRefs<any>(proxy?.useDict("store_file_storage_type"));
const {sys_yes_no} = toRefs<any>(proxy?.useDict("sys_yes_no"));
const fileConfigList = ref<FileConfigVo[]>([]);

const showTable = ref(true);
const loading = ref(true);
const showSearch = ref(true);
const idsRef = ref<Array<string | number>>([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);

const queryFormRef = ref<ElFormInstance>();
const formRef = ref();

const queryParams = reactive<FileConfigQuery>({
  pageNum: 1,
  pageSize: 10,
  name: undefined as string | undefined,
  storage: undefined as number | undefined,
} as FileConfigQuery)


/// 查询配置列表
const getList = async () => {
  loading.value = true;
  try {
    const response = await getFileConfigPageList(queryParams)
    fileConfigList.value = response.rows
    console.log("response = ", response)
    total.value = response.total;
  } finally {
    loading.value = false;
    showTable.value = true;
  }
}

/// 搜索按钮操作
const handleQuery = () => {
  queryParams.pageNum = 1;
  getList();
}

/// 重置按钮操作
function resetQuery() {
  queryFormRef.value?.resetFields();
  handleQuery();
}

/// 点击新增
const handleAdd = async () => {
  openForm("create")
}
const openForm = (type: string, id?: string | number) => {
  formRef.value.open(type, id)
}

/// 修改主配置
const handleMaster = async (configId: string | number) => {
  try {
    await proxy?.$modal.confirm('是否确认修改配置编号为"' + configId + '"的数据项为主配置?')
    await updateMaster(configId);
    await getList();
    proxy?.$modal.msgSuccess("修改成功");
  } catch {

  }
}

/// 删除按钮操作
const handleDelete = async (row?: FileConfigVo) => {
  const ids = row?.id || idsRef.value || [];
  await proxy?.$modal.confirm('是否确认删除OSS对象存储编号为"' + ids + '"的数据项?');
  loading.value = true;
  await delFileConfig(ids).finally(() => loading.value = false);
  await getList();
  proxy?.$modal.msgSuccess("删除成功");
}

/// 选择条数
function handleSelectionChange(selection: FileConfigVo[]) {
  idsRef.value = selection.map(item => item.id || 0);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 设置列的排序为我们自定义的排序 */
const handleHeaderClass = ({column}: any): any => {
  column.order = column.multiOrder
}
onMounted(() => {
  getList();
})
</script>
