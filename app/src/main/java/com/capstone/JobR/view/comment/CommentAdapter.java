package com.capstone.JobR.view.comment;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.JobR.DBA.comment.CommentController;
import com.capstone.JobR.DBA.comment.CommentVO;
import com.capstone.JobR.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    //현재 유저 ID
    private String userNickname;

    private CommentController controller = new CommentController();

    private List<CommentVO> commentList = new ArrayList<>();

    private Context context;

    public CommentAdapter(String userNickname) {
        this.userNickname = userNickname;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        context = parent.getContext();
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(commentList.get(position));

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public void addItem(CommentVO comment){
        commentList.add(comment);
    }

    public void deleteItem(){
        commentList.removeAll(commentList);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CommentVO comment;

        private TextView nickname;
        private TextView commentDate;
        private EditText commentCont;
        private Button bt_update;
        private Button bt_delete;
        private Button bt_submit;
        private Button bt_cancel;

        private String tmp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            nickname = itemView.findViewById(R.id.nickname);
            commentCont = itemView.findViewById(R.id.commentCont);
            commentDate = itemView.findViewById(R.id.commentDate);
            bt_update = itemView.findViewById(R.id.bt_update);
            bt_delete = itemView.findViewById(R.id.bt_delete);
            bt_submit = itemView.findViewById(R.id.bt_submit);
            bt_cancel = itemView.findViewById(R.id.bt_cancel);

            //수정
            bt_update.setOnClickListener(v -> {
                commentCont.setEnabled(true);
                bt_update.setVisibility(View.GONE);
                bt_delete.setVisibility(View.GONE);
                bt_submit.setVisibility(View.VISIBLE);
                bt_cancel.setVisibility(View.VISIBLE);
            });

            //수정 등록
            bt_submit.setOnClickListener(v -> {
                commentCont.setEnabled(false);
                tmp = commentCont.getText().toString();
                comment.setCommentCont(tmp);
                update(comment.getCommentNum(), comment);
                bt_update.setVisibility(View.VISIBLE);
                bt_delete.setVisibility(View.VISIBLE);
                bt_submit.setVisibility(View.GONE);
                bt_cancel.setVisibility(View.GONE);

            });

            //수정 취소
            bt_cancel.setOnClickListener(v -> {
                commentCont.setEnabled(false);
                commentCont.setText(tmp);
                bt_update.setVisibility(View.VISIBLE);
                bt_delete.setVisibility(View.VISIBLE);
                bt_submit.setVisibility(View.GONE);
                bt_cancel.setVisibility(View.GONE);
            });

            //삭제
            bt_delete.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("삭제").setMessage("정말로 삭제 하시겠습니까?");

                builder.setPositiveButton("예", (dialog, id) -> {
                    //삭제 기능 구현
                    delete(comment);
                    commentCont.setText("삭제된 댓글입니다.");
                    bt_update.setVisibility(View.GONE);
                    bt_delete.setVisibility(View.GONE);
                });

                builder.setNegativeButton("아니오", (dialog, id) -> {
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            });
        }

        public void onBind(CommentVO comment) {
            this.comment = comment;
            this.nickname.setText(comment.getNickname());
            this.commentCont.setText(comment.getCommentCont());
            this.commentDate.setText(comment.getCommentDate());

            //댓글 수정 취소용
            this.tmp = comment.getCommentCont();

            //본인 댓글일 경우 : 수정, 삭제 버튼 보이기
            if(userNickname.equals(comment.getNickname())){
                bt_update.setVisibility(View.VISIBLE);
                bt_delete.setVisibility(View.VISIBLE);
            }
        }
    }

    //서버 통신 : 댓글 수정
    private void update(int commentNum, CommentVO comment) {
        //로딩 창 생성
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("수정 중...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
        //로딩 시작
        progressDialog.show();
        controller.API.modify(commentNum, comment).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    Toast.makeText(context, "오류발생!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("연결이 성공적 : ", response.body().toString());
                    Toast.makeText(context, "수정되었습니다!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "오류발생!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //서버 통신 : 댓글 삭제
    private void delete(CommentVO comment) {
        //로딩 창 생성
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("삭제 중...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
        //로딩 시작
        progressDialog.show();
        controller.API.delete(comment.getCommentNum()).enqueue(new Callback<String>() {
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    Toast.makeText(context, "오류발생!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("연결이 성공적 : ", response.body().toString());
                    Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, "오류발생!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
