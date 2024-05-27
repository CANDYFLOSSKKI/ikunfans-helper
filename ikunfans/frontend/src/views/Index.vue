<template>
  <div class="main-layout">
    <a-layout class="main-layout-frame">
      <a-layout-header class="main-layout-header" :style="{ backgroundColor: '#FEAFBF' }">
        <a-layout class="main-layout-frame">
          <a-layout-header class="main-layout-header-part-drag" />
          <a-layout-content class="main-layout-header-part-logo">
            <a-row>
              <a-col :flex="5"><img alt=""
                     :src="IMG_LOGO"
                     class="main-layout-logo-img"
                     @mouseover="changeImgLogo(IMG_LOGO_ORIGIN)"
                     @mouseout="changeImgLogo(IMG_LOGO_WHITE)"
                     @click="handleSkipToHomePage"
              ></a-col>
              <a-col :flex="5"><div class="main-logo-font">IKUNFANS</div></a-col>
              <a-col :flex="60" />
              <a-col :flex="3"><img id="ImgAccount" alt=""
                     :src="IMG_ACCOUNT"
                     class="main-layout-func-img"
                     @mouseover="changeImgAccount(IMG_ACCOUNT_ORIGIN)"
                     @mouseout="changeImgAccount(IMG_ACCOUNT_WHITE)"
                     @click="handleSkipToAccountPage"
              ></a-col>
              <a-col :flex="3"><img id="ImgSetting" alt=""
                     :src="IMG_SETTING"
                     class="main-layout-func-img"
                     @mouseover="changeImgSetting(IMG_SETTING_ORIGIN)"
                     @mouseout="changeImgSetting(IMG_SETTING_WHITE)"
                     @click="handleSkipToSettingPage"
              ></a-col>
              <a-col :flex="1" /><a-col :flex="2"><img id="ImgOff" alt=""
                     :src="IMG_OFF"
                     class="main-layout-func-img"
                     @mouseover="changeImgOff(IMG_OFF_ORIGIN)"
                     @mouseout="changeImgOff(IMG_OFF_WHITE)"
                     @click="handleCloseFrame"
              ></a-col><a-col :flex="1" />
            </a-row>
          </a-layout-content>
        </a-layout>
      </a-layout-header>
      <a-layout-content class="main-layout-content">
        <login-page v-show="loginPageShow" />
        <home-page v-show="homePageShow" />
        <account-page v-show="accountPageShow" />
        <setting-page v-show="settingPageShow" />
        <detail-page v-show="detailPageShow" />
      </a-layout-content>
    </a-layout>
  </div>
</template>

<script setup>
import {computed, onMounted, ref, watch} from "vue";
import { ipc } from '@/utils/ipcRenderer';
import IMG_LOGO_ORIGIN from "@/assets/logo-origin.png";
import IMG_LOGO_WHITE from "@/assets/logo-white.png";
import IMG_ACCOUNT_ORIGIN from "@/assets/account-origin.png";
import IMG_ACCOUNT_WHITE from "@/assets/account-white.png";
import IMG_SETTING_ORIGIN from "@/assets/setting-origin.png";
import IMG_SETTING_WHITE from "@/assets/setting-white.png";
import IMG_OFF_ORIGIN from "@/assets/off-origin.png";
import IMG_OFF_WHITE from "@/assets/off-white.png";
import HomePage from "@/components/home-page/index.vue";
import AccountPage from "@/components/account-page/index.vue";
import SettingPage from "@/components/setting-page/index.vue";
import LoginPage from "@/components/login-page/index.vue";
import DetailPage from "@/components/detail-page/index.vue";
import {useAccountStore} from "@/store/account";
import {useSubjectStore} from "@/store/subject";
import {storeToRefs} from "pinia";
import {resetAuthToken, setAuthToken} from "@/utils/http";
import {Message} from "@arco-design/web-vue";
import {AlertViewEventBus} from "@/utils/mitt";
const ipcApiRoute = { close: 'controller.example.close' }
const accountStore = useAccountStore();
const subjectStore = useSubjectStore();
const { loginFlag, loginToken } = storeToRefs(accountStore);

