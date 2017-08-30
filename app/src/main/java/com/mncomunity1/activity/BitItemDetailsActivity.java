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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mncomunity1.R;
import com.mncomunity1.adapter.ListBitItemToOfferDetailsRecyclerAdapter;
import com.mncomunity1.adapter.ListBitItemToOfferRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.ModelPost;
import com.mncomunity1.model.PriceVndor;
import com.mncomunity1.service.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BitItemDetailsActivity extends AppCompatActivity {


    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    Toolbar toolbar;

    String companyCode;
    String bit_con;

    ImageView img_cover;
    TextView textONEs;
    TextView amount;
    EditText dt_price;
    TextView txt_send;
    TextView txt_question_vendor;

    String photo;
    String title;
    String details;
    String amountN;

    Dialog dialog;
    Dialog dialogLoanding;
    Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bit_item_activity3);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("รายละเอียด");
        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        companyCode = sp.getString("company_code", "00000");
        bit_con = getIntent().getStringExtra("bit_con");

        img_cover = (ImageView) findViewById(R.id.img_cover);
        textONEs = (TextView) findViewById(R.id.textONEs);
        amount = (TextView) findViewById(R.id.amount);
        dt_price = (EditText) findViewById(R.id.dt_price);
        txt_send = (TextView) findViewById(R.id.txt_send);
        txt_question_vendor = (TextView) findViewById(R.id.txt_question_vendor);

        photo = getIntent().getStringExtra("photo");
        title = getIntent().getStringExtra("title");
        details = getIntent().getStringExtra("details");
        amountN = getIntent().getStringExtra("amount");


        dialog = new Dialog(BitItemDetailsActivity.this, R.style.FullHeightDialog);
        dialog.setContentView(R.layout.dailog_complete_send_customer);
        btn_ok = (Button) dialog.findViewById(R.id.btn_ok);

        dialogLoanding = new Dialog(BitItemDetailsActivity.this, R.style.FullHeightDialog);
        dialogLoanding.setContentView(R.layout.dailog_loading_send);



        textONEs.setText(details);

        Glide.with(getApplicationContext())
                .load(photo)
                .crossFade()
                .into(img_cover);

        amount.setText(amountN + " ชิ้น");


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //  getListOffer("6");

//        Log.e("bit_con",bit_con);

        txt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLoanding.show();
                getListPost(bit_con, dt_price.getText().toString());
            }
        });

        txt_question_vendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), ListQuestionChatActivity.class);
                i.putExtra("bit_vender",bit_con);
                startActivity(i);

            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BitItemDetailsActivity.class);
                i.putExtra("bit_con",bit_con);
                i.putExtra("amount",amountN);
                i.putExtra("photo",photo);
                i.putExtra("title",title);
                i.putExtra("details",details);
                startActivity(i);
                finish();
                dialog.dismiss();
            }
        });
    }

    public void getListPost(String req_id, String price) {
        APIService service = ApiClient.getClient().create(APIService.class);

        Call<ModelPost> userCall = service.postBitOfferToVendorUpdate(req_id, price);

        userCall.enqueue(new Callback<ModelPost>() {
            @Override
            public void onResponse(Call<ModelPost> call, Response<ModelPost> response) {


                Log.e("getSucces",response.body().getSucces()+"");

                if (response.body().getSucces().equals("1")) {
                    Toast.makeText(getApplicationContext(), "ส่งข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show();
                    dt_price.setText("");
                    dialog.show();
                    dialogLoanding.dismiss();

                } else {

                }

            }

            @Override
            public void onFailure(Call<ModelPost> call, Throwable t) {

            }
        });
    }

}
