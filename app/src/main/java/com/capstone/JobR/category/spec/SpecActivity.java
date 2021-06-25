package com.capstone.JobR.category.spec;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.JobR.BoardModifyActivity;
import com.capstone.JobR.DBA.spec.SpecController;
import com.capstone.JobR.DBA.spec.SpecVO;
import com.capstone.JobR.R;
import com.capstone.JobR.UserInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecActivity extends AppCompatActivity {

    private TextView company,toeic,toefl, teps, opic, tos, internship, degree, score;
    private Button contentRevise, contentDelete;
    private String id;

    private SpecVO spec;
    private SpecController controller = new SpecController();

    //전역 유저 설정
    private UserInfo user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spec);

        //전역 유저 설정
        user = (UserInfo) getApplication();

        id = getIntent().getStringExtra("id");

        //View 설정
        company = findViewById(R.id.company);
        toeic = findViewById(R.id.toeic);
        toefl = findViewById(R.id.toefl);
        teps = findViewById(R.id.teps);
        opic = findViewById(R.id.opic);
        tos = findViewById(R.id.tos);
        internship = findViewById(R.id.internship);
        degree = findViewById(R.id.degree);
        score = findViewById(R.id.score);
        contentRevise = findViewById(R.id.contentRevise);
        contentDelete = findViewById(R.id.contentDelete);

        //스펙 수정
        contentRevise.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SpecModifyActivity.class);
            intent.putExtra("spec",spec);
            startActivity(intent);
        });

        //스펙 삭제
        contentDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("삭제").setMessage("정말로 삭제 하시겠습니까?");
            builder.setPositiveButton("예", (dialog, id) -> {
                //삭제 기능 구현
                delete();
            });
            builder.setNegativeButton("아니오", (dialog, id) -> {
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //서버 통신 : 스펙 상세조회
        init(id);
    }

    //서버 통신 : 스펙 상세조회
    private void init(String id) {
        //로딩 창 생성
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("데이터 불러오는 중...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
        //로딩 시작
        progressDialog.show();
        controller.API.get(id).enqueue(new Callback<SpecVO>() {
            @Override
            public void onResponse(Call<SpecVO> call, Response<SpecVO> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    Toast.makeText(getApplicationContext(), "오류 발생!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("연결이 성공적 : ", response.body().toString());
                    spec = response.body();

                    //스펙 설정
                    company.setText(String.valueOf(spec.getCompanyName()));
                    toeic.setText(String.valueOf(spec.getToeic()));
                    toefl.setText(String.valueOf(spec.getToefl()));
                    teps.setText(String.valueOf(spec.getTeps()));
                    opic.setText(String.valueOf(spec.getOpic()));
                    tos.setText(String.valueOf(spec.getTos()));
                    internship.setText(spec.isIntership() ? "O" : "X" );
                    degree.setText(spec.getDegree());
                    score.setText(String.valueOf(spec.getScore()));

                    //본인 스펙일 경우 : 수정, 삭제 버튼 보이기
                    if(spec.getId().equals(user.getUserVO().getId())){
                        contentRevise.setVisibility(View.VISIBLE);
                        contentDelete.setVisibility(View.VISIBLE);
                    }
                }
                //로딩 종료
                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<SpecVO> call, Throwable t) {
                t.printStackTrace();
                //로딩 종료
                progressDialog.dismiss();
                Log.e("연결실패", t.getMessage());
                Toast.makeText(getApplicationContext(), "오류 발생!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //서버 통신 : 스펙 삭제
    private void delete() {
        //로딩 창 생성
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("삭제 중...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
        //로딩 시작
        progressDialog.show();
        controller.API.delete(id).enqueue(new Callback<String>() {
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    Toast.makeText(getApplicationContext(), "오류 발생!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("연결이 성공적 : ", response.body());
                    Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                }
                //로딩 종료
                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //로딩 종료
                progressDialog.dismiss();
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
                Toast.makeText(getApplicationContext(), "오류 발생!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}