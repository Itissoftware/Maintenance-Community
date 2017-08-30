package com.mncomunity1.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.mncomunity1.R;
import com.mncomunity1.adapter.ListQuestionChatRecyclerAdapter;
import com.mncomunity1.adapter.ListQuestionVendorRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.GetMsgChat;
import com.mncomunity1.model.ModelPost;
import com.mncomunity1.model.QuestionListVendor;
import com.mncomunity1.service.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListQuestionChatActivity extends AppCompatActivity {
    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    Toolbar toolbar;

    RecyclerView cardList_main;
    ProgressBar pro;
    String userId;
    String id;

    String bit_vender;

    ArrayList<GetMsgChat> list = new ArrayList<>();

    ListQuestionChatRecyclerAdapter listQuestionChatRecyclerAdapter;

    EditText et_chat;
    Button bnt_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_chat_activity);
        cardList_main = (RecyclerView) findViewById(R.id.cardList_main);
        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        userId = sp.getString("userId", "000");
        id = getIntent().getStringExtra("id");
        bit_vender = getIntent().getStringExtra("bit_vender");

        et_chat = (EditText) findViewById(R.id.et_chat);
        bnt_send = (Button) findViewById(R.id.bnt_send);
        pro = (ProgressBar) findViewById(R.id.pro);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("รายการสอบถาม");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cardList_main.setHasFixedSize(true);
        final LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        cardList_main.setLayoutManager(llm);


        bnt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getListSendChat(bit_vender, et_chat.getText().toString(), "", userId);
            }
        });

        getListChat(bit_vender);
    }

    public void getListSendChat(String bit_venders, String msg, String image, String sender) {
        APIService service = ApiClient.getClient().create(APIService.class);

        Call<ModelPost> userCall = service.postBitOfferChat(bit_venders, msg, image, sender);

        userCall.enqueue(new Callback<ModelPost>() {
            @Override
            public void onResponse(Call<ModelPost> call, Response<ModelPost> response) {

                if (response.body().getSucces().equals("1")) {

                    Intent i = new Intent(getApplicationContext(), ListQuestionChatActivity.class);
                    i.putExtra("bit_vender", bit_vender);
                    i.putExtra("id", id);
                    startActivity(i);
                    finish();
                } else {

                }
            }

            @Override
            public void onFailure(Call<ModelPost> call, Throwable t) {

            }
        });
    }

    public void getListChat(String bit_vender) {
        APIService service = ApiClient.getClient().create(APIService.class);

        Call<GetMsgChat> userCall = service.getChatMsg(bit_vender);

        userCall.enqueue(new Callback<GetMsgChat>() {
            @Override
            public void onResponse(Call<GetMsgChat> call, Response<GetMsgChat> response) {

                if (response.body().getTotal() != null) {
                    pro.setVisibility(View.GONE);
                    for (int i = 0; i < response.body().getTotal().size(); i++) {
                        list.add(response.body());
                        listQuestionChatRecyclerAdapter = new ListQuestionChatRecyclerAdapter(getApplicationContext(), list);
                        cardList_main.setAdapter(listQuestionChatRecyclerAdapter);
                    }
                } else {
                    pro.setVisibility(View.GONE);
                }


            }

            @Override
            public void onFailure(Call<GetMsgChat> call, Throwable t) {

            }
        });
    }

}
