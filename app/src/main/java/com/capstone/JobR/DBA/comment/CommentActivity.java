package com.capstone.JobR.DBA.comment;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*

 */

public class CommentActivity extends AppCompatActivity {

    static private ArrayAdapter<CommentVO> adapter;
    private CommentController controller = new CommentController();
    List<CommentVO> commentList = new ArrayList<>();
    private EditText comment_delete_num;
    private Button btn_comment_delete, btn_comment_edit;
    private EditText comment;
    private Button button;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//1. 전체 댓글 출력
        controller.API.all_list().enqueue(new Callback<List<CommentVO>>(){
            @Override
            public void onResponse(Call<List<CommentVO>> call, Response<List<CommentVO>> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                } else {
                    Log.d("연결이 성공적 : ", response.body().toString());
                    for (CommentVO comment : response.body()) {
                        commentList.add(comment);
                    }
                    //setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<CommentVO>> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });

// 2, 댓글 작성
        int boardNum = 2;
        String nickname = "댓글 작성자";
        String commentCont = "댓글 내용";
        CommentVO comment = new CommentVO(boardNum, nickname, commentCont);

        //DB 게시글 삽입
        controller.API.write(comment).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                } else {
                    Log.d("연결이 성공적 : ", response.body());
                    //댓글쓰기 완료
                    Toast.makeText(getApplicationContext(), "등록되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });

        //해당 게시글 댓글 전체 조회
        controller.API.list(2).enqueue(new Callback<List<CommentVO>>() {
            @Override
            public void onResponse(Call<List<CommentVO>> call, Response<List<CommentVO>> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                } else {
                    Log.d("연결이 성공적 : ", response.body().toString());

                    for (CommentVO comment : response.body()) {
                        commentList.add(comment);
                    }
                    //setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<CommentVO>> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });

// 4. 게시글의 특정 댓글 수정
        int commentNum = comment.getCommentNum();
        controller.API.modify(commentNum, comment).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                } else {
                    Log.d("연결이 성공적 : ", response.body().toString());
                    //다음 화면 연결
                    Toast.makeText(getApplicationContext(), "수정되었습니다..", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });

//5. 댓글 삭제
        controller.API.delete(comment.getCommentNum()).enqueue(new Callback<String>() {
            public void onResponse(Call<String> call, Response<String> response) {
                commentList.remove(comment.getCommentNum());
                //화면 전환
                Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });
    }
}
