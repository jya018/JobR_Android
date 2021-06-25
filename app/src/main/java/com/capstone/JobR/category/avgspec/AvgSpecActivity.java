package com.capstone.JobR.category.avgspec;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.capstone.JobR.DBA.spec.SpecController;
import com.capstone.JobR.DBA.spec.SpecVO;
import com.capstone.JobR.R;
import com.capstone.JobR.view.avgSpec.AvgSpecChartAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvgSpecActivity extends AppCompatActivity {
    //서버 통신용
    private String companyName;
    private SpecController controller = new SpecController();

    //평균 스펙
    private float[] avgSpec;

    //스펙마다 List할당
    private ArrayList<Integer> toeicList= new ArrayList<>(), toeflList= new ArrayList<>(),
            tepsList= new ArrayList<>(), opicList= new ArrayList<>(), tosList= new ArrayList<>();
    private ArrayList<Float> scoreList= new ArrayList<>();
    private int internship;

    //스펙마다 점수분포 기준
    private String[] toeic = new String[]{"950","900","850","800","750","700","700 미만"};
    private String[] toefl = new String[]{"110","100","90","80","70미만"};
    private String[] teps = new String[]{"550","500","450","400","350미만"};
    private String[] opic = new String[]{"AL","IH","IM3","IM2","IM1","IM1미만"};
    private String[] tos = new String[]{"190","180","170","160","150","140","130","130미만"};
    private String[] score = new String[]{"4","3.5","3","2.5","2.5미만"};
    //인턴쉽

    //스펙마다 ChartView 생성
    private RecyclerView recyclerView;
    private AvgSpecChartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avgspec);

        companyName = getIntent().getStringExtra("companyName");

        if (savedInstanceState == null) {
            //서버로부터 데이터 가져오기
            getData(companyName);

            //화면 띄우기
            recyclerView = findViewById(R.id.recyclerView);

            //매니저 설정
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            //어댑터 설정
            adapter = new AvgSpecChartAdapter();
            recyclerView.setAdapter(adapter);
        }

    }

    //서버로부터 데이터 가져오기
    private void getData(String companyName){
        //로딩 창 생성
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("데이터 불러오는 중...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
        //로딩 시작
        progressDialog.show();
        controller.API.companySpec(companyName).enqueue(new Callback<List<SpecVO>>() {
//        controller.API.all_list().enqueue(new Callback<List<SpecVO>>() {
            @Override
            public void onResponse(Call<List<SpecVO>> call, Response<List<SpecVO>> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                } else {
                    Log.d("연결이 성공적 : ", response.body().toString());

                    //모든 스펙 평균계산 , List 할당
                    getAvgSpec(response.body());
                }
                //view 시작
                adapter.addItem("TOEIC",avgSpec[0],toeicList, toeic);
                adapter.addItem("TOEFL",avgSpec[1],toeflList, toefl);
                adapter.addItem("TEPS",avgSpec[2],tepsList, teps);
                adapter.addItem("OPIC",avgSpec[3],opicList, opic);
                adapter.addItem("TOEIC SPEAKING",avgSpec[4],tosList, tos);
//                adapter.addItem("SCORE",avgSpec[0],scoreList,score);
//                adapter.addItem("인턴쉽")
                adapter.notifyDataSetChanged();

                //로딩 종료
                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<List<SpecVO>> call, Throwable t) {
                //로딩 종료
                progressDialog.dismiss();
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });
    }

    //모든 스펙 평균계산, List 할당
    private void getAvgSpec(List<SpecVO> specList){
        //합계 변수
        int toeic=0, toefl=0, teps=0, opic=0, tos=0;
        float score=0;

        //스펙마다 합계, List 할당
        for(SpecVO spec : specList){
            if(spec.getToeic() != 0) {
                toeic += spec.getToeic();
                toeicList.add(spec.getToeic());
            }
            if(spec.getToefl() != 0) {
                toefl += spec.getToefl();
                toeflList.add(spec.getToefl());
            }
            if(spec.getTeps() != 0) {
                teps += spec.getTeps();
                tepsList.add(spec.getTeps());
            }
            if(spec.getOpic() != 0) {
                opic += spec.getOpic();
                opicList.add(spec.getOpic());
            }
            if(spec.getTos() != 0) {
                tos += spec.getTos();
                tosList.add(spec.getTos());
            }
            if(spec.isIntership())
                internship++;
            if(spec.getScore() != 0) {
                score += spec.getScore();
                scoreList.add(spec.getScore());
            }
        }

        //스펙중에 점수가 아예 없을 시, "0"추가
        if(toeicList.size() == 0) {
            toeicList.add(0);
        }
        if(toeflList.size() == 0) {
            toeflList.add(0);
        }
        if(tepsList.size() == 0) {
            tepsList.add(0);
        }
        if(opicList.size() == 0) {
            opicList.add(0);
        }
        if(tosList.size() == 0) {
            tosList.add(0);
        }
        if(scoreList.size() == 0) {
            scoreList.add((float)0);
        }
        if(specList.size() == 0) {
            specList.add(new SpecVO());
        }

        //평균
        avgSpec = new float[]{toeic/toeicList.size(), toefl/toeflList.size(),
                teps/tepsList.size(),opic/opicList.size(),tos/tosList.size(),
                internship/specList.size(), score/scoreList.size()};
    }
}