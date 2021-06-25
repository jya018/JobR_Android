package com.capstone.JobR;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.capstone.JobR.DBA.board.BoardController;
import com.capstone.JobR.DBA.board.BoardVO;
import com.capstone.JobR.DBA.comment.CommentController;
import com.capstone.JobR.DBA.comment.CommentVO;
import com.capstone.JobR.DBA.good.GoodController;
import com.capstone.JobR.DBA.good.GoodVO;
import com.capstone.JobR.view.comment.CommentAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//상세 게시글
public class BoardContentActivity extends AppCompatActivity {
    private TextView boardName,nickname,boardDate, view, comment,good, boardContent;
    private ImageView iv_good;
    private Button contentRevise, contentDelete, enroll;
    private EditText commentCont;

    private RecyclerView recyclerView;
    private CommentAdapter adapter;

    private BoardVO board;
    private int boardNum;
    private BoardController boardController = new BoardController();
    private GoodController goodController = new GoodController();
    private CommentController commentController = new CommentController();

    //전역 유저 설정
    private UserInfo user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_content);

        //전역 유저 가져오기
        user = (UserInfo) getApplication();

        //넘겨준 게시글 받기
        boardNum = getIntent().getIntExtra("boardNum",0);
        boolean tag = getIntent().getBooleanExtra("tag", false);

        // View  배정
        boardName = findViewById(R.id.boardName);
        nickname = findViewById(R.id.nickname);
        boardDate = findViewById(R.id.boardDate);
        view = findViewById(R.id.view);
        good = findViewById(R.id.good);
        boardContent = findViewById(R.id.boardContent);
        comment = findViewById(R.id.comment);
        iv_good = findViewById(R.id.iv_good);
        contentRevise = findViewById(R.id.contentRevise);
        contentDelete = findViewById(R.id.contentDelete);
        recyclerView = findViewById(R.id.recyclerView);
        enroll = findViewById(R.id.enroll);
        commentCont = findViewById(R.id.commentCont);

        //댓글 RecyclerView 설정
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //어댑터 설정
        adapter = new CommentAdapter(user.getUserVO().getNickname());
        recyclerView.setAdapter(adapter);
        //구분선 설정
        DividerItemDecoration divider = new DividerItemDecoration(getApplicationContext(),LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(divider);

        //게시글 수정
        contentRevise.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BoardModifyActivity.class);
            intent.putExtra("board",board);
            startActivity(intent);
        });

        //게시글 삭제
        contentDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("삭제").setMessage("정말로 삭제 하시겠습니까?");
            builder.setPositiveButton("예", (dialog, id) -> {
                //삭제 기능 구현
                deleteBoard();
            });
            builder.setNegativeButton("아니오", (dialog, id) -> {
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        //댓글 등록
        enroll.setOnClickListener(v -> {
            if(commentCont.getText().equals("")){
                Toast.makeText(getApplicationContext(), "댓글을 입력해주세요!", Toast.LENGTH_SHORT).show();
            } else{
                CommentVO comment = new CommentVO(boardNum, user.userVO.getNickname(), commentCont.getText().toString());
                insertComment(comment);
                commentCont.setText("");
//                adapter.addItem(comment);
//                adapter.notifyDataSetChanged();
            }
        });

        //좋아요 누른 게시글일 경우
        if(tag){
            iv_good.setImageResource(R.drawable.ic_filled_good);
            iv_good.setTag(true);
        }else iv_good.setTag(false);

        //좋아요 처리
        iv_good.setClickable(true);
        iv_good.setOnClickListener(v -> {
            //좋아요 하기
            if ((boolean) iv_good.getTag() == false) {
                insertGood(board.getBoardNum(), user.getUserVO().getId());
                iv_good.setImageResource(R.drawable.ic_filled_good);
                iv_good.setTag(true);
                board.setGood(board.getGood() + 1);
                good.setText(String.valueOf(board.getGood()));
            }
            //좋아요 취소하기
            else {
                deleteGood(board.getBoardNum(), user.getUserVO().getId());
                iv_good.setImageResource(R.drawable.ic_empty_good);
                iv_good.setTag(false);
                board.setGood(board.getGood() - 1);
                good.setText(String.valueOf(board.getGood()));
            }
        });
    }

    //초기설정 및 수정 이후, 새로고침 설정 onCreate -> .. -> onResume() -> 액티비티 화면
    @Override
    protected void onResume() {
            super.onResume();
            adapter.deleteItem();

            //서버 통신 : 게시글 상세 조회, 댓글 조회
            init(boardNum);
    }

    //서버 통신 : 게시글 상세 조회, 댓글 조회
    private void init(int boardNum) {
        //로딩 창 생성
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("데이터 불러오는 중...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
        //로딩 시작
        progressDialog.show();
        //게시글 상세 조회
        boardController.API.content(boardNum).enqueue(new Callback<BoardVO>() {
            @Override
            public void onResponse(Call<BoardVO> call, Response<BoardVO> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    Toast.makeText(getApplicationContext(), "오류 발생!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d("연결이 성공적 : ", response.body().toString());
                    board = response.body();

                    //각 textview에 게시글 데이터 세팅
                    boardName.setText(board.getBoardName());
                    nickname.setText(board.getNickname());
                    boardDate.setText(board.getBoardDate());
                    view.setText(String.valueOf(board.getView()));
                    good.setText(String.valueOf(board.getGood()));
                    boardContent.setText(board.getBoardCont());

                    //본인 게시글일 경우 : 수정, 삭제 버튼 보이기
                    if(board.getNickname().equals(user.userVO.getNickname())){
                        contentRevise.setVisibility(View.VISIBLE);
                        contentDelete.setVisibility(View.VISIBLE);
                    }
                }
                //로딩 종료
                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<BoardVO> call, Throwable t) {
                t.printStackTrace();
                //로딩 종료
                progressDialog.dismiss();
                Log.e("연결실패", t.getMessage());
                Toast.makeText(getApplicationContext(), "오류 발생!", Toast.LENGTH_SHORT).show();
            }
        });
        //해당 게시글 댓글 전체 조회
        commentController.API.list(boardNum).enqueue(new Callback<List<CommentVO>>() {
            @Override
            public void onResponse(Call<List<CommentVO>> call, Response<List<CommentVO>> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    Toast.makeText(getApplicationContext(), "오류발생!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("연결이 성공적 : ", response.body().toString());
                    comment.setText(Integer.toString(response.body().size()));
                    for (CommentVO comment : response.body()) {
                            adapter.addItem(comment);
                    }
                    //어댑터 값 변경 알림
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<CommentVO>> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
                Toast.makeText(getApplicationContext(), "오류발생!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //서버 통신 : 게시글 삭제
    private void deleteBoard(){
        //로딩 창 생성
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("데이터 불러오는 중...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
        //로딩 시작
        progressDialog.show();
        boardController.API.delete(boardNum).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    Toast.makeText(getApplicationContext(), "오류 발생!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d("연결이 성공적 : ", response.body());
                    Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    // 해당 게시글에 달린 좋아요, 댓글 모두 삭제 (DB에서 자동으로 실행)
                    finish();
                }
                //로딩 종료
                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //로딩 종료
                progressDialog.dismiss();
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
                Toast.makeText(getApplicationContext(), "오류 발생!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 서버 통신 : 특정 게시글 좋아요 누름
    private void insertGood(int boardNum,String id){
        GoodVO good = new GoodVO(boardNum,id);
        goodController.API.insert(good).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    Toast.makeText(getApplicationContext(), "오류 발생!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("연결이 성공적 : ", response.body());
                    Toast.makeText(getApplicationContext(), "게시글 좋아요 완료!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "오류 발생!", Toast.LENGTH_SHORT).show();
                Log.e("연결실패", t.getMessage());
            }
        });
    }

    // 서버 통신 : 특정 게시글 좋아요 취소
    private void deleteGood(int boardNum, String id){
        GoodVO good = new GoodVO(boardNum,id);

        goodController.API.delete(good).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    Toast.makeText(getApplicationContext(), "오류 발생!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d("연결이 성공적 : ", response.body());
                    Toast.makeText(getApplicationContext(), "게시글 좋아요 취소", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
                Toast.makeText(getApplicationContext(), "오류 발생!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //서버 통신 : 댓글 작성
    private void insertComment(CommentVO comment){
        commentController.API.write(comment).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    Toast.makeText(getApplicationContext(), "오류 발생!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("연결이 성공적 : ", response.body());
                    onResume();
                    //댓글쓰기 완료
                    Toast.makeText(getApplicationContext(), "등록되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
                Toast.makeText(getApplicationContext(), "오류 발생!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}