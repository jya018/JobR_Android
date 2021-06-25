package com.capstone.JobR.DBA.good;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GoodAPI {

    // 특정 회원 좋아요 게시글 조회
    @GET("{id}") //
    Call<List<GoodVO>> selectId(@Path("id") String id);

    // 특정 게시글 좋아요 누름
    @POST(".")
    Call<String> insert(@Body GoodVO good);

    // 특정 게시글 좋아요 취소
    @PUT(".")
    Call<String> delete(@Body GoodVO good);

    // 회원 탈퇴 시, 좋아요 모두 삭제
    @DELETE("{id}")
    Call deleteId(@Path("id") String id);

}
