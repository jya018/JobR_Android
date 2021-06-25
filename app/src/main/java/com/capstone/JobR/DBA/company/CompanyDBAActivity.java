package com.capstone.JobR.DBA.company;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CompanyDBAActivity extends AppCompatActivity {

    static private ArrayAdapter<CompanyVO> adapter;
    private CompanyController controller = new CompanyController();
    List<CompanyVO> companyList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//1. 기업정보 전체 게시글 출력

       /*controller.API.list().enqueue(new Callback<List<CompanyVO>>() {
            @Override
            public void onResponse(Call<List<CompanyVO>> call, Response<List<CompanyVO>> response) {
            if (!response.isSuccessful()) {
                     Log.e("연결이 비정상적 : ", "error code : " + response.code());
             } else {
                     Log.d("연결이 성공적 : ", response.body().toString());

                     for (CompanyVO company : response.body()) {
                               companyList.add(company);
                     }
              listView.setAdapter(adapter);
              }
             }

             @Override
             public void onFailure(Call<List<CompanyVO>> call, Throwable t) {
             t.printStackTrace();
             Log.e("연결실패", t.getMessage());
        }
     });*/


//////////////////////////////////////////////////////////////////////////////////////////////////


//2. 기업정보 게시판 게시글 작성

       /* String compName = "병욱이짱";    // 회사이름
        String compSort = "a";    // 회사계열사
        String compCeo = "b";     // 회사대표 이름
        String compEst = "1999-02-05";    // 회사 설립일
        String compType = "c";    // 회사 분류
        String compIpo = "1996-05-23";        // 회사 상장일
        String compSales = "d";   // 회사 매출
        String compEmp = "e";     // 회사 직원수
        String compAvgSal = "f";  // 회사 평균연봉
        String compPhNum = "g";   // 회사 번호
        String compLoc = "h";     // 회사 주소


        CompanyVO company = new CompanyVO(compName, compSort, compCeo, compEst, compType, compIpo, compSales, compEmp, compAvgSal, compPhNum, compLoc);

        //DB 게시글 삽입
        controller.API.write(company).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                } else {
                    Log.d("연결이 성공적 : ", response.body());

                    //다음 화면 연결
                    Intent intent = new Intent(getApplicationContext(), CompanyActivity.class);
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
        });*/


/////////////////////////////////////////////////////////////////////////////////////////////////

//3. 기업정보 게시글 상세조회
       /*controller.API.content("병욱이짱").enqueue(new Callback<CompanyVO>() {
            @Override
            public void onResponse(Call<CompanyVO> call, Response<CompanyVO> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                } else {
                    Log.d("연결이 성공적 : ", response.body().toString());

                        companyList.add(response.body());

                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<CompanyVO> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });*/
//////////////////////////////////////////////////////////////////////////////////////////////////
//4. 기업정보 게시글 수정


      /*  compSort = "계열계열";    // 회사계열사
        compCeo = "재엽이짱";     // 회사대표 이름
        compEst = "2021-05-11"; 	// 회사 설립일

        company.setCompSort(compSort);
        company.setCompCeo(compCeo);
        company.setCompEst(compEst);

        controller.API.modify(compName, company).enqueue(new Callback<String>() {
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
        });*/

////////////////////////////////////////////////////////////////////////////////////////////////////

        // 5. 기업정보 게시글 삭제

            /*controller.API.delete("병욱이짱").enqueue(new Callback<CompanyVO>() {
                public void onResponse(Call<CompanyVO> call, Response<CompanyVO> response) {
                    companyList.remove("한병욱");
                    Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<CompanyVO> call, Throwable t) {
                    t.printStackTrace();
                    Log.e("연결실패", t.getMessage());
                }
            });*/


    }
}
