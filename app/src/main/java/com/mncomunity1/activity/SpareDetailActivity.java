package com.mncomunity1.activity;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mncomunity1.R;
import com.mncomunity1.adapter.CartRecyclerAdapter;
import com.mncomunity1.adapter.SpareDetailRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.ModelSpare;
import com.mncomunity1.model.ModelSpareDetails;
import com.mncomunity1.model.MovieModel;
import com.mncomunity1.model.getOrder;
import com.mncomunity1.service.ApiClient;
import com.mncomunity1.until.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpareDetailActivity extends AppCompatActivity {
    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    String cat;
    String sub;

    @Bind(R.id.cardList_main)
    RecyclerView recyclerView;

    @Bind(R.id.td_search)
    EditText tdSearch;

    List<MovieModel> movies;
    SpareDetailRecyclerAdapter adapter;
    ImageView img_cart;
    TextView badge_notification_6;

    EditText td_search;
    Button btn_done;
    String search;

    String userId;
    String companyCodes;

    APIService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spare_sub);
        ButterKnife.bind(this);
        service = ApiClient.getClient().create(APIService.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        movies = new ArrayList<>();
        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();


        td_search = (EditText) findViewById(R.id.td_search);
        btn_done = (Button) findViewById(R.id.btn_done);


        toolbar.setTitle("บริษัท");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userId = sp.getString("userId", "00000");
        companyCodes = sp.getString("company_code", "0");

        img_cart = (ImageView) findViewById(R.id.img_cart);
        badge_notification_6 = (TextView) findViewById(R.id.badge_notification_6);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        cat = getIntent().getStringExtra("cat");
        sub = getIntent().getStringExtra("sub");


        Log.e("cat", cat);


        adapter = new SpareDetailRecyclerAdapter(getApplicationContext(), movies);
        adapter.setLoadMoreListener(new SpareDetailRecyclerAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        int index = movies.size() - 1;
                        loadMore(cat, index);
                        Log.e("index", index + "");
                    }
                });
                //Calling loadMore function in Runnable to fix the
                // java.lang.IllegalStateException: Cannot call this method while RecyclerView is computing a layout or scrolling error
            }
        });


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));


        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                search = tdSearch.getText().toString();

                Intent i = new Intent(getApplicationContext(), SearchResultActivity.class);
                i.putExtra("keyword", search);
                i.putExtra("cat", cat);
                startActivity(i);
            }
        });

        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(getApplicationContext(), CartTotalActivity.class);
                i.putExtra("userId", userId);
                i.putExtra("company_code", companyCodes);
                i.putExtra("cat", cat);
                startActivity(i);

            }
        });

        getSpareByServer(cat, 1);
    }

    private void getSpareByServer(final String cat, int index) {

        Call<List<MovieModel>> userCall = service.getSpareCat(cat, index);
        userCall.enqueue(new Callback<List<MovieModel>>() {
            @Override
            public void onResponse(Call<List<MovieModel>> call, Response<List<MovieModel>> response) {

                Log.e("response", response.body().size() + "");
                if (response.isSuccessful()) {
                    movies.addAll(response.body());
                    adapter.notifyDataChanged();

                    Log.e("movies", movies.size() + "");

                    recyclerView.setAdapter(adapter);

                    adapter.SetOnItemVideiosClickListener(new SpareDetailRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            String nameCompanyTh = movies.get(position).nameth;
                            String nameCompanyEn = movies.get(position).nameen;
                            String cover = movies.get(position).cover;
                            String address = movies.get(position).address;
                            String companyCode = movies.get(position).companyCode;


                            Intent i = new Intent(getApplicationContext(), CompanyDetailActivity.class);
                            i.putExtra("nameCompanyTh", nameCompanyTh);
                            i.putExtra("nameCompanyEn", nameCompanyEn);
                            i.putExtra("companyCode", companyCode);
                            i.putExtra("cover", cover);
                            i.putExtra("address", address);
                            startActivity(i);

                        }
                    });
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<MovieModel>> call, Throwable t) {

                Log.e("getLocalizedMessage", t.getLocalizedMessage());
                Log.e("getMessage", t.getMessage());

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        getOrder(userId);

    }

    private void getOrder(final String user_id) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<getOrder> userCall = service.getOrder(user_id);

        userCall.enqueue(new Callback<getOrder>() {
            @Override
            public void onResponse(Call<getOrder> call, Response<getOrder> response) {

                if (response.body().getTotal() != null) {

                    badge_notification_6.setText(response.body().getTotal().size() + "");

                } else {
                    badge_notification_6.setText("0");
                }

            }

            @Override
            public void onFailure(Call<getOrder> call, Throwable t) {

            }
        });
    }

    private void loadMore(String cat, int index) {

        //add loading progress view
        movies.add(new MovieModel("load"));
        adapter.notifyItemInserted(movies.size() - 1);

        Call<List<MovieModel>> call = service.getSpareCat(cat, index);
        call.enqueue(new Callback<List<MovieModel>>() {
            @Override
            public void onResponse(Call<List<MovieModel>> call, Response<List<MovieModel>> response) {
                if (response.isSuccessful()) {

                    //remove loading view
                    movies.remove(movies.size() - 1);

                    List<MovieModel> result = response.body();
                    Log.e("result",result.size()+"");
                    if (result.size() > 0) {
                        //add loaded data
                        movies.addAll(result);
                    } else {//result size 0 means there is no more data available at server
                        adapter.setMoreDataAvailable(false);
                        
                       // Toast.makeText(getApplicationContext(), "No More Data Available", Toast.LENGTH_LONG).show();
                    }
                    adapter.notifyDataChanged();
                    //should call the custom method adapter.notifyDataChanged here to get the correct loading status
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<MovieModel>> call, Throwable t) {
                Log.e("getLocalizedMessage", t.getLocalizedMessage());
            }
        });
    }

}
