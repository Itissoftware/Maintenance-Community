package com.mncomunity1.activity;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;
import com.mncomunity1.R;
import com.mncomunity1.adapter.NewsRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.News;
import com.mncomunity1.model.PostUpdateStatus;
import com.mncomunity1.model.Result;
import com.mncomunity1.model.SeminarDetails;
import com.mncomunity1.service.ApiClient;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListSeminarDetailsActivity extends AppCompatActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;

    String Key;


    ArrayList<News> listNews = new ArrayList<>();
    NewsRecyclerAdapter newsRecyclerAdapter;


    final String PREF_NAME = "LoginPreferences";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    String userId;

    ProgressBar pro;

    String id;

    TextView txt_title;
    TextView txt_course;
    TextView txt_prihciple;
    TextView txt_time;
    TextView txt_topic;

    Button btn_post;

    ImageView img_cover;

    EditText ed_name;
    EditText ed_email;
    EditText ed_phone;
    EditText ed_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_seminar);
        ButterKnife.bind(this);

        img_cover = (ImageView) findViewById(R.id.img_cover);
        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_course = (TextView) findViewById(R.id.txt_course);
        txt_prihciple = (TextView) findViewById(R.id.txt_prihciple);
        txt_time = (TextView) findViewById(R.id.txt_time);
        txt_topic = (TextView) findViewById(R.id.txt_topic);
        btn_post = (Button) findViewById(R.id.btn_post);

        ed_name = (EditText) findViewById(R.id.ed_name);
        ed_email = (EditText) findViewById(R.id.ed_email);
        ed_phone = (EditText) findViewById(R.id.ed_phone);
        ed_details = (EditText) findViewById(R.id.ed_details);


        pro = (ProgressBar) findViewById(R.id.pro);

        sp = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
        userId = sp.getString("userId", "000");
        toolbar.setTitle("รายละเอียด");

        id = getIntent().getStringExtra("id");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getDetails(id);

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postSeminar(ed_name.getText().toString(), ed_email.getText().toString(), ed_details.getText().toString(), ed_phone.getText().toString());
            }
        });
    }


    private void getDetails(String id) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<SeminarDetails> userCall = service.getSeminarDetails(id);

        userCall.enqueue(new Callback<SeminarDetails>() {
            @Override
            public void onResponse(Call<SeminarDetails> call, Response<SeminarDetails> response) {


                if (response.body().getTotal() != null) {
                    Log.e("2.0 getFeed ", new GsonBuilder().setPrettyPrinting().create().toJson(response));
                    for (int i = 0; i < response.body().getTotal().size(); i++) {
                        String title = response.body().getTotal().get(i).getTitle();
                        String course = response.body().getTotal().get(i).getCourse();
                        String prihciple = response.body().getTotal().get(i).getDetails();
                        String time = response.body().getTotal().get(i).getTimes();
                        String topic = response.body().getTotal().get(i).getTopics();
                        String urlImage = response.body().getTotal().get(i).getPath_image();

                        //  Log.e("prihciple",response.body().getTotal().get(i).getPrihciple());


                        txt_title.setText(title);
                        txt_course.setText(course);
                        txt_prihciple.setText(prihciple);
                        txt_time.setText(time);
                        txt_topic.setText(topic);

                        Glide.with(getApplicationContext())
                                .load(urlImage)
                                .into(img_cover);
                    }


                }

            }

            @Override
            public void onFailure(Call<SeminarDetails> call, Throwable t) {

            }
        });
    }

    private void postSeminar(String name, String email, String details, String phone) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<Result> userCall = service.postsemiarQuestion(name, email, details, phone);

        userCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                if (response.body().getResult().equals("1")) {
                    Toast.makeText(getApplicationContext(), "ส่งข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

            }
        });
    }


}
