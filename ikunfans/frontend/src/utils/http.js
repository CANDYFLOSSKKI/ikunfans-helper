import axios from 'axios'

const service = axios.create({
    baseURL: 'http://localhost:14800/api/v1',
    timeout: 10000,
    responseType: 'json',
    withCredentials: false,
    headers: {}
});

export function setAuthToken(token) {
    service.defaults.headers.common = {
        'Authorization': `Bearer ${token}`
    }
}

export function resetAuthToken(token) {
    service.defaults.headers.common = {};
}

export async function httpGet(url , params = {}) {
    return await request({
        url: url,
        params: params,
        method: 'get'
    });
}

export async function httpPost(url, data = {}) {
    return await request({
        url: url,
        data: data,
        method: 'post'
    });
}

export function httpErrorOutput(err) {
    const httpError = err;
    console.log(`请求(${httpError.type})${httpError.url}时发生错误 => ${httpError.type}\n错误信息 => ${httpError.message}\n完整信息 => ${err.toString()}`);
    return Promise.reject();
}

async function request(config) {
    try {
        const res = await service.request(config);
        return Promise.resolve(res.data);
    } catch (err) {
        const errorData = handleRequestError(config.url, config.method, err);
        return Promise.reject(errorData);
    }
}

function handleRequestError(url, method, err) {
    // 请求发送错误(无请求,默认值)
    let errorType = '请求发送错误';
    let errorMessage = err.message;
    // 响应状态错误(有请求有响应)
    if (err.response) {
        errorType = '请求响应错误';
        errorMessage = `${err.response.status} ${err.response.statusText}`;
    // 响应接收错误(有请求无响应)
    } else if (err.request) {
        errorType = '响应状态错误';
        errorMessage = err.request.message;
    }
    return {
        url: url,
        method: method === 'get' ? 'get' : 'post',
        type: errorType,
        message: errorMessage,
    };
}
