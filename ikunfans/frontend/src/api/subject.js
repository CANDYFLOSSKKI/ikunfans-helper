import {httpGet, httpPost} from "@/utils/http";

const API_SUBJECT_CONTENT = "/subject/content";
const API_SIMPLE_SUBJECT_CONTENT = "/subject/content/simple";
const API_SUBJECT_CALENDAR = "/subject/calendar";
const API_SUBJECT_USER = "/subject/user";
const API_SUBJECT_ADD_FAVORITE = "/subject/user/add";
const API_SUBJECT_REMOVE_FAVORITE = "/subject/user/remove";
const API_SUBJECT_DETAIL = "/subject/detail"

export async function postSubjectContentAPI(content, mode) {
    return await httpPost(API_SUBJECT_CONTENT, {
        content: content,
        mode: mode,
    });
}

export async function postSimpleSubjectContentAPI(content, mode) {
    return await httpPost(API_SIMPLE_SUBJECT_CONTENT, {
        content: content,
        mode: mode,
    });
}

export async function getSubjectCalendarAPI() {
    return await httpGet(API_SUBJECT_CALENDAR);
}

export async function getSubjectUserAPI() {
    return await httpGet(API_SUBJECT_USER);
}

export async function processUserAddSubjectAPI(id) {
    return await httpGet(API_SUBJECT_ADD_FAVORITE, {
        id: id,
    });
}

export async function processUserRemoveSubjectAPI(id) {
    return await httpGet(API_SUBJECT_REMOVE_FAVORITE, {
        id: id,
    });
}

export async function getSubjectDetailAPI(id) {
    return await httpGet(API_SUBJECT_DETAIL, {
        id: id,
    });
}
