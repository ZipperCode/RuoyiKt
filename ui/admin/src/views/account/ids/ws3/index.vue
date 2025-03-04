<script setup lang="ts">

import CommonIdsIndex from "@/views/account/ids/common/IdsIndex.vue";
import {DataClassify, Permission} from "@/views/account/ids/types"
</script>

<template>
  <CommonIdsIndex :classify="DataClassify.Ws3"
                  :add-permission="Permission.Ws3Add"
                  :edit-permission="Permission.Ws3Edit"
                  :delete-permission="Permission.Ws3Delete"
                  :upload-permission="Permission.Ws3Upload"
                  :dispatch-permission="Permission.Ws3Dispatch"
                  :unbind-permission="Permission.Ws3Unbind"
                  :export-permission="Permission.Ws3Export"/>
</template>
