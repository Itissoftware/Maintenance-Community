package com.mncomunity1.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.mncomunity1.R;
import com.mncomunity1.adapter.CartRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.app.Config;
import com.mncomunity1.model.Delete;
import com.mncomunity1.model.PostOrder;
import com.mncomunity1.model.Register;
import com.mncomunity1.model.getOrder;
import com.mncomunity1.service.ApiClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartTotalActivity extends AppCompatActivity {


    @Bind(R.id.cardList_main)
    RecyclerView recyclerView;
    List<getOrder> mCartList = new ArrayList<>();

    CartRecyclerAdapter cartRecyclerAdapter;
    RecyclerView cardList_main;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";

    TextView txt_total;
    Button btn_done;

    String userId;
    String userIdPre;
    String nameThComplate;
    String totalComplate;
    String companyCodes;
    Toolbar toolbar;

    Dialog dialog;
    Dialog dialog2;
    Dialog dialogEdit;

    String companyCode;
    String companyCodeOrder;

    String regId;
    String num;

    Dialog dialogSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dailog_total_cart);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ตะกร้าสินค้า");
        sharedpreferences = getSharedPreferences(mypreference, Context.MODE_PRIVATE);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);
        Log.e("LOf", "Firebase reg id: " + regId);

        dialog = new Dialog(CartTotalActivity.this, R.style.FullHeightDialog);
        dialog.setContentView(R.layout.dailog_pre_complete);

        dialogSubmit = new Dialog(CartTotalActivity.this, R.style.FullHeightDialog);
        dialogSubmit.setContentView(R.layout.dailog_submit);

        dialogEdit = new Dialog(CartTotalActivity.this, R.style.FullHeightDialog);
        dialogEdit.setContentView(R.layout.dailog_post_edit);

        userId = sharedpreferences.getString("userId", "1");
        userIdPre = sharedpreferences.getString("userIdPre", "1");
        companyCode = sharedpreferences.getString("company_code", "1");
        //userId = getIntent().getStringExtra("userId");

        Log.e("userId:", userId);
        Log.e("userIdPre:", userId);
        Log.e("company_code:", companyCode);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ButterKnife.bind(this);
        txt_total = (TextView) findViewById(R.id.txt_total);
        btn_done = (Button) findViewById(R.id.btn_done);


        getOrder(userId);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // loginByServerTrain(companyCode);

    }


    private void deleteOrder(final String user_id) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<Delete> userCall = service.deleteOrder(user_id);

        userCall.enqueue(new Callback<Delete>() {
            @Override
            public void onResponse(Call<Delete> call, Response<Delete> response) {
                if (response.body().getTotal().get(0).getStatus().equals("1")) {
                    mCartList.clear();
//                    Intent i =new Intent(getApplicationContext(),CartTotalActivity.class);
//                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Delete> call, Throwable t) {

            }
        });
    }


    private void PostOrderComplete(String id) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<PostOrder> userCall = service.postCompalte(id);

        userCall.enqueue(new Callback<PostOrder>() {
            @Override
            public void onResponse(Call<PostOrder> call, Response<PostOrder> response) {


                if (response.body().getComplete().get(0).getStatus().equals("1")) {

                    dialog2 = new Dialog(CartTotalActivity.this, R.style.FullHeightDialog);
                    dialog2.setContentView(R.layout.dailog_complete);
                    dialog2.show();
                    dialog.dismiss();
                    Button btn_ok = (Button) dialog2.findViewById(R.id.btn_ok);
                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog2.dismiss();
                          //  deleteOrder(userId);

                            Intent i = new Intent(getApplicationContext(), CartTotalActivity.class);
                            startActivity(i);
                            finish();

                        }
                    });

                }

            }

            @Override
            public void onFailure(Call<PostOrder> call, Throwable t) {

            }
        });
    }


    private void getOrder(final String user_id) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<getOrder> userCall = service.getOrder(user_id);

        userCall.enqueue(new Callback<getOrder>() {
            @Override
            public void onResponse(Call<getOrder> call, Response<getOrder> response) {

                if (response.body().getTotal() != null) {


                    // Make sure to clear the selections
                    for (int i = 0; i < response.body().getTotal().size(); i++) {

                        mCartList.add(response.body());
                        userId = mCartList.get(i).getTotal().get(i).getId();
                        nameThComplate = mCartList.get(i).getTotal().get(i).getNameth();
                        totalComplate = mCartList.get(i).getTotal().get(i).getTotal();
                        companyCodeOrder = mCartList.get(i).getTotal().get(i).getCompany_code();

                        cardList_main = (RecyclerView) findViewById(R.id.cardList_main);
                        cardList_main.setHasFixedSize(true);
                        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                        llm.setOrientation(LinearLayoutManager.VERTICAL);

                        cardList_main.setLayoutManager(llm);

                        txt_total.setText(mCartList.size() + "");
                        cartRecyclerAdapter = new CartRecyclerAdapter(getApplicationContext(), mCartList);
                        cardList_main.setAdapter(cartRecyclerAdapter);

                        btn_done.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                if (sharedpreferences.getBoolean("isLogin", false) == false) {

                                    dialogSubmit.show();
                                    final EditText td_name = (EditText) dialogSubmit.findViewById(R.id.td_name);
                                    final EditText td_lastname = (EditText) dialogSubmit.findViewById(R.id.td_lastname);
                                    final EditText td_phone = (EditText) dialogSubmit.findViewById(R.id.td_phone);
                                    final EditText td_mail = (EditText) dialogSubmit.findViewById(R.id.td_mail);
                                    final EditText td_address = (EditText) dialogSubmit.findViewById(R.id.td_address);
                                    final EditText td_company = (EditText) dialogSubmit.findViewById(R.id.td_company);
                                    final EditText td_password = (EditText) dialogSubmit.findViewById(R.id.td_password);
                                    LinearLayout txt_login = (LinearLayout) dialogSubmit.findViewById(R.id.txt_login);
                                    Button btn_done = (Button) dialogSubmit.findViewById(R.id.btn_done);
                                    Button btn_close = (Button) dialogSubmit.findViewById(R.id.btn_close);





                                    txt_login.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                            startActivity(i);
                                            finish();

                                        }
                                    });

                                    btn_done.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String name = td_name.getText().toString();
                                            String lastName = td_lastname.getText().toString();
                                            String tdPhone = td_phone.getText().toString();
                                            String tdMail = td_mail.getText().toString();
                                            String tdAddress = td_address.getText().toString();
                                            String company = td_company.getText().toString();
                                            String password = td_password.getText().toString();

                                            if (name == "") {
                        Toast.makeText(getApplicationContext(), "กรุณาใส่ข้อมูล",Toast.LENGTH_SHORT).show();
                                            } else {
                                                registerByServer(name, lastName, tdMail, tdPhone, tdAddress, regId, company, password);
                                            }


                                        }
                                    });

                                    btn_close.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialogSubmit.dismiss();
                                        }
                                    });


                                } else {
                                    dialog.show();
                                    PostOrderComplete(userId);
                                }


                            }
                        });

                        cartRecyclerAdapter.SetOnItemEditClickListener(new CartRecyclerAdapter.OnItemEditClickListener() {
                            @Override
                            public void onItemEditClick(View view, int position) {

                                String title = mCartList.get(position).getTotal().get(position).getNameth();
                                final String code = mCartList.get(position).getTotal().get(position).getCode();
                                String image = mCartList.get(position).getTotal().get(position).getImage();
                                final String id = mCartList.get(position).getTotal().get(position).getId();
                                final String idList = mCartList.get(position).getTotal().get(position).getId_list();

                                final NumberPicker edit_num = (NumberPicker) dialogEdit.findViewById(R.id.numberPicker1);
                                Button btn_done = (Button) dialogEdit.findViewById(R.id.btn_done);

                                edit_num.setMinValue(0);

                                edit_num.setMaxValue(100);

                                final NumberPicker picker = new NumberPicker(CartTotalActivity.this);
                                picker.setMinValue(1);
                                picker.setMaxValue(50);

                                final FrameLayout layout = new FrameLayout(CartTotalActivity.this);
                                layout.addView(picker, new FrameLayout.LayoutParams(
                                        FrameLayout.LayoutParams.WRAP_CONTENT,
                                        FrameLayout.LayoutParams.WRAP_CONTENT,
                                        Gravity.CENTER));

                                new AlertDialog.Builder(CartTotalActivity.this)
                                        .setView(layout)
                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                // do something with picker.getValue()

                                                num = Integer.toString(picker.getValue());
                                                PostEditOrder(idList,num);
                                            }
                                        })
                                        .setNegativeButton(android.R.string.cancel, null)
                                        .show();

                                btn_done.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                });
                            }
                        });


                        cartRecyclerAdapter.SetOnItemDelteClickListener(new CartRecyclerAdapter.OnItemDelteClickListener() {
                            @Override
                            public void onItemDeleteClick(View view, int position) {
                                Toast.makeText(getApplicationContext(),"ลบ",Toast.LENGTH_SHORT).show();

                                String title = mCartList.get(position).getTotal().get(position).getNameth();
                                final String code = mCartList.get(position).getTotal().get(position).getCode();
                                String image = mCartList.get(position).getTotal().get(position).getImage();
                                final String id = mCartList.get(position).getTotal().get(position).getId();
                                final String idList = mCartList.get(position).getTotal().get(position).getId_list();

                                PostDeleteOrder(idList);
                            }
                        });

                    }

                }

            }

            @Override
            public void onFailure(Call<getOrder> call, Throwable t) {

            }
        });
    }

    private void PostEditOrder(final String id, String total) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<PostOrder> userCall = service.postOrderEdit(total, id);

        userCall.enqueue(new Callback<PostOrder>() {
            @Override
            public void onResponse(Call<PostOrder> call, Response<PostOrder> response) {

                if (response.body().getComplete().get(0).getStatus().equals("1")) {


                    Intent i = new Intent(getApplicationContext(), CartTotalActivity.class);
                    startActivity(i);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<PostOrder> call, Throwable t) {

            }
        });
    }

    private void PostDeleteOrder(final String id) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<PostOrder> userCall = service.postOrderDelete(id);

        userCall.enqueue(new Callback<PostOrder>() {
            @Override
            public void onResponse(Call<PostOrder> call, Response<PostOrder> response) {

                if (response.body().getComplete().get(0).getStatus().equals("1")) {


                    Intent i = new Intent(getApplicationContext(), CartTotalActivity.class);
                    startActivity(i);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<PostOrder> call, Throwable t) {

            }
        });
    }


    private void registerByServer(String name, String lastname, String email, String phone, String address, String regid, String company_nameth, String passwords) {


        dialog.show();
        APIService service = ApiClient.getClient().create(APIService.class);

        Call<Register> userCall = service.getRegisterUpdate(name, lastname, email, phone, address, regid, company_nameth, passwords);

        userCall.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (response.body().getSuccess().equals("1")) {

                    String userId = response.body().getComplete().get(0).getCode();
                    String name = response.body().getComplete().get(0).getNameth();
                    String email = response.body().getComplete().get(0).getEmail();
                    String companyCode = response.body().getComplete().get(0).getCompany_code();

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean("isLogin", true);
                    editor.putString("userId", userId);
                    editor.putString("name", name);
                    editor.putString("email", email);
                    editor.putString("company_code", companyCode);
                    editor.commit();

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            dialog.dismiss();
                            Intent i = new Intent(getApplicationContext(), CartTotalActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }, 2000);


                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {

            }
        });
    }


}
