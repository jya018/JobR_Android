package com.capstone.JobR.view.board;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.capstone.JobR.DBA.board.BoardController;
import com.capstone.JobR.DBA.board.BoardVO;
import com.capstone.JobR.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoardFragment extends Fragment {

    private  View view;
    private RecyclerView recyclerView;
    private BoardAdapter adapter;

    //게시글 분류
    private String pageTitle;

    //유저 ID
    private String id;

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public BoardFragment(String pageTitle, String id) {
        this.pageTitle = pageTitle;
        this.id = id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_board_view, container, false);

        //화면 띄우기
        recyclerView = view.findViewById(R.id.recyclerView);

        //매니저 설정
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //어댑터 설정
        adapter = new BoardAdapter(id);
        recyclerView.setAdapter(adapter);

        //땡겼을 때, 새로고침 설정
        SwipeRefreshLayout refresh = view.findViewById(R.id.refresh_layout);
        //포인트 색깔 주기
        refresh.setColorSchemeColors(Color.parseColor("#FF5900"));
        refresh.setOnRefreshListener(() -> {
            adapter.deleteItem();
            init(pageTitle);
            refresh.setRefreshing(false);
            Toast.makeText(getContext(), "새로고침 완료!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }


    //서버 통신 : {boardSort} 게시글 목록 출력
    public void init(String boardSort){
        BoardController controller = new BoardController();
        if(boardSort.equals("전체 게시글")){
            //모든 게시글 조회
            controller.API.allList().enqueue(new Callback<List<BoardVO>>() {
                @Override
                public void onResponse(Call<List<BoardVO>> call, Response<List<BoardVO>> response) {
                    if (!response.isSuccessful()) {
                        Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    } else {
                        Log.d("연결이 성공적 : ", response.body().toString());
                        for (BoardVO board : response.body()) {
                            adapter.addItem(board);
                        }
                        //어댑터 값 변경 알림
                        adapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<List<BoardVO>> call, Throwable t) {
                    t.printStackTrace();
                    Log.e("연결실패", t.getMessage());
                }
            });
        } else if(boardSort.equals("베스트 게시글")){
            //모든 게시글 좋아요 순으로 조회
            controller.API.goodList().enqueue(new Callback<List<BoardVO>>() {
                @Override
                public void onResponse(Call<List<BoardVO>> call, Response<List<BoardVO>> response) {
                    if (!response.isSuccessful()) {
                        Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    } else {
                        Log.d("연결이 성공적 : ", response.body().toString());
                        for (BoardVO board : response.body()) {
                            adapter.addItem(board);
                        }
                        //어댑터 값 변경 알림
                        adapter.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<List<BoardVO>> call, Throwable t) {
                    t.printStackTrace();
                    Log.e("연결실패", t.getMessage());
                }
            });
        } else {
            //카테고리별로 게시글 조회
            controller.API.list(boardSort).enqueue(new Callback<List<BoardVO>>() {
                @Override
                public void onResponse(Call<List<BoardVO>> call, Response<List<BoardVO>> response) {
                    if (!response.isSuccessful()) {
                        Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    } else {
                        Log.d("연결이 성공적 : ", response.body().toString());
                        for (BoardVO board : response.body()) {
                            adapter.addItem(board);
                        }
                        //어댑터 값 변경 알림
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<BoardVO>> call, Throwable t) {
                    t.printStackTrace();
                    Log.e("연결실패", t.getMessage());
                }
            });
        }
    }

    //Fragment 초기 설정 및 돌아왔을 때, 새로고침 설정
    @Override
    public void onResume() {
        super.onResume();
        adapter.deleteItem();
        //서버 통신 : 게시글 목록 가져오기
        init(pageTitle);
    }
}