package com.capstone.JobR.DBA.comment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CommentAPI {

    //"http://13.124.129.238:8080/comment/"
    
    //댓글 전체 조회
    @GET(".")
    Call<List<CommentVO>> all_list();

    // 댓글 작성 요청
    @POST(".")
    Call<String> write(@Body CommentVO comment);

    // 특정 게시글 댓글 목록 출력
    @GET("{boardNum}") // => http://13.124.129.238:8080/board/sort/{boardSort}
    Call<List<CommentVO>> list(@Path("boardNum") int boardNum);

    // 댓글 수정 처리 요청
    @PUT("{commentNum}")
    Call<String> modify(@Path("commentNum") int commentNum, @Body CommentVO comment);

    // 댓글 삭제 처리 요청
    @DELETE("{commentNum}")
    Call<String> delete(@Path("commentNum") int commentNum);

}
