package com.capstone.JobR.category.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.capstone.JobR.BoardContentActivity;
import com.capstone.JobR.DBA.board.BoardController;
import com.capstone.JobR.DBA.board.BoardVO;
import com.capstone.JobR.DBA.good.GoodController;
import com.capstone.JobR.DBA.good.GoodVO;
import com.capstone.JobR.R;
import com.capstone.JobR.UserInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainFragment extends Fragment {

    private ListView listBest, listFavorite, listHumor, listFree;
    private MainItemListAdapter adapterB, adapterF, adapterE, adapterS;
    private List<MainItem> best, favorite, humor, free;
    private List<Integer> favorite1;
    private BoardController controller = new BoardController();
    private GoodController controller1 = new GoodController();
    private UserInfo user;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main, container, false);
        user = (UserInfo) getActivity().getApplication();

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.noticeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainFragment.this)
                        .navigate(R.id.action_메인화면_to_공지사항);
            }
        });
        view.findViewById(R.id.specButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainFragment.this)
                        .navigate(R.id.action_메인화면_to_평균스펙제공);
            }
        });
        view.findViewById(R.id.resumeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainFragment.this)
                        .navigate(R.id.action_메인화면_to_자기소개서첨삭);
            }
        });
        view.findViewById(R.id.humorButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainFragment.this)
                        .navigate(R.id.action_메인화면_to_유머방);
            }
        });
        view.findViewById(R.id.freeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainFragment.this)
                        .navigate(R.id.action_메인화면_to_자유게시판);
            }
        });
        view.findViewById(R.id.consultButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MainFragment.this)
                        .navigate(R.id.action_메인화면_to_고민상담);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        init(root);
    }

    public void init(View root) {
        //베스트 게시글
        controller.API.goodList().enqueue(new Callback<List<BoardVO>>() {
            @Override
            public void onResponse(Call<List<BoardVO>> call, Response<List<BoardVO>> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                } else {
                    Log.d("연결이 성공적 : ", response.body().toString());
                    List<BoardVO> boardList = new ArrayList<>();
                    for (BoardVO board : response.body()) {
                        boardList.add(board);
                    }
                    listBest = root.findViewById(R.id.listBest);
                    best = new ArrayList<>();
                    for (int i = 0; i < 5; i++) {
                        best.add(new MainItem(boardList.get(i).getBoardName(), boardList.get(i).getBoardCont(), boardList.get(i).getBoardDate()));
                    }
                    adapterB = new MainItemListAdapter(root.getContext(), best);
                    listBest.setAdapter(adapterB);
                    listBest.setOnItemClickListener((parent, view, position, id) -> {
                        //다음 화면연결
                        Intent intent = new Intent(getContext().getApplicationContext(), BoardContentActivity.class);
                        //보드넘버 넘겨주기
                        intent.putExtra("boardNum", boardList.get(position).getBoardNum());
                        intent.putExtra("tag", false);

                        listBest.getContext().startActivity(intent);
                        Toast.makeText(listBest.getContext().getApplicationContext(), boardList.get(position).getBoardName() + "게시글", Toast.LENGTH_SHORT).show();
                    });
                    //어댑터 값 변경 알림
                    adapterB.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<BoardVO>> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });

        //좋아요한 게시글
        controller1.API.selectId(user.getUserVO().getId()).enqueue(new Callback<List<GoodVO>>() {
            @Override
            public void onResponse(Call<List<GoodVO>> call, Response<List<GoodVO>> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                } else {
                    Log.d("연결이 성공적 : ", response.body().toString());
                    favorite = new ArrayList<>();
                    favorite1 = new ArrayList<>();
                    for (GoodVO board : response.body()) {
                        controller.API.content(board.getBoardNum()).enqueue(new Callback<BoardVO>() {
                            @Override
                            public void onResponse(Call<BoardVO> call, Response<BoardVO> response) {
                                if (!response.isSuccessful()) {
                                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                                    return;
                                } else {
                                    Log.d("연결이 성공적 : ", response.body().toString());
                                    BoardVO board = response.body();
                                    //좋아요한 게시글
                                    listFavorite = root.findViewById(R.id.listFavorite);
                                    if (favorite.size() < 5) {
                                        favorite.add(new MainItem(board.getBoardName(), board.getBoardCont(), board.getBoardDate()));
                                        favorite1.add(board.getBoardNum());
                                    }

                                    adapterF = new MainItemListAdapter(root.getContext(), favorite);
                                    listFavorite.setAdapter(adapterF);
                                    listFavorite.setOnItemClickListener((parent, view, position, id) -> {
                                        //다음 화면연결
                                        Intent intent = new Intent(getContext().getApplicationContext(), BoardContentActivity.class);
                                        //보드넘버 넘겨주기
                                        intent.putExtra("boardNum", favorite1.get(position));
                                        intent.putExtra("tag", true);

                                        listFavorite.getContext().startActivity(intent);
                                        Toast.makeText(listFavorite.getContext().getApplicationContext(), favorite.get(position).getName() + "게시글", Toast.LENGTH_SHORT).show();
                                    });
                                }
                            }

                            @Override
                            public void onFailure(Call<BoardVO> call, Throwable t) {
                                t.printStackTrace();
                                Log.e("연결실패", t.getMessage());
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GoodVO>> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });

        //유머방
        controller.API.list("유머방").enqueue(new Callback<List<BoardVO>>() {
            @Override
            public void onResponse(Call<List<BoardVO>> call, Response<List<BoardVO>> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                } else {
                    Log.d("연결이 성공적 : ", response.body().toString());
                    List<BoardVO> boardList = new ArrayList<>();
                    for (BoardVO board : response.body()) {
                        boardList.add(board);
                    }
                    //유머방
                    listHumor = root.findViewById(R.id.listHumor);
                    humor = new ArrayList<>();
                    for (int i = 0; i < 5; i++) {
                        humor.add(new MainItem(boardList.get(i).getBoardName(), boardList.get(i).getBoardCont(), boardList.get(i).getBoardDate()));
                    }
                    adapterE = new MainItemListAdapter(root.getContext(), humor);
                    listHumor.setAdapter(adapterE);
                    listHumor.setOnItemClickListener((parent, view, position, id) -> {
                        //다음 화면연결
                        Intent intent = new Intent(getContext().getApplicationContext(), BoardContentActivity.class);
                        //보드 넘버 넘겨주기
                        intent.putExtra("boardNum", boardList.get(position).getBoardNum());
                        intent.getBooleanExtra("tag", false);

                        listHumor.getContext().startActivity(intent);
                        Toast.makeText(listHumor.getContext().getApplicationContext(), boardList.get(position).getBoardName() + "게시글", Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onFailure(Call<List<BoardVO>> call, Throwable t) {
                t.printStackTrace();
                Log.e("연결실패", t.getMessage());
            }
        });

        //자유게시판
        controller.API.list("자유게시판").enqueue(new Callback<List<BoardVO>>() {
            @Override
            public void onResponse(Call<List<BoardVO>> call, Response<List<BoardVO>> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                } else {
                    Log.d("연결이 성공적 : ", response.body().toString());
                    List<BoardVO> boardList = new ArrayList<>();
                    for (BoardVO board : response.body()) {
                        boardList.add(board);
                    }
                    //자유게시판
                    listFree = root.findViewById(R.id.listFree);
                    free = new ArrayList<>();
                    for (int i = 0; i < 5; i++) {
                        free.add(new MainItem(boardList.get(i).getBoardName(), boardList.get(i).getBoardCont(), boardList.get(i).getBoardDate()));
                    }
                    adapterS = new MainItemListAdapter(root.getContext(), free);
                    listFree.setAdapter(adapterS);
                    listFree.setOnItemClickListener((parent, view, position, id) -> {

                        //다음 화면연결
                        Intent intent = new Intent(getContext().getApplicationContext(), BoardContentActivity.class);
                        //보드넘버 넘겨주기
                        intent.putExtra("boardNum", boardList.get(position).getBoardNum());
                        intent.putExtra("tag", false);

                        listFree.getContext().startActivity(intent);
                        Toast.makeText(listFree.getContext().getApplicationContext(), boardList.get(position).getBoardName() + "게시글", Toast.LENGTH_SHORT).show();
                    });
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