package com.mncomunity1.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mncomunity1.R;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.PostError;
import com.mncomunity1.service.ApiClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MorePoActivity extends AppCompatActivity {


    String cat;


    @Bind(R.id.td_name_product)
    EditText nameProduct;

    @Bind(R.id.td_name)
    EditText tdName;

    @Bind(R.id.td_email)
    EditText tdEmail;

    @Bind(R.id.td_number)
    EditText btnNumber;

    @Bind(R.id.td_phone)
    EditText tdPhone;

    @Bind(R.id.btn_done)
    Button btnDone;

    @Bind(R.id.btn_cancel)
    Button btnCancel;

    String nameCompanyTh;
    String nameCompanyEn;

    Dialog dialogs;

    String nProduct;
    String email;
    String name;
    String phone;
    String userId;
    String number;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_po);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("รายละเอียดบริษัท");
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        Log.e("isLogin", sharedpreferences.getString("userId", "NO") + "'");
        userId = sharedpreferences.getString("userId", "NO");

        nameCompanyTh = getIntent().getStringExtra("nameCompanyTh");
        nameCompanyEn = getIntent().getStringExtra("nameCompanyEn");




        dialogs = new Dialog(MorePoActivity.this, R.style.FullHeightDialog);
        dialogs.setContentView(R.layout.dailog_loading);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogs.show();

                nProduct = nameProduct.getText().toString();
                email = tdEmail.getText().toString();
                name = tdName.getText().toString();
                phone = tdPhone.getText().toString();
                number = btnNumber.getText().toString();

                postServer(nProduct, nameCompanyTh, email,name,phone,userId,number);

            }
        });

    }

    private void postServer(String title_product, String company, String email, String name, String phone, String user_id, String number) {


        APIService service = ApiClient.getClient().create(APIService.class);

        Call<PostError> userCall = service.postPo(title_product, company, email, name, phone, user_id, number);

        userCall.enqueue(new Callback<PostError>() {
            @Override
            public void onResponse(Call<PostError> call, Response<PostError> response) {

                if (response.body().getSuccess().equals("1")) {

                    dialogs.dismiss();

                } else {
                    Toast.makeText(getApplication(), "เกิดข้อผิดพลาด ลองอีกครั้ง", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<PostError> call, Throwable t) {

            }
        });
    }


}
