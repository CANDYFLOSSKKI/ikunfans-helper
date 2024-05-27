import {defineStore} from "pinia";
import {ref} from "vue";
import {
    getSubjectCalendarAPI,
    getSubjectUserAPI,
    postSubjectContentAPI,
    postSimpleSubjectContentAPI, getSubjectDetailAPI, processUserAddSubjectAPI, processUserRemoveSubjectAPI,
} from "@/api/subject";
import {httpErrorOutput} from "@/utils/http";
import {isDefaultDataRespValid, isDefaultMsgRespValid} from "@/utils/process";

export const useSubjectStore = defineStore('subject', () => {
    const calendarDate = ref(undefined);
    const calendarDateCN = ref(undefined);
    const calendarDateJP = ref(undefined);
    const animeCalendarSubjectList = ref(Array.of());

    const subjectPropose = ref(undefined);
    const subjectKeywords = ref(Array.of());
    const animeSubjectList = ref(Array.of());

    const animeUserSubjectList = ref(Array.of());

    const animeSubjectDetail = ref({id: 1, nameCN: '', name: '', image: '', summary: '', platform: '', date: '', score: 0.0, rank: 0, eps: 0, onHold: 0, dropped: 0, wish: 0, collect: 0, doing: 0, infobox: Array.of(), isFavorite: false,});

    function clearSubjectList() {
        calendarDate.value = undefined;
        calendarDateCN.value = undefined;
        calendarDateJP.value = undefined;
        animeCalendarSubjectList.value = Array.of();
        subjectPropose.value = undefined;
        subjectKeywords.value = Array.of();
        animeSubjectList.value = Array.of();
        animeUserSubjectList.value = Array.of();
        animeSubjectDetail.value = {id: 1, nameCN: '', name: '', image: '', summary: '', platform: '', date: '', score: 0.0, rank: 0, eps: 0, onHold: 0, dropped: 0, wish: 0, collect: 0, doing: 0, infobox: Array.of(), isFavorite: false,}
    }

    async function getSubjectCalendar() {
        await getSubjectCalendarAPI().then((res) => {
            if (!isDefaultDataRespValid(res)) { return Promise.reject({ type: "番剧日历" }); }
            calendarDate.value = res.data.date;
            calendarDateCN.value = res.data.cn;
            calendarDateJP.value = res.data.jp;
            animeCalendarSubjectList.value = res.data.infoList;
        })
    }

    async function postSubjectContent(content, mode) {
        await postSubjectContentAPI(content, mode).then((res) => {
            if (!isDefaultDataRespValid(res)) { return Promise.reject({ type: '番剧推荐' }); }
            subjectPropose.value = res.data.propose;
            subjectKeywords.value = res.data.keywords;
            animeSubjectList.value = res.data.subjects;
        }).catch((err) => httpErrorOutput(err));
    }

    async function postSimpleSubjectContent(content, mode) {
        await postSimpleSubjectContentAPI(content, mode).then((res) => {
            if (!isDefaultDataRespValid(res)) { return Promise.reject({ type: '番剧搜索' }); }
            subjectPropose.value = undefined;
            subjectKeywords.value = Array.of();
            animeSubjectList.value = res.data.subjects;
        }).catch((err) => httpErrorOutput(err));
    }

    async function getSubjectUser() {
        await getSubjectUserAPI().then((res) => {
            if (!isDefaultDataRespValid(res)) { return Promise.reject({ type: '用户收藏' }); }
            animeUserSubjectList.value = res.data;
        }).catch((err) => httpErrorOutput(err));
    }

    async function getSubjectDetail(id) {
        await getSubjectDetailAPI(id).then((res) => {
            if (!isDefaultDataRespValid(res)) { return Promise.reject({ type: '番剧详情' }); }
            animeSubjectDetail.value = res.data;
        }).catch((err) => httpErrorOutput(err));
    }

    async function processAddFavorite(id) {
        await processUserAddSubjectAPI(id).then((res) => {
            if (!isDefaultMsgRespValid(res)) { return Promise.reject({ type: '增加收藏' }); }
            animeSubjectDetail.value.isFavorite = true;
        }).catch((err) => httpErrorOutput(err));
    }

    async function processRemoveFavorite(id) {
        await processUserRemoveSubjectAPI(id).then((res) => {
            if (!isDefaultMsgRespValid(res)) { return Promise.reject({ type: '移除收藏' }); }
            animeSubjectDetail.value.isFavorite = false;
        }).catch((err) => httpErrorOutput(err));
    }

    return {
        calendarDate,
        calendarDateCN,
        calendarDateJP,
        animeCalendarSubjectList,
        subjectPropose,
        subjectKeywords,
        animeSubjectList,
        animeUserSubjectList,
        animeSubjectDetail,
        clearSubjectList,
        getSubjectCalendar,
        postSubjectContent,
        postSimpleSubjectContent,
        getSubjectUser,
        getSubjectDetail,
        processAddFavorite,
        processRemoveFavorite,
    }
})
