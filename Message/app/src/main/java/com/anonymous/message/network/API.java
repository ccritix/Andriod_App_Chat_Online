package com.anonymous.message.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {
    public static UserAPI getUserAPI(){


        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:8080/api/").addConverterFactory(GsonConverterFactory.create()).build();


        //Retrofit retrofit = new Retrofit.Builder().baseUrl("https://hieu4567cn.000webhostapp.com/server/").addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(UserAPI.class);
    }
}
