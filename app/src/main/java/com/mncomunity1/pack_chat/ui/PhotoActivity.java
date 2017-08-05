package com.mncomunity1.pack_chat.ui;

import android.app.Activity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.mncomunity1.R;

public class PhotoActivity extends Activity  {

    private static final String BUNDLE_POSITION = "position";
    String url;
    PhotoView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photos_activity);

        url = getIntent().getStringExtra("url");

        imageView = (PhotoView) findViewById(R.id.imageView);

        Glide.with(getApplicationContext())
                .load(url)
                .into(imageView);

    }





}
