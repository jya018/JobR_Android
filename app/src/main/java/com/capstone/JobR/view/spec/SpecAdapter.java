package com.capstone.JobR.view.spec;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.JobR.R;
import com.capstone.JobR.category.spec.SpecActivity;

import java.util.ArrayList;

public class SpecAdapter extends RecyclerView.Adapter<SpecAdapter.ViewHolder> {

    private ArrayList<String> companyList = new ArrayList<>();
    private ArrayList<String> idList = new ArrayList<>();

    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_basic, parent, false);

        context = parent.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(companyList.get(position), idList.get(position));
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    public void addItem(String companyName, String id) {
        companyList.add(companyName);
        idList.add(id);
    }

    public boolean containItem(String id){
        return idList.contains(id);
    }

    //ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView companyName;
        private String id;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            companyName = itemView.findViewById(R.id.companyName);

            itemView.setClickable(true);
            itemView.setOnClickListener(v -> {
                //다음 화면 연결: 게시글 목록 -> 상세 게시글 보기
                Intent intent = new Intent(context.getApplicationContext(), SpecActivity.class);
                //게시글 다음 화면에 넘겨주기
                intent.putExtra("id", id);
                //화면 전환
                context.startActivity(intent);
                Toast.makeText(context.getApplicationContext(), companyName.getText(), Toast.LENGTH_SHORT).show();
            });
        }

        public void onBind(String companyName, String id) {
            this.companyName.setText(companyName);
            this.id=id;
        }
    }

}
