package com.example.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
* @author lyc
* create date 2019/6/5 4:11 PM
* des  自定义 WebViewClient
* update date
**/
class CommonWebViewClient extends WebViewClient {

    private boolean mNeedCleanHistory;

    private WebViewClientListner mWebViewClientListner;


    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        if (mNeedCleanHistory) {
            view.clearHistory();
            mNeedCleanHistory = false;
        }
        super.doUpdateVisitedHistory(view, url, isReload);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Uri uri = Uri.parse(url);
        if (uri.getHost() != null && uri.getHost().contains("example.com")) {
            return false;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        view.getContext().startActivity(intent);
        return true;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
    }


    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        //通知UI做一些WebView启动时的界面操作
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        //解决有时候Cookie无法写入的问题，正常来说，Android系统使用文件管理浏览器中的
        //Cookie，采用的是异步操作，这样就可能会出现浏览器中有了Cookie而没有写入文件中，
        //那么下次启动的时候，Chrome浏览器就无法在文件中查出之前关闭程序时的Cookie，也就无法维护
        //这里使用比较粗暴的方法，直接让CookieManager写入Cookie，相应的会产生一些延时
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush();
        }
        //通知UI做一些WebView完成加载时的界面操作
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        // 处理错误
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!android.text.TextUtils.isEmpty(request.getUrl().getPath())) {
                noNetHandle(request.getUrl().getPath(), error.getDescription().toString(), 100);
            }

        }
    }


    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        // 处理错误
        if (!android.text.TextUtils.isEmpty(failingUrl)) {
            noNetHandle(failingUrl, description, errorCode);
        }

    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
        //Android 7.0 白屏问题修复
        if (error.getPrimaryError() == SslError.SSL_DATE_INVALID
                || error.getPrimaryError() == SslError.SSL_EXPIRED
                || error.getPrimaryError() == SslError.SSL_INVALID
                || error.getPrimaryError() == SslError.SSL_UNTRUSTED) {
            handler.proceed();

        } else {
            handler.cancel();
        }

    }


    /**
     * 处理过滤程序
     * @param url  当前请求资源
     * @param des 错误描述
     * @param errorCode 错误码
     */
    private void noNetHandle(String url, String des, int errorCode) {
        if (url == null) {
            return;
        }
        boolean isExistingQuery = url.contains("?");
        int startIndex = url.lastIndexOf("/") + 1;
        int endIndex = url.length();
        String endFile;
        if (isExistingQuery) {
            endIndex = url.lastIndexOf("?");
        }
        endFile = url.substring(startIndex, endIndex);
        //仅仅处理.html结尾或者无结尾符号的资源
        if (!Contants.SPECIAL_MARK_DOT.contains(endFile) || Contants.SPECIAL_MARK_HTML.equalsIgnoreCase(endFile.substring(endFile.indexOf(Contants.SPECIAL_MARK_DOT) + 1))) {

            if (Contants.WEBVIEW_ERROR_CODE_DISCONNECT_ERROR_MSG.equalsIgnoreCase(des)
                    || Contants.WEBVIEW_ERROR_CODE_DISCONNECT_EMPTY_ERROR_MSG.equalsIgnoreCase(des)
                    || Contants.WEBVIEW_ERROR_CODE_DISCONNECT_CONNECTION_TIMED_OUT_MSG.equalsIgnoreCase(des)
                    || Contants.WEBVIEW_ERROR_CODE_HUIWEI_TIMED_OUT.equalsIgnoreCase(des)
                    || errorCode == ERROR_HOST_LOOKUP
                    || errorCode == ERROR_CONNECT
                    || errorCode == ERROR_TIMEOUT
                    || Contants.WEBVIEW_ERROR_CODE_ERR_NAME_NOT_RESOLVED.equalsIgnoreCase(des)) {

                // TODO 出错处理
            }
        }
    }

    public void setWebViewClientListner(WebViewClientListner webViewClientListner) {
        mWebViewClientListner = webViewClientListner;
    }


    public interface WebViewClientListner{
        void onPageStart();
        void onPageEnd();
        void onPageError();
    }
}
