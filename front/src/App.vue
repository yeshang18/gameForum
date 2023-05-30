<template>
  <el-config-provider :locale="locale" :message="config">
    <router-view />
  </el-config-provider>
    
</template>

<script setup>
  import {reactive} from "vue";
  import zhCn from "element-plus/lib/locale/lang/zh-cn";
  const locale = zhCn;
  const config =reactive({
    max:1,
  })



</script>

<style>
body{
  background-image: url(./assets/bg/bg5.jpg);
  background-position: 50% 50%;
  background-attachment: fixed;
}
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  border: none;
}

a {
  text-decoration: none;
  color: inherit;
}

li {
  list-style: none;
}

input {
  border: none;
  outline: none;
}

button {
  cursor: pointer;
}
</style>
