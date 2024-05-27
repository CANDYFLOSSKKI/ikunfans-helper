<template>
  <a-scrollbar type="track" style="height: 1200px;overflow: auto;">
    <div class="main-layout-frame">
      <a-row class="home-text-row-v2">
        <a-col :flex="2" />
        <a-col :flex="40"><div style="font-size: 20px;font-weight: bold;color: gray;">欢迎回来,&nbsp;{{ userInfo.account }}</div></a-col>
      </a-row>
      <a-row class="home-text-row-v3">
        <a-col :flex="2" />
        <a-col :flex="40"><div style="font-size: 20px;font-weight: bold;color: gray;">今天是&nbsp;{{ calendarDate }}&nbsp;{{ calendarDateCN }}&nbsp;({{ calendarDateJP }})</div></a-col>
      </a-row>
      <a-row class="home-text-row-v2">
        <a-col :flex="2" />
        <a-col :flex="40"><div style="font-size: 25px;font-weight: bold;color: #FEAFBF;">每日放送</div></a-col>
      </a-row>
      <a-collapse :default-active-key="[1]" accordion class="home-collapse">
        <a-collapse-item v-for="item in animeCalendarSubjectList" :key="item.index">
          <template #header>
            <div style="font-size: 18px;font-weight: bold;color: #FEAFBF;">{{ item.cn }}&nbsp;({{ item.jp }})</div>
          </template>
          <a-row v-for="subject in item.subjects" class="home-calendar-row">
            <a-col :span="1" style="margin-left: 20px">
              <a-image :src="subject.image"></a-image>
            </a-col>
            <a-col :span="14" style="margin-left: 20px;">
              <a-layout>
                <a-layout-header style="height: 70%">
                  <a-link style="font-size: 17px;font-weight: bold;color:black" @click="handleSwitchToDetail(subject.id)">{{ processSubjectNameInCalendar(subject.nameCN) }}</a-link>
                </a-layout-header>
                <a-layout-content style="height: 30%">
                  <div style="margin-left: 5px;font-size: 14px;color: darkgrey;">{{ processSubjectNameInCalendar(subject.name) }}</div>
                </a-layout-content>
              </a-layout>
            </a-col>
            <a-col :span="3">
              <a-tag color="green" size="large" style="margin-top: 8px;">
                <div style="font-size: 14px;font-weight: bold;">{{ subject.airDate }}</div>
              </a-tag>
            </a-col>
            <a-col :span="2">
              <a-tag color="orangered" size="large" style="margin-top: 8px;margin-left: 2px;">
                <template #icon><icon-star/></template>
                <div style="font-size: 14px;font-weight: bold;">{{ processSubjectScoreInCalendar(subject.score) }}</div>
              </a-tag>
            </a-col>
            <a-col :span="2">
              <a-tag color="blue" size="large" style="margin-top: 8px;margin-left: 5px;">
                <template #icon><icon-bar-chart /></template>
                <div style="font-size: 14px;font-weight: bold;">{{ processSubjectRankInCalendar(subject.rank) }}</div>
              </a-tag>
            </a-col>
          </a-row>
        </a-collapse-item>
      </a-collapse>
      <a-row class="home-text-row-v2">
        <a-col :flex="2" />
        <a-col :flex="40"><div style="font-size: 25px;font-weight: bold;color: #FEAFBF;">番剧搜索</div></a-col>
      </a-row>
      <a-row class="home-text-row-v3">
        <a-col :span="7" style="margin-left: 45px;">
          <a-radio-group type="button" v-model:model-value="searchMode">
            <a-radio :value="1" style="color: #FEAFBF;">关键字搜索</a-radio>
            <a-radio :value="2" style="color: #FEAFBF;">AI大模型智能推荐搜索</a-radio>
          </a-radio-group>
        </a-col>
        <a-col :span="7">
          <a-radio-group type="button" v-model:model-value="searchInterfaceEntry">
            <a-radio :value="1" style="color: #FEAFBF;">远端接口1(推荐)</a-radio>
            <a-radio :value="2" style="color: #FEAFBF;">远端接口2(备用)</a-radio>
          </a-radio-group>
        </a-col>
      </a-row>
      <a-row class="home-text-row">
        <a-col :flex="1" />
        <a-col :flex="42" style="margin-left: 25px;"><a-input placeholder="有什么想看的动画" :max-length="30" size="large" style="height: 50px;" allow-clear show-word-limit v-model:model-value="searchContent">
          <template #prefix>
            <icon-message />
          </template>
        </a-input></a-col>
        <a-col :flex="3" style="margin-left: 10px;">
          <a-button large style="margin-top: 8px;" @click="handleAnimeSearch" :disabled="searchLoading">
            <template #icon><icon-search /></template>
          </a-button>
        </a-col>
        <a-col :flex="2" />
      </a-row>
      <a-row class="home-anime-propose-row" v-show="searchMode === 2">
        <a-col :span="2">
          <div style="margin-top: 15px;margin-left: 15px;">
            <icon-code-sandbox size="50" style="color: #FEAFBF;" />
          </div>
        </a-col>
        <a-col :span="21" style="margin-left: 10px;margin-top: 10px;">
          <a-scrollbar type="track" style="height: 130px;overflow: auto;">
            <div style="font-size: 14px;line-height: 18px;">{{ subjectPropose }}</div>
          </a-scrollbar>
        </a-col>
      </a-row>
      <a-row v-for="item in animeSubjectList" :key="item.id" >
        <a-card class="home-anime-card">
          <a-layout class="home-layout">
            <a-layout-sider class="home-anime-card-sider" style="width: 125px;">
              <a-image width="125" height="170" :src="item.image" />
            </a-layout-sider>
            <a-layout-content class="home-anime-card-content">
              <a-row style="overflow: hidden;">
                <a-link class="anime-title" @click="handleSwitchToDetail(item.id)">{{ item.nameCN }}</a-link>
              </a-row>
              <a-row style="overflow: hidden; margin-left: 5px;"><div class="anime-sub-title">{{ item.name }}</div></a-row>
              <a-row style="margin-left: 5px;margin-right: 10px;overflow: hidden;"><div class="anime-summary">{{ processSubjectSummaryInSearch(item.summary) }}</div></a-row>
              <a-row style="overflow: hidden; margin-top: 10px;margin-left: 5px;">
                <a-tag color="green" size="large">
                  <div style="font-size: 14px;font-weight: bold;">{{ item.airDate }}</div>
                </a-tag>
                <a-tag color="orangered" size="large" style="margin-left: 10px;">
                  <template #icon><icon-star/></template>
                  <div style="font-size: 12px;">Star&nbsp</div>
                  <div style="font-weight: bold;">{{ processSubjectScoreInCalendar(item.score) }}</div>
                </a-tag>
                <a-tag color="blue" size="large" style="margin-left: 10px;">
                  <template #icon><icon-bar-chart /></template>
                  <div style="font-size: 12px;">Rank&nbsp</div>
                  <div style="font-weight: bold;">{{ processSubjectRankInCalendar(item.rank) }}</div>
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
import {ref} from "vue";
import {Message} from "@arco-design/web-vue";
import {useSubjectStore} from "@/store/subject";
import {storeToRefs} from "pinia";
import {useAccountStore} from "@/store/account";
import {
  processSubjectNameInCalendar,
  processSubjectRankInCalendar,
  processSubjectScoreInCalendar, processSubjectSummaryInSearch
} from "@/utils/process";
import {AlertViewEventBus} from "@/utils/mitt";
const subjectStore = useSubjectStore();
const accountStore = useAccountStore();
const {calendarDate, calendarDateCN, calendarDateJP, animeCalendarSubjectList, subjectPropose, subjectKeywords, animeSubjectList} = storeToRefs(subjectStore);
const {userInfo} = storeToRefs(accountStore);
const searchMode = ref(1);
const searchInterfaceEntry = ref(1);
const searchContent = ref(undefined);
const searchLoading = ref(false);

