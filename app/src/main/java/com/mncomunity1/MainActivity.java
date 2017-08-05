package com.mncomunity1;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.javiersantos.appupdater.AppUpdater;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mncomunity1.activity.CartTotalActivity;
import com.mncomunity1.activity.KnowledgeActivity;
import com.mncomunity1.activity.ListActivity;
import com.mncomunity1.activity.ListNewsActivity;
import com.mncomunity1.activity.LoginActivity;
import com.mncomunity1.activity.MoreActivity;
import com.mncomunity1.activity.RegisterActivity;
import com.mncomunity1.activity.SearchResultActivity;
import com.mncomunity1.activity.SettingsActivity;
import com.mncomunity1.activity.SpareActivity;
import com.mncomunity1.activity.TabsOrderActivity;
import com.mncomunity1.activity.VendorOrderActivity;
import com.mncomunity1.adapter.GetVendorBannerRecyclerAdapter;
import com.mncomunity1.adapter.MockPagerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.app.Config;
import com.mncomunity1.model.CheckRead;
import com.mncomunity1.model.GetVendorBanner;
import com.mncomunity1.model.ModelVP;
import com.mncomunity1.model.Register;
import com.mncomunity1.model.getOrder;
import com.mncomunity1.service.ApiClient;
import com.mncomunity1.until.ConnectivityReceiver;
import com.mncomunity1.util.NotificationUtils;
import com.zanlabs.widget.infiniteviewpager.InfiniteViewPager;
import com.zanlabs.widget.infiniteviewpager.indicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.leolin.shortcutbadger.ShortcutBadger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener, NavigationView.OnNavigationItemSelectedListener {
    Menu myMenu;

    private int mSelectedId;
    private int mPrevSelectedId;
    private final Handler mDrawerHandler = new Handler();
    private static final String SELECTED_ITEM_ID = "SELECTED_ITEM_ID";

    @Bind(R.id.toolbar)
    Toolbar toolbar;


    @Bind(R.id.img_train)
    ImageView imgTrain;

    @Bind(R.id.img_news)
    ImageView imgNews;

    @Bind(R.id.img_art)
    ImageView imgArt;

    @Bind(R.id.img_help)
    ImageView imgHelp;

    @Bind(R.id.img_vender)
    ImageView imgVender;

    @Bind(R.id.badge_notification_3)
    TextView badge_notification_3;


    @Bind(R.id.badge_notification_4)
    TextView badge_notification_4;

    @Bind(R.id.ls_history)
    LinearLayout ls_history;

    RecyclerView recyclerview;

    ImageView cart;

    ArrayList<ModelVP> listVP = new ArrayList<>();
    Dialog dialogsNoti;

    Dialog dialogsPmii;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";

    private BroadcastReceiver mRegistrationBroadcastReceiver;


    SharedPreferences sharedpreferences_count;
    public static final String mypreference_count = "myprefTest";
    List<Integer> myList = new ArrayList<>();

    int[] intArray;
    int showNoti;
    String userId;
    String companyCode;
    Dialog dialogs;

    InfiniteViewPager mViewPager2;
    CirclePageIndicator mCircleIndicator;
    MockPagerAdapter pagerAdapter2;
    TextView badge_notification_6;

    String check;
    private ActionBar actionbar;
    String count;
    int badgeCount;

    SharedPreferences pref;

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private LinearLayout ls_logout;

    ImageView img_search;

    TextView txt_logout;
    TextView txt_login;

    boolean isLogin;

    GetVendorBannerRecyclerAdapter getVendorBannerRecyclerAdapter;
    ArrayList<GetVendorBanner> listbanner = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        badge_notification_6 = (TextView) findViewById(R.id.badge_notification_6);

        mViewPager2 = (InfiniteViewPager) findViewById(R.id.viewpager2);
        mCircleIndicator = (CirclePageIndicator) findViewById(R.id.indicator2);

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        sharedpreferences_count = getSharedPreferences(mypreference_count, Context.MODE_PRIVATE);

        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);

        img_search = (ImageView) toolbar.findViewById(R.id.img_search);

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        View header = mNavigationView.getHeaderView(0);

        txt_login = (TextView) header.findViewById(R.id.txt_login);
        txt_logout = (TextView) header.findViewById(R.id.txt_logout);


        AppUpdater appUpdater = new AppUpdater(this);
        appUpdater.start();

        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(false);

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), SearchResultActivity.class);
                i.putExtra("keyword", "");
                startActivity(i);

            }
        });

        isLogin = sharedpreferences.getBoolean("isLogin", false);
        //Log.e("isLogin", sharedpreferences.getBoolean("isLogin", false) + "'");
        Log.e("isLogin", sharedpreferences.getBoolean("isLogin", false) + "'");

        displayFirebaseRegId();
        userId = sharedpreferences.getString("userId", "0");
        companyCode = sharedpreferences.getString("company_code", "0");
        check = sharedpreferences.getString("check", "0");
        Log.e("userId:", userId);
        Log.e("company_code:", sharedpreferences.getString("company_code", "0"));
        getCount(userId);


        toolbar.setTitle("Maintanace Community");
        setSupportActionBar(toolbar);

        cart = (ImageView) toolbar.findViewById(R.id.img_noti);

        pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        // loginByServer();
        OnClickNews();
        getOrder(regId);
        showNoti = sharedpreferences_count.getInt("myArrayKey", 0);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");
                    String count = intent.getStringExtra("count");

                    if (message.equals("Chat")) {
                        Toast.makeText(getApplicationContext(), "Chat", Toast.LENGTH_SHORT).show();

                    }


                    //  showDialog();
