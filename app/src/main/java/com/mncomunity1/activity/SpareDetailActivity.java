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

    ArrayList<ModelSpareDetails> listPost = new ArrayList<>();
    SpareDetailRecyclerAdapter spareRecyclerAdapter;
    CartRecyclerAdapter cartRecyclerAdapter;
    List<ModelSpare> mCartList;
    ImageView img_cart;
    Dialog dialogTotal;
    TextView badge_notification_6;

    EditText td_search;
    Button btn_done;
    String search;

    String userId;
    String companyCodes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spare_sub);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

        td_search = (EditText) findViewById(R.id.td_search);
        btn_done = (Button) findViewById(R.id.btn_done);


        toolbar.setTitle("บริษัท");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userId = sp.getString("userId", "00000");
        companyCodes = sp.getString("company_code","0");

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


        Log.e("cat",cat);
        getSpareByServer(cat);
        ButterKnife.bind(this);


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                search = tdSearch.getText().toString();

                Intent i =new Intent(getApplicationContext(),SearchResultActivity.class);
                i.putExtra("keyword",search);
                i.putExtra("cat",cat);
                startActivity(i);
            }
        });

        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(getApplicationContext(), CartTotalActivity.class);
                i.putExtra("userId", userId);
                i.putExtra("company_code", companyCodes);
                i.putExtra("cat",cat);
                startActivity(i);

            }
        });


    }

    private void getSpareByServer(final String cat) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<ModelSpareDetails> userCall = service.getSpareCat(cat);

        userCall.enqueue(new Callback<ModelSpareDetails>() {
            @Override
            public void onResponse(Call<ModelSpareDetails> call, Response<ModelSpareDetails> response) {

                if (response.body() != null) {

                    if (response.body().isResult() == true) {
                        for (int i = 0; i < response.body().getTotal().size(); i++) {

                            listPost.add(response.body());
                            spareRecyclerAdapter = new SpareDetailRecyclerAdapter(getApplicationContext(), listPost);

                            // recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), R.drawable.divider));
                            recyclerView.setAdapter(spareRecyclerAdapter);

                            spareRecyclerAdapter.SetOnItemVideiosClickListener(new SpareDetailRecyclerAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    String nameCompanyTh = listPost.get(position).getTotal().get(position).getNameth();
                                    String nameCompanyEn = listPost.get(position).getTotal().get(position).getNameen();
                                    String cover = listPost.get(position).getTotal().get(position).getCover();
                                    String address = listPost.get(position).getTotal().get(position).getAddress();
                                    String companyCode = listPost.get(position).getTotal().get(position).getCompanyCode();


                                    Intent i = new Intent(getApplicationContext(), CompanyDetailActivity.class);
                                    i.putExtra("nameCompanyTh", nameCompanyTh);
                                    i.putExtra("nameCompanyEn", nameCompanyEn);
                                    i.putExtra("companyCode", companyCode);
                                    i.putExtra("cover", cover);
                                    i.putExtra("address", address);
                                    startActivity(i);

                                }
                            });

                        }
                    } else {

                        Toast.makeText(getApplication(), "ยังไม่มีข้อมูล", Toast.LENGTH_SHORT).show();

                    }

                }

            }

            @Override
            public void onFailure(Call<ModelSpareDetails> call, Throwable t) {

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
