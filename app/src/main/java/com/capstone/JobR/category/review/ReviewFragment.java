package com.capstone.JobR.category.review;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.capstone.JobR.BoardWriteActivity;
import com.capstone.JobR.R;
import com.capstone.JobR.UserInfo;
import com.capstone.JobR.view.board.BoardFragment;
import com.capstone.JobR.view.board.ViewPagerAdapter;

import java.util.ArrayList;

//면접후기
public class ReviewFragment extends Fragment {

    private View root;
    private ImageButton btn_Write;
    private ArrayList<BoardFragment> fragments;
    private PagerSlidingTabStrip tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_board_list, container, false);

        //전역 유저 가져오기
        UserInfo user = (UserInfo) getActivity().getApplication();

        //게시글 목록 출력
        init(user.getUserVO().getId());

        //게시글 작성 버튼
        btn_Write = root.findViewById(R.id.btn_write);

        btn_Write.setOnClickListener(view -> {
            //다음 화면 연결: 게시글 목록 -> 게시글 쓰기
            Intent intent = new Intent(getContext(), BoardWriteActivity.class);
            //다음 화면에 작성자 정보 넘겨주기
            intent.putExtra("nickname", user.getUserVO().getNickname());
            //다음화면에 게시글 분류 넘겨주기
            intent.putExtra("boardSort", viewPagerAdapter.getPageTitle(tabLayout.getCurrentPosition()));
            //화면 전환
            startActivity(intent);
            Toast.makeText(getContext(), "게시글을 작성합니다.", Toast.LENGTH_SHORT).show();
        });

        return root;
    }

    private void init(String id) {
        tabLayout = root.findViewById(R.id.tabLayout);
        viewPager = root.findViewById(R.id.vp_board);

        fragments =new ArrayList<>();
        fragments.add(new BoardFragment("면접후기",id));
        fragments.add(new BoardFragment("합격비법",id));

        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setViewPager(viewPager);
    }
}
