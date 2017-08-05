package com.mncomunity1.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mncomunity1.R;
import com.mncomunity1.adapter.NewsArrAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.NewsRc;
import com.mncomunity1.service.ApiClient;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewstActivity extends AppCompatActivity {
    @Bind(R.id.cardList_main)
    RecyclerView recyclerView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;


    String id;

    String title;

    ArrayList<NewsRc> list = new ArrayList<>();
    NewsArrAdapter newsArrAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);

        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");

        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        getNewsArr("1",id);
    }


    public void getNewsArr(String cat, String idContent) {
        APIService service = ApiClient.getClient().create(APIService.class);

        Call<NewsRc> userCall = service.getNewsArr(cat, idContent);

        userCall.enqueue(new Callback<NewsRc>() {
            @Override
            public void onResponse(Call<NewsRc> call, Response<NewsRc> response) {
                if (response.body() != null) {



                    for(int i = 0 ; i < response.body().getArr1().size();i++){

                        list.add(response.body());



                        newsArrAdapter = new NewsArrAdapter(getApplicationContext(),list);
                        recyclerView.setAdapter(newsArrAdapter);
                    }



                }
            }

            @Override
            public void onFailure(Call<NewsRc> call, Throwable t) {

            }
        });
    }


}
