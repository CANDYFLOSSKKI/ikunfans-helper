import {defineStore} from "pinia";
import {ref} from "vue";
import {getSubjectUserAPI, postSubjectContentAPI} from "@/api/subject";
import {httpErrorOutput} from "@/utils/http";
import {isDefaultDataRespValid} from "@/utils/process";

export const useSubjectStore = defineStore('subject', () => {
    const animeShowFlag = ref(false);
    const animeSubjectList = ref(Array.of());

    const animeUserShowFlag = ref(false);
    const animeUserSubjectList = ref(Array.of());

    async function postSubjectContent(content) {
        await postSubjectContentAPI(content).then((res) => {
            if (!isDefaultDataRespValid(res)) { return Promise.reject({ type: 'AI推荐' }); }
            animeSubjectList.value = res.data;
            if (animeSubjectList.value.length > 0) {
                animeShowFlag.value = true;
            }
        }).catch((err) => httpErrorOutput(err));
    }

    async function getSubjectUser() {
        await getSubjectUserAPI().then((res) => {
            if (!isDefaultDataRespValid(res)) { return Promise.reject({ type: '用户看过' }); }
            animeUserSubjectList.value = res.data;
            if (animeUserSubjectList.value.length > 0) {
                animeUserShowFlag.value = true;
            }
        }).catch((err) => httpErrorOutput(err));
    }

    return {
        animeShowFlag,
        animeSubjectList,
        animeUserShowFlag,
        animeUserSubjectList,
        postSubjectContent,
        getSubjectUser,
    }
})
