package com.capstone.JobR.category.spec;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.JobR.DBA.spec.SpecController;
import com.capstone.JobR.DBA.spec.SpecVO;
import com.capstone.JobR.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpecWriteActivity extends AppCompatActivity {

    private TextView company,toeic,toefl, teps, opic, tos, degree, score;
    private RadioButton internTrue, internFalse;
    private Button submit;
    private Spinner spinner;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spec_write);

        //작성자 ID
        id = getIntent().getStringExtra(id);

        //View 설정
        company = findViewById(R.id.company);
        toeic = findViewById(R.id.toeic);
        toefl = findViewById(R.id.toefl);
        teps = findViewById(R.id.teps);
        opic = findViewById(R.id.opic);
        tos = findViewById(R.id.tos);
        internTrue = findViewById(R.id.internbutton1);
        internFalse = findViewById(R.id.internbutton2);
        degree = findViewById(R.id.degree);
        score = findViewById(R.id.score);
        submit = findViewById(R.id.submit);
        spinner = findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!parent.getSelectedItem().toString().equals("직접입력")) {
                    company.setText(parent.getSelectedItem().toString());
                    company.setEnabled(false);
                }
                else{
                    company.setText("");
                    company.setEnabled(true);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //작성 버튼 클릭
        submit.setOnClickListener(v -> {
            if(!company.getText().equals("")) {
                SpecController controller = new SpecController();
                boolean internship = internTrue.isChecked() ? true : false;
                SpecVO spec = new SpecVO(
                        id,
                        company.getText().toString(),
                        toeic.getText().toString(),
                        toefl.getText().toString(),
                        teps.getText().toString(),
                        opic.getText().toString(),
                        tos.getText().toString(),
                        internship,
                        degree.getText().toString(),
                        score.getText().toString());
                //로딩 창 생성
                ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("작성 중...");
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
                //로딩 시작
                progressDialog.show();
                //서버 통신 : 스펙 수정
                controller.API.write(spec).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (!response.isSuccessful()) {
                            Log.e("연결이 비정상적 : ", "error code : " + response.code());
                            Toast.makeText(getApplicationContext(), "오류발생!", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("연결이 성공적 : ", response.body());
                            Toast.makeText(getApplicationContext(), "작성되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                        //로딩 종료
                        progressDialog.dismiss();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        //로딩 종료
                        progressDialog.dismiss();
                        t.printStackTrace();
                        Log.e("연결실패", t.getMessage());
                        Toast.makeText(getApplicationContext(), "오류발생!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else{
                Toast.makeText(getApplicationContext(), "회사명을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}