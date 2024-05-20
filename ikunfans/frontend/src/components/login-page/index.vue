<template>
  <div class="main-layout-frame">
    <a-layout class="main-layout">
      <a-layout-header class="main-layout-header" />
      <a-layout-content class="main-layout-content">
        <a-row>
          <a-col :flex="5" /><a-col :flex="2">
            <a-avatar :size="180" :style="{ backgroundColor: '#FEAFBF' }"><IconUser /></a-avatar>
          </a-col><a-col :flex="5" />
        </a-row>
        <a-row style="margin-top: 30px;">
          <a-col :flex="5" /><a-col :flex="3">
            <a-input size="large" placeholder="请输入用户名" allow-clear long v-model:model-value="account">
              <template #prefix><icon-user /></template>
            </a-input>
          </a-col><a-col :flex="5" />
        </a-row>
        <a-row style="margin-top: 10px;">
          <a-col :flex="5" /><a-col :flex="3">
            <a-input size="large" placeholder="请输入密码" allow-clear long v-model:model-value="password">
              <template #prefix><icon-lock /></template>
            </a-input>
          </a-col><a-col :flex="5" />
        </a-row>
        <a-row style="margin-top: 20px;">
          <a-col :flex="20" /><a-col :flex="4">
            <a-button class="login-button" long size="large" @click="handleLogin">登陆</a-button>
          </a-col>
          <a-col :flex="1" /><a-col :flex="4">
            <a-button class="login-button" long size="large" @click="handleSign">注册</a-button>
          </a-col><a-col :flex="20" />
        </a-row>
      </a-layout-content>
    </a-layout>
  </div>
</template>

<script setup>
import {ref} from "vue";
import {Message} from "@arco-design/web-vue";
import {useAccountStore} from "@/store/account";
const account = ref(undefined);
const password = ref(undefined);
const accountStore = useAccountStore();

async function handleLogin() {
  if (account.value === undefined || password.value === undefined
      || account.value === '' || password.value === '') {
    Message.warning("请输入有效的用户名和密码");
  }
  await accountStore.postUserLogin(account.value, password.value)
      .then((_) => {
        Message.success('登陆成功');
        account.value = undefined;
        password.value = undefined;
      })
      .catch((_) => Message.warning('登陆失败,请检查用户名和密码'));
}

async function handleSign() {
  if (account.value === undefined || password.value === undefined
      || account.value === '' || password.value === '') {
    Message.warning("请输入有效的用户名和密码");
  }
  await accountStore.postUserSign(account.value, password.value)
      .then((_) => {
        Message.success('注册成功,已为您自动登陆');
        account.value = undefined;
        password.value = undefined;
      })
      .catch((_) => Message.warning('注册失败,请检查用户名或密码'));
}
</script>

<style scoped>
@import "@/style/app.css";
@import "@/style/login.css";
</style>