//                    int count_noti = Integer.parseInt(count);
//                    showNoti = sharedpreferences_count.getInt("myArrayKey", 0);
//                    showNoti += count_noti;
//                    myList.add(showNoti);


                    dialogsNoti = new Dialog(MainActivity.this, R.style.FullHeightDialog);
                    dialogsNoti.setContentView(R.layout.dailog_noti);
                    dialogsNoti.show();
                    getCount(userId);
                    Button btn_close = (Button) dialogsNoti.findViewById(R.id.btn_close);
                    Button btn_open = (Button) dialogsNoti.findViewById(R.id.btn_open);

                    btn_open.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialogsNoti.dismiss();
                        }
                    });

                    btn_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            NotificationUtils.clearNotifications(getApplicationContext());
                            dialogsNoti.dismiss();


                        }
                    });


                }
            }
        };
        checkConnection();

        // ShortcutBadger.removeCount(context); //for 1.1.4+

        assert mNavigationView != null;
        mNavigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                super.onDrawerSlide(drawerView, 0);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, 0);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mSelectedId = mNavigationView.getMenu().getItem(prefs.getInt("default_view", 0)).getItemId();
        mSelectedId = savedInstanceState == null ? mSelectedId : savedInstanceState.getInt(SELECTED_ITEM_ID);

        mNavigationView.getMenu().findItem(mSelectedId).setChecked(true);
        Log.e("mSelectedId", mSelectedId + "");

        if (savedInstanceState == null) {
            mDrawerHandler.removeCallbacksAndMessages(null);
            mDrawerHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    navigate(mSelectedId);
                }
            }, 250);

            boolean openDrawer = prefs.getBoolean("open_drawer", false);

            if (openDrawer)
                mDrawerLayout.openDrawer(GravityCompat.START);
            else
                mDrawerLayout.closeDrawers();
        }


        if (sharedpreferences.getBoolean("isLogin", false) != false) {
//            mNavigationView.getMenu().findItem(1).setVisible(false);

            txt_logout.setVisibility(View.VISIBLE);
            txt_login.setVisibility(View.GONE);

            txt_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor token = pref.edit();
                    token.clear();
                    token.commit();

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.clear();
                    editor.commit();

                    SharedPreferences.Editor editorCount = sharedpreferences_count.edit();
                    editorCount.clear();
                    editorCount.commit();

                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                    finish();

                }
            });

        } else {

            txt_logout.setVisibility(View.GONE);
            txt_login.setVisibility(View.VISIBLE);

            txt_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences.Editor tokenLogin = pref.edit();
                    tokenLogin.clear();
                    tokenLogin.commit();

                    SharedPreferences.Editor editorLogin = sharedpreferences.edit();
                    editorLogin.clear();
                    editorLogin.commit();

                    SharedPreferences.Editor editorCountLogin = sharedpreferences_count.edit();
                    editorCountLogin.clear();
                    editorCountLogin.commit();

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();

                }
            });

        }

        recyclerview.setHasFixedSize(true);
        LinearLayoutManager llm = new GridLayoutManager(getApplicationContext(), 1);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(llm);
        getVenderBanner();



    }

    public void OnClickNews() {
        imgNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ข่าวสาร
                Intent i = new Intent(getApplicationContext(), ListNewsActivity.class);
                startActivity(i);
            }
        });


        imgTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //สัมมนา
                Intent i = new Intent(getApplicationContext(), ListActivity.class);
                i.putExtra("Key", "1");
                startActivity(i);
            }
        });

        imgArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ความรู้
                Intent i = new Intent(getApplicationContext(), KnowledgeActivity.class);
                startActivity(i);
            }
        });

        imgHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isLogin == true) {
                    //ช่วยเหลือ
                    Intent i = new Intent(getApplicationContext(), MoreActivity.class);
                    startActivity(i);
                }else{
                    Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(i);
                }



                //dialogsPmii.dismiss();

            }
        });

        imgVender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //สินค้า
                Intent i = new Intent(getApplicationContext(), SpareActivity.class);
                startActivity(i);

