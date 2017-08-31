package com.mncomunity1.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mncomunity1.R;
import com.mncomunity1.adapter.GetHistoryOrderRecyclerAdapter;
import com.mncomunity1.adapter.GetOrderRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.app.Config;
import com.mncomunity1.model.HistoryOrder;
import com.mncomunity1.model.Order;
import com.mncomunity1.service.ApiClient;
import com.mncomunity1.until.SimpleDividerItemDecoration;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderActivity extends AppCompatActivity {

    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    RecyclerView recyclerView;

    ArrayList<Order> listOr = new ArrayList<>();
    GetOrderRecyclerAdapter getLogErrorRecyclerAdapter;


    ArrayList<HistoryOrder> listPost = new ArrayList<>();
    GetHistoryOrderRecyclerAdapter getHistoryOrderRecyclerAdapter;


    String userId;
    String companyCode;
    String check;

    String regId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_get_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("รายการ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);
        Log.e("LOf", "Firebase reg id: " + regId);

        recyclerView = (RecyclerView) findViewById(R.id.cardList_main);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));

        recyclerView.setLayoutManager(llm);


        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        userId = sp.getString("userId", "000");
        companyCode = sp.getString("company_code", "1");
        check = sp.getString("check", "0");

        getVendor(userId);


    }


    private void getVendor(String userId) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<Order> userCall = service.getOrderHistory(userId);

        userCall.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {

                if (response.body().isResult() == true) {

                    for (int i = 0; i < response.body().getTotal().size(); i++) {

                        listOr.add(response.body());

                        getLogErrorRecyclerAdapter = new GetOrderRecyclerAdapter(getApplicationContext(), listOr);
                        recyclerView.setAdapter(getLogErrorRecyclerAdapter);
                        getLogErrorRecyclerAdapter.notifyDataSetChanged();

                        getLogErrorRecyclerAdapter.SetOnItemVideiosClickListener(new GetOrderRecyclerAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                String companyCode = listOr.get(position).getTotal().get(position).getCompany_code();
                                Intent i = new Intent(getApplicationContext(), CompanyOrderActivity.class);
                                i.putExtra("company_code", companyCode);
                                startActivity(i);
                            }
                        });
                    }
                } else {

                }


            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.e("getLocalizedMessage", t.getLocalizedMessage());
                Log.e("getMessage", t.getMessage());
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

    }
}
