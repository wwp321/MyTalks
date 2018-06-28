package com.byron.mytalks;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_player);

        Toolbar toolbar = findViewById(R.id.webplayer_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        WebView webPlayerViwe = findViewById(R.id.web_player_view);
        WebSettings settings = webPlayerViwe.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setAllowFileAccess(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDomStorageEnabled(true);
        settings.setSupportMultipleWindows(true);
        settings.setAppCacheEnabled(true);


        webPlayerViwe.setWebChromeClient( new WebChromeClient());
        webPlayerViwe.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.startsWith("intent") || url.startsWith("youku")){
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

        Intent intent = getIntent();
        String link = intent.getStringExtra("videoUrl");
        webPlayerViwe.loadUrl(link);
    }
}
