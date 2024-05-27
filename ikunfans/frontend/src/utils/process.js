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


export function processSubjectNameInCalendar(name) {
    return name.substring(0, 25) + (name.length > 25 ? '...' : '');
}

export function processSubjectScoreInCalendar(score) {
    return score === 0 ? "暂无" : score.toFixed(2).toString();
}

export function processSubjectRankInCalendar(rank) {
    return rank === 0 ? "暂无" : rank.toString();
}

export function processSubjectSummaryInSearch(summary) {
    return summary.substring(0, 180) + (summary.length > 180 ? '...' : '');
}

export function processSubjectDetailKV(value) {
    return value.substring(0, 40) + (value.length > 40 ? '...' : '');
}

export function processArrayOfEps(eps) {
    let result = [];
    for (let i = 1;; i++) {
        if (i > eps) { break; }
        if (i > 30) {
            result.push('...');
            break;
        }
        result.push(i);
    }
    return result;
}

export function processArrayOfEpsIndex(index) {
    if (index === '...') { return index; }
    return index < 10 ? `0${index}` : index.toString();
}
