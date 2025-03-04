<script setup lang="ts">

import CommonIdsIndex from "@/views/account/ids/common/IdsIndex.vue";
import {DataClassify, Permission} from "@/views/account/ids/types"
</script>

<template>
  <CommonIdsIndex :classify="DataClassify.Ws7"
                  :add-permission="Permission.Ws7Add"
                  :edit-permission="Permission.Ws7Edit"
                  :delete-permission="Permission.Ws7Delete"
                  :upload-permission="Permission.Ws7Upload"
                  :dispatch-permission="Permission.Ws7Dispatch"
                  :unbind-permission="Permission.Ws7Unbind"
                  :export-permission="Permission.Ws7Export"/>
</template>
