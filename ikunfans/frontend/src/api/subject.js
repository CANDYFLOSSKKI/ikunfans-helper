import {httpGet, httpPost} from "@/utils/http";

const API_SUBJECT_CONTENT = "/subject/content";
const API_SUBJECT_USER = "/subject/user"

export async function postSubjectContentAPI(content) {
    let req = {
        content: content
    };
    return await httpPost(API_SUBJECT_CONTENT, req);
}

export async function getSubjectUserAPI() {
    return await httpGet(API_SUBJECT_USER);
}


