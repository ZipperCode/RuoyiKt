<script setup lang="ts">

import CommonIdsIndex from "@/views/account/ids/common/IdsIndex.vue";
import {DataClassify, Permission} from "@/views/account/ids/types"
</script>

<template>
  <CommonIdsIndex :classify="DataClassify.Line"
                  :add-permission="Permission.LineAdd"
                  :edit-permission="Permission.LineEdit"
                  :delete-permission="Permission.LineDelete"
                  :upload-permission="Permission.LineUpload"
                  :dispatch-permission="Permission.LineDispatch"
                  :unbind-permission="Permission.LineUnbind"
                  :export-permission="Permission.LineExport"/>
</template>
