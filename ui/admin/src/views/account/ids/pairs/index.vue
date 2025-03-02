<script setup lang="ts">

import CommonIdsIndex from "@/views/account/ids/common/IdsIndex.vue";
import {DataClassify, Permission} from "@/views/account/ids/types"
</script>

<template>
  <CommonIdsIndex :classify="DataClassify.Pairs"
                  :add-permission="Permission.PairsAdd"
                  :edit-permission="Permission.PairsEdit"
                  :delete-permission="Permission.PairsDelete"
                  :upload-permission="Permission.PairsUpload"
                  :dispatch-permission="Permission.PairsDispatch"
                  :unbind-permission="Permission.PairsUnbind"
                  :export-permission="Permission.PairsExport"/>
</template>
