package com.mncomunity1.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class ContactActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.td_company)
    EditText tdCompany;

    @Bind(R.id.td_phone)
    EditText tdPhone;

    @Bind(R.id.td_user)
    EditText tdUser;

    @Bind(R.id.td_email)
    EditText tdEmail;

    @Bind(R.id.td_details)
    EditText tdDetails;

    @Bind(R.id.btn_done)
    Button done;

    @Bind(R.id.btn_log)
    Button btnLog;

    @Bind(R.id.btn_cancel)
    Button btnCancel;


    @Bind(R.id.ls_group)
    LinearLayout ls_group;


    private RadioGroup radioGroup;
    private RadioButton radioButton;

    Dialog dialogs;

    String name;
    String company;
    String email;
    String userId;
    LinearLayout td_choose;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";

    String group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ButterKnife.bind(this);

        td_choose = (LinearLayout) findViewById(R.id.td_choose);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroups);

        toolbar.setTitle("แจ้งปัญหาโปรแกรม");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        Log.e("isLogin", sharedpreferences.getString("userId", "NO") + "'");
        userId = sharedpreferences.getString("userId", "NO");


        name = getIntent().getStringExtra("name");
        company = getIntent().getStringExtra("company");
        email = getIntent().getStringExtra("email");


        tdCompany.setText(company);
        tdUser.setText(name);
        tdEmail.setText(email);

        tdUser.setEnabled(false);
        tdCompany.setEnabled(false);
        tdEmail.setEnabled(false);

        td_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder1 = new AlertDialog.Builder(ContactActivity.this);
                builder1.setMessage("เลือกรูปภาพ");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "แกลอรี่",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "ถ่ายภาพ",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                Log.d("chk", "id" + checkedId);

                if (checkedId == R.id.radio_program) {
                    //some code
                    ls_group.setVisibility(View.GONE);
                } else if (checkedId == R.id.radio_more) {
                    //some code
                    ls_group.setVisibility(View.GONE);
                }else if(checkedId == R.id.radio_other){

                    ls_group.setVisibility(View.VISIBLE);
                }
            }
        });



        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postServer();
            }
        });

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),LogActivity.class);
                startActivity(i);

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void postServer() {

        String company = tdCompany.getText().toString();
        String user = tdUser.getText().toString();
        String email = tdEmail.getText().toString();
        String details = tdDetails.getText().toString();
        String phone = tdPhone.getText().toString();


        APIService service = ApiClient.getClient().create(APIService.class);

        Call<PostError> userCall = service.getError(user, phone, email, details, company, userId,"");

        userCall.enqueue(new Callback<PostError>() {
            @Override
            public void onResponse(Call<PostError> call, Response<PostError> response) {

                if (response.body().getSuccess().equals("1")) {
                    dialogs = new Dialog(ContactActivity.this, R.style.FullHeightDialog);
                    dialogs.setContentView(R.layout.dailog_company);
                    dialogs.show();
                    Button btn_done = (Button) dialogs.findViewById(R.id.btn_done);
                    btn_done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogs.dismiss();
                            finish();
                        }
                    });
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
