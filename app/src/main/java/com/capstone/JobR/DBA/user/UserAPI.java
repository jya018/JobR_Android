package com.capstone.JobR.DBA.user;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserAPI {

    //"http://13.124.129.238:8080/user/"

   // 아이디 중복 확인
   @GET("checkid/{id}")
   Call<String> checkId(@Path("id") String id);

    //닉네임 중복 확인
    @GET("checknick/{nickname}")
    Call<String> checkNick(@Path("nickname") String nickname);

   //회원가입
   @POST(".")
   Call<String> regsiter(@Body UserVO user);

   //비밀번호 변경
    @POST("{id}")
    Call<String> updatePw(@Body UserVO user, @Path("id") String id);

    //회원정보 변경
    @PUT("{id}")
    Call<String> update(@Body UserVO user, @Path("id") String id);

    //회원정보 삭제
    @DELETE("{id}")
    Call<String> delete(@Path("id") String id);

    //로그인
    @POST("log/{id}")
    Call<UserVO> login(@Body UserVO user, @Path("id") String id);

    //로그아웃
    @GET("log/{id}")
    Call<String> logout(@Path("id") String id);
}
