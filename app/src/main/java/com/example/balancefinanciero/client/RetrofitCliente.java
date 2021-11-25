package com.example.balancefinanciero.client;

import com.example.balancefinanciero.service.RetrofitAPIService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCliente {

    public static final String URL_BASE = "";
    public static Retrofit retrofit;

    public static RetrofitAPIService getApiService(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }//Fin if
        return retrofit.create(RetrofitAPIService.class);
    }//Fin getApiService

}//Fin clase RetrofitCliente
