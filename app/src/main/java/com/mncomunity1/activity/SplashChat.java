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

import com.google.firebase.iid.FirebaseInstanceId;
import com.mncomunity1.MainActivity;
import com.mncomunity1.R;
import com.mncomunity1.app.Config;


public class SplashChat extends Activity {
    Handler handler;
    Runnable runnable;
    long delay_time;
    long time = 3000L;

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splashscreen);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


            }
        };
        handler = new Handler();

        runnable = new Runnable() {
            public void run() {
//                Intent intent = new Intent(Splash.this, LoginActivityChat.class);
//                startActivity(intent);
//                finish();
                Intent intent = new Intent(SplashChat.this,  MainActivity.class);
                startActivity(intent);
                finish();
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

        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor2 = pref.edit();
        editor2.putString("regId", token);
        editor2.commit();





    }
}