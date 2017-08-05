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

import com.mncomunity1.R;
import com.mncomunity1.adapter.GetHistoryOrderRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.HistoryOrder;
import com.mncomunity1.service.ApiClient;
import com.mncomunity1.until.SimpleDividerItemDecoration;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryOrderActivity2 extends AppCompatActivity {

    RecyclerView recyclerView;


    ArrayList<HistoryOrder> listPost = new ArrayList<>();
    GetHistoryOrderRecyclerAdapter getHistoryOrderRecyclerAdapter;
    SharedPreferences sharedpreferences;

    public static final String mypreference = "mypref";

    String userId;
    String companyCode;
    String check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_get_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ประวัติการสั่งซื้อ");
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.cardList_main);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));

        recyclerView.setLayoutManager(llm);


        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId = sharedpreferences.getString("userId", "NO");
        companyCode = sharedpreferences.getString("company_code", "1");
        check = sharedpreferences.getString("check", "0");
        Log.e("userId", userId);
        Log.e("check", check);


        getPublic(userId);

    }

    private void getPublic(String userId) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<HistoryOrder> userCall = service.getHistoryVender(userId);

        userCall.enqueue(new Callback<HistoryOrder>() {
            @Override
            public void onResponse(Call<HistoryOrder> call, Response<HistoryOrder> response) {

                if (response.body() != null) {
                    if (response.body().isResult() == true) {
                        for (int i = 0; i < response.body().getTotal().size(); i++) {
                            listPost.add(response.body());
                            getHistoryOrderRecyclerAdapter = new GetHistoryOrderRecyclerAdapter(getApplicationContext(), listPost);
                            // recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), R.drawable.divider));
                            recyclerView.setAdapter(getHistoryOrderRecyclerAdapter);
                            getHistoryOrderRecyclerAdapter.notifyDataSetChanged();
                            getHistoryOrderRecyclerAdapter.SetOnItemVideiosClickListener(new GetHistoryOrderRecyclerAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    String companyCode = listPost.get(position).getTotal().get(position).getCompany_code();
                                    Intent i = new Intent(getApplicationContext(), CompanyOrderActivity.class);
                                    i.putExtra("company_code", companyCode);
                                    startActivity(i);
                                }
                            });
                        }
                    }else{

                    }


                }

            }

            @Override
            public void onFailure(Call<HistoryOrder> call, Throwable t) {

            }
        });
    }

}
