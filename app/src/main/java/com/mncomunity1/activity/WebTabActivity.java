package com.mncomunity1.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.mncomunity1.R;


public class WebTabActivity extends AppCompatActivity {


    Toolbar toolbar;

    WebView webview;


    String id;
    String title;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webview = (WebView) findViewById(R.id.webview);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");

        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setUserAgentString("Desktop");
        webview.loadUrl(url);

    }

}
