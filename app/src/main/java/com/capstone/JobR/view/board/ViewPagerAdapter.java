package com.capstone.JobR.view.board;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> pageTitle = new ArrayList<>();

    private ArrayList<BoardFragment> fragments;


    public ViewPagerAdapter(FragmentManager fm, ArrayList<BoardFragment> fragments) {
        super(fm);
        this.fragments = fragments;

        //게시글 분류 가져오기
        for( int i =0; i < fragments.size(); i++){
            pageTitle.add(fragments.get(i).getPageTitle());
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitle.get(position);
    }
}