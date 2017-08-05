package com.mncomunity1.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mncomunity1.R;
import com.mncomunity1.adapter.CartRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.Delete;
import com.mncomunity1.model.getOrder;
import com.mncomunity1.service.ApiClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpareActivity2 extends AppCompatActivity {

    ImageView img_cart;
    Dialog dialogTotal;
    RecyclerView cardList_main;
    List<getOrder> mCartList = new ArrayList<>();
    TextView badge_notification_6;
    CartRecyclerAdapter cartRecyclerAdapter;
    String user_id;
    String nameThComplate;
    String totalComplate;
    String userId;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spare_main_fragment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("หมวดหมู่อะไหล่");
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId = sharedpreferences.getString("userId","1");
        Log.e("userId:",userId);

        img_cart = (ImageView) findViewById(R.id.img_cart);
        badge_notification_6 = (TextView) findViewById(R.id.badge_notification_6);



//        MenuSpareFragment oneFragment = new MenuSpareFragment();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment_container, oneFragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
        getOrder(userId);

        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialogTotal = new Dialog(SpareActivity2.this, R.style.FullHeightDialog);
                dialogTotal.setContentView(R.layout.dailog_total_cart);
                dialogTotal.show();

                TextView txt_total = (TextView) dialogTotal.findViewById(R.id.txt_total);
                Button btn_done = (Button) dialogTotal.findViewById(R.id.btn_done);
                Button btn_close = (Button) dialogTotal.findViewById(R.id.btn_close);

                cardList_main = (RecyclerView) dialogTotal.findViewById(R.id.cardList_main);
                cardList_main.setHasFixedSize(true);
                LinearLayoutManager llm = new LinearLayoutManager(getApplication());
                llm.setOrientation(LinearLayoutManager.VERTICAL);

                cardList_main.setLayoutManager(llm);

                txt_total.setText(mCartList.size() + "");
                cartRecyclerAdapter = new CartRecyclerAdapter(getApplicationContext(), mCartList);
                cardList_main.setAdapter(cartRecyclerAdapter);

                btn_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Toast.makeText(getApplication(), "mm", Toast.LENGTH_SHORT).show();

                        for (int i = 0; i < mCartList.size(); i++) {
                            user_id = mCartList.get(i).getTotal().get(i).getId();
                            nameThComplate = mCartList.get(i).getTotal().get(i).getNameth();
                            totalComplate = mCartList.get(i).getTotal().get(i).getTotal();

                            Log.e("nameThComplate",nameThComplate);


                            mCartList.clear();
                            dialogTotal.dismiss();
                        }

                    }
                });

                btn_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteOrder("1");


                    }
                });


            }
        });

    }

    private void deleteOrder(final String user_id) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<Delete> userCall = service.deleteOrder(user_id);

        userCall.enqueue(new Callback<Delete>() {
            @Override
            public void onResponse(Call<Delete> call, Response<Delete> response) {
                if (response.body().getTotal().get(0).getStatus().equals("1")) {
                    mCartList.clear();
                    dialogTotal.dismiss();

                }
            }

            @Override
            public void onFailure(Call<Delete> call, Throwable t) {

            }
        });
    }

    private void getOrder(final String user_id) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<getOrder> userCall = service.getOrder(user_id);

        userCall.enqueue(new Callback<getOrder>() {
            @Override
            public void onResponse(Call<getOrder> call, Response<getOrder> response) {

                if (response.body().getTotal() != null) {

                    Log.e("cCHECK", response.body().getTotal().size() + "");
                    badge_notification_6.setText(response.body().getTotal().size() + "");

                    // Make sure to clear the selections
                    for (int i = 0; i < response.body().getTotal().size(); i++) {

                        mCartList.add(response.body());

                    }

                }

            }

            @Override
            public void onFailure(Call<getOrder> call, Throwable t) {

            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();

    }
}
