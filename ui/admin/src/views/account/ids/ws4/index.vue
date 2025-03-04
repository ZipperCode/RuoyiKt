<script setup lang="ts">

import CommonIdsIndex from "@/views/account/ids/common/IdsIndex.vue";
import {DataClassify, Permission} from "@/views/account/ids/types"
</script>

<template>
  <CommonIdsIndex :classify="DataClassify.Ws4"
                  :add-permission="Permission.Ws4Add"
                  :edit-permission="Permission.Ws4Edit"
                  :delete-permission="Permission.Ws4Delete"
                  :upload-permission="Permission.Ws4Upload"
                  :dispatch-permission="Permission.Ws4Dispatch"
                  :unbind-permission="Permission.Ws4Unbind"
                  :export-permission="Permission.Ws4Export"/>
</template>
