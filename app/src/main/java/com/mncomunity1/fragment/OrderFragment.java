package com.mncomunity1.fragment;

import android.content.Context;
import android.content.Intent;
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
import com.mncomunity1.activity.CompanyOrderActivity;
import com.mncomunity1.adapter.GetHistoryOrderRecyclerAdapter;
import com.mncomunity1.adapter.GetOrderRecyclerAdapter;
import com.mncomunity1.api.APIService;
import com.mncomunity1.app.Config;
import com.mncomunity1.model.HistoryOrder;
import com.mncomunity1.model.Order;
import com.mncomunity1.service.ApiClient;
import com.mncomunity1.until.SimpleDividerItemDecoration;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderFragment extends Fragment {

    RecyclerView recyclerView;

    ArrayList<Order> listOr = new ArrayList<>();
    GetOrderRecyclerAdapter getLogErrorRecyclerAdapter;
    SharedPreferences sharedpreferences;

    ArrayList<HistoryOrder> listPost = new ArrayList<>();
    GetHistoryOrderRecyclerAdapter getHistoryOrderRecyclerAdapter;

    public static final String mypreference = "mypref";

    String userId;
    String companyCode;
    String check;

    String regId;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_get_history, container, false);

        SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);
        Log.e("LOf", "Firebase reg id: " + regId);

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
        check = sharedpreferences.getString("check", "0");

        getVendor(userId);



        return rootView;
    }


    private void getVendor(String userId) {

        APIService service = ApiClient.getClient().create(APIService.class);

        Call<Order> userCall = service.getOrderHistory(userId);

        userCall.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {

                        if(response.body().isResult() ==  true){

                            for (int i = 0; i < response.body().getTotal().size(); i++) {

                                listOr.add(response.body());

                                getLogErrorRecyclerAdapter = new GetOrderRecyclerAdapter(getActivity(), listOr);
                                recyclerView.setAdapter(getLogErrorRecyclerAdapter);
                                getLogErrorRecyclerAdapter.notifyDataSetChanged();

                                getLogErrorRecyclerAdapter.SetOnItemVideiosClickListener(new GetOrderRecyclerAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        String companyCode = listOr.get(position).getTotal().get(position).getCompany_code();
                                        Intent i = new Intent(getActivity(), CompanyOrderActivity.class);
                                        i.putExtra("company_code", companyCode);
                                        startActivity(i);
                                    }
                                });
                            }
                        }else{

                        }


            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
            Log.e("getLocalizedMessage",t.getLocalizedMessage());
                Log.e("getMessage",t.getMessage());
            }
        });
    }



    @Override
    public void onResume() {
        super.onResume();

    }
}
