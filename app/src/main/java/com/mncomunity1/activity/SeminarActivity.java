package com.mncomunity1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.mncomunity1.R;
import com.mncomunity1.adapter.ArticlesRecyclerAdapter3;
import com.mncomunity1.adapter.SeminarRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.NewsRc;
import com.mncomunity1.model.Post;
import com.mncomunity1.service.ApiClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SeminarActivity extends AppCompatActivity {


    @Bind(R.id.cardList_main)
    RecyclerView recyclerView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    ProgressBar progressBar;

    SeminarRecyclerAdapter seminarRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressBar.setVisibility(View.VISIBLE);


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

    }

    public void getShow(String id) {
        APIService service = ApiClient.getClient().create(APIService.class);

        Call<NewsRc> userCall = service.getSemianerList(id);

        userCall.enqueue(new Callback<NewsRc>() {
            @Override
            public void onResponse(Call<NewsRc> call, Response<NewsRc> response) {
//                for (int i = 0; i < response.body().getPost().size(); i++) {
//
//
//                    //  seminarRecyclerAdapter = new SeminarRecyclerAdapter(getApplicationContext(), list3);
//                    // recyclerView.setAdapter(articlesRecyclerAdapter3);
//
//                }
            }

            @Override
            public void onFailure(Call<NewsRc> call, Throwable t) {

            }
        });
    }

}