const IMG_LOGO = ref(IMG_LOGO_WHITE);
const IMG_ACCOUNT = ref(IMG_ACCOUNT_WHITE);
const IMG_SETTING = ref(IMG_SETTING_WHITE);
const IMG_OFF = ref(IMG_OFF_WHITE);

const PAGE_HOME_SHOW_FLAG = ref(true);
const PAGE_ACCOUNT_SHOW_FLAG = ref(false);
const PAGE_SETTING_SHOW_FLAG = ref(false);
const PAGE_DETAIL_SHOW_FLAG = ref(false);

const changeImgAccount = (img) => IMG_ACCOUNT.value = img;
const changeImgSetting = (img) => IMG_SETTING.value = img;
const changeImgOff = (img) => IMG_OFF.value = img;
const changeImgLogo = (img) => IMG_LOGO.value = img;

const loginPageShow = computed(() => !loginFlag.value);
const homePageShow = computed(() => PAGE_HOME_SHOW_FLAG.value && loginFlag.value);
const accountPageShow = computed(() => PAGE_ACCOUNT_SHOW_FLAG.value && loginFlag.value);
const settingPageShow = computed(() => PAGE_SETTING_SHOW_FLAG.value && loginFlag.value);
const detailPageShow = computed(() => PAGE_DETAIL_SHOW_FLAG.value && loginFlag.value);

async function handleSkipToHomePage() {
  if (!loginFlag.value) { return; }
  if (homePageShow.value) {
    await subjectStore.getSubjectCalendar();
  } else {
    await subjectStore.getSubjectCalendar().then((_) => {
      PAGE_ACCOUNT_SHOW_FLAG.value = false;
      PAGE_SETTING_SHOW_FLAG.value = false;
      PAGE_DETAIL_SHOW_FLAG.value = false;
      PAGE_HOME_SHOW_FLAG.value = true;
    });
  }
}

async function handleSkipToAccountPage() {
  if (!loginFlag.value) { return; }
  if (accountPageShow.value) {
    await subjectStore.getSubjectUser();
  } else {
    await subjectStore.getSubjectUser().then((_) => {
      PAGE_HOME_SHOW_FLAG.value = false;
      PAGE_SETTING_SHOW_FLAG.value = false;
      PAGE_DETAIL_SHOW_FLAG.value = false;
      PAGE_ACCOUNT_SHOW_FLAG.value = true;
    });
  }
}

function handleSkipToSettingPage() {
  if (!loginFlag.value || settingPageShow.value) { return; }
  PAGE_HOME_SHOW_FLAG.value = false;
  PAGE_ACCOUNT_SHOW_FLAG.value = false;
  PAGE_DETAIL_SHOW_FLAG.value = false;
  PAGE_SETTING_SHOW_FLAG.value = true;
}

function handleSkipToDetailPage() {
  PAGE_HOME_SHOW_FLAG.value = false;
  PAGE_ACCOUNT_SHOW_FLAG.value = false;
  PAGE_SETTING_SHOW_FLAG.value = false;
  PAGE_DETAIL_SHOW_FLAG.value = true;
}

watch(loginFlag, async (newValue, _) => {
  if (newValue) {
    setAuthToken(loginToken.value);
    await accountStore.getUserInfo().then((_) => handleSkipToHomePage());
  }
})

AlertViewEventBus.on("logout", async (_) => {
  resetAuthToken();
  subjectStore.clearSubjeFEctList();
  accountStore.logout();
  PAGE_HOME_SHOW_FLAG.value = false;
  PAGE_ACCOUNT_SHOW_FLAG.value = false;
  PAGE_SETTING_SHOW_FLAG.value = false;
  PAGE_DETAIL_SHOW_FLAG.value = false;
})

AlertViewEventBus.on("detail", async (_) => {
  handleSkipToDetailPage();
})

function handleCloseFrame() {
  ipc.invoke(ipcApiRoute.close, { flag: true });
}
</script>

<style scoped>
@import "@/style/app.css";
</style>
