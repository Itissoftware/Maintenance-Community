package com.mncomunity1.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Window;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.mncomunity1.MainActivity;
import com.mncomunity1.R;
import com.mncomunity1.app.Config;
import com.mncomunity1.until.ConnectivityReceiver;


public class Splash extends Activity {

    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    Handler handler;
    Runnable runnable;
    long delay_time;
    long time = 3000L;

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splashscreen);
        checkConnection();


        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();



        if (sp.getBoolean("isLoginF", false) == true) {
            handler = new Handler();

            runnable = new Runnable() {
                public void run() {
//                Intent intent = new Intent(Splash.this, LoginActivityChat.class);
//                startActivity(intent);
//                finish();
                    Intent intent = new Intent(Splash.this,  LayoutDemoActivity.class);
                    startActivity(intent);
                    finish();
                }
            };
        } else {

            Intent intent = new Intent(Splash.this,  LoginActivityF.class);
            startActivity(intent);
            finish();

        }



            mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


            }
        };


        subscribeToPushService();
    }


    public void onResume() {
        super.onResume();
        delay_time = time;
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();

        // Register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // Register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));
    }

    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }

    private void subscribeToPushService() {
        String token = FirebaseInstanceId.getInstance().getToken();

    }
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            subscribeToPushService();
            message = "ติดต่อเครือข่าย สำเร็จ";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

        } else {
            message = "ไม่สามารถเชื่มต่ออินเทอร์เน็ตได้";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }


    }
}