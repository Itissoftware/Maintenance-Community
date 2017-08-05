package com.mncomunity1.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mncomunity1.R;
import com.mncomunity1.adapter.CategorySpareRecyclerAdapter;
import com.mncomunity1.adapter.SearchSpareRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.Category;
import com.mncomunity1.model.SearchSpare;
import com.mncomunity1.model.getOrder;
import com.mncomunity1.service.ApiClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpareActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText tdSearch;
    Button btnDone;

    ArrayList<Category> listPost = new ArrayList<>();
    ArrayList<SearchSpare> listSearch = new ArrayList<>();


    CategorySpareRecyclerAdapter spareRecyclerAdapter;
    SearchSpareRecyclerAdapter searchSpareRecyclerAdapter;

    private GridLayoutManager lLayout;

    String search;

    boolean check = false;

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
        setContentView(R.layout.activity_spare);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("กลุ่มสินค้า");
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
        userId = sharedpreferences.getString("userId", "0");
        companyCodes = sharedpreferences.getString("company_code","0");

        img_cart = (ImageView) findViewById(R.id.img_cart);
        badge_notification_6 = (TextView) findViewById(R.id.badge_notification_6);

        recyclerView = (RecyclerView) findViewById(R.id.cardList_main);
        btnDone = (Button) findViewById(R.id.btn_done);
        tdSearch = (EditText) findViewById(R.id.td_search);


        lLayout = new GridLayoutManager(getApplicationContext(), 3);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(lLayout);
        //recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        getSpareByServer();

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                search = tdSearch.getText().toString();

                Intent i =new Intent(getApplicationContext(),SearchResultActivity.class);
                i.putExtra("keyword",search);
                startActivity(i);

            }
        });

        tdSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int st, int b, int c) {
                if (tdSearch.getText().toString().trim().equals("")) {
                    getSpareByServer();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {

            }

            @Override
            public void afterTextChanged(Editable s) {


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

    private void getSpareByServer() {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<Category> userCall = service.getCategory();

        userCall.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if (response.body() != null) {
                    for (int i = 0; i < response.body().getTotal().size(); i++) {
                        listPost.add(response.body());

                        if(response.body().getTotal().size() >= i){
                            spareRecyclerAdapter = new CategorySpareRecyclerAdapter(getApplicationContext(), listPost);

                            // recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), R.drawable.divider));
                            recyclerView.setAdapter(spareRecyclerAdapter);
                            spareRecyclerAdapter.SetOnItemVideiosClickListener(new CategorySpareRecyclerAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    String cat = listPost.get(position).getTotal().get(position).getCat();
                                Intent i = new Intent(getApplicationContext(), SubGroupActivity.class);
                                i.putExtra("cat", cat);
                                startActivity(i);

                                }
                            });
                        }

                    }

                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

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
