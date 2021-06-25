package com.capstone.JobR.DBA.good;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GoodActivity extends AppCompatActivity {

    static private ArrayAdapter<GoodVO> adapter;
    private GoodController controller = new GoodController();
    List<GoodVO> goodList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 특정 회원 좋아요 게시글 조회
        controller.API.selectId("shintu96").enqueue(new Callback<List<GoodVO>>() {
            @Override
            public void onResponse(Call<List<GoodVO>> call, Response<List<GoodVO>> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                } else {
                    Log.d("연결이 성공적 : ", response.body().toString());
                    for (GoodVO board : response.body()) {
                        goodList.add(board);
                    }
                    //setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<GoodVO>> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });

        // 특정 게시글 좋아요 누름
        int boardNum = 120;
        String id = "shintu96";

        GoodVO good = new GoodVO(boardNum,id);

        controller.API.insert(good).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                } else {
                    Log.d("연결이 성공적 : ", response.body());
                    Toast.makeText(getApplicationContext(), "해당 게시글 좋아요 완료!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });

        // 특정 게시글 좋아요 취소
        controller.API.delete(good).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                else{
                    Log.d("연결이 성공적 : ", response.body());
                    Toast.makeText(getApplicationContext(), "해당 게시글 좋아요 취소.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });

        // 회원 탈퇴 시, 좋아요 모두 삭제 : 작동 X
        /*controller.API.deleteId(id).enqueue(new Callback<String>() {
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                else{
                    Log.d("연결이 성공적 : ", response.body());
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });*/
    }
}
