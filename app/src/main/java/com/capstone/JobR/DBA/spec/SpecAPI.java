package com.capstone.JobR.DBA.spec;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SpecAPI {

    //"http://13.124.129.238:8080/spec/"

    //모든 스펙 조회
    @GET(".")
    Call<List<SpecVO>> all_list();

    // 스펙 작성 요청
    @POST(".")
    Call<String> write(@Body SpecVO spec);

    // 상세스펙 출력
    @GET("{id}") // => http://13.124.129.238:8080/spec/{id}
    Call<SpecVO> get(@Path("id") String id);

    // 스펙 수정 처리 요청
    @PUT("{id}")
    Call<String> modify(@Path("id") String id, @Body SpecVO spec);

    // 스펙 삭제 처리 요청
    @DELETE("{id}")
    Call<String> delete(@Path("id") String id);

    //등록된 회사 스펙 목록
    @PUT(".")
    Call<List<String>> companyList();

    //해당 회사 스펙 목록 출력
    @POST("{companyName}")
    Call<List<SpecVO>> companySpec(@Path("companyName") String companyName);

}
