package com.mncomunity1.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mncomunity1.R;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.GetLog;
import com.mncomunity1.model.SendEmail;
import com.mncomunity1.service.ApiClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VertiCodeActivity extends AppCompatActivity {


    @Bind(R.id.txt_pin)
    LinearLayout txt_pin;

    @Bind(R.id.et_pin)
    EditText et_pin;


    String userId;
    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    Dialog dialog;
    String pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verti_code);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("กรอกรหัส");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dialog = new Dialog(VertiCodeActivity.this, R.style.FullHeightDialog);
        dialog.setContentView(R.layout.dailog_loading);
        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        userId = sp.getString("userId", "000");



        txt_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pin = et_pin.getText().toString();
                dialog.show();
                Intent i =new Intent(getApplicationContext(),NewPassActivity.class);
                startActivity(i);

                postVerti(pin);
            }
        });


    }

    private void postVerti(String code) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<SendEmail> userCall = service.validateCode(code);

        userCall.enqueue(new Callback<SendEmail>() {
            @Override
            public void onResponse(Call<SendEmail> call, Response<SendEmail> response) {

                if (response.body().getComplete().equals("1")) {
                    dialog.dismiss();
                    Intent i = new Intent(getApplicationContext(), NewPassActivity.class);
                    i.putExtra("pin",pin);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<SendEmail> call, Throwable t) {

            }
        });
    }

}
