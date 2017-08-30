package com.mncomunity1.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mncomunity1.R;
import com.mncomunity1.adapter.ListBitOfferVendorRecyclerAdapter;
import com.mncomunity1.adapter.ListQuestionRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.BitOfferDetails;
import com.mncomunity1.model.ModelCheckBoxQuestion;
import com.mncomunity1.model.NewsRc;
import com.mncomunity1.model.PostBitOffer;
import com.mncomunity1.service.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BitItemListVendorActivity extends AppCompatActivity {

    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    Toolbar toolbar;
    RecyclerView cardList_main;
    ProgressBar pro;
    TextView txt_gone;

    String userId;
    Button btnGetSelected;

    ListBitOfferVendorRecyclerAdapter listBitOfferVendorRecyclerAdapter;
    ArrayList<BitOfferDetails> list = new ArrayList<>();

    String title;
    String amount;
    String category;
    String id;

    Dialog dialogLoanding;
    Dialog dialog;
    Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bit_item_activity);
        txt_gone = (TextView) findViewById(R.id.txt_gone);
        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        id = getIntent().getStringExtra("id");
        userId = sp.getString("userId", "000");

        dialog = new Dialog(BitItemListVendorActivity.this, R.style.FullHeightDialog);
        dialog.setContentView(R.layout.dailog_complete_send_tovender);
        btn_ok = (Button) dialog.findViewById(R.id.btn_ok);

        dialogLoanding = new Dialog(BitItemListVendorActivity.this, R.style.FullHeightDialog);
        dialogLoanding.setContentView(R.layout.dailog_loading_send);


        Log.e("id", id);


        title = getIntent().getStringExtra("title");
        amount = getIntent().getStringExtra("amount");
        category = getIntent().getStringExtra("category");


        btnGetSelected = (Button) findViewById(R.id.btnGetSelected);
        cardList_main = (RecyclerView) findViewById(R.id.cardList_main);
        pro = (ProgressBar) findViewById(R.id.pro);

        cardList_main.setHasFixedSize(true);
        final LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        cardList_main.setLayoutManager(llm);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("ผู้ขายที่มีสินค้าของคุณ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getListVendor(id);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SpareActivity.class);
                startActivity(i);
                finish();
                dialog.dismiss();
            }
        });


    }

    public void getListVendor(String id) {
        APIService service = ApiClient.getClient().create(APIService.class);

        Call<BitOfferDetails> userCall = service.getBitOfferListVendor(id);

        userCall.enqueue(new Callback<BitOfferDetails>() {
            @Override
            public void onResponse(Call<BitOfferDetails> call, Response<BitOfferDetails> response) {

                if (response.body() != null) {
                    if (response.body().getTotal() != null) {
                        pro.setVisibility(View.GONE);
                        txt_gone.setVisibility(View.GONE);
                        for (int i = 0; i < response.body().getTotal().size(); i++) {
                            list.add(response.body());

                        }
                        listBitOfferVendorRecyclerAdapter = new ListBitOfferVendorRecyclerAdapter(getApplicationContext(), list);
                        cardList_main.setAdapter(listBitOfferVendorRecyclerAdapter);

                        btnGetSelected.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {



                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                                //builder.setTitle("Dlete ");
                                builder.setMessage("คุณต้องส่งข้อมูลหรือไม่ ?")
                                        .setCancelable(false)
                                        .setPositiveButton("ต้องการ",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int pos) {
                                                        dialogLoanding.dismiss();
                                                        for (int i = 0; i < list.size(); i++) {

                                                            String companyCode = list.get(i).getTotal().get(i).getCompany_code();
                                                            String id = list.get(i).getTotal().get(i).getId();


                                                            getPostBitOffer(userId, "", companyCode, id);
                                                        }
                                                    }
                                                })
                                        .setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                dialog.dismiss();
                                            }
                                        });

                                builder.show();




                            }
                        });
                    } else {
                        pro.setVisibility(View.GONE);
                        txt_gone.setVisibility(View.VISIBLE);
                    }

                }


            }

            @Override
            public void onFailure(Call<BitOfferDetails> call, Throwable t) {

            }
        });
    }


    public void getPostBitOffer(String user_id, String category, String company_code, String id_bitoffer) {
        APIService service = ApiClient.getClient().create(APIService.class);

        Call<PostBitOffer> userCall = service.postBitOfferToVendor(user_id, "", company_code, id_bitoffer);

        userCall.enqueue(new Callback<PostBitOffer>() {
            @Override
            public void onResponse(Call<PostBitOffer> call, Response<PostBitOffer> response) {

                if (response.body() != null) {
                    if (response.body().getResult().equals("success")) {
                        dialog.show();
                        dialogLoanding.dismiss();
                    }
                }

            }

            @Override
            public void onFailure(Call<PostBitOffer> call, Throwable t) {

            }
        });
    }

}
