package com.capstone.JobR.DBA.board;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BoardAPI {

    //"http://13.124.129.238:8080/board/"

    // 특정 게시글 목록 출력
    @GET("sort/{boardSort}") // => http://13.124.129.238:8080/board/sort/{boardSort}
    Call<List<BoardVO>> list(@Path("boardSort") String boardSort);

    //모든 게시글 조회
    @GET(".")
    Call<List<BoardVO>> allList();

    // 게시글 작성 요청
    @POST(".")
    Call<String> write(@Body BoardVO board);

    //모든 게시글 좋아요 순으로 조회
    @PUT(".")
    Call<List<BoardVO>> goodList();

    // 게시글 상세 조회 요청
    @GET("{boardNum}")
    Call<BoardVO> content(@Path("boardNum") int boardNum);

    // 게시글 수정 처리 요청
    @PUT("{boardNum}")
    Call<String> modify(@Path("boardNum") int boardNum, @Body BoardVO board);

    // 게시글 삭제 처리 요청
    @DELETE("{boardNum}")
    Call<String> delete(@Path("boardNum") int boardNum);

    //전체 게시글 검색
    @GET("search/{option}/{keywords}")
    Call<List<BoardVO>> searchcontent(@Path("option") String option, @Path("keywords") String keywords);

}
