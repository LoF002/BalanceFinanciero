package com.example.balancefinanciero.service;

import com.example.balancefinanciero.modelo.Dog;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitAPIService {

    @GET("random")
    Call<Dog> getDog();

}//Fin clase RetrofitAPIService
