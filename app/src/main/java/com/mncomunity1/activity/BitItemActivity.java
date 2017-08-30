package com.mncomunity1.activity;

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
import android.widget.ProgressBar;

import com.mncomunity1.R;
import com.mncomunity1.adapter.ListBitItemToOfferRecyclerAdapter;
import com.mncomunity1.adapter.ListBitOfferVendorRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.BitItem;
import com.mncomunity1.model.BitOfferDetails;
import com.mncomunity1.model.PriceVndor;
import com.mncomunity1.service.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BitItemActivity extends AppCompatActivity {


    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    Toolbar toolbar;

    RecyclerView cardList_main;
    ProgressBar pro;

    ArrayList<PriceVndor> list = new ArrayList<>();
    ListBitItemToOfferRecyclerAdapter listBitItemToOfferRecyclerAdapter;

    String companyCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bit_item_activity2);
        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        companyCode = sp.getString("company_code", "00000");


        cardList_main = (RecyclerView) findViewById(R.id.cardList_main);
        pro = (ProgressBar) findViewById(R.id.pro);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        cardList_main.setHasFixedSize(true);
        final LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        cardList_main.setLayoutManager(llm);


        toolbar.setTitle("รายการที่ขอเสนอราคา");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getListOffer("00083");
    }

    public void getListOffer(String company_code) {
        APIService service = ApiClient.getClient().create(APIService.class);

        Call<PriceVndor> userCall = service.getListBitOfferToVendor(company_code);

        userCall.enqueue(new Callback<PriceVndor>() {
            @Override
            public void onResponse(Call<PriceVndor> call, Response<PriceVndor> response) {


                if (response.body() != null) {
                    pro.setVisibility(View.GONE);
                    for (int i = 0; i < response.body().getTotal().size(); i++) {

                        list.add(response.body());
                        listBitItemToOfferRecyclerAdapter = new ListBitItemToOfferRecyclerAdapter(getApplicationContext(), list);
                        cardList_main.setAdapter(listBitItemToOfferRecyclerAdapter);

                        listBitItemToOfferRecyclerAdapter.SetOnItemVideiosClickListener(new ListBitItemToOfferRecyclerAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                String bit_con = list.get(position).getTotal().get(position).getId();
                                String photo = list.get(position).getTotal().get(position).getPath_photo();
                                String cover = "http://mn-community.com/web/api/upload/" + photo;
                                String title = list.get(position).getTotal().get(position).getTitle();
                                String details = list.get(position).getTotal().get(position).getDetails();
                                String amount = list.get(position).getTotal().get(position).getAmount();

                                Intent i = new Intent(getApplicationContext(), BitItemDetailsActivity.class);
                                i.putExtra("bit_con", bit_con);
                                i.putExtra("photo", cover);
                                i.putExtra("title", title);
                                i.putExtra("details", details);
                                i.putExtra("amount",amount);
                                startActivity(i);
                            }
                        });
                    }

                }

            }

            @Override
            public void onFailure(Call<PriceVndor> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