async function handleAnimeSearch() {
  if (searchContent.value === undefined || searchContent.value.length === 0) { Message.warning("请输入有效的内容"); }
  searchLoading.value = true;
  if (searchMode.value === 1) {
    await subjectStore.postSimpleSubjectContent(searchContent.value, searchInterfaceEntry.value)
        .then((_) => searchLoading.value = false)
        .catch((_) => {
          searchLoading.value = false;
          Message.warning('服务繁忙或该时间段请求次数过多,请稍后再试');
        });
  } else {
    Message.info({ content: "使用大模型搜索时间较长,请耐心等待", duration: 5000 });
    await subjectStore.postSubjectContent(searchContent.value, searchInterfaceEntry.value)
        .then((_) => searchLoading.value = false)
        .catch((_) => {
          searchLoading.value = false;
          Message.warning('服务繁忙或该时间段请求次数过多,请稍后再试');
        });
  }
}

async function handleSwitchToDetail(id) {
  await subjectStore.getSubjectDetail(id)
      .then((_) => AlertViewEventBus.emit("detail", true))
      .catch((_) => { Message.warning('服务繁忙或该时间段请求次数过多,请稍后再试'); });
}

</script>

<style scoped>
@import "@/style/app.css";
@import "@/style/home.css";
</style>
