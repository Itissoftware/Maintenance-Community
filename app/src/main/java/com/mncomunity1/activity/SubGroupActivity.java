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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mncomunity1.R;
import com.mncomunity1.adapter.SubGroupRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.SubGroup;
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

public class SubGroupActivity extends AppCompatActivity {


    String cat;

    @Bind(R.id.cardList_main)
    RecyclerView recyclerView;

    @Bind(R.id.td_search)
    EditText tdSearch;

    ArrayList<SubGroup> listPost = new ArrayList<>();
    SubGroupRecyclerAdapter spareRecyclerAdapter;

    EditText td_search;
    Button btn_done;
    String search;

    ImageView img_cart;
    TextView badge_notification_6;
    List<getOrder> mCartList = new ArrayList<>();

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String userId;
    String companyCodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spare_detail);
        td_search = (EditText) findViewById(R.id.td_search);
        btn_done = (Button) findViewById(R.id.btn_done);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ประเภทสินค้า");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        img_cart = (ImageView) findViewById(R.id.img_cart);
        badge_notification_6 = (TextView) findViewById(R.id.badge_notification_6);

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId = sharedpreferences.getString("userId", "0");
        companyCodes = sharedpreferences.getString("company_code","0");
        Log.e("userId:", userId);

        cat = getIntent().getStringExtra("cat");
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
                startActivity(i);

            }
        });



    }

    private void getSpareByServer(final String cat) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<SubGroup> userCall = service.getGroupSub(cat);

        userCall.enqueue(new Callback<SubGroup>() {
            @Override
            public void onResponse(Call<SubGroup> call, Response<SubGroup> response) {

                if (response.body() != null) {

                    if (response.body().isResult() == true) {
                        for (int i = 0; i < response.body().getTotal().size(); i++) {

                            listPost.add(response.body());
                            spareRecyclerAdapter = new SubGroupRecyclerAdapter(getApplicationContext(), listPost);

                            // recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), R.drawable.divider));
                            recyclerView.setAdapter(spareRecyclerAdapter);

                            spareRecyclerAdapter.SetOnItemVideiosClickListener(new SubGroupRecyclerAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    String cat = listPost.get(position).getTotal().get(position).getCategory();
                                    String sub = listPost.get(position).getTotal().get(position).getSubCategory();

                                    Intent i = new Intent(getApplicationContext(), SpareDetailActivity.class);
                                    i.putExtra("cat", cat);
                                    i.putExtra("sub", sub);
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
            public void onFailure(Call<SubGroup> call, Throwable t) {

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

                    // Make sure to clear the selections
                    for (int i = 0; i < response.body().getTotal().size(); i++) {

                        mCartList.add(response.body());

                    }

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
