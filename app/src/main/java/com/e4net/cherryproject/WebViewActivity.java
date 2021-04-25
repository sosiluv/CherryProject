package com.e4net.cherryproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class WebViewActivity extends AppCompatActivity {
    public static Activity webViewActivity;
    WebView webView;
    WebSettings webSettings;
    Context context;

    Button btn_nextWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webViewActivity = WebViewActivity.this;
        context = this.getApplicationContext();
        webView = findViewById(R.id.webView);
        btn_nextWebView = findViewById(R.id.btn_nextWebView);

        webView.setWebViewClient(new WebViewClientClass());
        webSettings = webView.getSettings();

//        webSettings.setSupportMultipleWindows(false);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
//        webSettings.setSupportZoom(false);
//        webSettings.setBuiltInZoomControls(false);
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        webSettings.setDomStorageEnabled(true);

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);

        String url = getIntent().getStringExtra("url");
        Log.d("Webview","url >>>>>>> : " + url);
        if(url.contains("pinResetScreen1") || url.contains("accountScreen1") || url.contains("joinScreen")){
            btn_nextWebView.setVisibility(View.INVISIBLE);
            Log.d("Webview","버튼 비활성화");
        }else{
            btn_nextWebView.setVisibility(View.VISIBLE);
            Log.d("Webview","활성화");
        }

        webView.loadUrl(url);

        btn_nextWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class WebViewClientClass extends WebViewClient{

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("WebView","url >>>>>>>>>> : " + url);
            if(url.startsWith("http://cherryqr.joinscreen3/"))   {
               Log.d("Webview","url.startsWith");
               Intent intent = new Intent(context.getApplicationContext(), JoinActivity3.class);
               startActivity(intent);
               return true;
                
            }else{
                Log.d("Webview","not url.startsWith");
                view.loadUrl(url);
                return true;
            }
        }
    }
}