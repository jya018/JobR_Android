package com.capstone.JobR.category.spec;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.JobR.BoardWriteActivity;
import com.capstone.JobR.DBA.spec.SpecController;
import com.capstone.JobR.DBA.spec.SpecVO;
import com.capstone.JobR.R;
import com.capstone.JobR.UserInfo;
import com.capstone.JobR.view.spec.SpecAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//취업선배 스펙
public class SpecFragment extends Fragment {

    private  View view;
    private RecyclerView recyclerView;
    private SpecAdapter adapter;
    private ImageButton btn_Write;

    UserInfo user;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_spec, container, false);

        //전역 유저 설정
        user = (UserInfo) getActivity().getApplication();

        //화면 띄우기
        recyclerView = view.findViewById(R.id.recyclerView);

        //매니저 설정
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //어댑터 설정
        adapter = new SpecAdapter();
        recyclerView.setAdapter(adapter);

        //서버에서 데이터 가져오기
        init();

        //게시글 작성 버튼
        btn_Write = view.findViewById(R.id.btn_write);

        btn_Write.setOnClickListener(view -> {
            if(adapter.containItem(user.getUserVO().getId())){
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("작성").setMessage("작성하신 스펙이 이미 존재합니다.\n하나 더 작성하시겠습니까?");
                builder.setPositiveButton("아니오", (dialog, id) -> {
                });
                builder.setNegativeButton("예", (dialog, id) -> {
                    //스펙 작성
                    write();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }else {
                //스펙 작성
                write();
            }
        });

        return view;
    }

    //서버에서 데이터 가져오기
    private void init(){
        SpecController controller = new SpecController();

        //서버 통신 : 모든 스펙 정보 출력
        controller.API.all_list().enqueue(new Callback<List<SpecVO>>() {
            @Override
            public void onResponse(Call<List<SpecVO>> call, Response<List<SpecVO>> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                } else {
                    Log.d("연결이 성공적 : ", response.body().toString());
                    for (SpecVO spec : response.body()) {
                        adapter.addItem(spec.getCompanyName(), spec.getId());
                    }
                    //어댑터 값 변경 알림
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<SpecVO>> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });
    }

    public void write(){
        if(user.getUserVO().getJobSort().equals("회사원")) {
            //다음 화면 연결: 게시글 목록 -> 스펙 작성
            Intent intent = new Intent(getContext(), SpecWriteActivity.class);
            //다음 화면에 작성자 정보 넘겨주기
            intent.putExtra("id", user.getUserVO().getId());
            //화면 전환
            startActivity(intent);
            Toast.makeText(getContext(), "스펙을 작성합니다.", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "현재 회사원이 아니십니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
