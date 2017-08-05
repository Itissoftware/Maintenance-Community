package com.mncomunity1.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mncomunity1.R;
import com.mncomunity1.adapter.SpareOnCompanyRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.app.Config;
import com.mncomunity1.model.Delete;
import com.mncomunity1.model.ModelSpare;
import com.mncomunity1.model.PostOrder;
import com.mncomunity1.model.getOrder;
import com.mncomunity1.service.ApiClient;
import com.mncomunity1.until.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyDetailActivity extends AppCompatActivity {

    @Bind(R.id.txt_nameth)
    TextView nameTh;

    @Bind(R.id.txt_details_more)
    TextView txtDetailsMore;


    @Bind(R.id.img_cover)
    ImageView img_cover;

    @Bind(R.id.btn_back)
    Button btnBack;


    @Bind(R.id.btnGetSelected)
    Button btnGetSelected;
    String n;

    @Bind(R.id.cardList_main)
    RecyclerView recyclerView;

    String nameCompanyTh;
    String nameCompanyEn;
    String address;
    String cover;
    String companyCodes;
    String url;
    Dialog dialog;
    Dialog dialogDetails;
    Dialog dialogTotal;
    Dialog dialogDe;

    ArrayList<ModelSpare> listSpare = new ArrayList<>();
    List<getOrder> mCartList = new ArrayList<>();
    SpareOnCompanyRecyclerAdapter spareCheckBoxAdapter;

    ImageView img_cart;
    TextView badge_notification_6;


    String userId;
    int number = 0;

    String regId;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_profile);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("รายละเอียดบริษัท");

        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);
        Log.e("LOf", "Firebase reg id: " + regId);

        img_cart = (ImageView) findViewById(R.id.img_cart);
        badge_notification_6 = (TextView) findViewById(R.id.badge_notification_6);

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId = sharedpreferences.getString("userId", "0");
        Log.e("userId:", userId);

        dialogDetails = new Dialog(CompanyDetailActivity.this, R.style.FullHeightDialog);
        dialogDetails.setContentView(R.layout.dailog_submit_spare);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        nameCompanyTh = getIntent().getStringExtra("nameCompanyTh");
        nameCompanyEn = getIntent().getStringExtra("nameCompanyEn");
        address = getIntent().getStringExtra("address");
        companyCodes = getIntent().getStringExtra("companyCode");
        cover = getIntent().getStringExtra("cover");

        url = "http://mn-community.com/admin_mc/" + cover;
        Log.e("imageUrl", url);


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        nameTh.setText(nameCompanyTh);

        loginByServerTrain(companyCodes);

        Glide.with(getApplicationContext())
                .load(url)
                .centerCrop()
                .crossFade()
                .into(img_cover);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        txtDetailsMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new Dialog(CompanyDetailActivity.this, R.style.FullHeightDialog);
                dialog.setContentView(R.layout.dailog_details_company);
                dialog.show();

                TextView txt_title = (TextView) dialog.findViewById(R.id.txt_title);
                TextView txt_details = (TextView) dialog.findViewById(R.id.txt_details);
                Button btn_close = (Button) dialog.findViewById(R.id.btn_close);

                txt_title.setText(nameCompanyTh);
                txt_details.setText(address);

                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

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

        getOrder(userId);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // loginByServerTrain(companyCode);
        getOrder(userId);
    }

    private void loginByServerTrain(final String companyCode) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<ModelSpare> userCall = service.getSpare(companyCode);

        userCall.enqueue(new Callback<ModelSpare>() {
            @Override
            public void onResponse(Call<ModelSpare> call, Response<ModelSpare> response) {
                if (response.body() != null) {

                    if (response.body().isResult() != false) {
                        for (int i = 0; i < response.body().getTotal().size(); i++) {

                            listSpare.add(response.body());
                            spareCheckBoxAdapter = new SpareOnCompanyRecyclerAdapter(getApplicationContext(), listSpare);
                            recyclerView.setAdapter(spareCheckBoxAdapter);
                            companyCodes = listSpare.get(i).getTotal().get(i).getCompanycode();
                            Log.e("companyCode", companyCodes);

                            spareCheckBoxAdapter.SetOnItemVideiosClickListener(new SpareOnCompanyRecyclerAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {

                                    final String title = listSpare.get(position).getTotal().get(position).getNameProduct();
                                    final String companyCode = listSpare.get(position).getTotal().get(position).getCompanycode();
                                    final String code = listSpare.get(position).getTotal().get(position).getId();
                                    final String url = "http://mn-community.com/admin_mc/" + listSpare.get(position).getTotal().get(position).getImg();
                                    String details = listSpare.get(position).getTotal().get(position).getDetails();
//                                    dialogDe = new Dialog(CompanyDetailActivity.this, R.style.FullHeightDialog);
//                                    dialogDe.setContentView(R.layout.dailog_details_spare);
//
//                                    ImageView img_cover11 = (ImageView) dialogDe.findViewById(R.id.img_cover11);
//                                    TextView txt_ddd = (TextView) dialogDe.findViewById(R.id.txt_ddd);
//                                    dialogDe.show();
//
//                                    Glide.with(getApplicationContext())
//                                            .load(url)
//                                            .centerCrop()
//                                            .crossFade()
//                                            .into(img_cover11);
//
//                                    txt_ddd.setText(details);


                                    Intent i = new Intent(getApplicationContext(), DetailsSpareActivity.class);
                                    i.putExtra("title", title);
                                    i.putExtra("image", url);
                                    i.putExtra("details",details);
                                    i.putExtra("companyCode",companyCode);
                                    i.putExtra("code",code);
                                    startActivity(i);


                                }
                            });

                            spareCheckBoxAdapter.SetOnItemTextClickListener(new SpareOnCompanyRecyclerAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {

                                    if (userId.equals("0")) {
                                        Toast.makeText(getApplicationContext(), "ยังไม่ได้ลงทะเบียน", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                                        startActivity(i);
                                        finish();
                                    } else {
                                        dialogDetails.show();
                                        final String title = listSpare.get(position).getTotal().get(position).getNameProduct();
                                        String details = listSpare.get(position).getTotal().get(position).getDetails();
                                        String cover = listSpare.get(position).getTotal().get(position).getImg();
                                        final String url = "http://mn-community.com/admin_mc/" + cover;


                                        final ImageView img_cover = (ImageView) dialogDetails.findViewById(R.id.img_cover);
                                        TextView txt_del = (TextView) dialogDetails.findViewById(R.id.txt_del);
                                        final TextView txt_count = (TextView) dialogDetails.findViewById(R.id.txt_count);
                                        Button btn_done = (Button) dialogDetails.findViewById(R.id.btn_done);
                                        final EditText et_number = (EditText) dialogDetails.findViewById(R.id.et_number);
                                        Button txt_cont1 = (Button) dialogDetails.findViewById(R.id.txt_cont1);
                                        Button txt_cont2 = (Button) dialogDetails.findViewById(R.id.txt_cont2);

                                        Glide.with(getApplicationContext())
                                                .load(url)
                                                .centerCrop()
                                                .crossFade()
                                                .into(img_cover);

                                        txt_del.setText(details);

                                        txt_cont1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                number--;
                                                n = Integer.toString(number);
                                                et_number.setText(n);
                                            }
                                        });

                                        txt_cont2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                number++;
                                                n = Integer.toString(number);
                                                et_number.setText(n);
                                            }
                                        });


                                        btn_done.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {


                                                PostOrder(userId, title, "", et_number.getText().toString(), url, companyCodes);
                                            }
                                        });
                                    }

                                }
                            });


                            if (mCartList != null) {

                            } else {
                                badge_notification_6.setText(response.body().getTotal().size() + "");
                                badge_notification_6.notify();
                            }

                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "ไม่มีข้อมูล", Toast.LENGTH_SHORT).show();
                    }


                }
            }

            @Override
            public void onFailure(Call<ModelSpare> call, Throwable t) {
                Log.e("getLocalizedMessage", t.getLocalizedMessage() + "");
                Log.e("getMessage", t.getMessage() + "");
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
                    dialogDetails.dismiss();
                    Intent i = new Intent(getApplicationContext(), CompanyDetailActivity.class);
                    i.putExtra("companyCode", companyCodes);
                    i.putExtra("nameCompanyTh", nameCompanyTh);
                    i.putExtra("nameCompanyEn", nameCompanyEn);
                    i.putExtra("cover", cover);
                    i.putExtra("address", address);
                    startActivity(i);
                    finish();

                }

            }

            @Override
            public void onFailure(Call<PostOrder> call, Throwable t) {

            }
        });
    }


    private void deleteOrder(final String user_id) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<Delete> userCall = service.deleteOrder(user_id);

        userCall.enqueue(new Callback<Delete>() {
            @Override
            public void onResponse(Call<Delete> call, Response<Delete> response) {
                if (response.body().getTotal().get(0).getStatus().equals("1")) {
                    mCartList.clear();
                    dialogTotal.dismiss();
                    Intent i = new Intent(getApplicationContext(), CompanyDetailActivity.class);
                    i.putExtra("companyCode", companyCodes);
                    startActivity(i);
                    finish();

                }
            }

            @Override
            public void onFailure(Call<Delete> call, Throwable t) {

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

                    // Make sure to clear the selections
                    for (int i = 0; i < response.body().getTotal().size(); i++) {

                        mCartList.add(response.body());

                    }

                }else{
                    badge_notification_6.setText("0");
                }

            }

            @Override
            public void onFailure(Call<getOrder> call, Throwable t) {

            }
        });
    }


}
