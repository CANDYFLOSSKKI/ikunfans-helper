import {httpGet, httpPost} from "@/utils/http";

const API_USER_LOGIN = '/user/login';
const API_USER_SIGN = '/user/sign';
const API_USER_GET_INFO = '/user/info';

export async function postUserLoginAPI(account, password) {
    return await httpPost(API_USER_LOGIN, {
        account: account,
        password: password,
    });
}

export async function postUserSignAPI(account, password) {
    return await httpPost(API_USER_SIGN, {
        account: account,
        password: password,
    });
}

export async function getUserInfoAPI() {
    return await httpGet(API_USER_GET_INFO);
}
