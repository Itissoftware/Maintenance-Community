package com.mncomunity1.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mncomunity1.R;
import com.mncomunity1.adapter.GetVendorOrderRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.DetailsVendor;
import com.mncomunity1.model.Update;
import com.mncomunity1.service.ApiClient;
import com.mncomunity1.until.SimpleDividerItemDecoration;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyOrderActivity extends AppCompatActivity {


    @Bind(R.id.cardList_main)
    RecyclerView recyclerView;



    ArrayList<DetailsVendor> listPost = new ArrayList<>();
    GetVendorOrderRecyclerAdapter getLogErrorRecyclerAdapter;
    SharedPreferences sharedpreferences;
    String company_code;

    String userId;
    String check;

    public static final String mypreference = "mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_order_vendor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("ประวัติการสั่งซื้อ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ButterKnife.bind(this);

        company_code = getIntent().getStringExtra("company_code");

        Log.e("company_code",company_code);

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId = sharedpreferences.getString("userId", "NO");
        check = sharedpreferences.getString("check", "0");
        Log.e("userId", userId);


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        getLog(userId,company_code);

    }

    private void getLog(String userId,String company_codes) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<DetailsVendor> userCall = service.getOrderHistoryMe(userId,company_codes);

        userCall.enqueue(new Callback<DetailsVendor>() {
            @Override
            public void onResponse(Call<DetailsVendor> call, Response<DetailsVendor> response) {
                if (response.body() != null) {
                    for (int i = 0; i < response.body().getTotal().size(); i++) {
                        listPost.add(response.body());


                        getLogErrorRecyclerAdapter = new GetVendorOrderRecyclerAdapter(getApplicationContext(), listPost);
                        // recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), R.drawable.divider));
                        recyclerView.setAdapter(getLogErrorRecyclerAdapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<DetailsVendor> call, Throwable t) {

            }
        });
    }

    private void Update(String userId) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<Update> userCall = service.upDate(userId);

        userCall.enqueue(new Callback<Update>() {
            @Override
            public void onResponse(Call<Update> call, Response<Update> response) {
                if(response.body().getTotal() !=null){

                    if(response.body().getTotal().get(0).getStatus().equals("1")){
                        Toast.makeText(getApplicationContext(),"ยืนยันสำเร็จ",Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),"ยืนยันข้อมูลไม่สำเร็จ",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Update> call, Throwable t) {

            }
        });
    }

}
