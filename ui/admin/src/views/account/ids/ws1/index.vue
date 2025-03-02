<script setup lang="ts">

import CommonIdsIndex from "@/views/account/ids/common/IdsIndex.vue";
import {DataClassify, Permission} from "@/views/account/ids/types"
</script>

<template>
  <CommonIdsIndex :classify="DataClassify.Ws1"
                  :add-permission="Permission.Ws1Add"
                  :edit-permission="Permission.Ws1Edit"
                  :delete-permission="Permission.Ws1Delete"
                  :upload-permission="Permission.Ws1Upload"
                  :dispatch-permission="Permission.Ws1Dispatch"
                  :unbind-permission="Permission.Ws1Unbind"
                  :export-permission="Permission.Ws1Export"/>
</template>
