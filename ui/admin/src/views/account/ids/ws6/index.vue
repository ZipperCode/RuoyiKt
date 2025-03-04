<script setup lang="ts">

import CommonIdsIndex from "@/views/account/ids/common/IdsIndex.vue";
import {DataClassify, Permission} from "@/views/account/ids/types"
</script>

<template>
  <CommonIdsIndex :classify="DataClassify.Ws6"
                  :add-permission="Permission.Ws6Add"
                  :edit-permission="Permission.Ws6Edit"
                  :delete-permission="Permission.Ws6Delete"
                  :upload-permission="Permission.Ws6Upload"
                  :dispatch-permission="Permission.Ws6Dispatch"
                  :unbind-permission="Permission.Ws6Unbind"
                  :export-permission="Permission.Ws6Export"/>
</template>
