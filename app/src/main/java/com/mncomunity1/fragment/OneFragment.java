package com.mncomunity1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mncomunity1.R;
import com.mncomunity1.activity.ArticlesActivity;
import com.mncomunity1.adapter.ArticlesRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.Post;
import com.mncomunity1.service.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OneFragment extends Fragment {

    RecyclerView cardList_main;
    ArrayList<Post> list = new ArrayList<>();
    ArticlesRecyclerAdapter knowRecyclerAdapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_one, container, false);



        cardList_main = (RecyclerView) rootView.findViewById(R.id.cardList_main);

        cardList_main.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        cardList_main.setLayoutManager(llm);


        //loginByServerAtr();

        return rootView;
    }


    public void loginByServerAtr() {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<Post> userCall = service.getArt();

        userCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (response.body().getPost() != null) {

                    list.clear();

                    for (int i = 0; i < response.body().getPost().size(); i++) {

                        list.add(response.body());

                        Log.e("size",list.size()+"");
                        knowRecyclerAdapter = new ArticlesRecyclerAdapter(getActivity(), list);

                        cardList_main.setAdapter(knowRecyclerAdapter);
                        knowRecyclerAdapter.notifyDataSetChanged();

                        knowRecyclerAdapter.SetOnItemVideiosClickListener(new ArticlesRecyclerAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {



                                int codes = list.get(position).getPost().get(position).getCode();
                                Log.e("codes",codes+"");
                                String title = list.get(position).getPost().get(position).getTitle();
                                int code = list.get(position).getPost().get(position).getCode();
                                String postition = Integer.toString(code);

                                Intent i =new Intent(getActivity(),ArticlesActivity.class);
                                i.putExtra("code",postition);
                                i.putExtra("title",title);
                                startActivity(i);

                            }
                        });

                    }
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        loginByServerAtr();
    }
}
