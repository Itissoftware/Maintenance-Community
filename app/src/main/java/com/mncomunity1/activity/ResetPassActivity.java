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
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mncomunity1.R;
import com.mncomunity1.adapter.GetLogErrorRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.GetLog;
import com.mncomunity1.model.SendEmail;
import com.mncomunity1.service.ApiClient;
import com.mncomunity1.until.SimpleDividerItemDecoration;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassActivity extends AppCompatActivity {


    @Bind(R.id.txt_next)
    LinearLayout txt_next;

    @Bind(R.id.et_email)
    EditText et_email;


    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;


    String email;
    String userId;

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("กรอกอีเมล์");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        dialog = new Dialog(ResetPassActivity.this, R.style.FullHeightDialog);
        dialog.setContentView(R.layout.dailog_loading);

        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        userId = sp.getString("userId", "000");

        if (sp.getBoolean("stateForget", false) == true) {
            Intent i = new Intent(getApplicationContext(), VertiCodeActivity.class);
            startActivity(i);
        }

        txt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = et_email.getText().toString();

                dialog.show();
                Intent i = new Intent(getApplicationContext(), VertiCodeActivity.class);
                startActivity(i);

                postEmail(email);
            }
        });


//        SharedPreferences.Editor editor = sharedpreferences.edit();
//        editor.putBoolean("resetPass", true);
//        editor.commit();


    }

    private void postEmail(String email) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<SendEmail> userCall = service.forgetEmail(email);

        userCall.enqueue(new Callback<SendEmail>() {
            @Override
            public void onResponse(Call<SendEmail> call, Response<SendEmail> response) {

                if (response.body().getComplete().equals("1")) {
                    dialog.dismiss();
                    editor.putBoolean("stateForget", true);
                    editor.commit();
                    Intent i = new Intent(getApplicationContext(), VertiCodeActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onFailure(Call<SendEmail> call, Throwable t) {

            }
        });
    }

}
