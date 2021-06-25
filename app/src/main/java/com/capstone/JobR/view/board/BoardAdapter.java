package com.capstone.JobR.view.board;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.JobR.BoardContentActivity;
import com.capstone.JobR.DBA.board.BoardVO;
import com.capstone.JobR.DBA.comment.CommentController;
import com.capstone.JobR.DBA.comment.CommentVO;
import com.capstone.JobR.DBA.good.GoodController;
import com.capstone.JobR.DBA.good.GoodVO;
import com.capstone.JobR.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    //유저 ID
    private String id;

    //목록
    private List<BoardVO> boardList = new ArrayList<>();
    private List<Integer> goodList = new ArrayList<>();

    private GoodController controller = new GoodController();
    private Context context;

    public BoardAdapter(String id) {
        this.id = id;

        // 특정 회원 좋아요 게시글 조회
        goodList();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board, parent, false);

        context = parent.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(boardList.get(position));
    }

    @Override
    public int getItemCount() {
        return boardList.size();
    }

    public void addItem(BoardVO board) {
        boardList.add(board);
    }

    public void deleteItem(){
        boardList.removeAll(boardList);
    }

    //ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {

        private BoardVO board;

        private TextView boardName;
        private TextView nickname;
        private TextView boardDate;
        private TextView view;
        private TextView good;
        private TextView comment;

        private ImageView iv_good;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            boardName = itemView.findViewById(R.id.boardName);
            nickname = itemView.findViewById(R.id.nickname);
            boardDate = itemView.findViewById(R.id.boardDate);
            view = itemView.findViewById(R.id.view);
            good = itemView.findViewById(R.id.good);
            comment = itemView.findViewById(R.id.comment);
            iv_good = itemView.findViewById(R.id.iv_good);

            itemView.setClickable(true);
            itemView.setOnClickListener(v -> {
                //다음 화면 연결: 게시글 목록 -> 상세 게시글 보기
                Intent intent = new Intent(context.getApplicationContext(), BoardContentActivity.class);
                //게시글 다음 화면에 넘겨주기
                intent.putExtra("boardNum", board.getBoardNum());
                intent.putExtra("tag", (boolean) iv_good.getTag());

                //화면 전환
                context.startActivity(intent);
                Toast.makeText(context.getApplicationContext(), board.getBoardName() + "게시글", Toast.LENGTH_SHORT).show();
            });

            //좋아요 처리
            iv_good.setClickable(true);
            iv_good.setOnClickListener(v -> {
                //좋아요 하기
                if((boolean)iv_good.getTag() == false){
                    insertGood(board.getBoardNum());
                    iv_good.setImageResource(R.drawable.ic_filled_good);
                    iv_good.setTag(true);
                    board.setGood(board.getGood()+1);
                }
                //좋아요 취소하기
                else{
                    deleteGood(board.getBoardNum());
                    iv_good.setImageResource(R.drawable.ic_empty_good);
                    iv_good.setTag(false);
                    board.setGood(board.getGood()-1);
                }
                good.setText(String.valueOf(board.getGood()));
            });

        }

        public void onBind(BoardVO board) {
            this.board = board;
            commentCount(board.getBoardNum());
            boardName.setText(board.getBoardName());
            nickname.setText(board.getNickname());
            boardDate.setText(board.getBoardDate());
            view.setText(String.valueOf(board.getView()));
            good.setText(String.valueOf(board.getGood()));


            //좋아요 누른 게시글일 경우
            if(goodList.contains(board.getBoardNum())){
                iv_good.setImageResource(R.drawable.ic_filled_good);
                iv_good.setTag(true);
            }else iv_good.setTag(false);

        }

        //서버 통신 : 댓글 수 조회
        public void commentCount(int boardNum){
            CommentController controller = new CommentController();
            controller.API.list(boardNum).enqueue(new Callback<List<CommentVO>>() {
                @Override
                public void onResponse(Call<List<CommentVO>> call, Response<List<CommentVO>> response) {
                    if (!response.isSuccessful()) {
                        Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    } else {
                        Log.d("연결이 성공적 : ", response.body().toString());
                        comment.setText(Integer.toString(response.body().size()));
                    }
                }
                @Override
                public void onFailure(Call<List<CommentVO>> call, Throwable t) {
                    t.printStackTrace();
                    Log.e("연결실패", t.getMessage());
                }
            });
        }
    }

    // 서버 통신 : 특정 회원 좋아요 게시글 조회
    void goodList(){
        controller.API.selectId(id).enqueue(new Callback<List<GoodVO>>() {
            @Override
            public void onResponse(Call<List<GoodVO>> call, Response<List<GoodVO>> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    Toast.makeText(context, "오류 발생!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("연결이 성공적 : ", response.body().toString());
                    for (GoodVO good : response.body()) {
                        goodList.add(good.getBoardNum());
                    }
                }
            }
            @Override
            public void onFailure(Call<List<GoodVO>> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
                Toast.makeText(context, "오류 발생!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 서버 통신 : 특정 게시글 좋아요 누름
    void insertGood(int boardNum){
        GoodVO good = new GoodVO(boardNum,id);
        controller.API.insert(good).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    Toast.makeText(context, "게시글 좋아요 완료!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("연결이 성공적 : ", response.body());
                    Toast.makeText(context, "게시글 좋아요 완료!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
                Toast.makeText(context, "게시글 좋아요 완료!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 특정 게시글 좋아요 취소
    void deleteGood(int boardNum){
        GoodVO good = new GoodVO(boardNum,id);

        controller.API.delete(good).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    Toast.makeText(context, "게시글 좋아요 완료!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d("연결이 성공적 : ", response.body());
                    Toast.makeText(context.getApplicationContext(), "게시글 좋아요 취소", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
                Toast.makeText(context, "게시글 좋아요 완료!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
