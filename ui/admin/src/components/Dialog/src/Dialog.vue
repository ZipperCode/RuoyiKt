<script lang="ts" setup>
import {propTypes} from '@/utils/propTypes'
import {isNumber} from "@vueuse/core";

import {Close, FullScreen} from "@element-plus/icons-vue";

defineOptions({name: 'Dialog'})

const slots = useSlots()

const props = defineProps({
  modelValue: propTypes.bool.def(false),
  title: propTypes.string.def('Dialog'),
  fullscreen: propTypes.bool.def(true),
  width: propTypes.oneOfType([String, Number]).def('40%'),
  scroll: propTypes.bool.def(false), // 是否开启滚动条。如果是的话，按照 maxHeight 设置最大高度
  maxHeight: propTypes.oneOfType([String, Number]).def('400px')
})

const getBindValue = computed(() => {
  const delArr: string[] = ['fullscreen', 'title', 'maxHeight', 'appendToBody']
  const attrs = useAttrs()
  let obj = {...attrs, ...props}
  // for (const key in obj) {
  //   if (delArr.indexOf(key) !== -1) {
  //     delete obj[key]
  //   }
  // }
  return Object.entries(obj).reduce((acc, [key, value]) => {
    if (!delArr.includes(key)) {
      acc[key] = value
    }
    return acc
  }, {} as { [key: string]: any })
})

const isFullscreen = ref(false)

const toggleFull = () => {
  isFullscreen.value = !unref(isFullscreen)
}

const dialogHeight = ref(isNumber(props.maxHeight) ? `${props.maxHeight}px` : props.maxHeight)

watch(
  () => isFullscreen.value,
  async (val: boolean) => {
    await nextTick()
    if (val) {
      const windowHeight = document.documentElement.offsetHeight
      dialogHeight.value = `${windowHeight - 55 - 60 - (slots.footer ? 63 : 0)}px`
    } else {
      dialogHeight.value = isNumber(props.maxHeight) ? `${props.maxHeight}px` : props.maxHeight
    }
  },
  {
    immediate: true
  }
)

const dialogStyle = computed(() => {
  return {
    height: unref(dialogHeight)
  }
})
</script>

<template>
  <ElDialog
    v-bind="getBindValue"
    :close-on-click-modal="true"
    :fullscreen="isFullscreen"
    :width="width"
    destroy-on-close
    lock-scroll
    draggable
    class="com-dialog"
    :show-close="false"
  >
    <template #header="{ close }">
      <div class="relative h-54px flex items-center justify-between pl-15px pr-15px">
        <slot name="title">
          {{ title }}
        </slot>
        <div
          class="absolute right-15px top-[50%] h-54px flex translate-y-[-50%] items-center justify-between"
        >
          <el-icon class="is-hover mr-10px cursor-pointer" v-if="fullscreen" @click="toggleFull">
            <FullScreen/>
          </el-icon>
          <el-icon class="is-hover cursor-pointer" @click="close">
            <Close/>
          </el-icon>
        </div>
      </div>
    </template>

    <ElScrollbar v-if="scroll" :style="dialogStyle">
      <slot></slot>
    </ElScrollbar>
    <slot v-else></slot>
    <template v-if="slots.footer" #footer>
      <slot name="footer"></slot>
    </template>
  </ElDialog>
</template>

<style lang="scss">
.com-dialog {
  .el-overlay-dialog {
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .el-dialog {
    margin: 0 !important;

    &__header {
      height: 54px;
      padding: 0;
      margin-right: 0 !important;
      border-bottom: 1px solid var(--el-border-color);
    }

    &__body {
      padding: 15px !important;
    }

    &__footer {
      border-top: 1px solid var(--el-border-color);
    }

    &__headerbtn {
      top: 0;
    }
  }
}
</style>
