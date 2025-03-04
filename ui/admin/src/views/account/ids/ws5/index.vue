<script setup lang="ts">

import CommonIdsIndex from "@/views/account/ids/common/IdsIndex.vue";
import {DataClassify, Permission} from "@/views/account/ids/types"
</script>

<template>
  <CommonIdsIndex :classify="DataClassify.Ws5"
                  :add-permission="Permission.Ws5Add"
                  :edit-permission="Permission.Ws5Edit"
                  :delete-permission="Permission.Ws5Delete"
                  :upload-permission="Permission.Ws5Upload"
                  :dispatch-permission="Permission.Ws5Dispatch"
                  :unbind-permission="Permission.Ws5Unbind"
                  :export-permission="Permission.Ws5Export"/>
</template>
