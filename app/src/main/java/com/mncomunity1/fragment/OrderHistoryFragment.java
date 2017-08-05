package com.mncomunity1.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mncomunity1.R;
import com.mncomunity1.adapter.GetHistoryOrderRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.model.HistoryOrder;
import com.mncomunity1.service.ApiClient;
import com.mncomunity1.until.SimpleDividerItemDecoration;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderHistoryFragment extends Fragment {

    RecyclerView recyclerView;

    ArrayList<HistoryOrder> listPost = new ArrayList<>();
    GetHistoryOrderRecyclerAdapter getLogErrorRecyclerAdapter;
    SharedPreferences sharedpreferences;

    String companyCode;

    public static final String mypreference = "mypref";

    String userId;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_get_history, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.cardList_main);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        recyclerView.setLayoutManager(llm);



        sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        userId = sharedpreferences.getString("userId", "NO");
        companyCode = sharedpreferences.getString("company_code", "1");
        Log.e("companyCode",companyCode);

        Log.e("userId", userId);
        getLog(companyCode);

        return rootView;
    }


    private void getLog(String userId) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<HistoryOrder> userCall = service.getHistoryVender(userId);

        userCall.enqueue(new Callback<HistoryOrder>() {
            @Override
            public void onResponse(Call<HistoryOrder> call, Response<HistoryOrder> response) {

                if (response.body() != null) {
                    if(response.body().isResult() == true){
                        for (int i = 0; i < response.body().getTotal().size(); i++) {
                            listPost.add(response.body());


                            getLogErrorRecyclerAdapter = new GetHistoryOrderRecyclerAdapter(getActivity(), listPost);
                            // recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), R.drawable.divider));
                            recyclerView.setAdapter(getLogErrorRecyclerAdapter);

                        }
                    }else{

                    }

                }

            }

            @Override
            public void onFailure(Call<HistoryOrder> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
