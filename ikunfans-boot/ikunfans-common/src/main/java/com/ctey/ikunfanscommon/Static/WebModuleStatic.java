package com.ctey.ikunfanscommon.Static;

public class WebModuleStatic {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_SUBJECT = "ctey";
    public static final long TOKEN_EXPIRATION = 1000 * 24 * 60 * 60 * 7;
    public static final String TOKEN_APP_SECRET_KEY = "26a0fc06229811a5afef7511a2666d472ec946ad3e66805ebae517a642d3f7e4";
    public static final String CORS_MAPPING = "/**";
    public static final String CORS_HEADERS = "*";
    public static final String CORS_METHODS = "*";
    public static final String CORS_ORIGIN_PATTERNS = "*";
    public static final int CORS_MAX_AGE = 3600;
    public static final int WEB_RESP_CODE_SUCCESS = 200;
    public static final int WEB_RESP_CODE_FAILURE = 500;
    public static final int ASYNC_SERVICE_TIMEOUT = 30000;
}
