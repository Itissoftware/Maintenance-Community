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

import com.mncomunity1.R;
import com.mncomunity1.adapter.GetLogErrorRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.GetLog;
import com.mncomunity1.service.ApiClient;
import com.mncomunity1.until.SimpleDividerItemDecoration;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogActivity extends AppCompatActivity {


    @Bind(R.id.cardList_main)
    RecyclerView recyclerView;

    ArrayList<GetLog> listPost = new ArrayList<>();
    GetLogErrorRecyclerAdapter getLogErrorRecyclerAdapter;
    SharedPreferences sharedpreferences;

    String userId;

    public static final String mypreference = "mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_log_error);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ประวัติ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ButterKnife.bind(this);

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId = sharedpreferences.getString("userId", "NO");

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        getLog(userId);

    }

    private void getLog(String userId) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<GetLog> userCall = service.getLogError(userId);

        userCall.enqueue(new Callback<GetLog>() {
            @Override
            public void onResponse(Call<GetLog> call, Response<GetLog> response) {
                if (response.body() != null) {
                    for (int i = 0; i < response.body().getMovie().size(); i++) {

                        listPost.add(response.body());
                        Log.e("Deatils: ", response.body().getMovie().get(i).getName_company() + "");

                        getLogErrorRecyclerAdapter = new GetLogErrorRecyclerAdapter(getApplicationContext(), listPost);

                        // recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), R.drawable.divider));
                        recyclerView.setAdapter(getLogErrorRecyclerAdapter);

                        getLogErrorRecyclerAdapter.SetOnItemVideiosClickListener(new GetLogErrorRecyclerAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }
                        });

                    }
                }
            }

            @Override
            public void onFailure(Call<GetLog> call, Throwable t) {
                Log.e("msg: ", t.getLocalizedMessage());
                Log.e("msg: ", t.getMessage());
            }
        });
    }

}
