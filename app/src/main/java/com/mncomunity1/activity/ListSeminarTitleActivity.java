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
import android.widget.ProgressBar;

import com.mncomunity1.R;
import com.mncomunity1.adapter.ListRecyclerAdapter;
import com.mncomunity1.adapter.NewsRecyclerAdapter;
import com.mncomunity1.adapter.SeminarTitleAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.News;
import com.mncomunity1.model.PostUpdateStatus;
import com.mncomunity1.model.SeminarTitle;
import com.mncomunity1.service.ApiClient;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListSeminarTitleActivity extends AppCompatActivity {

    @Bind(R.id.cardList_main)
    RecyclerView recyclerView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    String Key;


    ArrayList<SeminarTitle> listNews = new ArrayList<>();
    SeminarTitleAdapter newsRecyclerAdapter;


    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    String userId;

    ProgressBar pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        ButterKnife.bind(this);

        pro = (ProgressBar) findViewById(R.id.pro);

        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        userId = sp.getString("userId", "000");
        toolbar.setTitle("สัมมนา");


        // loginByServerNews(userId);


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.setLayoutManager(llm);
        //   recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        getSeminar();
    }


    private void getSeminar() {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<SeminarTitle> userCall = service.getSeminar();

        userCall.enqueue(new Callback<SeminarTitle>() {
            @Override
            public void onResponse(Call<SeminarTitle> call, Response<SeminarTitle> response) {
                if (response.body().getTotal() != null) {
                    listNews.clear();
                    for (int i = 0; i < response.body().getTotal().size(); i++) {
                        pro.setVisibility(View.GONE);
                        listNews.add(response.body());
                        newsRecyclerAdapter = new SeminarTitleAdapter(getApplicationContext(), listNews);

                        // recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), R.drawable.divider));
                        recyclerView.setAdapter(newsRecyclerAdapter);
                        newsRecyclerAdapter.notifyDataSetChanged();

                        newsRecyclerAdapter.SetOnItemVideiosClickListener(new ListRecyclerAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                String id = listNews.get(position).getTotal().get(position).getId();

                                Intent i = new Intent(getApplicationContext(), ListSeminarDetailsActivity.class);
                                i.putExtra("id", id);
                                startActivity(i);

                            }
                        });



                    }
                }
            }

            @Override
            public void onFailure(Call<SeminarTitle> call, Throwable t) {

            }
        });
    }

}
