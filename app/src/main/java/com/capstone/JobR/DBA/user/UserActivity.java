package com.capstone.JobR.DBA.user;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.capstone.JobR.R;

public class UserActivity extends AppCompatActivity {

    private UserController controller = new UserController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        //1. 특정 id 중복확인(내용 가져오기) 중복일 경우: fail, 중복이 아니면 success

        /*controller.API.checkId("sfdsafeasd").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                } else {
                    Log.d("연결이 성공적 : check", response.body());

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });*/

///////////////////////////////////////////////////////////////////////////////////////////////////

//2. 회원가입

        /*String id = "testID";
        String password = "testPassword";
        String email = "testtest@naver.com";
        String nickname = "테스트닉네임";
        String jobSort = "취준생";

        UserVO user = new UserVO(id, password, email, nickname, jobSort);

        //DB 게시글 삽입
        controller.API.regsiter(user).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                } else {
                    Log.d("연결이 성공적 : insert", response.body());
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });
*/

///////////////////////////////////////////////////////////////////////////////////////////////////
//3. 비밀번호 변경

        /*String id = "testID";
        String password = "UpdatePassword";

        UserVO user = new UserVO(password);
        user.setPassword(password);

        //DB 게시글 삽입
        controller.API.updatePw(user, id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                } else {
                    Log.d("연결이 성공적 : updatePw", response.body());
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });*/


///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//4. 회원 정보 변경

        /*String id = "testID";
        String nickname = "수정닉네임";
        String jobSort = "회사원";

        UserVO user  = new UserVO(nickname,jobSort);

        controller.API.update(user,id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                } else {
                    Log.d("연결이 성공적 : update", response.body());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });*/

/////////////////////////////////////////////////////////////////////////////////////////////////////
//5. 회원 탈퇴

        /*String Id = "testID";

        controller.API.delete(Id).enqueue(new Callback<String>() {
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                } else {
                    Log.d("연결이 성공적 : delete", response.body());
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });*/

/////////////////////////////////////////////////////////////////////////////////////////////////////////
//6. 로그인

        /*String Id = "gksqui12";
        String password = "abcde123";

        UserVO user = new UserVO(password);

        controller.API.login(user, Id).enqueue(new Callback<String>() {
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                } else {
                    Log.d("연결이 성공적 : login", response.body());
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });*/

////////////////////////////////////////////////////////////////////////////////////////////////////
//7. 로그아웃

        /*String Id = "gksqui12";

        controller.API.logout(Id).enqueue(new Callback<String>() {
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                } else {
                    Log.d("연결이 성공적 : logout", response.body());
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });*/
    }
}
