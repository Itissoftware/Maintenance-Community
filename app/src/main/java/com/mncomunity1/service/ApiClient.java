package com.mncomunity1.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mncomunity1.api.APIService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    public static final String BASE_URL = "https://mn-community.com";
    private static final String ROOT_URL = "https://mn-community.com";
    private static Retrofit retrofit = null;



    public static Retrofit getClient() {
        if (retrofit==null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        }
        return retrofit;
    }



    private static Retrofit getRetroClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static APIService getApiService() {
        return getRetroClient().create(APIService.class);
    }
}