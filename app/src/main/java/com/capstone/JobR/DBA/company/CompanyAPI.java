package com.capstone.JobR.DBA.company;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CompanyAPI {

    //"http://13.124.129.238:8080/company/"

    // 모든 기업정보 게시글 출력 => http://13.124.129.238:8080/company
    @GET(".")
    Call<List<CompanyVO>> list();

    // 기업정보 게시글 작성 요청
    @POST(".")
    Call<String> write(@Body CompanyVO company);

    // 기업정보 상세 조회 요청
    @GET("{compName}")
    Call<CompanyVO> content(@Path("compName") String compName);

    // 기업정보 수정 처리 요청
    @PUT("{compName}")
    Call<String> modify(@Path("compName") String compName, @Body CompanyVO company);

    // 기업정보 삭제 처리 요청
    @DELETE("{compName}")
    Call<CompanyVO> delete(@Path("compName") String compName);

}
