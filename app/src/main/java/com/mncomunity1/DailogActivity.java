package com.mncomunity1;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.mncomunity1.pack_chat.MainActivityChat;

public class DailogActivity extends Activity {
    Dialog dialog = null;

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    String msg;
    String roomId;
    String userName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transparentprogressdialog);

        msg = getIntent().getStringExtra("msg");
        roomId = getIntent().getStringExtra("roomId");
        userName = getIntent().getStringExtra("userName");

        showNewDialog(msg, userName);
    }

    public void showNewDialog(String msg, String userName) {

        TextView txt_msg = (TextView) findViewById(R.id.txt_msg);
        TextView txt_name = (TextView) findViewById(R.id.txt_name);
        Button btn_open = (Button) findViewById(R.id.btn_open);
        Button btn_cancel = (Button) findViewById(R.id.btn_cancel);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 1.0f;
        getWindow().setAttributes(params);

        txt_msg.setText(msg);
        txt_name.setText(userName);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplication(), MainActivityChat.class);
                startActivity(i);

                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}