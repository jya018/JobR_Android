package com.capstone.JobR.category.avgspec;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.JobR.DBA.spec.SpecController;
import com.capstone.JobR.R;
import com.capstone.JobR.view.avgSpec.AvgSpecAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//평균스펙 제공
public class AvgSpecFragment extends Fragment {

    private  View view;
    private RecyclerView recyclerView;
    private AvgSpecAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_avgspec, container, false);

        //화면 띄우기
        recyclerView = view.findViewById(R.id.recyclerView);

        //매니저 설정
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //어댑터 설정
        adapter = new AvgSpecAdapter();
        recyclerView.setAdapter(adapter);

        //서버에서 데이터 가져오기
        init();

        return view;
    }

    private void init(){
        SpecController controller = new SpecController();

        //서버 통신 : 모든 스펙 목록
        controller.API.companyList().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                }
                else {
                    Log.d("연결이 성공적 : ", response.body().toString());
                    for (String companyName : response.body()) {
                        adapter.addItem(companyName);
                    }
                    //어댑터 값 변경 알림
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });
    }
}