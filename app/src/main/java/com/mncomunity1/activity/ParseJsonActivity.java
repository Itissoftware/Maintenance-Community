package com.mncomunity1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mncomunity1.R;
import com.mncomunity1.adapter.ParseRecyclerAdapter;
import com.mncomunity1.model.ParseJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ParseJsonActivity extends AppCompatActivity {
    @Bind(R.id.cardList_main)
    RecyclerView recyclerView;

    ParseRecyclerAdapter parseRecyclerAdapter;
    ArrayList<ParseJson> list = new ArrayList<>();

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ButterKnife.bind(this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        url = getIntent().getStringExtra("link");
        Log.e("url",url);



        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("post");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jo = jsonArray.getJSONObject(i);
                         Log.e("topic",jo.getString("topic"));

                        String name = jo.optString("topic");
                        String url = jo.optString("link");

                        ParseJson item = new ParseJson();
                        item.setName(name);
                        item.setUrl(url);
                        list.add(item);


                        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                        llm.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(llm);
                        parseRecyclerAdapter = new ParseRecyclerAdapter(ParseJsonActivity.this,list);
                        recyclerView.setAdapter( parseRecyclerAdapter );

                        parseRecyclerAdapter.SetOnItemVideiosClickListener(new ParseRecyclerAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                String url = list.get(position).getUrl();
                                String title = list.get(position).getName();
                                Log.e("url",url);

                                Intent i = new Intent(getApplicationContext(),WebTabActivity.class);
                                i.putExtra("url",url);
                                i.putExtra("title",title);
                                startActivity(i);
                            }
                        });


                    }
                    Log.e("SIZE",list.size()+"");



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Anything you want
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);



    }


}
