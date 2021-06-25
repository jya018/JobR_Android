package com.capstone.JobR.DBA.board;

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

public class BoardActivity  extends AppCompatActivity {

    static private ArrayAdapter<BoardVO> adapter;
    private BoardController controller = new BoardController();
    List<BoardVO> boardList = new ArrayList<>();
    private BoardVO board;
    private Button btn_create,button, delete_button ;
    private EditText content, title, delete_text;
    private int board_delete_num_int;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. 해당 카테고리 모든 게시글 출력
        controller.API.list("유머방").enqueue(new Callback<List<BoardVO>>() {
            @Override
            public void onResponse(Call<List<BoardVO>> call, Response<List<BoardVO>> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                } else {
                    Log.d("연결이 성공적 : ", response.body().toString());
                    for (BoardVO board : response.body()) {
                        boardList.add(board);
                    }
                    //setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<BoardVO>> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });

        //모든 게시글 조회
        controller.API.allList().enqueue(new Callback<List<BoardVO>>() {
            @Override
            public void onResponse(Call<List<BoardVO>> call, Response<List<BoardVO>> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                } else {
                    Log.d("연결이 성공적 : ", response.body().toString());
                    for (BoardVO board : response.body()) {
                        boardList.add(board);
                    }
                    //setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<BoardVO>> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });

        //모든 게시글 좋아요 순으로 조회
        controller.API.goodList().enqueue(new Callback<List<BoardVO>>() {
            @Override
            public void onResponse(Call<List<BoardVO>> call, Response<List<BoardVO>> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                } else {
                    Log.d("연결이 성공적 : ", response.body().toString());
                    for (BoardVO board : response.body()) {
                        boardList.add(board);
                    }
                    //setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<List<BoardVO>> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });

        //2. 게시글 작성
        String nickname = "안";
        String boardSort = "유머방";
        String boardName = "게시글 제목";
        String boardCont = "게시글 내용";

        //밑에 4가지 요소들 board에 넣어주기
        BoardVO board = new BoardVO(boardName, boardCont, nickname, boardSort);

        //DB 게시글 삽입
        controller.API.write(board).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                } else {
                    Log.d("연결이 성공적 : ", response.body());

                    //다음 화면 연결 => 글쓰기가 완료 됐으니 Main화면으로 돌아감
                    Toast.makeText(getApplicationContext(), "등록되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });

        //3. 게시글 상세 조회
        controller.API.content(board_delete_num_int).enqueue(new Callback<BoardVO>() {
            @Override
            public void onResponse(Call<BoardVO> call, Response<BoardVO> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                else{
                    Log.d("연결이 성공적 : ", response.body().toString());
                    //다음 화면 연결
                    Toast.makeText(getApplicationContext(), "게시글 조회.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<BoardVO> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });

        //4. 해당 게시글 수정
        int boardNum = board.getBoardNum();
        controller.API.modify(boardNum, board).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                else{
                    Log.d("연결이 성공적 : ", response.body());
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

         //5.삭제
        controller.API.delete(board_delete_num_int).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                else{
                    Log.d("연결이 성공적 : ", response.body());
                    //다음 화면 연결
                    Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });
    }
}
