package com.example.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.app.view.CommonWebView;

public class MainActivity extends Activity implements CommonWebViewClient.WebViewClientListner {

    private WebView mWebView;

    private String mUrl="http://www.hao123.com/";

    private ViewGroup mRoot;

    private CommonWebViewClient mCommonWebViewClient;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRoot=findViewById(R.id.root);
        if (prepareWebView()){
            loadUrl(mUrl);
        }

    }

    /**
     * 预处理WebView
     */
    private boolean prepareWebView() {
        if (mWebView==null){
            mWebView=new CommonWebView(this);
            mCommonWebViewClient=new CommonWebViewClient();
            mCommonWebViewClient.setWebViewClientListner(this);
            //设置WebViewClient
            mWebView.setWebViewClient(mCommonWebViewClient);
        }
        // 防止内存泄漏
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        mRoot.addView(mWebView,layoutParams);
        return true;
    }

    private void loadUrl(String url){
        if (mWebView==null){
            return;
        }
        mWebView.loadUrl(url);
    }

    /**
     * Prevent the back-button from closing the app
     */
    @Override
    public void onBackPressed() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView!=null){
            mWebView.destroy();
            mWebView=null;
        }
    }

    // WebView页面客户端回调处理开始

    @Override
    public void onPageStart() {

    }

    @Override
    public void onPageEnd() {

    }

    @Override
    public void onPageError() {

    }

    // WebView页面客户端回调处理结束
}