package com.mncomunity1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.mncomunity1.R;
import com.mncomunity1.adapter.ArticlesRecyclerAdapter;
import com.mncomunity1.adapter.ArticlesRecyclerAdapter2;
import com.mncomunity1.adapter.ArticlesRecyclerAdapter3;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.Post;
import com.mncomunity1.service.ApiClient;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticlesActivity extends AppCompatActivity {
    @Bind(R.id.cardList_main)
    RecyclerView recyclerView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;


    ProgressBar pro;
    String id;

    String title;
    String code;
    String id_titld;

    ArrayList<Post> list = new ArrayList<>();
    ArrayList<Post> list2 = new ArrayList<>();
    ArrayList<Post> list3 = new ArrayList<>();
    ArrayList<Post> list4 = new ArrayList<>();
    ArticlesRecyclerAdapter newsArrAdapter;
    ArticlesRecyclerAdapter2 articlesRecyclerAdapter2;
    ArticlesRecyclerAdapter3 articlesRecyclerAdapter3;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        pro = (ProgressBar) findViewById(R.id.pro);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);

        code = getIntent().getStringExtra("code");
        id_titld = getIntent().getStringExtra("title");

        Log.e("code", code);

        if (code.equals("0")) {
            getNewsArrList();
        }
        if (code.equals("1")) {
            getNewsArrList2();
        }
        if (code.equals("2")) {
            getNewsArrList3();
        }
        if (code.equals("3")) {
            loginByServerAtr();
        }


        toolbar.setTitle(id_titld);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }


    public void getNewsArrList() {
        APIService service = ApiClient.getClient().create(APIService.class);

        Call<Post> userCall = service.getArtList();

        userCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                for (int i = 0; i < response.body().getPost().size(); i++) {

                    list.add(response.body());
                    pro.setVisibility(View.GONE);

                    newsArrAdapter = new ArticlesRecyclerAdapter(getApplicationContext(), list);
                    recyclerView.setAdapter(newsArrAdapter);

                    newsArrAdapter.SetOnItemVideiosClickListener(new ArticlesRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            String url = list.get(position).getPost().get(position).getLink();
                            String title = list.get(position).getPost().get(position).getTitle();
                            Intent i = new Intent(getApplicationContext(), WebTabActivity.class);
                            i.putExtra("url", url);
                            i.putExtra("title", title);
                            startActivity(i);


                        }
                    });


                }

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    public void getNewsArrList2() {
        APIService service = ApiClient.getClient().create(APIService.class);

        Call<Post> userCall = service.getArtList2();

        userCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                list2.clear();
                for (int i = 0; i < response.body().getPost().size(); i++) {
                    pro.setVisibility(View.GONE);
                    list2.add(response.body());

                    articlesRecyclerAdapter2 = new ArticlesRecyclerAdapter2(getApplicationContext(), list2);
                    recyclerView.setAdapter(articlesRecyclerAdapter2);

                    articlesRecyclerAdapter2.SetOnItemVideiosClickListener(new ArticlesRecyclerAdapter2.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            Intent i = new Intent(getApplicationContext(), ParseJsonActivity.class);
                            i.putExtra("link", list2.get(position).getPost().get(position).getLink());
                            startActivity(i);
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    public void getNewsArrList3() {
        APIService service = ApiClient.getClient().create(APIService.class);

        Call<Post> userCall = service.getArtList3();

        userCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                for (int i = 0; i < response.body().getPost().size(); i++) {
                    pro.setVisibility(View.GONE);
                    list3.add(response.body());


                    articlesRecyclerAdapter3 = new ArticlesRecyclerAdapter3(getApplicationContext(), list3);
                    recyclerView.setAdapter(articlesRecyclerAdapter3);
                    articlesRecyclerAdapter3.SetOnItemVideiosClickListener(new ArticlesRecyclerAdapter3.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            String url = list3.get(position).getPost().get(position).getLink();
                            String title = list3.get(position).getPost().get(position).getTitle();
                            Intent i = new Intent(getApplicationContext(), WebTabActivity.class);
                            i.putExtra("url", url);
                            i.putExtra("title", title);
                            startActivity(i);
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    public void loginByServerAtr() {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<Post> userCall = service.getSuccess();

        userCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (response.body().getPost() != null) {

                    list.clear();

                    for (int i = 0; i < response.body().getPost().size(); i++) {

                        list4.add(response.body());

                        Log.e("size",list.size()+"");
                        articlesRecyclerAdapter3 = new ArticlesRecyclerAdapter3(getApplicationContext(), list4);

                        recyclerView.setAdapter(articlesRecyclerAdapter3);
                        articlesRecyclerAdapter3.notifyDataSetChanged();

                        articlesRecyclerAdapter3.SetOnItemVideiosClickListener(new ArticlesRecyclerAdapter3.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                String url = list4.get(position).getPost().get(position).getLink();
                                String title = list4.get(position).getPost().get(position).getTitle();
                                Intent i = new Intent(getApplicationContext(), WebTabActivity.class);
                                i.putExtra("url", url);
                                i.putExtra("title", title);
                                startActivity(i);
                            }
                        });

                    }
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }


}
