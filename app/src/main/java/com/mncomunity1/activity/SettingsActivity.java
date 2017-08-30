package com.mncomunity1.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mncomunity1.R;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.PostOrder;
import com.mncomunity1.model.Profile;
import com.mncomunity1.service.ApiClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.et_name)
    EditText et_name;

    @Bind(R.id.et_email)
    EditText et_email;

    @Bind(R.id.et_password)
    EditText et_password;

    @Bind(R.id.et_phone)
    EditText et_phone;

    @Bind(R.id.et_address)
    EditText et_address;

    @Bind(R.id.btn_done)
    Button btn_done;


    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    String userId;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

        dialog = ProgressDialog.show(SettingsActivity.this, "", "รอสักครู่...", true);


        userId = sp.getString("userId", "1");
        Log.e("ddddd", userId);
        GetProfile(userId);


        toolbar.setTitle("ข้อมูลส่วนตัว");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dialog.dismiss();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

                postProfileEdit(userId, et_name.getText().toString(), et_email.getText().toString(), et_password.getText().toString(), et_phone.getText().toString(), et_address.getText().toString());


            }
        });


    }


    private void GetProfile(String id) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<Profile> userCall = service.getProfile(id);

        userCall.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {


                if (response.body().isResult() != false) {
                    for (int i = 0; i < response.body().getTotal().size(); i++) {

                        et_name.setText(response.body().getTotal().get(i).getNameth());
                        et_email.setText(response.body().getTotal().get(i).getEmail());
                        et_password.setText(response.body().getTotal().get(i).getPass());
                        et_phone.setText(response.body().getTotal().get(i).getPhone());
                        et_address.setText(response.body().getTotal().get(i).getAddress());

                    }

                }

            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {

            }
        });
    }


    private void postProfileEdit(String userId, String nameth, String email, String pass, String phone, String address) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<PostOrder> userCall = service.postUpdateEdit(userId, nameth, email, pass, phone, address);

        userCall.enqueue(new Callback<PostOrder>() {
            @Override
            public void onResponse(Call<PostOrder> call, Response<PostOrder> response) {
                if (response.body().getComplete().get(0).getStatus().equals("1")) {

                    new Handler().postDelayed(
                            new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                    Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }, 2000);

                }
            }

            @Override
            public void onFailure(Call<PostOrder> call, Throwable t) {

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();


    }
}
