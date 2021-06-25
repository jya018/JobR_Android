package com.capstone.JobR;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.capstone.JobR.DBA.board.BoardController;
import com.capstone.JobR.DBA.board.BoardVO;
import com.capstone.JobR.view.board.BoardAdapter;
import com.capstone.JobR.view.board.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends FragmentActivity {

    private SearchView mSearchView;
    private BoardController controller = new BoardController();
    private List<BoardVO> boardList = new ArrayList<>();
    private Button nicknameSearch, titleSearch, contentSearch;
    private String id;

    private String sort = "제목";


    private RecyclerView recyclerView;
    private BoardAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        this.InitializeView();
        this.write();

        //전역 유저 가져오기
        UserInfo user = (UserInfo) getApplication();
        setId(user.getUserVO().getId());

        //게시글 목록 출력
        recyclerView = findViewById(R.id.recyclerView);

        //매니저 설정
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //어댑터 설정
        adapter = new BoardAdapter(id);
        recyclerView.setAdapter(adapter);

    }
    private void setId(String id){
        this.id = id;
    }

    public void InitializeView() {
        mSearchView = findViewById(R.id.searchview);
        titleSearch = (Button)findViewById(R.id.titleSearch);
        nicknameSearch = (Button)findViewById(R.id.nicknameSearch);
        contentSearch = (Button)findViewById(R.id.contentSearch);
    }

    public void write(){
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // 입력란의 문자열이 바뀔 때 처리
                View.OnClickListener Listener = view -> {
                    switch (view.getId()) {
                        case R.id.titleSearch:
                            titleSearch.setSelected(true);
                            nicknameSearch.setSelected(false);
                            contentSearch.setSelected(false);
                            adapter.deleteItem();
                            sort = "제목";
                            init(sort,s);
                            break;
                        case R.id.nicknameSearch:
                            titleSearch.setSelected(false);
                            nicknameSearch.setSelected(true);
                            contentSearch.setSelected(false);
                            adapter.deleteItem();
                            sort = "닉네임";
                            init(sort,s);
                            break;
                        case R.id.contentSearch:
                            titleSearch.setSelected(false);
                            nicknameSearch.setSelected(false);
                            contentSearch.setSelected(true);
                            adapter.deleteItem();
                            sort = "내용";
                            init(sort,s);
                            break;
                    }
                };
                titleSearch.setOnClickListener(Listener);
                nicknameSearch.setOnClickListener(Listener);
                contentSearch.setOnClickListener(Listener);
                return false;
            }
        });
    }

    private void init(String sort, String context){
        controller.API.searchcontent(sort,context).enqueue(new Callback<List<BoardVO>>() {
            @Override
            public void onResponse(Call<List<BoardVO>> call, Response<List<BoardVO>> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                } else {
                    Log.d("연결이 성공적 : ", response.body().toString());

                    for (BoardVO board : response.body()) {
                        adapter.addItem(board);
                    }
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

