package com.mncomunity1.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import android.widget.Spinner;
import android.widget.TextView;

import com.mncomunity1.R;
import com.mncomunity1.adapter.DeatailsImageRecyclerAdapter;
import com.mncomunity1.adapter.MyImagePagerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.DetailsImage;
import com.mncomunity1.model.PostOrder;
import com.mncomunity1.model.getOrder;
import com.mncomunity1.service.ApiClient;
import com.mncomunity1.until.SimpleDividerItemDecoration;

import java.util.ArrayList;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsSpareActivity extends AppCompatActivity {


    Dialog dialog;


    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";

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
    MyImagePagerAdapter myImagePagerAdapter;
    RecyclerView recyclerView;

    ViewPager viewPager;

    ImageView img_cart;
    TextView badge_notification_6;
    String companyCodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_spare);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("รายละเอียดสินค้า");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        viewPager = (ViewPager) findViewById(R.id.viewPager);

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

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId = sharedpreferences.getString("userId", "0");
        companyCodes = sharedpreferences.getString("company_code", "0");

        img_cart = (ImageView) findViewById(R.id.img_cart);
        badge_notification_6 = (TextView) findViewById(R.id.badge_notification_6);

        txt_cont2 = (Button) findViewById(R.id.txt_cont2);

        image = getIntent().getStringExtra("image");
        title = getIntent().getStringExtra("title");
        details = getIntent().getStringExtra("details");
        companyCode = getIntent().getStringExtra("companyCode");
        code = getIntent().getStringExtra("code");
        getDetailImage(code);


        txt_cont2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PostOrder(userId, title, code, ed_num.getText().toString(), image, companyCode);

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
                    myImagePagerAdapter = new MyImagePagerAdapter(getApplicationContext(), list12);
                    viewPager.setAdapter(myImagePagerAdapter);
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


}
