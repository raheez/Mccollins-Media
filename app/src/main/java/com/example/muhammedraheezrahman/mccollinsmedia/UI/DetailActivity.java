package com.example.muhammedraheezrahman.mccollinsmedia.UI;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.muhammedraheezrahman.mccollinsmedia.R;

public class DetailActivity extends RootActivity {
    private WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        webview =(WebView)findViewById(R.id.webview);

        Bundle p = getIntent().getExtras();
        String url =p.getString("key_url");

        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webview.loadUrl(url);
    }
}
