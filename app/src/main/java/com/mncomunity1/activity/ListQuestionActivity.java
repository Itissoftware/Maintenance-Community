package com.mncomunity1.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mncomunity1.R;
import com.mncomunity1.adapter.ListQuestionRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.BitOfferDetails;
import com.mncomunity1.model.ModelCheckBoxQuestion;
import com.mncomunity1.model.ModelPost;
import com.mncomunity1.model.QuestionList;
import com.mncomunity1.service.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListQuestionActivity extends AppCompatActivity {
    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    Toolbar toolbar;

    RecyclerView cardList_main;
    ProgressBar pro;

    TextView txt_gone;

    private CheckBox chk_select_all;
    private Button btn_delete_all;

    ArrayList<QuestionList> lists = new ArrayList<>();
    ArrayList<ModelCheckBoxQuestion> listCheckBox = new ArrayList<>();

    ListQuestionRecyclerAdapter listQuestionRecyclerAdapter;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);
        txt_gone = (TextView) findViewById(R.id.txt_gone);
        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        chk_select_all = (CheckBox) findViewById(R.id.chk_select_all);
        btn_delete_all = (Button) findViewById(R.id.btn_delete_all);

        editor = sp.edit();
        userId = sp.getString("userId", "000");
        cardList_main = (RecyclerView) findViewById(R.id.cardList_main);
        pro = (ProgressBar) findViewById(R.id.pro);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        cardList_main.setHasFixedSize(true);
        final LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        cardList_main.setLayoutManager(llm);

        toolbar.setTitle("รายการ");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getListQuest(userId);

        chk_select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chk_select_all.isChecked()) {

                    for (ModelCheckBoxQuestion model : listCheckBox) {
                        model.setSelected(true);
                    }
                } else {

                    for (ModelCheckBoxQuestion model : listCheckBox) {
                        model.setSelected(false);
                    }
                }

                listQuestionRecyclerAdapter.notifyDataSetChanged();
            }
        });

        btn_delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                //builder.setTitle("Dlete ");
                builder.setMessage("คุณต้องการลบรายการหรือไม่ ?")
                        .setCancelable(false)
                        .setPositiveButton("ต้องการ",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        String data = "";
                                        ArrayList<ModelCheckBoxQuestion> stList = ((ListQuestionRecyclerAdapter) listQuestionRecyclerAdapter).getStudentist();

                                        for (int i = 0; i < stList.size(); i++) {
                                            ModelCheckBoxQuestion singleStudent = stList.get(i);
                                            if (singleStudent.isSelected() == true) {

                                                data = data + "\n" + singleStudent.getId().toString();
                                                deleteBitbuffer(singleStudent.getId());
                                            }

                                        }

                                    }
                                })
                        .setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                            }
                        });

                builder.show();


            }
        });

    }

    public void getListQuest(String user_id) {
        APIService service = ApiClient.getClient().create(APIService.class);

        Call<QuestionList> userCall = service.getListQuest(user_id);

        userCall.enqueue(new Callback<QuestionList>() {
            @Override
            public void onResponse(Call<QuestionList> call, Response<QuestionList> response) {


                if (response != null) {
                    if (response.body().getTotal() != null) {
                        for (int i = 0; i < response.body().getTotal().size(); i++) {
                            pro.setVisibility(View.GONE);

                            String title = response.body().getTotal().get(i).getTitle();
                            String amount = response.body().getTotal().get(i).getAmount();
                            String category = response.body().getTotal().get(i).getCategory();
                            String path_photo = response.body().getTotal().get(i).getPath_photo();
                            String details = response.body().getTotal().get(i).getDetails();
                            String id = response.body().getTotal().get(i).getId();


                            ModelCheckBoxQuestion item = new ModelCheckBoxQuestion();

                            item.setTitle(title);
                            item.setAmount(amount);
                            item.setCategory(category);
                            item.setPath_photo(path_photo);
                            item.setDetails(details);
                            item.setId(id);
                            item.setSelected(false);
                            listCheckBox.add(item);

                            lists.add(response.body());
                            txt_gone.setVisibility(View.GONE);
                            listQuestionRecyclerAdapter = new ListQuestionRecyclerAdapter(getApplicationContext(), listCheckBox);
                            cardList_main.setAdapter(listQuestionRecyclerAdapter);

                        }

                        listQuestionRecyclerAdapter.SetOnItemVideiosClickListener(new ListQuestionRecyclerAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                String id = lists.get(position).getTotal().get(position).getId();

                                //postRequestPriceStatus(id);

                                Intent i = new Intent(getApplicationContext(), ListQuestionVendorActivity.class);
                                i.putExtra("id", id);
                                startActivity(i);


                            }
                        });
                    } else {
                        pro.setVisibility(View.GONE);
                        txt_gone.setVisibility(View.VISIBLE);
                    }

                }

            }

            @Override
            public void onFailure(Call<QuestionList> call, Throwable t) {

            }
        });
    }

    public void postRequestPriceStatus(String req_id) {
        APIService service = ApiClient.getClient().create(APIService.class);

        Call<ModelPost> userCall = service.postRequestPriceStatus(req_id);

        userCall.enqueue(new Callback<ModelPost>() {
            @Override
            public void onResponse(Call<ModelPost> call, Response<ModelPost> response) {

            }

            @Override
            public void onFailure(Call<ModelPost> call, Throwable t) {

            }
        });
    }

    public void deleteBitbuffer(String id) {
        APIService service = ApiClient.getClient().create(APIService.class);

        Call<ModelPost> userCall = service.deleteBitbuffer(id);

        userCall.enqueue(new Callback<ModelPost>() {
            @Override
            public void onResponse(Call<ModelPost> call, Response<ModelPost> response) {

                Intent i = new Intent(getApplicationContext(), ListQuestionActivity.class);
                startActivity(i);
                finish();

            }

            @Override
            public void onFailure(Call<ModelPost> call, Throwable t) {

            }
        });
    }


}
