<script setup lang="ts">
import {AppAccountVo} from "@/api/account/ids/types";

const baseUrl = import.meta.env.VITE_APP_BASE_API;
defineOptions({name: "AppAccountScreenshotDialog"});
type Vo = AppAccountVo
const dialogVisible = ref(false)
const dialogTitle = ref("")
const srcList = ref<string[]>()


/**
 * 打开弹窗
 * @param vo 数据
 */
const open = async (vo: Vo) => {
  dialogVisible.value = true;
  dialogTitle.value = "查看截图"
  if (!vo.screenshot) {
    dialogVisible.value = false
    return
  }
  const urlPaths = vo.screenshot.split(",")
  srcList.value = urlPaths.map(url => {
    return baseUrl + url
  })

}
defineExpose({open})

</script>
<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle">
    <el-image
      show-progress
      :initial-index="1"
      style="width: 150px; height: 150px"
      :src="url"
      :preview-src-list="srcList"
      fit="cover"
      v-for="url in srcList"
      :key="url"
    />
  </Dialog>
</template>
<style scoped lang="scss">

</style>
