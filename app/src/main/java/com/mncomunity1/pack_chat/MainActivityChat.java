package com.mncomunity1.pack_chat;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mncomunity1.R;
import com.mncomunity1.pack_chat.data.StaticConfig;
import com.mncomunity1.pack_chat.service.ServiceUtils;
import com.mncomunity1.pack_chat.ui.FriendsFragment;
import com.mncomunity1.pack_chat.ui.GroupFragment;
import com.mncomunity1.pack_chat.ui.LoginActivityChat;
import com.mncomunity1.pack_chat.ui.UserProfileFragment;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.tabhost;

public class MainActivityChat extends TabActivity {
    private static String TAG = "MainActivityChat";
    private ViewPager viewPager;
    private TabLayout tabLayout = null;
    public static String STR_FRIEND_FRAGMENT = "FRIEND";
    public static String STR_GROUP_FRAGMENT = "GROUP";
    public static String STR_INFO_FRAGMENT = "INFO";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    LovelyProgressDialog dialogWait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);
        final TabHost tabHost = (TabHost) findViewById(tabhost);
        dialogWait = new LovelyProgressDialog(MainActivityChat.this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Chat Service");
        }

        TabHost.TabSpec spec;
        Intent intent;
        spec = tabHost.newTabSpec("แชท");
        spec.setIndicator("แชท");
        intent = new Intent(this, FriendsFragment.class);
        spec.setContent(intent);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("โปรไฟล์");
        spec.setIndicator("โปรไฟล์");
        intent = new Intent(this, UserProfileFragment.class);
        spec.setContent(intent);
        tabHost.addTab(spec);

        TabHost tabhost = getTabHost();
        for(int i=0;i<tabhost.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) tabhost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.parseColor("#000000"));
        }


        initFirebase();
    }

    private void initFirebase() {

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    StaticConfig.UID = user.getUid();
                } else {
                    MainActivityChat.this.finish();
                    // User is signed in
                    startActivity(new Intent(MainActivityChat.this, LoginActivityChat.class));
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
        ServiceUtils.stopServiceFriendChat(getApplicationContext(), false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onDestroy() {
        ServiceUtils.startServiceFriendChat(getApplicationContext());
        super.onDestroy();
    }


}