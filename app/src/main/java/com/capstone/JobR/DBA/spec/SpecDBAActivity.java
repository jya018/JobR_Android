/*

package com.example.jobrserver;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.jobrserver.spec.SpecController;
import com.example.jobrserver.spec.SpecVO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecActivity extends AppCompatActivity{


        static private ArrayAdapter<SpecVO> adapter;
        private SpecController controller = new SpecController();
        List<SpecVO> specList = new ArrayList<>();


    private String ID = "helloGuys";
    private String companyName = "아디다스";
    private int toeic = 900;
    private int toefl = 0;
    private int teps = 0;
    private int opic = 0;
    private int tos = 0;
    private boolean intership = true;
    private String degree = "명지대";
    private float score = 3;


    public SpecActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.board_main);

            ListView listView = findViewById(R.id.listview_mainBoard);
            adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, specList);

 //1. 모든 스펙 출력
*/
/*
            controller.API.all_list().enqueue(new Callback<List<SpecVO>>() {
                @Override
                public void onResponse(Call<List<SpecVO>> call, Response<List<SpecVO>> response) {
                    if (!response.isSuccessful()) {
                        Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    } else {
                        Log.d("연결이 성공적 : ", response.body().toString());

                        for (SpecVO spec : response.body()) {
                            specList.add(spec);
                        }
                        listView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<List<SpecVO>> call, Throwable t) {
                    t.printStackTrace();
                    Log.e("연결실패", t.getMessage());
                }
            });

*//*

///////////////////////////////////////////////////////////////////////////////////////////////////

//2. 스펙 작성




                    //작성한 제목과 내용을 가져오기

                    //밑에 10가지 요소들 Spec에 넣어주기
                    SpecVO spec = new SpecVO(ID, companyName, toeic, toefl,teps,opic,tos,intership,degree,score);

                    //DB 게시글 삽입
                    controller.API.write(spec).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(!response.isSuccessful()){
                                Log.e("연결이 비정상적 : ", "error code : " + response.code());
                                return;
                            }
                            else{
                                Log.d("연결이 성공적 : ", response.body());

                                //다음 화면 연결
                                Intent intent = new Intent(getApplicationContext(),SpecActivity.class);
                                //화면 전환
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "등록되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            t.printStackTrace();
                            Log.e("연결실패", t.getMessage());
                        }
                    });



////////////////////////////////////////////////////////////////////////////////////////////////////////
//3. 상세 스펙 출력

            controller.API.get("helloGuys").enqueue(new Callback<SpecVO>() {
                @Override
                public void onResponse(Call<SpecVO> call, Response<SpecVO> response) {
                    if (!response.isSuccessful()) {
                        Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    } else {
                        Log.d("연결이 성공적 : ", response.body().toString());


                            specList.add(response.body());

                        listView.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<SpecVO> call, Throwable t) {
                    t.printStackTrace();
                    Log.e("연결실패", t.getMessage());
                }
            });



///////////////////////////////////////////////////////////////////////////////////////////////////
//4. 클릭된 스펙 다른페이지(SpecContentActivity)로 가져오기 -> 수정

        companyName = "삼성sds";
        degree = "서울대";

            spec.setCompanyName(companyName);
            spec.setDegree(degree);

            controller.API.modify(ID, spec).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(!response.isSuccessful()){
                        Log.e("연결이 비정상적 : ", "error code : " + response.code());
                        return;
                    }
                    else{
                        Log.d("연결이 성공적 : ", response.body());

                        //다음 화면 연결
                        Intent intent = new Intent(getApplicationContext(), SpecActivity.class);
                        //화면 전환
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "수정되었습니다..", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();
                    Log.e("연결실패", t.getMessage());
                }
            });


//////////////////////////////////////////////////////////////////////////////////////////////////////////

// 5. Spec 테이블 삭제





                controller.API.delete(ID).enqueue(new Callback<String>() {
                    public void onResponse(Call<String> call, Response<String> response) {
                        specList.remove(ID);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        //화면 전환
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        t.printStackTrace();
                        Log.e("연결실패", t.getMessage());
                    }
                });

        }


}
*/
