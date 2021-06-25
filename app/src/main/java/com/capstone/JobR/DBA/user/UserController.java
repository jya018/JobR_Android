package com.capstone.JobR.DBA.user;

import androidx.appcompat.app.AppCompatActivity;

import com.capstone.JobR.DBA.NullOnEmptyConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UserController extends AppCompatActivity {

    //서버 URL
    private static final String BASE_URL = "http://13.124.129.238:8080/user/";

    //RETROFIT2, GSON 서버통신
    Gson gson = new GsonBuilder().setLenient().create();
    Retrofit retrofitClient = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(new NullOnEmptyConverterFactory())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    //URI매핑 API
    public UserAPI API = retrofitClient.create(UserAPI.class);

}
