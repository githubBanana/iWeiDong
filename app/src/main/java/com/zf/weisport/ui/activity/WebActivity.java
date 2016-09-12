package com.zf.weisport.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zf.weisport.R;
import com.zf.weisport.ui.activity.base.BaseActivity;

/**
 * Created by Administrator on 2016/9/11.
 */
public class WebActivity extends BaseActivity{

    private static final String TAG = "WebActivity";
    public static final String INSTALL_URL = "http://a.app.qq.com/o/simple.jsp?pkgname=com.zf.weisport";
    private WebView mWeb;
    private int     type ;

    public static final int WEB_VIDEO = 1;
    public static final int WEB_NEWS = 2;
    public static final int WEB_ABOUTUS = 3;
    public static final int WEB_INSTALL_UPDATE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web,true);
        mWeb = (WebView) findViewById(R.id.wb_WebId);
        init();
    }


    @Override
    protected void onResume() {
        super.onResume();
        switch (type) {
            case 1:
                setCenterTitle(getString(R.string.videoDetail_text));
                break;
            case 2:
                setCenterTitle(getString(R.string.news_detail_text));
                break;
            case 3:
                setCenterTitle(getString(R.string.aboutus_text));
                break;
            case 4:
                setCenterTitle(getString(R.string.software_update_text));
                break;
            default:break;
        }
    }

    @Override
    public void onBackPressed() {
        dismissLoadingView();
        super.onBackPressed();
    }

    private void init() {
        webInit();
        Intent intent = getIntent();
        type = intent.getIntExtra("type",0);
        final String urlString = intent.getStringExtra("url");

        if(mWeb != null) {
            mWeb.setWebViewClient(new WebViewClient()
            {
                @Override
                public void onPageFinished(WebView view, String url) {
                    // TODO Auto-generated method stub
                    super.onPageFinished(view, url);
                    dismissLoadingView();
                }

                @Override
                public void onPageStarted(WebView view, String url,
                                          Bitmap favicon) {
                    // TODO Auto-generated method stub
                    super.onPageStarted(view, url, favicon);
                    showLoadingView();
                }
            });
        }
        mWeb.loadUrl(urlString);

    }

    void webInit() {
        //如果访问的页面中有 Javascript，则 webview 必须设置支持 Javascript
        mWeb.getSettings().setJavaScriptEnabled(true);
        mWeb.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        //web.setBackgroundColor(Color.BLUE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWeb.clearCache(true);
        mWeb.destroy();
    }
}
