package com.mncomunity1.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mncomunity1.MainActivity;
import com.mncomunity1.R;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.Mail;
import com.mncomunity1.model.PostError;
import com.mncomunity1.pack_chat.ui.LoginActivityChat;
import com.mncomunity1.service.ApiClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoreActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.td_name)
    EditText tdnsme;

    @Bind(R.id.td_phone)
    EditText tdPhone;


    @Bind(R.id.td_email)
    EditText tdEmail;


    @Bind(R.id.btn_done)
    Button done;


    @Bind(R.id.btn_cancel)
    Button btn_cancel;


    TextView btn_log;


    Dialog dialogs;
    Dialog dialogsMail;
    Dialog dialogsProgram;

    String name;
    String company;
    String email;
    String userId;

    LinearLayout radio_more1;
    LinearLayout radio_program;

    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    boolean isLogin;
    String check_status;
    Button btn_done;

    String details ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        ButterKnife.bind(this);

        radio_more1 = (LinearLayout) findViewById(R.id.radio_more1);
        radio_program = (LinearLayout) findViewById(R.id.radio_program);

        btn_log = (Button) findViewById(R.id.btn_log);

        toolbar.setTitle("สอบถามข้อมูลเพิ่มเติม");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();

        isLogin = sp.getBoolean("isLogin", false);
        Log.e("isLogin", sp.getBoolean("isLogin", false) + "'");

        name = sp.getString("name", "default");
        company = sp.getString("company_code", "iSSOFTWARE");
        email = sp.getString("email", "default");
        userId = sp.getString("userId", "00001");
        check_status = sp.getString("check_status", "0");

        details = "รหัสลูกค้า: "+userId+" บริษัท: "+company+"ชื่อลูกค้า: "+name;

        tdnsme.setEnabled(false);
        tdEmail.setEnabled(false);

        tdnsme.setText(name);
        tdEmail.setText(email);

        dialogs = new Dialog(MoreActivity.this, R.style.FullHeightDialog);
        dialogs.setContentView(R.layout.dailog_edittext);

        dialogsMail = new Dialog(MoreActivity.this,R.style.FullHeightDialog);
        dialogsMail.setContentView(R.layout.dailog_mail);
        btn_done = (Button) dialogsMail.findViewById(R.id.btn_done);


        dialogsProgram = new Dialog(MoreActivity.this, R.style.FullHeightDialog);
        dialogsProgram.setContentView(R.layout.dailog_program);


        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), LogActivity.class);
                i.putExtra("userId", userId);
                startActivity(i);
            }
        });

        radio_more1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (check_status.equals("0")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MoreActivity.this);
                    builder.setMessage("คุณยังไม่ได้ทำการยืนยันระบบสามชิก PMII")
                            .setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Do something on Share
                                    sendEmail("sattboot1@gmail.com","ยืนยันลุกค้าPMII",details);
                                    dialog.dismiss();
                                    dialogsMail.show();
                                    btn_done.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialogsMail.dismiss();
                                            finish();
                                        }
                                    });

                                }
                            })
                            .setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Do something on Cancel

                                    dialog.dismiss();
                                }
                            });
                    builder.show();

                } else {

                    Intent i = new Intent(getApplicationContext(), LoginActivityChat.class);
                    startActivity(i);
                }

//                Button btn_submit = (Button) dialogs.findViewById(R.id.btn_submit);
//                Button btn_cancel = (Button) dialogs.findViewById(R.id.btn_cancel);
//
//
//                btn_submit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        // Toast.makeText(MoreActivity.this, "แชท", Toast.LENGTH_SHORT).show();
//
//
////                            if (isLogin == true) {
////                                Intent i = new Intent(getApplicationContext(), LoginActivityChat.class);
////                                startActivity(i);
////                            } else {
////                                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
////                                startActivity(i);
////                            }
//
//
//
//
//                    }
//                });
//
//                btn_cancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialogs.dismiss();
//                    }
//                });
//
//                dialogs.show();

            }
        });

        radio_program.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (check_status.equals("0")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MoreActivity.this);
                    builder.setMessage("คุณยังไม่ได้ทำการยืนยันระบบสามชิก PMII")
                            .setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Do something on Share
                                    sendEmail("sattboot1@gmail.com","ยืนยันลูกค้าPMII",details);
                                    dialog.dismiss();
                                    dialogsMail.show();
                                    btn_done.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialogsMail.dismiss();
                                            finish();
                                        }
                                    });


                                }
                            })
                            .setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Do something on Cancel

                                    dialog.dismiss();
                                }
                            });
                    builder.show();

                } else {

                    dialogsProgram.show();

                    final Spinner spinner = (Spinner) dialogsProgram.findViewById(R.id.sp_main);
                    final EditText et_ = (EditText) dialogsProgram.findViewById(R.id.et_);
                    Button btn_submit = (Button) dialogsProgram.findViewById(R.id.btn_submit);
                    Button btn_cancel = (Button) dialogsProgram.findViewById(R.id.btn_cancel);

                    List<String> list = new ArrayList<String>();
                    list.add("ข้อมูลหลัก");
                    list.add("แจ้งซ่อม สั่งซ่อม");
                    list.add("งานบำรุงรักษา");
                    list.add("อะไหล่");
                    list.add("รายงาน");

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                            (MoreActivity.this, android.R.layout.simple_spinner_item, list);

                    dataAdapter.setDropDownViewResource
                            (android.R.layout.simple_spinner_dropdown_item);

                    spinner.setAdapter(dataAdapter);



                        btn_submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (TextUtils.isEmpty(et_.getText().toString())) {
                                    Toast.makeText(getApplicationContext(), "กรุณากรอกรายละเอียดการแจ้งปัญหา", Toast.LENGTH_SHORT).show();
                                } else {
                                    postServer(name, "", email, et_.getText().toString(), company, userId, spinner.getSelectedItem().toString());
                                    dialogs.dismiss();
                                    et_.setText("");
                                }
                            }
                        });




                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogsProgram.dismiss();
                        }
                    });

                    dialogs.dismiss();
                }


            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void postServer(String user, String phone, String email, String details, String company, String userId, String type) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<PostError> userCall = service.getError(user, phone, email, details, company, userId, type);

        userCall.enqueue(new Callback<PostError>() {
            @Override
            public void onResponse(Call<PostError> call, Response<PostError> response) {

                if (response.body().getSuccess().equals("1")) {
                    dialogs = new Dialog(MoreActivity.this, R.style.FullHeightDialog);
                    dialogs.setContentView(R.layout.dailog_company);
                    dialogs.show();
                    Button btn_done = (Button) dialogs.findViewById(R.id.btn_done);
                    btn_done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogs.dismiss();
                            // finish();

                            Intent i = new Intent(getApplicationContext(), MoreActivity.class);
                            startActivity(i);
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

    public void sendEmail(String email,String title,String details) {

        APIService service = ApiClient.getClient().create(APIService.class);
        Call<Mail> userCall = service.postMail(email,title,details);

        userCall.enqueue(new Callback<Mail>() {
            @Override
            public void onResponse(Call<Mail> call, Response<Mail> response) {


            }

            @Override
            public void onFailure(Call<Mail> call, Throwable t) {

            }
        });




    }

}
