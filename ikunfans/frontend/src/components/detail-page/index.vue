<template>
  <a-scrollbar type="track" style="height: 530px;overflow: auto;">
    <div class="main-layout-frame">
      <a-row class="detail-title-row">
        <a-layout>
          <a-layout-header style="height: 70%">
            <div style="font-size: 26px;font-weight: bold;color:black">{{ animeSubjectDetail.nameCN }}</div>
          </a-layout-header>
          <a-layout-content style="height: 30%">
            <div style="font-size: 16px;color: darkgrey;">{{ animeSubjectDetail.name }}</div>
          </a-layout-content>
        </a-layout>
      </a-row>
      <a-row class="detail-title-row-v2">
        <a-tag color="green" size="large" style="margin-top: 8px;">
          <div style="font-size: 14px;font-weight: bold;">{{ animeSubjectDetail.date }}</div>
        </a-tag>
        <a-tag color="orangered" size="large" style="margin-top: 8px;margin-left: 10px;">
          <template #icon><icon-star/></template>
          <div style="font-size: 14px;font-weight: bold;">{{ processSubjectScoreInCalendar(animeSubjectDetail.score) }}</div>
        </a-tag>
        <a-tag color="blue" size="large" style="margin-top: 8px;margin-left: 10px;">
          <template #icon><icon-bar-chart /></template>
          <div style="font-size: 14px;font-weight: bold;">{{ processSubjectRankInCalendar(animeSubjectDetail.rank) }}</div>
        </a-tag>
        <a-tag color="purple" size="large" style="margin-top: 8px;margin-left: 10px;">
          <div style="font-size: 14px;font-weight: bold;">{{ animeSubjectDetail.platform }}</div>
        </a-tag>
        <a-button large style="margin-top: 8px;margin-left: 10px;" @click="handleChangeFavorite(animeSubjectDetail.id)">
          <template #icon>
            <icon-heart size="25" style="color: #FEAFBF;" v-show="!animeSubjectDetail.isFavorite" />
            <icon-heart-fill size="25" style="color: #FEAFBF;" v-show="animeSubjectDetail.isFavorite" />
          </template>
        </a-button>
        <a-popover>
          <template #content>
            <a-row><a-button style="background-color: #FEAFBF; color: white; width: 150px;" @click="handleTransToBangumi(animeSubjectDetail.id)">跳转至Bangumi</a-button></a-row>
            <a-row style="margin-top: 5px;"><a-button style="background-color: #FEAFBF; color: white; width: 150px;" @click="handleTransToMikan(animeSubjectDetail.nameCN)">跳转至Mikan</a-button></a-row>
            <a-row style="margin-top: 5px;"><a-button style="background-color: #FEAFBF; color: white; width: 150px;" @click="handleTransToDmhy(animeSubjectDetail.nameCN)">跳转至动漫花园</a-button></a-row>
          </template>
          <a-button style="margin-left: 10px;margin-top: 8px;background-color: #FEAFBF; color: white">链接跳转</a-button>
        </a-popover>
      </a-row>
      <a-row class="detail-title-row">
        <a-layout>
          <a-layout-sider style="width: 25%">
            <a-image :src="animeSubjectDetail.image" width="215" height="300"></a-image>
          </a-layout-sider>
          <a-layout-content style="width: 70%;margin-left: 20px;">
            <a-row style="margin-bottom: 10px;">
              <a-col :span="4">
                <a-card class="detail-status-frame">
                  <div style="text-align: center;font-size: 14px;margin-top: -12px;">追番</div>
                  <div style="text-align: center;font-size: 22px;font-weight: bold;padding-top: 5px;">{{ animeSubjectDetail.doing }}</div>
                </a-card>
              </a-col>
              <a-col :span="4" style="margin-left: 10px;">
                <a-card class="detail-status-frame">
                  <div style="text-align: center;font-size: 14px;margin-top: -12px;">看过</div>
                  <div style="text-align: center;font-size: 22px;font-weight: bold;padding-top: 5px;">{{ animeSubjectDetail.collect }}</div>
                </a-card>
              </a-col>
              <a-col :span="4" style="margin-left: 10px;">
                <a-card class="detail-status-frame">
                  <div style="text-align: center;font-size: 14px;margin-top: -12px;">想看</div>
                  <div style="text-align: center;font-size: 22px;font-weight: bold;padding-top: 5px;">{{ animeSubjectDetail.wish }}</div>
                </a-card>
              </a-col>
              <a-col :span="4" style="margin-left: 10px;">
                <a-card class="detail-status-frame">
                  <div style="text-align: center;font-size: 14px;margin-top: -12px;">搁置</div>
                  <div style="text-align: center;font-size: 22px;font-weight: bold;padding-top: 5px;">{{ animeSubjectDetail.onHold }}</div>
                </a-card>
              </a-col>
              <a-col :span="4" style="margin-left: 10px;">
                <a-card class="detail-status-frame">
                  <div style="text-align: center;font-size: 14px;margin-top: -12px;">弃番</div>
                  <div style="text-align: center;font-size: 22px;font-weight: bold;padding-top: 5px;">{{ animeSubjectDetail.dropped }}</div>
                </a-card>
              </a-col>
            </a-row>
            <a-row>
              <a-col :span="1.5" v-for="item of processArrayOfEps(animeSubjectDetail.eps)">
                <a-tag size="medium" color="magenta" bordered style="margin-right: 3px;margin-bottom: 3px;">{{ processArrayOfEpsIndex(item) }}</a-tag>
              </a-col>
            </a-row>
            <a-row style="margin-top: 5px;">
              <div style="font-size: 16px; line-height: 20px;">{{ animeSubjectDetail.summary }}</div>
            </a-row>
          </a-layout-content>
        </a-layout>
      </a-row>
      <a-row class="home-text-row-v2" style="margin-bottom: 10px;">
        <a-col :flex="2" />
        <a-col :flex="40"><div style="font-size: 20px;font-weight: bold;">更多信息</div></a-col>
      </a-row>
      <a-row v-for="item in animeSubjectDetail.infobox" class="detail-title-row-v3">
        <a-card hoverable style="height: 35px;width: 900px;">
          <a-row>
            <icon-attachment size="25" style="margin-top: -11px;" />
            <div style="margin-top: -6px;margin-left: 5px;">
              {{ item.key }}&nbsp;:&nbsp;{{ processSubjectDetailKV(item.value) }}
            </div>
          </a-row>
        </a-card>
      </a-row>
    </div>
  </a-scrollbar>
