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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mncomunity1.R;
import com.mncomunity1.adapter.GetOrderForVendorDetailsRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.OrderForDetails;
import com.mncomunity1.model.Update;
import com.mncomunity1.service.ApiClient;
import com.mncomunity1.until.SimpleDividerItemDecoration;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsForVendorActivity extends AppCompatActivity {


    @Bind(R.id.cardList_main)
    RecyclerView recyclerView;

    @Bind(R.id.txt_name)
    TextView txt_name;

    @Bind(R.id.txt_company)
    TextView txt_company;

    @Bind(R.id.txt_email)
    TextView txt_email;

    @Bind(R.id.txt_phone)
    TextView txt_phone;

    @Bind(R.id.txt_address)
    TextView txt_address;



    Button btn_done;

    ArrayList<OrderForDetails> listPost = new ArrayList<>();
    GetOrderForVendorDetailsRecyclerAdapter getLogErrorRecyclerAdapter;
    SharedPreferences sharedpreferences;
    String userId;
    String statusVendor;
    String company_code;

    public static final String mypreference = "mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_order_vendor_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        btn_done = (Button) findViewById(R.id.btn_done);
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

        userId = getIntent().getStringExtra("userId");
        company_code = getIntent().getStringExtra("company_code");

        Log.e("userId",userId);
        Log.e("company_code",company_code);


        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        statusVendor = sharedpreferences.getString("statusVendor","0");
        getLog(userId,company_code);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        btn_done.setVisibility(View.GONE);
        if (statusVendor.equals("0")) {
            btn_done.setVisibility(View.GONE);
        } else {
            btn_done.setVisibility(View.VISIBLE);
            btn_done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    for (int i = 0; i < listPost.size(); i++) {
                        Update(listPost.get(i).getTotal().get(i).getCompany_code());
                    }
                }
            });
        }

    }

    private void getLog(String userId,String companycode) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<OrderForDetails> userCall = service.getOrderHistoryVendor(userId,companycode);

        userCall.enqueue(new Callback<OrderForDetails>() {
            @Override
            public void onResponse(Call<OrderForDetails> call, Response<OrderForDetails> response) {
                if (response.body().isResult() != false) {

                    String name = response.body().getNameth();
                    String nameCom = response.body().getNameCom();
                    String email = response.body().getEmail();
                    String phone = response.body().getPhone();
                    String address = response.body().getAddress();


                    txt_name.setText(name);
                    txt_company.setText(nameCom);
                    txt_email.setText(email);
                    txt_phone.setText(phone);
                    txt_address.setText(address);

                    for (int i = 0; i < response.body().getTotal().size(); i++) {
                        listPost.add(response.body());


                        getLogErrorRecyclerAdapter = new GetOrderForVendorDetailsRecyclerAdapter(getApplicationContext(), listPost);
                        // recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), R.drawable.divider));
                        recyclerView.setAdapter(getLogErrorRecyclerAdapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<OrderForDetails> call, Throwable t) {

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
