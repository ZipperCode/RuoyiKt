<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 20px">
        <h2>使用方式</h2>
        <p>
          <b>角色管理</b>
          <br />
          * 角色和权限挂钩，包括菜单权限和操作权限，目前内置超级管理员、管理员、Line粉端、Line业务员<br />
          * 用户可以绑定多个角色， 分配时只分配对应的角色<br />
          * 比如后期新增Ws业务员时，同时给一个用户分配line和ws的业务员角色，用户就能同时管理line和ws<br/>
          * 粉端权限命名规范: xx_uploader_{type} 其中 type对应关系右图所示，表示对应数据的权限<br/>
          * 业务权限命名规范: xx_salesman_{type} 其中 type对应关系右图所示，表示对应数据的权限<br/>
          <hr/>
          <b> 菜单管理</b>
          <br/>
          * 菜单不可随意修改，更不可删除。禁用的菜单都是没有实现的功能   <br/>
          * 对角色的菜单权限进行分配时，只开放对应的权限，比如业务员，只开放数据查看和修改的权限即可   <br/>

          <b> 部门管理</b>
          <br/>
          * 目前部门没有特别的需求，一般不适用，倒是可以用来分配给用户，但是没有什么特别的逻辑  <br/>

        </p>
      </el-col>
      <el-col :sm="24" :lg="12" style="padding-left: 20px">
        <h2>类型说明</h2>
        <p>
          <b>业务类别</b>
          <br />
          <br />
          <el-tag type="success">Line: 1</el-tag>&#160;
          <el-tag type="success">Pairs: 2</el-tag>&nbsp;
          <el-tag type="success">Ws1: 3</el-tag>&nbsp;
          <el-tag type="success">Ws2: 4</el-tag>&nbsp;
          <el-tag type="success">Ws3: 5</el-tag>&nbsp;
          <el-tag type="success">Ws4: 6</el-tag>&nbsp;
          <el-tag type="success">Ws5: 7</el-tag>&nbsp;
          <el-tag type="success">Ws6: 8</el-tag>&nbsp;
          <el-tag type="success">Ws7: 9</el-tag>&nbsp;
        </p>
        <p>

        </p>

      </el-col>
    </el-row>
    <el-divider />
  </div>
</template>

<script setup name="Index" lang="ts">
import { initWebSocket } from '@/utils/websocket';

onMounted(() => {
  let protocol = window.location.protocol === 'https:' ? 'wss://' : 'ws://'
  initWebSocket(protocol + window.location.host + import.meta.env.VITE_APP_BASE_API + "/resource/websocket");
});

const goTarget = (url:string) => {
  window.open(url, '__blank')
}
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>
