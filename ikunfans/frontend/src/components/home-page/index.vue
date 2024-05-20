<template>
  <a-scrollbar type="track" style="height: 539px; overflow: auto;">
    <div class="main-layout-frame">
      <a-row class="home-text-row">
        <a-col :flex="3" />
        <a-col :flex="50"><a-input placeholder="有什么想看的动画，告诉IKUNFANS AI吧"
                                   :max-length="20"
                                   size="large"
                                   style="height: 50px;"
                                   allow-clear
                                   show-word-limit
                                   v-model:model-value="searchContent"
        >
          <template #prefix>
            <icon-message />
          </template>
        </a-input></a-col>
        <a-col :flex="1" />
        <a-col :flex="3">
          <a-button large style="margin-top: 8px;"
                    :loading="searchLoading"
                    @click="handleAnimeSearch"
          ><template #icon><icon-search /></template></a-button>
        </a-col>
        <a-col :flex="2" />
      </a-row>
      <a-row v-for="item in animeSubjectList" :key="item.id" >
        <a-card class="home-anime-card">
          <a-layout class="home-layout">
            <a-layout-sider class="home-anime-card-sider" style="width: 125px;">
              <a-image width="125" height="170" :src="item.image" />
            </a-layout-sider>
            <a-layout-content class="home-anime-card-content">
              <a-row style="overflow: hidden;">
                <a-col :flex="50">
                  <div class="anime-title">{{ item.nameCN }}</div>
                </a-col>
                <a-col :flex="5">
                  <a-popover>
                    <template #content>
                      <a-row><a-button style="background-color: #FEAFBF; color: white; width: 150px;" @click="handleTransToBangumi(item)">跳转至Bangumi</a-button></a-row>
                      <a-row style="margin-top: 5px;"><a-button style="background-color: #FEAFBF; color: white; width: 150px;" @click="handleTransToMikan(item)">跳转至Mikan</a-button></a-row>
                      <a-row style="margin-top: 5px;"><a-button style="background-color: #FEAFBF; color: white; width: 150px;" @click="handleTransToDmhy(item)">跳转至动漫花园</a-button></a-row>
                    </template>
                    <a-button style="background-color: #FEAFBF; color: white">链接跳转</a-button>
                  </a-popover>
                </a-col>
              </a-row>
              <a-row style="overflow: hidden;"><div class="anime-sub-title">{{ item.name }}&nbsp&nbsp({{ item.airDate }})</div></a-row>
              <a-row style="overflow: hidden; margin-left: -5px;"><a-tag v-for="tag in item.tags" color="magenta" style="margin-left: 5px; margin-top: 5px;">{{ tag }}</a-tag></a-row>
              <a-row style="overflow: hidden;"><div class="anime-summary">{{ itemSummarySubstr(item.summary) }}</div></a-row>
              <a-row style="overflow: hidden; margin-top: 5px">
                <a-tag color="orangered" size="large">
                  <template #icon><icon-star/></template>
                  <div style="font-size: 12px;">Star&nbsp</div>
                  <div style="font-weight: bold;">{{ item.score }}</div>
                </a-tag>
                <a-tag color="blue" size="large" style="margin-left: 10px;">
                  <template #icon><icon-thumb-up /></template>
                  <div style="font-size: 12px;">Rank&nbsp</div>
                  <div style="font-weight: bold;">{{ item.rank }}</div>
                </a-tag>
              </a-row>
            </a-layout-content>
          </a-layout>
        </a-card>
      </a-row>
    </div>
  </a-scrollbar>
</template>

<script setup>
import {ref, watch} from "vue";
import {Message} from "@arco-design/web-vue";
import {useSubjectStore} from "@/store/subject";
import {storeToRefs} from "pinia";
import {ipc} from "@/utils/ipcRenderer";
const subjectStore = useSubjectStore();
const {animeShowFlag, animeSubjectList} = storeToRefs(subjectStore);
const searchContent = ref(undefined);
const searchLoading = ref(false);
const searchResultShowFlag = ref(false);
const itemSummarySubstr = (summary) => summary.substring(0, 130) + '...';

async function handleAnimeSearch() {
  searchLoading.value = true;
  if (searchContent.value === undefined || searchContent.value.length === 0) {
    Message.warning("请输入有效的文字");
    searchLoading.value = false;
  }
  await subjectStore.postSubjectContent(searchContent.value)
      .then((_) => setTimeoutReleaseSearch())
      .catch((_) => {
        Message.warning('远端服务繁忙,请稍后再试');
        searchLoading.value = false;
      });
}

function setTimeoutReleaseSearch() {
  setTimeout(() => {
    searchLoading.value = false;
  }, 60000);
}

const homeIpcApiRoute = { tran: 'controller.example.tran' }
function handleTransToBangumi(item) {
  ipc.invoke(homeIpcApiRoute.tran, { url: 'https://bangumi.tv/subject/' + item.id });
}

function handleTransToMikan(item) {
  ipc.invoke(homeIpcApiRoute.tran, { url: 'https://mikanani.me/Home/Search?searchstr=' + item.nameCN })
}

function handleTransToDmhy(item) {
  ipc.invoke(homeIpcApiRoute.tran, { url: 'https://www.dmhy.org/topics/list?keyword=' + item.nameCN })
}
</script>

<style scoped>
@import "@/style/app.css";
@import "@/style/home.css";
</style>
