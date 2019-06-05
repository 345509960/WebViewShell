package com.example.app.view;

import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
* @author lyc
* create date 2019/6/5 3:44 PM
* des
* update date 
**/
public class CommonWebView extends WebView {
    
    public CommonWebView(Context context) {
        super(context);
        initSetting();
    }

    /**
     * WebSetting配置
     */
    private void initSetting() {
        WebSettings webSettings = getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //图片自动缩放 打开
            webSettings.setLoadsImagesAutomatically(true);
        } else {
            //图片自动缩放 关闭
            webSettings.setLoadsImagesAutomatically(false);
        }
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setBuiltInZoomControls(true);
        String ua = webSettings.getUserAgentString();
        webSettings.setUserAgentString(ua + ";webview");
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setSupportZoom(false);
        webSettings.supportMultipleWindows();
        webSettings.setAllowFileAccess(true);
        webSettings.setNeedInitialFocus(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //开启DOM stoare
        webSettings.setDomStorageEnabled(true);
        //为了迎合浏览器缓存 设置有缓存 缓存本地的、无缓存访问网络的缓存模式
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);


    }
}
