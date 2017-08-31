package com.mncomunity1.activity;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.javiersantos.appupdater.AppUpdater;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mncomunity1.MainActivity;
import com.mncomunity1.MyApplication;
import com.mncomunity1.R;
import com.mncomunity1.adapter.GetVendorBannerRecyclerAdapter;
import com.mncomunity1.adapter.MockPagerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.app.Config;
import com.mncomunity1.model.CheckRead;
import com.mncomunity1.model.CountOrderVonder;
import com.mncomunity1.model.GetVendorBanner;
import com.mncomunity1.model.ModelVP;
import com.mncomunity1.model.PostError;
import com.mncomunity1.model.getOrder;
import com.mncomunity1.pack_chat.data.FriendDB;
import com.mncomunity1.pack_chat.data.GroupDB;
import com.mncomunity1.pack_chat.service.ServiceUtils;
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

public class LayoutDemoActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener, NavigationView.OnNavigationItemSelectedListener {

    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

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


    @Bind(R.id.ls_know)
    LinearLayout ls_know;

    @Bind(R.id.ls_semena)
    LinearLayout ls_semena;


    @Bind(R.id.ls_service)
    LinearLayout ls_service;


//Button Search


    @Bind(R.id.btn_done)
    LinearLayout btn_done;

    @Bind(R.id.td_search)
    EditText td_search;


    @Bind(R.id.badge_notification_history_vender)
    TextView badge_notification_history_vender;

    //set1 บน

    @Bind(R.id.ls_spare_part)
    LinearLayout ls_spare_part;

    @Bind(R.id.ls_history)
    LinearLayout ls_history;

    @Bind(R.id.ls_history_vender)
    LinearLayout ls_history_vender;

    @Bind(R.id.ls_history_vender_view)
    LinearLayout ls_history_vender_view;

    @Bind(R.id.ls_bitoffer_price_history_vender_view)
    LinearLayout ls_bitoffer_price_history_vender_view;


    //END

    //set2
    @Bind(R.id.ls_bitoffer_price)
    LinearLayout ls_bitoffer_price;

    @Bind(R.id.ls_bitoffer_price_history)
    LinearLayout ls_bitoffer_price_history;

    @Bind(R.id.ls_bitoffer_price_history_vender)
    LinearLayout ls_bitoffer_price_history_vender;


    @Bind(R.id.badge_history_bitoffer_vender)
    TextView badge_history_bitoffer_vender;

    //END


    RecyclerView recyclerview;

    ImageView cart;

    ArrayList<ModelVP> listVP = new ArrayList<>();
    Dialog dialogsNoti;

    Dialog dialogsLogin;
    String checkNoti;


    private BroadcastReceiver mRegistrationBroadcastReceiver;


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

    String countVender;
    int badgeCountVender;

    String countCustomer;
    int badgeCountCustomer;

    SharedPreferences pref;

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private LinearLayout ls_logout;

    ImageView img_search;

    TextView txt_logout;
    TextView txt_login;

    int countOrderByCompany;

    boolean isLogin;
    String message;

    GetVendorBannerRecyclerAdapter getVendorBannerRecyclerAdapter;
    ArrayList<GetVendorBanner> listbanner = new ArrayList<>();

    LinearLayout ls_news;

    String check_vebdor;
    String search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout_demo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        ButterKnife.bind(this);

        dialogsLogin = new Dialog(LayoutDemoActivity.this, R.style.FullHeightDialog);
        dialogsLogin.setContentView(R.layout.dailog_loading);
        dialogsLogin.show();


        toolbar.setTitle("Maintanace Community");
        setSupportActionBar(toolbar);

        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

        badge_notification_6 = (TextView) findViewById(R.id.badge_notification_6);
        ls_news = (LinearLayout) findViewById(R.id.ls_news);

