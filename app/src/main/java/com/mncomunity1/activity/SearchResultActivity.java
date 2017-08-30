package com.mncomunity1.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mncomunity1.R;
import com.mncomunity1.adapter.SearchSpareRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.SearchModel;
import com.mncomunity1.model.getOrder;
import com.mncomunity1.service.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchResultActivity extends AppCompatActivity {

    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    TextView badge_notification_6;
    EditText tdSearch;
    Button btnDone;

    SearchSpareRecyclerAdapter searchSpareRecyclerAdapter;
    RecyclerView cardList_main;
    String keyword;
    String cat;

    ArrayList<SearchModel> list = new ArrayList<>();
    String search;
    String company_code;

    Toolbar toolbar;

    String userId;
    String companyCodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        userId = sp.getString("userId", "00000");
        companyCodes = sp.getString("company_code","0");
        Log.e("userId",userId);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ค้นหาสินค้า");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        badge_notification_6 = (TextView) findViewById(R.id.badge_notification_6);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnDone = (Button) findViewById(R.id.btn_done);
        tdSearch = (EditText) findViewById(R.id.td_search);

        keyword = getIntent().getStringExtra("keyword");
        company_code = getIntent().getStringExtra("company_code");
        cat = getIntent().getStringExtra("cat");

        cardList_main = (RecyclerView) findViewById(R.id.cardList_main);
        cardList_main.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        cardList_main.setLayoutManager(llm);

        Log.e("keyword",keyword+"");

        if (keyword.equals("")) {

        }else{
            getSearch(keyword, "", "");
        }
        if (keyword == "") {

           // getSearch("", "", "");
        }
        if (company_code != "") {
           // getSearch(keyword, "", company_code);
        }

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                search = tdSearch.getText().toString();


//                if (TextUtils.isEmpty(search)) {
//                    Toast.makeText(getApplicationContext(), "ใส่ข้อมูลค้นหา", Toast.LENGTH_SHORT).show();
//
//                } else {
//
//                  //  getSearch(search);
//
//                    Intent i =new Intent(getApplicationContext(),SearchResultActivity.class);
//                    i.putExtra("keyword",search);
//                    startActivity(i);
//
//                }

                Intent i = new Intent(getApplicationContext(), SearchResultActivity.class);
                i.putExtra("keyword", search);
                startActivity(i);
                finish();

            }
        });


    }

    private void getSearch(String keyword, String cat, String vendor) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<SearchModel> userCall = service.getSearch(keyword, cat, vendor);

        userCall.enqueue(new Callback<SearchModel>() {
            @Override
            public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {


                if (response.body().isResult() == true) {
                    list.clear();
                    for (int i = 0; i < response.body().getTotal().size(); i++) {
                        list.add(response.body());
                    }

                    searchSpareRecyclerAdapter = new SearchSpareRecyclerAdapter(getApplicationContext(), list);
                    cardList_main.setAdapter(searchSpareRecyclerAdapter);

                    searchSpareRecyclerAdapter.SetOnItemVideiosClickListener(new SearchSpareRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            String nameCompanyTh = list.get(position).getTotal().get(position).getNameProduct();
                            String cover = "http://mn-community.com/admin_mc/" + list.get(position).getTotal().get(position).getImg();
                            String details = list.get(position).getTotal().get(position).getDetails();
                            String companyCode = list.get(position).getTotal().get(position).getCompanycode();
                            String code = list.get(position).getTotal().get(position).getId();

                            Intent i = new Intent(getApplicationContext(), DetailsSpareActivity.class);
                            i.putExtra("title", nameCompanyTh);
                            i.putExtra("image", cover);
                            i.putExtra("details", details);
                            i.putExtra("companyCode", companyCode);
                            i.putExtra("code", code);
                            startActivity(i);

                        }
                    });
                }


            }

            @Override
            public void onFailure(Call<SearchModel> call, Throwable t) {

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
