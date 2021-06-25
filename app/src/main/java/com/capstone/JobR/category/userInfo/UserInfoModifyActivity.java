package com.capstone.JobR.category.userInfo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.capstone.JobR.DBA.user.UserController;
import com.capstone.JobR.DBA.user.UserVO;
import com.capstone.JobR.LoginActivity;
import com.capstone.JobR.R;
import com.capstone.JobR.UserInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoModifyActivity extends AppCompatActivity {

    private EditText nicknameContent;
    private Spinner jobSortSpinner;
    private TextView idContent, emailContent;
    private Button reviseSubmit;


    private UserController userController = new UserController();
    private UserInfo user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo_modify);

        //전역 유저 가져오기
        user = (UserInfo) getApplication();

        //View 설정
        idContent = findViewById(R.id.idContent);
        emailContent = findViewById(R.id.emailContent);
        nicknameContent = findViewById(R.id.nicknameContent);
        jobSortSpinner = findViewById(R.id.jobSortSpinner);

        //받아온 정보 설정
        idContent.setText(user.getUserVO().getId());
        nicknameContent.setText(user.getUserVO().getNickname());
        emailContent.setText(user.getUserVO().getEmail());

        reviseSubmit = findViewById(R.id.reviseSubmit);
        // 버튼 이벤트 추가
        reviseSubmit.setOnClickListener(view -> {
            //작성한 제목과 내용을 가져오기
            UserVO newUser = new UserVO(user.getUserVO().getId(), emailContent.getText().toString(), nicknameContent.getText().toString(), jobSortSpinner.getSelectedItem().toString());

            //서버 통신 : 게시글 수정
            userController.API.update(newUser, user.getUserVO().getId()).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(!response.isSuccessful()){
                        Log.e("연결이 비정상적 : ", "error code : " + response.code());
                        Toast.makeText(getApplicationContext(), "수정 실패...", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Log.d("연결이 성공적 : ", response.body());
                        Toast.makeText(getApplicationContext(), "수정 성공!", Toast.LENGTH_SHORT).show();
                        user.setUserVO(newUser);
                        finish();
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();
                    Log.e("연결실패", t.getMessage());
                    Toast.makeText(getApplicationContext(), "수정 실패...", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}