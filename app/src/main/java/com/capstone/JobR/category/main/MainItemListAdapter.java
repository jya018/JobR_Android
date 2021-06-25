package com.capstone.JobR.category.main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.capstone.JobR.R;

import java.util.List;

public class MainItemListAdapter extends BaseAdapter {

    private Context context;
    private List<MainItem> mainItemList;

    public MainItemListAdapter(Context context, List<MainItem> mainItemList) {
        this.context = context;
        this.mainItemList = mainItemList;
    }

    @Override
    public int getCount() {
        return mainItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return mainItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i , View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.item_main, null);
        TextView nameText = v.findViewById(R.id.nameText);
        TextView contentText = v.findViewById(R.id.contentText);
        TextView dateText = v.findViewById(R.id.dateText);

        nameText.setText(mainItemList.get(i).getName());
        contentText.setText(mainItemList.get(i).getContent());
        dateText.setText(mainItemList.get(i).getDate());

        return v;
    }
}
