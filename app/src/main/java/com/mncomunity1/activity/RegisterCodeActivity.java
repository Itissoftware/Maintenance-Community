package com.mncomunity1.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mncomunity1.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterCodeActivity extends AppCompatActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_code);

        ButterKnife.bind(this);

        toolbar.setTitle("Register Code");
        setSupportActionBar(toolbar);
    }
}
