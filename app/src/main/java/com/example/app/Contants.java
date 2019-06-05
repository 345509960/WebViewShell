package com.example.app;

/**
* @author lyc
* create date 2019/6/5 4:19 PM
* des 常量类
* update date
**/
public class Contants {

    public static final String SPECIAL_MARK_DOT = ".";
    public static final String SPECIAL_MARK_HTML = "html";


    /**
     * WebView 网页回调的处理码
     */
    public static final String WEBVIEW_ERROR_CODE_DISCONNECT_ERROR_MSG = "net::ERR_NAME_NOT_RESOLVED";
    public static final String WEBVIEW_ERROR_CODE_DISCONNECT_EMPTY_ERROR_MSG = "net::ERR_EMPTY_RESPONSE";
    /**
     * 兼容OPPO 8.0超时问题
     */
    public static final String WEBVIEW_ERROR_CODE_DISCONNECT_CONNECTION_TIMED_OUT_MSG = "net::ERR_CONNECTION_TIMED_OUT";
    /**
     * 华为超时兼容
     */
    public static final String WEBVIEW_ERROR_CODE_HUIWEI_TIMED_OUT = "net::ERR_TIMED_OUT";

    public static final String WEBVIEW_ERROR_CODE_ERR_NAME_NOT_RESOLVED = "net::ERR_NAME_NOT_RESOLVED";
}