//                Intent i = new Intent(getApplicationContext(),SoonActivity.class);
//                startActivity(i);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(getApplicationContext(), CartTotalActivity.class);
                i.putExtra("userId", userId);
                startActivity(i);
            }
        });

        ls_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), TabsOrderActivity.class);
                startActivity(i);


            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        MyApplication.getInstance().setConnectivityListener(this);

        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        NotificationUtils.clearNotifications(getApplicationContext());

        getOrder(userId);
        getCount(userId);

    }


    private void loginByServer() {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<ModelVP> userCall = service.getBandner();

        userCall.enqueue(new Callback<ModelVP>() {
            @Override
            public void onResponse(Call<ModelVP> call, Response<ModelVP> response) {


                if (response.body().getPost() != null) {

                    for (int i = 0; i < response.body().getPost().size(); i++) {

                        listVP.add(response.body());

                        pagerAdapter2 = new MockPagerAdapter(MainActivity.this);
                        pagerAdapter2.setDataList(listVP);
                        mViewPager2.setAdapter(pagerAdapter2);
                        mViewPager2.setAutoScrollTime(10000);
                        mViewPager2.startAutoScroll();
                        mCircleIndicator.setViewPager(mViewPager2);


                    }
                }


            }

            @Override
            public void onFailure(Call<ModelVP> call, Throwable t) {

            }
        });


    }


    private void getOrder(String user_id) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<getOrder> userCall = service.getOrder(user_id);

        userCall.enqueue(new Callback<getOrder>() {
            @Override
            public void onResponse(Call<getOrder> call, Response<getOrder> response) {

                if (response.body().getTotal() != null) {

                    badge_notification_6.setVisibility(View.VISIBLE);
                    badge_notification_6.setText(response.body().getTotal().size() + "");

                } else {
                    badge_notification_6.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<getOrder> call, Throwable t) {

            }
        });
    }


    private void postRegister(String regids) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<Register> userCall = service.postRegisterOne(regids);

        userCall.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {


                if (response.body().getSuccess().equals("1")) {

                    if (response.body().getComplete() != null) {
                        String userId = response.body().getComplete().get(0).getCode();
                        String statusVendor = response.body().getComplete().get(0).getCheck_status();
                        String companycode = response.body().getComplete().get(0).getCompany_code();
                        String check_status = response.body().getComplete().get(0).getCheck_status();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
//                    editor.putBoolean("isLogin", true);
                        editor.putString("userId", userId);
                        editor.putString("company_code", companycode);
                        editor.putString("statusVendor", statusVendor);
                        editor.putString("check_status", check_status);
                        editor.commit();

                    }


                }


            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {

            }
        });
    }

    private void getCount(final String userId) {
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<CheckRead> userCall = service.getCheckRead(userId);

        userCall.enqueue(new Callback<CheckRead>() {
            @Override
            public void onResponse(Call<CheckRead> call, Response<CheckRead> response) {


                if (response.body() != null) {
                    count = response.body().getCount().get(0).getTotal();
                    badgeCount = Integer.parseInt(count);

                    if (badgeCount > 0) {
                        badge_notification_3.setVisibility(TextView.VISIBLE);
                        badge_notification_3.invalidate();
                        badge_notification_3.setText(response.body().getCount().get(0).getTotal());
                        Log.e("size", response.body().getCount().get(0).getTotal() + "");


                        ShortcutBadger.applyCount(MainActivity.this, badgeCount); //for 1.1.4+
                    } else {
                        badge_notification_3.setVisibility(TextView.GONE);
                    }


                }


            }

            @Override
            public void onFailure(Call<CheckRead> call, Throwable t) {

            }
        });
    }


    private void getVenderBanner() {
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<GetVendorBanner> userCall = service.getVendorBandner();

        userCall.enqueue(new Callback<GetVendorBanner>() {
            @Override
            public void onResponse(Call<GetVendorBanner> call, Response<GetVendorBanner> response) {

                listbanner.add(response.body());

                getVendorBannerRecyclerAdapter = new GetVendorBannerRecyclerAdapter(getApplicationContext(), listbanner);
                recyclerview.setAdapter(getVendorBannerRecyclerAdapter);
                getVendorBannerRecyclerAdapter.notifyDataSetChanged();

                getVendorBannerRecyclerAdapter.SetOnItemVideiosClickListener(new GetVendorBannerRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String company_code = listbanner.get(position).getTotal().get(position).getCompany_code();
                        String company_name = listbanner.get(position).getTotal().get(position).getCompany_name();

                        Intent i = new Intent(getApplicationContext(), SearchResultActivity.class);
                        i.putExtra("company_code", company_code);
                        i.putExtra("company_name", company_name);
                        startActivity(i);
                    }
                });
            }

            @Override
            public void onFailure(Call<GetVendorBanner> call, Throwable t) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mViewPager2 != null)
            mViewPager2.startAutoScroll();
    }

    @Override
    public void onStop() {
        if (mViewPager2 != null)
            mViewPager2.stopAutoScroll();
        super.onStop();
    }


    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
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
            loginByServer();
            message = "ติดต่อเครือข่าย สำเร็จ";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

        } else {
            message = "ไม่สามารถเชื่มต่ออินเทอร์เน็ตได้";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        if (!TextUtils.isEmpty(regId))
            Log.e("Firebase Reg Id: ", regId);
        else
            Log.e("is not received yet!", "Firebase ");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        menuItem.setChecked(true);

        mSelectedId = menuItem.getItemId();
        mDrawerHandler.removeCallbacksAndMessages(null);
        mDrawerHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigate(mSelectedId);
            }
        }, 250);
        mDrawerLayout.closeDrawers();
        return true;
    }

    private void subscribeToPushService() {

        Toast.makeText(MainActivity.this, "Subscribed", Toast.LENGTH_SHORT).show();
        String token = FirebaseInstanceId.getInstance().getToken();
        postRegister(token);
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor2 = pref.edit();
        editor2.putString("regId", token);
        editor2.commit();

        // Log and toast
       // Log.e("AndroidBash", token);

    }

    private void navigate(final int itemId) {

        Fragment navFragment = null;
        switch (itemId) {
            case R.id.nav_1:

                break;
            case R.id.nav_2:
                Intent iV = new Intent(getApplicationContext(), VendorOrderActivity.class);
                startActivity(iV);
                break;

            case R.id.nav_5:

                Intent iVS = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(iVS);

                return;
            case R.id.nav_6:

                return;

        }

        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(4));

        if (navFragment != null) {

        }
    }

    public int dp(float value) {
        float density = getApplicationContext().getResources().getDisplayMetrics().density;

        if (value == 0) {
            return 0;
        }
        return (int) Math.ceil(density * value);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM_ID, mSelectedId);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}


