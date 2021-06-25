package com.capstone.JobR.category.company;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.JobR.DBA.company.CompanyController;
import com.capstone.JobR.DBA.company.CompanyVO;
import com.capstone.JobR.R;
import com.capstone.JobR.view.company.CompanyAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//기업 정보
public class CompanyFragment extends Fragment {

    private  View view;
    private RecyclerView recyclerView;
    private CompanyAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_company, container, false);

        //화면 띄우기
        recyclerView = view.findViewById(R.id.recyclerView);

        //매니저 설정
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //어댑터 설정
        adapter = new CompanyAdapter();
        recyclerView.setAdapter(adapter);

        //서버에서 데이터 가져오기
        init();

        return view;
    }

    //서버에서 데이터 가져오기
    private void init(){
        CompanyController controller = new CompanyController();

        //서버 통신 : 모든 기업정보 출력
        controller.API.list().enqueue(new Callback<List<CompanyVO>>() {
            @Override
            public void onResponse(Call<List<CompanyVO>> call, Response<List<CompanyVO>> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                } else {
                    Log.d("연결이 성공적 : ", response.body().toString());

                    for (CompanyVO company : response.body()) {
                        adapter.addItem(company.getCompName());
                    }
                    //어댑터 값 변경 알림
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<CompanyVO>> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });
    }
}
