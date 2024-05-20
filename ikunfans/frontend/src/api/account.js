import {httpGet, httpPost} from "@/utils/http";

const API_USER_LOGIN = '/user/login';
const API_USER_SIGN = '/user/sign';
const API_USER_GET_INFO = '/user/info';

export async function postUserLoginAPI(account, password) {
    let req = {
        account: account,
        password: password,
    };
    return await httpPost(API_USER_LOGIN, req);
}

export async function postUserSignAPI(account, password) {
    let req = {
        account: account,
        password: password,
    };
    return await httpPost(API_USER_SIGN, req);
}

export async function getUserInfoAPI() {
    return await httpGet(API_USER_GET_INFO, {});
}
