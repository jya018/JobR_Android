package com.capstone.JobR;

import androidx.appcompat.app.AppCompatActivity;

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

public class BoardWriteActivity extends AppCompatActivity {

    private BoardController controller = new BoardController();

    // 사용할 컴포넌트 선언
    private TextView category, writer;
    private EditText title, content;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        // 컴포넌트 초기화
        category = findViewById(R.id.category);
        writer = findViewById(R.id.writer);
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        button = findViewById(R.id.button);

        //받아온 정보 설정
        String nickname = getIntent().getStringExtra("nickname");
        String boardSort = getIntent().getStringExtra("boardSort");
        category.setText(boardSort+" 게시글 등록");
        writer.setText(nickname);

        // 버튼 이벤트 추가
        button.setOnClickListener(view -> {
            //작성한 제목과 내용을 가져오기
            String boardName = title.getText().toString();
            String boardCont = content.getText().toString();

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
                        Toast.makeText(getApplicationContext(), "등록되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();
                    Log.e("연결실패", t.getMessage());
                }
            });
        });
    }
}