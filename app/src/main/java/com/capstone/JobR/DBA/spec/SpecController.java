package com.capstone.JobR.DBA.spec;

import com.capstone.JobR.DBA.NullOnEmptyConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpecController {

    //서버 URL
    private static final String BASE_URL = "http://13.124.129.238:8080/spec/";

    //RETROFIT2, GSON 서버통신
    Gson gson = new GsonBuilder().setLenient().create();
    Retrofit retrofitClient = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(new NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    //URI매핑 API
    public SpecAPI API = retrofitClient.create(SpecAPI.class);
}
