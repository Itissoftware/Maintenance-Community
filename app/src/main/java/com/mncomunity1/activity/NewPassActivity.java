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
import android.widget.Toast;

import com.mncomunity1.MainActivity;
import com.mncomunity1.R;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.GetLog;
import com.mncomunity1.model.NewPass;
import com.mncomunity1.model.SendEmail;
import com.mncomunity1.service.ApiClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPassActivity extends AppCompatActivity {


    @Bind(R.id.txt_save)
    LinearLayout txt_save;

    @Bind(R.id.et_pin1)
    EditText et_pin1;

    @Bind(R.id.et_pin2)
    EditText et_pin2;



    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Dialog dialog;

    String pass1;
    String pass2;
    String pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_new_password);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("กรอกรหัสผ่านใหม่");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dialog = new Dialog(NewPassActivity.this, R.style.FullHeightDialog);
        dialog.setContentView(R.layout.dailog_loading);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        pin = getIntent().getStringExtra("pin");


        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

        txt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass1 = et_pin1.getText().toString();
                pass2 = et_pin2.getText().toString();
                Log.e("pass1",pass1);
                Log.e("pass2",pass2);


                if (pass1.equals(pass2)) {
                    dialog.show();
                    postNewPass(pin, pass1);
                } else {
                    Toast.makeText(getApplicationContext(), "รหัสผ่านไม่ตรงกัน", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    private void postNewPass(String pin1, String pin2) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<NewPass> userCall = service.newPassword(pin1, pin2);

        userCall.enqueue(new Callback<NewPass>() {
            @Override
            public void onResponse(Call<NewPass> call, Response<NewPass> response) {

                if(response.body().getComplete().equals("1")){

                    dialog.dismiss();
                    String name = response.body().getNameth();
                    String email = response.body().getEmail();
                    String companyCode = response.body().getCompany_code();
                    String userId = response.body().getId();
                    String check = response.body().getCheck();

                    editor.putBoolean("stateForget", false);
                    editor.putBoolean("isLogin", true);
                    editor.putString("userId", userId);
                    editor.putString("name", name);
                    editor.putString("email", email);
                    editor.putString("company_code", companyCode);
                    editor.putString("check", check);
                    editor.commit();

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();


                }


            }

            @Override
            public void onFailure(Call<NewPass> call, Throwable t) {

            }
        });
    }

}
