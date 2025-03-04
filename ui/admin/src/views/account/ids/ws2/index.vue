<script setup lang="ts">

import CommonIdsIndex from "@/views/account/ids/common/IdsIndex.vue";
import {DataClassify, Permission} from "@/views/account/ids/types"
</script>

<template>
  <CommonIdsIndex :classify="DataClassify.Ws2"
                  :add-permission="Permission.Ws2Add"
                  :edit-permission="Permission.Ws2Edit"
                  :delete-permission="Permission.Ws2Delete"
                  :upload-permission="Permission.Ws2Upload"
                  :dispatch-permission="Permission.Ws2Dispatch"
                  :unbind-permission="Permission.Ws2Unbind"
                  :export-permission="Permission.Ws2Export"/>
</template>
