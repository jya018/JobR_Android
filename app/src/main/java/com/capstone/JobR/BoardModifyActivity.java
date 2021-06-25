package com.capstone.JobR;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.JobR.DBA.board.BoardController;
import com.capstone.JobR.DBA.board.BoardVO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardModifyActivity extends AppCompatActivity {
    private BoardVO board;
    private int boardNum;
    private EditText title, content;
    private TextView nickname;
    private Button sumbit;

    private BoardController controller = new BoardController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_modify);

        //View 설정
        title = findViewById(R.id.title);
        nickname = findViewById(R.id.nickname);
        content = findViewById(R.id.content);
        sumbit = findViewById(R.id.sumbit);

        //받아온 정보 설정
        board = (BoardVO) getIntent().getSerializableExtra("board");
        boardNum = board.getBoardNum();
        title.setText(board.getBoardName());
        nickname.setText(board.getNickname());
        content.setText(board.getBoardCont());

        // 버튼 이벤트 추가
        sumbit.setOnClickListener(view -> {
            //작성한 제목과 내용을 가져오기
            board = new BoardVO(title.getText().toString(), content.getText().toString());
            //로딩 창 생성
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("수정 중...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
            //로딩 시작
            progressDialog.show();
            //서버 통신 : 게시글 수정
            controller.API.modify(boardNum, board).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(!response.isSuccessful()){
                        Log.e("연결이 비정상적 : ", "error code : " + response.code());
                        Toast.makeText(getApplicationContext(), "오류발생!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Log.d("연결이 성공적 : ", response.body());
                        if (response.body().equals("success")){
                            Toast.makeText(getApplicationContext(), "수정되었습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "수정 실패...", Toast.LENGTH_SHORT).show();
                    }
                    //로딩 종료
                    progressDialog.dismiss();
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();
                    //로딩 종료
                    progressDialog.dismiss();
                    Log.e("연결실패", t.getMessage());
                    Toast.makeText(getApplicationContext(), "오류발생!", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}