</template>

<script setup>
import {useSubjectStore} from "@/store/subject";
import {storeToRefs} from "pinia";
import {ipc} from "@/utils/ipcRenderer";
import {Message} from "@arco-design/web-vue";
import {
  processArrayOfEps, processArrayOfEpsIndex, processSubjectDetailKV,
  processSubjectRankInCalendar,
  processSubjectScoreInCalendar
} from "@/utils/process";
const subjectStore = useSubjectStore();
const {animeSubjectDetail} = storeToRefs(subjectStore);

async function handleChangeFavorite(id, flag) {
  if (flag) {
    await subjectStore.processRemoveFavorite(id)
        .then((_) => Message.success('成功移除收藏'))
        .catch((_) => Message.warning('收藏状态更新失败'));
  } else {
    await subjectStore.processAddFavorite(id)
        .then((_) => Message.success('成功添加收藏'))
        .catch((_) => Message.warning('收藏状态更新失败'));
  }
}

const homeIpcApiRoute = { tran: 'controller.example.tran' }
function handleTransToBangumi(item) {
  ipc.invoke(homeIpcApiRoute.tran, { url: 'https://bangumi.tv/subject/' + item });
}

function handleTransToMikan(item) {
  ipc.invoke(homeIpcApiRoute.tran, { url: 'https://mikanani.me/Home/Search?searchstr=' + item })
}

function handleTransToDmhy(item) {
  ipc.invoke(homeIpcApiRoute.tran, { url: 'https://www.dmhy.org/topics/list?keyword=' + item })
}
</script>

<style scoped>
@import "@/style/app.css";
@import "@/style/home.css";
</style>
