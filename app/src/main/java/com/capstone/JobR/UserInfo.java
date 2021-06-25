package com.capstone.JobR;

import android.app.Application;

import com.capstone.JobR.DBA.user.UserVO;

public class UserInfo extends Application {

    UserVO userVO;

    @Override
    public void onCreate() {
        //변수 초기화
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        //프로세스 소멸 시 호출
        super.onTerminate();
    }

    public UserVO getUserVO() {
        return userVO;
    }

    public void setUserVO(UserVO userVO) {
        this.userVO = userVO;
    }
}
