package com.capstone.JobR.category.userInfo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.capstone.JobR.DBA.user.UserController;
import com.capstone.JobR.LoginActivity;
import com.capstone.JobR.R;
import com.capstone.JobR.UserInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoFragment extends Fragment {

    private TextView idContent, nicknameContent, emailContent, jobSortContent;
    private Button logoutButton, userReviseButton, userDeleteButton;

    private UserInfo user;

    private UserController userController = new UserController();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_userinfo, container, false);


        //각 textview의 키 값 배정
        idContent = root.findViewById(R.id.idContent);
        nicknameContent = root.findViewById(R.id.nicknameContent);
        emailContent = root.findViewById(R.id.emailContent);
        jobSortContent = root.findViewById(R.id.jobSortContent);
        userReviseButton = root.findViewById(R.id.userReviseButton);
        userDeleteButton = root.findViewById(R.id.userDeleteButton);
        logoutButton = root.findViewById(R.id.logoutButton);

        //로그아웃
        logoutButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("로그아웃").setMessage("정말로 로그아웃 하시겠습니까?");
            builder.setNegativeButton("예", (dialog, id) -> {
                //로그아웃 실행
                user.setUserVO(null);
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent);
            });
            builder.setPositiveButton("아니오", (dialog, id) -> {
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        //회원정보 수정
        userReviseButton.setOnClickListener(view -> {
            //다음 화면 연결: 게시글 목록 -> 게시글 쓰기
            Intent intent = new Intent(getContext(), UserInfoModifyActivity.class);
            //화면 전환
            startActivity(intent);
            Toast.makeText(getContext(), "회원정보를 수정합니다.", Toast.LENGTH_SHORT).show();
        });

        //회원정보 삭제
        userDeleteButton.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            //화면 전환
            startActivity(intent);
        });

        //회원정보 삭제
        userDeleteButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("JobR 회원탈퇴").setMessage("정말로 회원탈퇴를 하시겠습니까?");
            builder.setPositiveButton("예", (dialog, id) -> {
                //삭제 기능 구현
                deleteUserInfo(user.getUserVO().getId());
            });
            builder.setNegativeButton("아니오", (dialog, id) -> {
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        //전역 유저 가져오기
        user = (UserInfo) getActivity().getApplication();

        //view 설정
        idContent.setText(user.getUserVO().getId());
        nicknameContent.setText(user.getUserVO().getNickname());
        emailContent.setText(user.getUserVO().getEmail());
        jobSortContent.setText(user.getUserVO().getJobSort());

    }

    //서버 통신 : 회원정보 삭제
    private void deleteUserInfo(String id) {
        userController.API.delete(id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                } else {
                    Log.d("연결이 성공적 : ", response.body());
                    //다음 화면 연결: 회원정보 내용 -> 로그인 화면
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    //화면 전환
                    startActivity(intent);
                    Toast.makeText(getContext(), "회원탈퇴 되었습니다.", Toast.LENGTH_SHORT).show();
                    // 해당 회원정보에 내용(ID, email, password, nickname, jobSort 모두 삭제 (DB에서 자동으로 실행)
                    userController.finish();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });
    }

}