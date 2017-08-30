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
import android.widget.Toast;

import com.mncomunity1.R;
import com.mncomunity1.adapter.ListQuestionRecyclerAdapter;
import com.mncomunity1.adapter.ListQuestionVendorRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.QuestionList;
import com.mncomunity1.model.QuestionListVendor;
import com.mncomunity1.service.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListQuestionVendorActivity extends AppCompatActivity {
    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    Toolbar toolbar;

    RecyclerView cardList_main;
    ProgressBar pro;
    String userId;
    String id;

    ArrayList<QuestionListVendor> list = new ArrayList<>();

    ListQuestionVendorRecyclerAdapter listQuestionVendorRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_question2);
        cardList_main = (RecyclerView) findViewById(R.id.cardList_main);
        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        userId = sp.getString("userId", "000");
        id = getIntent().getStringExtra("id");

        Log.e("id",id);

        pro = (ProgressBar) findViewById(R.id.pro);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        cardList_main.setHasFixedSize(true);
        final LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        cardList_main.setLayoutManager(llm);

        toolbar.setTitle("ผู้ขายที่เสนอราคา");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getListQuestVendor(id);

    }

    public void getListQuestVendor(String user_id) {
        APIService service = ApiClient.getClient().create(APIService.class);

        Call<QuestionListVendor> userCall = service.getListQuestVendor(user_id);

        userCall.enqueue(new Callback<QuestionListVendor>() {
            @Override
            public void onResponse(Call<QuestionListVendor> call, Response<QuestionListVendor> response) {

                if (response.body() != null) {

                    if(response.body().getTotal() != null){
                        for (int i = 0; i < response.body().getTotal().size(); i++) {
                            pro.setVisibility(View.GONE);
                            list.add(response.body());

                            listQuestionVendorRecyclerAdapter = new ListQuestionVendorRecyclerAdapter(getApplicationContext(), list);
                            cardList_main.setAdapter(listQuestionVendorRecyclerAdapter);

                        }

                        listQuestionVendorRecyclerAdapter.SetOnItemVideiosClickListener(new ListQuestionVendorRecyclerAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                String bit_vender = list.get(position).getTotal().get(position).getId_com();

                                Intent i = new Intent(getApplicationContext(), ListQuestionChatActivity.class);
                                i.putExtra("bit_vender",bit_vender);
                                startActivity(i);

                            }
                        });
                    }else {
                        pro.setVisibility(View.GONE);
                    }


                }
            }

            @Override
            public void onFailure(Call<QuestionListVendor> call, Throwable t) {

            }
        });
    }

}
