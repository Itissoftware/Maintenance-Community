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

import com.google.gson.Gson;
import com.mncomunity1.R;
import com.mncomunity1.adapter.GetVendorForOrderRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.OrderVendor;
import com.mncomunity1.service.ApiClient;
import com.mncomunity1.until.SimpleDividerItemDecoration;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VendorOrderActivity extends AppCompatActivity {


    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Bind(R.id.cardList_main)
    RecyclerView recyclerView;

    ArrayList<OrderVendor> listPost = new ArrayList<>();
    GetVendorForOrderRecyclerAdapter getLogErrorRecyclerAdapter;
    String company_code;

    String userId;
    String check;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_order_for_vendor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("รายการขอซื้อ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ButterKnife.bind(this);

        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        userId = sp.getString("userId", "000");
        check = sp.getString("check", "0");
        company_code = sp.getString("company_code", "0");

        Log.e("company_code",company_code);
        getLog(company_code);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));


    }

    private void getLog(String company_code) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<OrderVendor> userCall = service.getOrderForVendor(company_code);

        userCall.enqueue(new Callback<OrderVendor>() {
            @Override
            public void onResponse(Call<OrderVendor> call, Response<OrderVendor> response) {
                Log.e("2.0 getFeed ",new Gson().toJson(response));
                if (response.body().isResult() != false) {

                    for (int i = 0; i < response.body().getTotal().size(); i++) {
                        listPost.add(response.body());

                        getLogErrorRecyclerAdapter = new GetVendorForOrderRecyclerAdapter(getApplicationContext(), listPost);
                        recyclerView.setAdapter(getLogErrorRecyclerAdapter);
                        getLogErrorRecyclerAdapter.notifyDataSetChanged();

                        getLogErrorRecyclerAdapter.SetOnItemVideiosClickListener(new GetVendorForOrderRecyclerAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                String userId = listPost.get(position).getTotal().get(position).getId();
                                String company_code = listPost.get(position).getTotal().get(position).getCompany_code();

                                Intent i = new Intent(getApplicationContext(),OrderDetailsForVendorActivity.class);
                                i.putExtra("userId",userId);
                                i.putExtra("company_code",company_code);
                                startActivity(i);

                            }
                        });

                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<OrderVendor> call, Throwable t) {

            }
        });
    }

}
