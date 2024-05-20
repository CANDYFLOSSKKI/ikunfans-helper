export function postConstructorFunc(func) {
    let executed = false;
    return function(...args) {
        if (executed) { return; }
        func(...args);
        executed = true;
    };
}

export function isDefaultDataRespValid(res) {
    return res.flag;
}

export function isDefaultMsgRespValid(res) {
    return res.flag;
}

export function isArrayEmpty(data) {
    return data === undefined || data === null || data.length === 0;
}
