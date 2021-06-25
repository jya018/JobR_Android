package com.capstone.JobR.category.company;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.JobR.DBA.company.CompanyController;
import com.capstone.JobR.DBA.company.CompanyVO;
import com.capstone.JobR.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapSdk;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.MarkerIcons;


import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyActivity extends AppCompatActivity implements OnMapReadyCallback {
    private String companyName;

    private TextView title;      // 회사이름
    private TextView compName;      // 회사이름
    private TextView compSort;    // 회사계열사
    private TextView compCeo;     // 회사대표 이름
    private TextView compEst;     // 회사 설립일
    private TextView compType;    // 회사 업종
    private TextView compIpo;      // 회사 상장일
    private TextView compSales;   // 회사 매출액
    private TextView compEmp;     // 회사 직원수
    private TextView compAvgSal;  // 회사 평균연봉
    private TextView compPhNum;   // 회사 번호
    private TextView compLoc;     // 회사 주소

    private MapView mapView;
    private static NaverMap naverMap;

    private  Address addr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        companyName = getIntent().getStringExtra("companyName");

        title = findViewById(R.id.title);
        compName = findViewById(R.id.compName);
        compSort = findViewById(R.id.compSort);
        compCeo = findViewById(R.id.compCeo);
        compEst = findViewById(R.id.compEst);
        compType = findViewById(R.id.compType);
        compIpo = findViewById(R.id.compIpo);
        compSales = findViewById(R.id.compSales);
        compEmp = findViewById(R.id.compEmp);
        compAvgSal = findViewById(R.id.compAvgSal);
        compPhNum = findViewById(R.id.compPhNum);
        compLoc = findViewById(R.id.compLoc);

        //서버 통신 : 기업정보 상세 조회
        init(companyName);

        //네이버 클라이언트 ID
        NaverMapSdk.getInstance(this).setClient(new NaverMapSdk.NaverCloudPlatformClient("gvmdodoco1"));

        //네이버 지도 생성
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(@NonNull final NaverMap naverMap) {

        this.naverMap = naverMap;

        //네이버지도 시작위치 위도,경도 설정
        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(addr.getLatitude(), addr.getLongitude()), 15); // 위치 지정
        naverMap.setCameraPosition(cameraPosition);

        //마커 생성 및 위치설정
        Marker marker = new Marker();
        marker.setPosition(new LatLng(addr.getLatitude(), addr.getLongitude()));
        marker.setMap(naverMap);

        //마커 색상설정
        marker.setIcon(MarkerIcons.BLACK);
        marker.setIconTintColor(Color.parseColor("#FF6600"));


    }

    //서버 통신 : 기업정보 상세 조회
    private void init(String companyName) {
        CompanyController controller = new CompanyController();
        controller.API.content(companyName).enqueue(new Callback<CompanyVO>() {
            @Override
            public void onResponse(Call<CompanyVO> call, Response<CompanyVO> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                } else {
                    Log.d("연결이 성공적 : ", response.body().toString());

                    //기업 정보 연결
                    title.setText(response.body().getCompName()+" 기업정보");
                    compName.setText(response.body().getCompName());
                    compSort.setText(response.body().getCompSort());
                    compCeo.setText(response.body().getCompCeo());
                    compEst.setText(response.body().getCompEst());
                    compType.setText(response.body().getCompType());
                    compIpo.setText(response.body().getCompIpo());
                    compSales.setText(response.body().getCompSales());
                    compEmp.setText(response.body().getCompEmp());
                    compAvgSal.setText(response.body().getCompAvgSal());
                    compPhNum.setText(response.body().getCompPhNum());
                    compLoc.setText(response.body().getCompLoc());

                    //네이버 맵 설정
                    final Geocoder geocoder = new Geocoder(getApplicationContext());
                    String location = response.body().getCompLoc(); // 주소데이터 받아오기
                    List<Address> list = null;
                    try {
                        list = geocoder.getFromLocationName
                                (location, 15); // 지역 이름 // 읽을 개수
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (list != null) {
                        if (list.size() == 0) {
                            Toast.makeText(getApplicationContext(), "해당되는 주소 정보를 찾지 못했습니다.", Toast.LENGTH_LONG).show();
                        } else {
                            addr = list.get(0);
                            addr.getLatitude(); // String value에 대한 위도값
                            addr.getLongitude(); // String value에 대한 경도값
                        }
                    }
                    //네이버 맵 ASync
                    mapView.getMapAsync(CompanyActivity.this);
                }
            }
            @Override
            public void onFailure(Call<CompanyVO> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });
    }
}