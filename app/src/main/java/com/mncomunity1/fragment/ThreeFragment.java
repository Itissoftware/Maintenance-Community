package com.mncomunity1.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mncomunity1.R;
import com.mncomunity1.adapter.ListRecyclerAdapter;
import com.mncomunity1.model.Post;

import java.util.ArrayList;


public class ThreeFragment extends Fragment {

    RecyclerView cardList_main;
    ArrayList<Post> listPost = new ArrayList<>();
    ListRecyclerAdapter listRecyclerAdapter;

    public ThreeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_three, container, false);


        cardList_main = (RecyclerView) rootView.findViewById(R.id.cardList_main);
        cardList_main.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);



        return rootView;
    }



}