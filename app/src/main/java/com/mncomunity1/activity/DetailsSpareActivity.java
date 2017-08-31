package com.mncomunity1.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.mncomunity1.MainActivity;
import com.mncomunity1.R;
import com.mncomunity1.adapter.DeatailsImageRecyclerAdapter;
import com.mncomunity1.adapter.MockPager2Adapter;
import com.mncomunity1.adapter.MockPagerAdapter;
import com.mncomunity1.adapter.MyImagePagerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.DetailsImage;
import com.mncomunity1.model.PostOrder;
import com.mncomunity1.model.Register;
import com.mncomunity1.model.getOrder;
import com.mncomunity1.pack_chat.data.StaticConfig;
import com.mncomunity1.pack_chat.model.User;
import com.mncomunity1.service.ApiClient;
import com.mncomunity1.until.SimpleDividerItemDecoration;
import com.zanlabs.widget.infiniteviewpager.InfiniteViewPager;
import com.zanlabs.widget.infiniteviewpager.indicator.CirclePageIndicator;

import java.util.ArrayList;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsSpareActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    Dialog dialog;


    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    String image;
    String title;
    String details;

    Spinner spinner;
    Button txt_cont2;

    String userId;
    String companyCode;
    String code;

    int number;
    String aString;

    EditText td_email;
    EditText td_pass;
    TextView btnRegister;
    EditText ed_num;
    Button btn_done;
    Button btn_close;

    String regId;

    ArrayList<DetailsImage> list1 = new ArrayList<>();
    ArrayList<String> list12 = new ArrayList<>();

    DeatailsImageRecyclerAdapter deatailsImageRecyclerAdapter;
    MockPager2Adapter myImagePagerAdapter;
    RecyclerView recyclerView;

    InfiniteViewPager viewPager;
    CirclePageIndicator mCircleIndicator;

    ImageView img_cart;
    TextView badge_notification_6;
    String companyCodes;
    String titleCompany;

    Dialog dialogSubmit;

    TextView title_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_spare);
        ButterKnife.bind(this);
        titleCompany = getIntent().getStringExtra("titleCompany");
        image = getIntent().getStringExtra("image");
        title = getIntent().getStringExtra("title");
        details = getIntent().getStringExtra("details");
        companyCode = getIntent().getStringExtra("companyCode");
        code = getIntent().getStringExtra("code");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        title_name = (TextView) toolbar.findViewById(R.id.title_name);
        title_name.setText(titleCompany);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dialogSubmit = new Dialog(DetailsSpareActivity.this, R.style.FullHeightDialog);
        dialogSubmit.setContentView(R.layout.dailog_submit);

        initFirebase();
        viewPager = (InfiniteViewPager) findViewById(R.id.viewpager2);
        mCircleIndicator = (CirclePageIndicator) findViewById(R.id.indicator2);

        recyclerView = (RecyclerView) findViewById(R.id.cardList_main);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
        recyclerView.setLayoutManager(llm);

        ed_num = (EditText) findViewById(R.id.ed_num);

        dialog = new Dialog(DetailsSpareActivity.this, R.style.FullHeightDialog);
        dialog.setContentView(R.layout.dailog_login);

        userId = sp.getString("userId", "000");
        companyCodes = sp.getString("company_code", "0");

        img_cart = (ImageView) findViewById(R.id.img_cart);
        badge_notification_6 = (TextView) findViewById(R.id.badge_notification_6);

        txt_cont2 = (Button) findViewById(R.id.txt_cont2);

        list12.add(image);

        Log.e("image",image);


        myImagePagerAdapter = new MockPager2Adapter(getApplicationContext());
        myImagePagerAdapter.setDataList(list12);
        viewPager.setAdapter(myImagePagerAdapter);
        mCircleIndicator.setViewPager(viewPager);

        getDetailImage(code);


        txt_cont2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sp.getBoolean("isLogin", false) == false) {

                    dialogSubmit.show();
                    final EditText td_name = (EditText) dialogSubmit.findViewById(R.id.td_name);
                    final EditText td_lastname = (EditText) dialogSubmit.findViewById(R.id.td_lastname);
                    final EditText td_phone = (EditText) dialogSubmit.findViewById(R.id.td_phone);
                    final EditText td_mail = (EditText) dialogSubmit.findViewById(R.id.td_mail);
                    final EditText td_address = (EditText) dialogSubmit.findViewById(R.id.td_address);
                    final EditText td_company = (EditText) dialogSubmit.findViewById(R.id.td_company);
                    final EditText td_password = (EditText) dialogSubmit.findViewById(R.id.td_password);
                    LinearLayout txt_login = (LinearLayout) dialogSubmit.findViewById(R.id.txt_login);
                    Button btn_done = (Button) dialogSubmit.findViewById(R.id.btn_done);
                    Button btn_close = (Button) dialogSubmit.findViewById(R.id.btn_close);


                    txt_login.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            i.putExtra("checkLogin","1");
                            startActivity(i);
                            finish();

                        }
                    });

                    btn_done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String name = td_name.getText().toString();
                            String lastName = td_lastname.getText().toString();
                            String tdPhone = td_phone.getText().toString();
                            String tdMail = td_mail.getText().toString();
                            String tdAddress = td_address.getText().toString();
                            String company = td_company.getText().toString();
                            String password = td_password.getText().toString();

                            if (name == "") {
                                Toast.makeText(getApplicationContext(), "กรุณาใส่ข้อมูล", Toast.LENGTH_SHORT).show();
                            } else {
                                createUser(tdMail, password);
                                registerByServer(name, lastName, tdMail, tdPhone, tdAddress, regId, company, password,userId);
                            }


                        }
                    });

                    btn_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogSubmit.dismiss();
                        }
                    });


                } else {

                    PostOrder(userId, title, code, ed_num.getText().toString(), image, companyCode);


                }
            }
        });


        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(getApplicationContext(), CartTotalActivity.class);
                i.putExtra("userId", userId);
                i.putExtra("company_code", companyCodes);
                startActivity(i);

            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();

        getOrder(userId);
    }


    private void getDetailImage(final String code) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<DetailsImage> userCall = service.getDetailsImage(code);

        userCall.enqueue(new Callback<DetailsImage>() {
            @Override
            public void onResponse(Call<DetailsImage> call, Response<DetailsImage> response) {


                if (response.body().getTotal() != null) {
                    list1.add(response.body());
                    deatailsImageRecyclerAdapter = new DeatailsImageRecyclerAdapter(getApplicationContext(), list1);
                    recyclerView.setAdapter(deatailsImageRecyclerAdapter);
                }

                if (response.body().getImage() != null) {

                    for (int i = 0; i < response.body().getImage().size(); i++) {
                        list12.add(response.body().getImage().get(i).getPath());
                        Log.e("image", response.body().getImage().get(i).getPath());
                    }
                    if(list12.size() > 0){
                        myImagePagerAdapter = new MockPager2Adapter(getApplicationContext());
                        myImagePagerAdapter.setDataList(list12);
                        viewPager.setAdapter(myImagePagerAdapter);
                        mCircleIndicator.setViewPager(viewPager);
                    }

                }

            }

            @Override
            public void onFailure(Call<DetailsImage> call, Throwable t) {

            }
        });
    }

    private void PostOrder(final String user_id, final String nameth, String code, String total, String image, String company_code) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<PostOrder> userCall = service.postOrder(user_id, nameth, code, total, image, company_code);

        userCall.enqueue(new Callback<PostOrder>() {
            @Override
            public void onResponse(Call<PostOrder> call, Response<PostOrder> response) {

                if (response.body().getComplete().get(0).getStatus().equals("1")) {

                    finish();
                }

            }

            @Override
            public void onFailure(Call<PostOrder> call, Throwable t) {

            }
        });
    }

    private void getOrder(final String user_id) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<getOrder> userCall = service.getOrder(user_id);

        userCall.enqueue(new Callback<getOrder>() {
            @Override
            public void onResponse(Call<getOrder> call, Response<getOrder> response) {

                if (response.body().getTotal() != null) {

                    badge_notification_6.setText(response.body().getTotal().size() + "");

                } else {
                    badge_notification_6.setText("0");
                }

            }

            @Override
            public void onFailure(Call<getOrder> call, Throwable t) {

            }
        });
    }


    private void registerByServer(String name, String lastname, String email, String phone, String address, String regid, String company_nameth, String passwords,String userId) {


        dialog.show();
        APIService service = ApiClient.getClient().create(APIService.class);

        Call<Register> userCall = service.getRegisterUpdate(name, lastname, email, phone, address, regid, company_nameth, passwords,userId);

        userCall.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (response.body().getSuccess().equals("1")) {

                    String userId = response.body().getComplete().get(0).getCode();
                    String name = response.body().getComplete().get(0).getNameth();
                    String email = response.body().getComplete().get(0).getEmail();
                    String companyCode = response.body().getComplete().get(0).getCompany_code();


                    editor.putBoolean("isLogin", true);
                    editor.putString("userId", userId);
                    editor.putString("name", name);
                    editor.putString("email", email);
                    editor.putString("company_code", companyCode);
                    editor.commit();

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            dialog.dismiss();
                            Intent i = new Intent(getApplicationContext(), CartTotalActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }, 3000);


                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {

            }
        });
    }

    public void createUser(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(DetailsSpareActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
//                            new LovelyInfoDialog(CartTotalActivity.this) {
//                                @Override
//                                public LovelyInfoDialog setConfirmButtonText(String text) {
//                                    findView(com.yarolegovich.lovelydialog.R.id.ld_btn_confirm).setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View view) {
//                                            dismiss();
//                                        }
//                                    });
//                                    return super.setConfirmButtonText(text);
//                                }
//                            }
//                                    .setTopColorRes(R.color.colorAccent)
//                                    .setIcon(R.drawable.ic_add_friend)
//                                    .setTitle("Register false")
//                                    .setMessage("Email exist or weak password!")
//                                    .setConfirmButtonText("ok")
//                                    .setCancelable(false)
//                                    .show();
                        } else {
                            dialog.dismiss();
                            initNewUserInfo();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                })
        ;
    }

    private void initFirebase() {
        //Khoi tao thanh phan de dang nhap, dang ky
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();

            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    void initNewUserInfo() {
        User newUser = new User();
        newUser.email = user.getEmail();
        newUser.name = user.getEmail().substring(0, user.getEmail().indexOf("@"));
        newUser.avata = StaticConfig.STR_DEFAULT_BASE64;
        FirebaseDatabase.getInstance().getReference().child("user/" + user.getUid()).setValue(newUser);
    }


}