        mViewPager2 = (InfiniteViewPager) findViewById(R.id.viewpager2);
        mCircleIndicator = (CirclePageIndicator) findViewById(R.id.indicator2);


        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);

        img_search = (ImageView) toolbar.findViewById(R.id.img_search);

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Menu menu = mNavigationView.getMenu();

        // find MenuItem you want to change
        MenuItem nav_camara = menu.findItem(R.id.nav_2);
        MenuItem nav_3 = menu.findItem(R.id.nav_3);
        MenuItem nav_4 = menu.findItem(R.id.nav_4);


        View header = mNavigationView.getHeaderView(0);


        txt_login = (TextView) header.findViewById(R.id.txt_login);
        txt_logout = (TextView) header.findViewById(R.id.txt_logout);


        AppUpdater appUpdater = new AppUpdater(this);
        appUpdater.start();

        setSupportActionBar(toolbar);
        actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(false);

        userId = sp.getString("userId", "000");
        companyCode = sp.getString("company_code", "00000");
        isLogin = sp.getBoolean("isLogin", false);
        check_vebdor = sp.getString("check_vebdor", "0");

        Log.e("Check", sp.getBoolean("isLogin", false) + "");
        Log.e("userId", sp.getString("userId", "000") + "");
        Log.e("check_vebdor", sp.getString("check_vebdor", "0") + "");

        if (check_vebdor.equals("1")) {
            nav_camara.setVisible(false);
            nav_3.setVisible(false);
            nav_4.setVisible(false);
            badge_history_bitoffer_vender.setVisibility(View.VISIBLE);
            ls_history_vender.setVisibility(View.VISIBLE);
            ls_history_vender_view.setVisibility(View.GONE);
            ls_bitoffer_price_history_vender_view.setVisibility(View.GONE);
            ls_bitoffer_price_history_vender.setVisibility(View.VISIBLE);

        } else {
            nav_camara.setVisible(false);
            nav_3.setVisible(false);
            nav_4.setVisible(false);
            ls_bitoffer_price_history_vender_view.setVisibility(View.VISIBLE);
            ls_bitoffer_price_history_vender.setVisibility(View.GONE);
            ls_history_vender_view.setVisibility(View.VISIBLE);
            badge_history_bitoffer_vender.setVisibility(View.GONE);
            ls_history_vender.setVisibility(View.GONE);

        }

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), SearchResultActivity.class);
                i.putExtra("keyword", "");
                startActivity(i);

            }
        });
        getCount(userId);
        getCountSendToVender(companyCode);
        getCountSendToCustomer(userId);
        getCountByVonder(companyCode);

        if (userId.equals("000")) {
            ShortcutBadger.removeCount(LayoutDemoActivity.this);
        } else {

        }


        cart = (ImageView) toolbar.findViewById(R.id.img_noti);

        pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        // loginByServer();
        OnClickNews();
        getOrder(regId);


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "ข้อความเข้า " + message, Toast.LENGTH_LONG).show();

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


        if (sp.getBoolean("isLogin", false) != false) {
//            mNavigationView.getMenu().findItem(1).setVisible(false);

            txt_logout.setVisibility(View.VISIBLE);
            txt_login.setVisibility(View.GONE);

            txt_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    editor.clear();
                    editor.commit();


                    FirebaseAuth.getInstance().signOut();
                    FriendDB.getInstance(getApplicationContext()).dropDB();
                    GroupDB.getInstance(getApplicationContext()).dropDB();
                    ServiceUtils.stopServiceFriendChat(getApplicationContext().getApplicationContext(), true);

                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    i.putExtra("checkLogin", "0");
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

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.putExtra("userId", userId);
                    intent.putExtra("checkLogin", "0");
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

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = td_search.getText().toString();
                Intent i = new Intent(getApplicationContext(), SearchResultActivity.class);
                i.putExtra("keyword", search);
                startActivity(i);
            }
        });

    }

    public void OnClickNews() {


        ls_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ListNewsActivity.class);
                startActivity(i);
            }
        });

        imgNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ข่าวสาร
                Intent i = new Intent(getApplicationContext(), ListNewsActivity.class);
                startActivity(i);
            }
        });

        ls_semena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //สัมมนา
                Intent i = new Intent(getApplicationContext(), ListSeminarTitleActivity.class);
                i.putExtra("Key", "1");
                startActivity(i);
            }
        });

        imgTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //สัมมนา
                Intent i = new Intent(getApplicationContext(), ListSeminarTitleActivity.class);
                i.putExtra("Key", "1");
                startActivity(i);
            }
        });

        ls_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ความรู้
                Intent i = new Intent(getApplicationContext(), KnowledgeActivity.class);
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

        ls_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin == true) {
                    //ช่วยเหลือ
                    Intent i = new Intent(getApplicationContext(), MoreActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(i);
                }
            }
        });

        imgHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isLogin == true) {
                    //ช่วยเหลือ
                    Intent i = new Intent(getApplicationContext(), MoreActivity.class);
                    startActivity(i);
                } else {
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
//set1
        ls_spare_part.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //สินค้า
                Intent i = new Intent(getApplicationContext(), SpareActivity.class);
                startActivity(i);
            }
        });

        ls_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent iVv = new Intent(getApplicationContext(), OrderActivity.class);
                startActivity(iVv);


            }
        });

        ls_history_vender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iV = new Intent(getApplicationContext(), VendorOrderActivity.class);
                startActivity(iV);
            }
        });

        //END
        //SET2

        ls_bitoffer_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BitOfferActivity.class);
                startActivity(i);
            }
        });

        ls_bitoffer_price_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iVvv = new Intent(getApplicationContext(), ListQuestionActivity.class);
                startActivity(iVvv);
            }
        });

        ls_bitoffer_price_history_vender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iVv = new Intent(getApplicationContext(), BitItemActivity.class);
                startActivity(iVv);
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

        if (userId.equals("000")) {
            ShortcutBadger.removeCount(LayoutDemoActivity.this);
        } else {
            getCount(userId);
        }


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

                        pagerAdapter2 = new MockPagerAdapter(LayoutDemoActivity.this);
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

                    //   badge_notification_history_vender.setVisibility(View.VISIBLE);
                    // badge_notification_history_vender.setText(response.body().getTotal().size() + "");

                } else {
                    // badge_notification_history_vender.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<getOrder> call, Throwable t) {

            }
        });
    }

    private void getCount(String userId) {
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<CheckRead> userCall = service.getCheckRead(userId);

        userCall.enqueue(new Callback<CheckRead>() {
            @Override
            public void onResponse(Call<CheckRead> call, Response<CheckRead> response) {


                if (response.body() != null) {
                    count = response.body().getCount().get(0).getTotal();
                    badgeCount = Integer.parseInt(count);


                    ShortcutBadger.applyCount(getApplicationContext(), badgeCount); //for 1.1.4+

                    if (badgeCount > 0) {

                        badge_notification_3.setVisibility(TextView.VISIBLE);
                        badge_notification_3.invalidate();
                        badge_notification_3.setText(response.body().getCount().get(0).getTotal());
                        Log.e("size", response.body().getCount().get(0).getTotal() + "");

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

    private void getCountSendToVender(String company) {
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<CheckRead> userCall = service.getCheckSenToVender(company);

        userCall.enqueue(new Callback<CheckRead>() {
            @Override
            public void onResponse(Call<CheckRead> call, Response<CheckRead> response) {


                if (response.body() != null) {
                    countVender = response.body().getCount().get(0).getTotal();
                    badgeCountVender = Integer.parseInt(countVender);

                    dialogsLogin.dismiss();
                    // ShortcutBadger.applyCount(getApplicationContext(), badgeCount); //for 1.1.4+

                    if (badgeCountVender > 0) {

                        badge_history_bitoffer_vender.setVisibility(TextView.VISIBLE);
                        badge_history_bitoffer_vender.invalidate();
                        badge_history_bitoffer_vender.setText(response.body().getCount().get(0).getTotal());
                        Log.e("size", response.body().getCount().get(0).getTotal() + "");

                    } else {
                        dialogsLogin.dismiss();
                        badge_history_bitoffer_vender.setVisibility(TextView.GONE);
                    }


                }


            }

            @Override
            public void onFailure(Call<CheckRead> call, Throwable t) {

            }
        });
    }

    private void getCountSendToCustomer(String user_id) {
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<CheckRead> userCall = service.getCheckSenToCustomer(user_id);

        userCall.enqueue(new Callback<CheckRead>() {
            @Override
            public void onResponse(Call<CheckRead> call, Response<CheckRead> response) {


                if (response.body() != null) {
                    countCustomer = response.body().getCount().get(0).getTotal();
                    badgeCountCustomer = Integer.parseInt(countCustomer);


                    // ShortcutBadger.applyCount(getApplicationContext(), badgeCount); //for 1.1.4+

                    if (badgeCountCustomer > 0) {

                        //  badge_notification_history_customer.setVisibility(TextView.VISIBLE);
                        // badge_notification_history_customer.invalidate();
                        // badge_notification_history_customer.setText(response.body().getCount().get(0).getTotal());
                        Log.e("size", response.body().getCount().get(0).getTotal() + "");
                        dialogsLogin.dismiss();

                    } else {
                        // badge_notification_history_customer.setVisibility(TextView.GONE);
                        dialogsLogin.dismiss();
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
                dialogsLogin.dismiss();
                listbanner.add(response.body());

                getVendorBannerRecyclerAdapter = new GetVendorBannerRecyclerAdapter(getApplicationContext(), listbanner);
                recyclerview.setAdapter(getVendorBannerRecyclerAdapter);
                getVendorBannerRecyclerAdapter.notifyDataSetChanged();

                getVendorBannerRecyclerAdapter.SetOnItemVideiosClickListener(new GetVendorBannerRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String company_code = listbanner.get(position).getTotal().get(position).getCompany_code();
                        String company_name = listbanner.get(position).getTotal().get(position).getCompany_name();


                        Toast.makeText(getApplicationContext(), "คลิก", Toast.LENGTH_SHORT).show();

//                        Intent i = new Intent(getApplicationContext(), SearchResultActivity.class);
//                        i.putExtra("company_code", company_code);
//                        i.putExtra("company_name", company_name);
//                        startActivity(i);
                    }
                });
            }

            @Override
            public void onFailure(Call<GetVendorBanner> call, Throwable t) {

            }
        });
    }

    private void getCountByVonder(String companyCode) {
        APIService service = ApiClient.getClient().create(APIService.class);
        Call<CountOrderVonder> userCall = service.postCountOrderVonder(companyCode);

        userCall.enqueue(new Callback<CountOrderVonder>() {
            @Override
            public void onResponse(Call<CountOrderVonder> call, Response<CountOrderVonder> response) {

                countOrderByCompany = response.body().getTotal();
                Log.e("countOrderByCompany", countOrderByCompany + "");


                if (countOrderByCompany > 0) {

                    badge_notification_history_vender.setVisibility(TextView.VISIBLE);
                    badge_notification_history_vender.invalidate();
                    badge_notification_history_vender.setText(countOrderByCompany + "");

                } else {
                    dialogsLogin.dismiss();
                    badge_notification_history_vender.setVisibility(TextView.GONE);
                }

            }

            @Override
            public void onFailure(Call<CountOrderVonder> call, Throwable t) {

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
//            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

        } else {
            message = "ไม่สามารถเชื่มต่ออินเทอร์เน็ตได้";
//            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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

        String token = FirebaseInstanceId.getInstance().getToken();

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

            case R.id.nav_3:

                Intent iVv = new Intent(getApplicationContext(), BitItemActivity.class);
                startActivity(iVv);

                return;

            case R.id.nav_4:
                Intent iVvv = new Intent(getApplicationContext(), ListQuestionActivity.class);
                startActivity(iVvv);

                return;

            case R.id.nav_5:

                Intent iVS = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(iVS);

                return;

            case R.id.nav_6:

                Intent about = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(about);

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
