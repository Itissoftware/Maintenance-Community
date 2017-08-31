package com.mncomunity1.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mncomunity1.MainActivity;
import com.mncomunity1.R;
import com.mncomunity1.api.APIService;
import com.mncomunity1.app.Config;
import com.mncomunity1.model.Register;
import com.mncomunity1.pack_chat.data.StaticConfig;
import com.mncomunity1.pack_chat.model.User;
import com.mncomunity1.service.ApiClient;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    Toolbar toolbar;

    @Bind(R.id.et_name)
    EditText et_name;

    @Bind(R.id.et_mail)
    EditText et_mail;

    @Bind(R.id.et_pass)
    EditText et_pass;

    @Bind(R.id.et_company)
    EditText et_company;

    @Bind(R.id.et_last_name)
    EditText et_last_name;

    @Bind(R.id.et_phone)
    EditText et_phone;

    @Bind(R.id.btn_cancel)
    Button btn_cancel;

    @Bind(R.id.et_address)
    EditText et_address;

    @Bind(R.id.btn_reg)
    Button btn_reg;

    String name;
    String email;
    String pass;
    String company;
    String token;
    String group;
    String lastName;
    String phone;
    String address;
    private RadioGroup radioGroup;
    private RadioButton radioButton;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    LinearLayout txt_login;

    String regId;

    Dialog dialog;
    String userId;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_login = (LinearLayout) findViewById(R.id.txt_login);

        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

        userId = sp.getString("userId", "000");

        toolbar.setTitle("ลงทะเบียน");
        ButterKnife.bind(this);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);


        dialog = new Dialog(RegisterActivity.this, R.style.FullHeightDialog);
        dialog.setContentView(R.layout.dailog_loading);


        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                name = et_name.getText().toString();
                email = et_mail.getText().toString();
                pass = et_pass.getText().toString();
                company = et_company.getText().toString();
                lastName = et_last_name.getText().toString();
                phone = et_phone.getText().toString();
                address = et_address.getText().toString();

                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                group = radioButton.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(company)) {

                    Toast.makeText(getApplicationContext(), "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.show();
                    createUser(email, pass);
                    registerByServer(name, lastName, email, phone, address, token, company, pass,userId);
                }


            }
        });

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        subscribeToPushService();
        initFirebase();
        displayFirebaseRegId();

    }

    private void initFirebase() {
        //Khoi tao thanh phan de dang nhap, dang ky
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();

            }
        };

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
        token = regId;
        Log.e("LOf", "Firebase reg id: " + regId);

    }

    private void registerByServer(String name, String lastname, String email, String phone, String address, String regid, String company, String pass,String userIds) {


        dialog.show();
        APIService service = ApiClient.getClient().create(APIService.class);

        Call<Register> userCall = service.getRegisterUpdate(name, lastname, email, phone, address, regid, company, pass,userIds);

        userCall.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {

                if (response.body().getSuccess().equals("1")) {

                    dialog.dismiss();
                   //  userId = response.body().getComplete().get(0).getCode();
                    String name = response.body().getComplete().get(0).getNameth();
                    String email = response.body().getComplete().get(0).getEmail();
                    String companyCode = response.body().getComplete().get(0).getCompany_code();
                    String checkStatus = response.body().getComplete().get(0).getCheck_status();
                    String vendorCheck = response.body().getComplete().get(0).getVendor();


                    editor.putBoolean("isLogin", true);
                    editor.putString("userId", userId);
                    editor.putString("name", name);
                    editor.putString("email", email);
                    editor.putString("company_code", companyCode);
                    editor.putString("check_status", checkStatus);
                    editor.putString("check_vebdor", vendorCheck);
                    editor.commit();


                } else {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "ผู้ใช้นี้มีในระบบแล้ว", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {

            }
        });
    }

    public void createUser(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            new LovelyInfoDialog(RegisterActivity.this) {
                                @Override
                                public LovelyInfoDialog setConfirmButtonText(String text) {
                                    findView(com.yarolegovich.lovelydialog.R.id.ld_btn_confirm).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dismiss();
                                        }
                                    });
                                    return super.setConfirmButtonText(text);
                                }
                            }
                                    .setTopColorRes(R.color.colorAccent)
                                    .setIcon(R.drawable.ic_add_friend)
                                    .setTitle("Register false")
                                    .setMessage("Email exist or weak password!")
                                    .setConfirmButtonText("ok")
                                    .setCancelable(false)
                                    .show();
                        } else {
                            dialog.dismiss();
                            initNewUserInfo(userId);
                            Intent i = new Intent(getApplicationContext(), LayoutDemoActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    void initNewUserInfo(String userId) {
        User newUser = new User();
        newUser.userId = userId;
        newUser.email = user.getEmail();
        newUser.name = user.getEmail().substring(0, user.getEmail().indexOf("@"));
        newUser.avata = StaticConfig.STR_DEFAULT_BASE64;
        FirebaseDatabase.getInstance().getReference().child("user/" + userId).setValue(newUser);
    }

    private void subscribeToPushService() {
        token = FirebaseInstanceId.getInstance().getToken();

        // Log and toast
        Log.e("AndroidBash", token);

    }

}
