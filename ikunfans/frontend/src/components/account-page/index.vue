<template>
  <a-scrollbar type="track" style="height: 539px; overflow: auto;">
    <div class="main-layout-frame">
      <a-row class="home-text-row-v2">
        <a-col :flex="2" />
        <a-col :flex="40"><div style="font-size: 20px;font-weight: bold;color: gray;">欢迎回来,&nbsp;{{ userInfo.account }}</div></a-col>
      </a-row>
      <a-row class="home-text-row-v3">
        <a-col :flex="2" />
        <a-col :flex="40"><div style="font-size: 20px;font-weight: bold;color: gray;">您收藏的动画</div></a-col>
      </a-row>
      <a-row v-for="item in animeUserSubjectList" :key="item.id" >
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
import {useSubjectStore} from "@/store/subject";
import {storeToRefs} from "pinia";
import {useAccountStore} from "@/store/account";
import {AlertViewEventBus} from "@/utils/mitt";
import {Message} from "@arco-design/web-vue";
import {
  processSubjectRankInCalendar,
  processSubjectScoreInCalendar, processSubjectSummaryInSearch
} from "@/utils/process";
const accountStore = useAccountStore();
const subjectStore = useSubjectStore();
const {animeUserSubjectList} = storeToRefs(subjectStore);
const {userInfo} = storeToRefs(accountStore);
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
