import {defineStore} from "pinia";
import {ref} from "vue";
import {getUserInfoAPI, postUserLoginAPI, postUserSignAPI} from "@/api/account";
import {httpErrorOutput} from "@/utils/http";
import {isDefaultDataRespValid, isDefaultMsgRespValid} from "@/utils/process";

export const useAccountStore = defineStore('account', () => {
    const userInfo = ref({ account: undefined });
    const loginFlag = ref(false);
    const loginToken = ref(undefined);

    function logout() {
        userInfo.value = { account: undefined };
        loginFlag.value = false;
        loginToken.value = undefined;
    }

    async function postUserLogin(account, password) {
        await postUserLoginAPI(account, password).then((res) => {
            if (!isDefaultMsgRespValid(res)) { return Promise.reject({ type: '用户登陆' }); }
            loginToken.value = res.msg;
            loginFlag.value = true;
        }).catch((err) => httpErrorOutput(err));
    }

    async function postUserSign(account, password) {
        await postUserSignAPI(account, password).then((res) => {
            if (!isDefaultMsgRespValid(res)) { return Promise.reject({ type: '用户注册' }); }
            loginToken.value = res.msg;
            loginFlag.value = true;
        }).catch((err) => httpErrorOutput(err));
    }

    async function getUserInfo() {
        await getUserInfoAPI().then((res) => {
            if (!isDefaultDataRespValid(res)) { return Promise.reject({ type: '用户信息' }); }
            userInfo.value = res.data;
        }).catch((err) => httpErrorOutput(err));
    }

    return {
        userInfo,
        loginFlag,
        loginToken,
        postUserLogin,
        postUserSign,
        getUserInfo,
        logout,
    }
})
