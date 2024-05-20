<template>
  <div class="main-layout-frame">
    <a-row style="margin-top: 20px;">
      <a-col :flex="10" />
      <a-col :flex="3">
        <a-button type="primary" status="danger" size="large" long @click="handleLogOut">登出账号</a-button>
      </a-col>
      <a-col :flex="10" />
    </a-row>
  </div>
</template>

<script setup>
import {AlertViewEventBus} from "@/utils/mitt";
function handleLogOut() {
  AlertViewEventBus.emit("logout", undefined);
}
</script>

<style scoped>
@import "@/style/app.css";
</style>
