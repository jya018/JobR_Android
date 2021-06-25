package com.capstone.JobR.DBA.board;

import com.capstone.JobR.DBA.NullOnEmptyConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BoardController {

    //서버 URL
    private static final String BASE_URL = "http://13.124.129.238:8080/board/";

    //RETROFIT2, GSON 서버통신
    Gson gson = new GsonBuilder().setLenient().create();
    Retrofit retrofitClient = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(new NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    //URI매핑 API
    public BoardAPI API = retrofitClient.create(BoardAPI.class);

    //게시글을 담을 LIST
    public List<BoardVO> boardList;

    // 특정 게시글 목록 출력 : 작동안됨
    /*public List<BoardVO> list(String boardSort){
        API.list(boardSort).enqueue(new Callback<List<BoardVO>>() {
            @Override
            public void onResponse(Call<List<BoardVO>> call, Response<List<BoardVO>> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                }
                else {
                    Log.d("연결이 성공적 : ", response.body().toString());
                    boardList = response.body();
                }
            }
            @Override
            public void onFailure(Call<List<BoardVO>> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });
        return boardList;
    }*/
}
