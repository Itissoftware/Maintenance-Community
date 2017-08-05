package com.mncomunity1.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mncomunity1.R;
import com.mncomunity1.adapter.TipsAndTecRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.Post;
import com.mncomunity1.service.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TwoFragment extends Fragment {

    RecyclerView cardList_main;
    ArrayList<Post> listPost = new ArrayList<>();
    TipsAndTecRecyclerAdapter tipsAndTecRecyclerAdapter;

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_two, container, false);

        cardList_main = (RecyclerView) rootView.findViewById(R.id.cardList_main);

        cardList_main.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        cardList_main.setLayoutManager(llm);

        loginByServerAtr();



        return rootView;
    }


    public void loginByServerAtr() {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<Post> userCall = service.getTip();

        userCall.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (response.body().getPost() != null) {
                    listPost.clear();
                    for (int i = 0; i < response.body().getPost().size(); i++) {
                        listPost.add(response.body());

                        tipsAndTecRecyclerAdapter = new TipsAndTecRecyclerAdapter(getActivity(), listPost);

                        cardList_main.setAdapter(tipsAndTecRecyclerAdapter);
                        tipsAndTecRecyclerAdapter.notifyDataSetChanged();
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


    }

}