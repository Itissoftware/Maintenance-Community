package com.mncomunity1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.mncomunity1.R;
import com.mncomunity1.adapter.KnowledgeRecyclerAdapter;
import com.mncomunity1.model.Post;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class KnowledgeActivity extends AppCompatActivity {
    @Bind(R.id.cardList_main)
    RecyclerView recyclerView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    ProgressBar pro;

    String id;

    String code;
    String id_titld;

    ArrayList<Post> list = new ArrayList<>();
    KnowledgeRecyclerAdapter newsArrAdapter;

    String[] title = {"บทความ","เคล็ดลับและเทคนิค","แบ่งปัน","Success Story"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        pro = (ProgressBar) findViewById(R.id.pro);
        pro.setVisibility(View.GONE);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);

        code = getIntent().getStringExtra("code");
        id_titld = getIntent().getStringExtra("title");

        toolbar.setTitle("ความรู้");



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        newsArrAdapter = new KnowledgeRecyclerAdapter(getApplicationContext(), title);
        recyclerView.setAdapter(newsArrAdapter);
        newsArrAdapter.SetOnItemVideiosClickListener(new KnowledgeRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(position == 0){
                    Intent i =new Intent(getApplicationContext(),ArticlesActivity.class);
                    i.putExtra("code","0");
                    i.putExtra("title","บทความ");
                    startActivity(i);
                }if(position == 1){
                    Intent i =new Intent(getApplicationContext(),ArticlesActivity.class);
                    i.putExtra("code","1");
                    i.putExtra("title","เคล็ดลับและเทคนิค");
                    startActivity(i);
                }if(position == 2){
                    Intent i =new Intent(getApplicationContext(),ArticlesActivity.class);
                    i.putExtra("code","2");
                    i.putExtra("title","แบ่งปัน");
                    startActivity(i);
                }

                if(position == 3){
                    Intent i =new Intent(getApplicationContext(),ArticlesActivity.class);
                    i.putExtra("code","3");
                    i.putExtra("title","Success Story");
                    startActivity(i);
                }

            }
        });


    }





}
