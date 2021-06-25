package com.capstone.JobR;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.capstone.JobR.DBA.user.UserController;
import com.capstone.JobR.DBA.user.UserVO;
import com.royrodriguez.transitionbutton.TransitionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText userid, userpawd;
    private TransitionButton btn_login;
    private Button btn_register;
    UserInfo userStatic;
    private InputMethodManager manager;

    private boolean isSuccessful = false;

    private UserController controller = new UserController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //전역 유저 설정
        userStatic = (UserInfo) getApplication();

        //컴포넌트 초기화
        userid = findViewById(R.id.et_id);
        userpawd = findViewById(R.id.et_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        //키보드 제어
        manager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        // 버튼 이벤트 추가
        btn_login.setOnClickListener(view -> {
            String id = userid.getText().toString();
            String password = userpawd.getText().toString();

            if(id.equals("")||password.equals("")){
                //아무것도 입력안함
                Toast.makeText(getApplicationContext(), "회원정보를 입력해주세요!", Toast.LENGTH_SHORT).show();
            }
            else {
                //키보드 내리기
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                //로그인 시작
                login(id, password);
                btn_login.startAnimation();
            }
        });

        btn_register.setOnClickListener(view -> {
            //다음 화면 연결: 회원가입 클릭 -> 회원가입 화면
            Intent intent = new Intent(getApplicationContext(), RegisterButtonPage.class);
            startActivity(intent);
        });
    }

    //로그인 시작
    public void login(String id, String password){
        UserVO user = new UserVO(password);
        //서버 통신 시작
        controller.API.login(user, id).enqueue(new Callback<UserVO>() {
            public void onResponse(Call<UserVO> call, Response<UserVO> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    Toast.makeText(getApplicationContext(), "오류발생!", Toast.LENGTH_SHORT).show();
                } else {
                    //연결 성공
                    //성공
                    if(response.body() != null) isSuccessful = true;
                    //fail - 실패
                    else {
                        isSuccessful = false;
                        Toast.makeText(getApplicationContext(), "존재하지 않는 계정입니다!", Toast.LENGTH_SHORT).show();
                    }

                    //로그인 결과 확인
                    if (isSuccessful) {
                        btn_login.stopAnimation(TransitionButton.StopAnimationStyle.EXPAND, new TransitionButton.OnAnimationStopEndListener() {
                            @Override
                            public void onAnimationStopEnd() {
                                //전역 유저 설정
                                userStatic.setUserVO(response.body());
                                //다음 화면 연결: 로그인 클릭 -> 메인페이지
                                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "로그인되었습니다!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        btn_login.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);
                    }
                }
            }
            @Override
            public void onFailure(Call<UserVO> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
                btn_login.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);
                Toast.makeText(getApplicationContext(), "오류가 발생했습니다!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
