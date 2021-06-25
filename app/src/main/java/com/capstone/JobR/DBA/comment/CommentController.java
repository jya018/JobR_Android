package com.capstone.JobR.DBA.comment;

import com.capstone.JobR.DBA.NullOnEmptyConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentController {

    //서버 URL
    private static final String BASE_URL = "http://13.124.129.238:8080/comment/";

    //RETROFIT2, GSON 서버통신
    Gson gson = new GsonBuilder().setLenient().create();
    Retrofit retrofitClient = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(new NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    //URI매핑 API
    public CommentAPI API = retrofitClient.create(CommentAPI.class);

    //댓글을 담을 LIST
    public List<CommentVO> commentList;
    
}
