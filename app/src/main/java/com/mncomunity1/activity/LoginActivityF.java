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
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.mncomunity1.MainActivity;
import com.mncomunity1.R;
import com.mncomunity1.api.APIService;
import com.mncomunity1.app.Config;
import com.mncomunity1.model.MSG;
import com.mncomunity1.service.ApiClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivityF extends AppCompatActivity {

    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;


    @Bind(R.id.toolbar)
    Toolbar toolbar;


    @Bind(R.id.btn_done)
    Button btn_done;

    @Bind(R.id.td_email)
    EditText _email;

    @Bind(R.id.td_name)
    EditText td_name;

    @Bind(R.id.txt_login)
    TextView txt_login;


    Dialog dialog;


    SharedPreferences pref;
    String regId;
    String userId;
    String email;
    String name;

    String checkLogin = "1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_f);
        ButterKnife.bind(this);

        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

        subscribeToPushService();
        checkLogin = getIntent().getStringExtra("checkLogin");

        pref = getSharedPreferences(Config.SHARED_PREF, 0);


        if (pref.getBoolean("isLoginF", false) == true) {
            Intent i = new Intent(getApplicationContext(), LayoutDemoActivity.class);
            startActivity(i);
            finish();
        } else {

        }

        dialog = new Dialog(LoginActivityF.this, R.style.FullHeightDialog);
        dialog.setContentView(R.layout.dailog_loading);


        toolbar.setTitle("เข้าสู่ระบบ");
        setSupportActionBar(toolbar);


        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Log.e("Login userId", userId);
                login(userId);
            }
        });


        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.putExtra("checkLogin","0");
                startActivity(i);
                finish();
            }
        });


    }

    public void login(String userIdLast) {
        loginByServer(userIdLast);

    }

    private void loginByServer(String userIdLast) {

        email = _email.getText().toString();
        name = td_name.getText().toString();

        dialog.show();
        APIService service = ApiClient.getClient().create(APIService.class);

        Call<MSG> userCall = service.userLogInF(email, regId, name);

        userCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response) {


                if (response.body().getSuccess().equals("1")) {
                    startActivity(new Intent(LoginActivityF.this, LayoutDemoActivity.class));
                    dialog.dismiss();

                    String userId = response.body().getId();
                    String name = response.body().getNameth();
                    String email = response.body().getEmail();
                    String companyCode = response.body().getCompany_code();
                    String check = response.body().getCheck();
                    String vendorCheck = response.body().getVendor();
                    String regid = response.body().getRegid();


                    editor.putBoolean("isLogin", false);
                    editor.putBoolean("isLoginF", true);
                    editor.putString("userId", userId);
                    editor.putString("name", name);
                    editor.putString("email", email);
                    editor.putString("company_code", companyCode);
                    editor.putString("check", check);
                    editor.putString("check_status", check);
                    editor.putString("check_vebdor", vendorCheck);
                    editor.commit();

                    Intent i = new Intent(getApplicationContext(), LayoutDemoActivity.class);
                    startActivity(i);


                    finish();
                } else {
                    Toast.makeText(LoginActivityF.this, "Email or Password ผิด", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<MSG> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });
    }

    private void subscribeToPushService() {

        String token = FirebaseInstanceId.getInstance().getToken();
        regId = token;

    }


}
