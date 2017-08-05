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
import com.mncomunity1.adapter.ListRecyclerAdapter;
import com.mncomunity1.adapter.ListSRecyclerAdapter;
import com.mncomunity1.adapter.NewsRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.News;
import com.mncomunity1.model.Post;
import com.mncomunity1.model.PostS;
import com.mncomunity1.model.PostUpdateStatus;
import com.mncomunity1.service.ApiClient;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    @Bind(R.id.cardList_main)
    RecyclerView recyclerView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    String Key;


    private static final int VERTICAL_ITEM_SPACE = 48;
    ArrayList<Post> listPost = new ArrayList<>();
    ArrayList<PostS> listPostS = new ArrayList<>();
    ArrayList<News> listNews = new ArrayList<>();
    ListRecyclerAdapter listRecyclerAdapter;
    NewsRecyclerAdapter newsRecyclerAdapter;
    ListSRecyclerAdapter listSRecyclerAdapter;



    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        ButterKnife.bind(this);

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId = sharedpreferences.getString("userId", "1");
        Log.e("ddddd", userId);


        Key = getIntent().getStringExtra("Key");

        if (Key.equals("0")) {
            toolbar.setTitle("ข่าวสาร");
            loginByServerNews(userId);
        }
        if (Key.equals("1")) {
            toolbar.setTitle("สัมมนา");
            loginByServerTrain();
        }

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


    }


    private void loginByServerNews(String user_id) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<News> userCall = service.getFeed(user_id);

        userCall.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.body().getTotal() != null) {

                    for (int i = 0; i < response.body().getTotal().size(); i++) {

                        listNews.add(response.body());
                        newsRecyclerAdapter = new NewsRecyclerAdapter(getApplicationContext(), listNews);

                        // recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), R.drawable.divider));
                        recyclerView.setAdapter(newsRecyclerAdapter);
                        newsRecyclerAdapter.notifyDataSetChanged();

                        newsRecyclerAdapter.SetOnItemVideiosClickListener(new NewsRecyclerAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                String id = listNews.get(position).getTotal().get(position).getId();
                                String title = listNews.get(position).getTotal().get(position).getNameth();


                                UpdateStatus(userId, id);


                                Intent i = new Intent(getApplicationContext(), WebActivity.class);
                                i.putExtra("title", title);
                                i.putExtra("id", id);
                                startActivity(i);


                            }
                        });

                    }
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
    }

    private void UpdateStatus(String id, String cat) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<PostUpdateStatus> userCall = service.getUpdateStatus(id, cat);

        userCall.enqueue(new Callback<PostUpdateStatus>() {
            @Override
            public void onResponse(Call<PostUpdateStatus> call, Response<PostUpdateStatus> response) {


                if (response.body().getTotal() != null) {

                    if (response.body().getTotal().get(0).getStatus().equals("1")) {


                    } else {

                    }


                }

            }

            @Override
            public void onFailure(Call<PostUpdateStatus> call, Throwable t) {

            }
        });
    }


    private void loginByServerTrain() {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<PostS> userCall = service.getTrian();

        userCall.enqueue(new Callback<PostS>() {
            @Override
            public void onResponse(Call<PostS> call, Response<PostS> response) {
                if (response.body().getPost() != null) {

                    for (int i = 0; i < response.body().getPost().size(); i++) {
                        listPostS.add(response.body());
                        listSRecyclerAdapter = new ListSRecyclerAdapter(getApplicationContext(), listPostS);
                        // recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), R.drawable.divider));
                        recyclerView.setAdapter(listSRecyclerAdapter);

                        Log.e("Title: ", listPostS.get(i).getPost().get(i).getTitle());

                    }
                }
            }

            @Override
            public void onFailure(Call<PostS> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();



    }
}
