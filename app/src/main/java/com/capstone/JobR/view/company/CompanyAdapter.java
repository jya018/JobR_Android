package com.capstone.JobR.view.company;

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
import com.capstone.JobR.category.company.CompanyActivity;

import java.util.ArrayList;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHolder>{

    private ArrayList<String> companyList = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public CompanyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_basic, parent, false);

        context = parent.getContext();

        return new CompanyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyAdapter.ViewHolder holder, int position) {
        holder.onBind(companyList.get(position));
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    public void addItem(String companyName) {
        companyList.add(companyName);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView companyName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            companyName = (TextView) itemView.findViewById(R.id.companyName);

            itemView.setClickable(true);
            itemView.setOnClickListener(v -> {
                //다음 화면 연결: 게시글 목록 -> 상세 게시글 보기
                Intent intent = new Intent(context.getApplicationContext(), CompanyActivity.class);
                //게시글 다음 화면에 넘겨주기
                intent.putExtra("companyName", companyName.getText().toString());
                //화면 전환
                context.startActivity(intent);
                Toast.makeText(context.getApplicationContext(), companyName.getText() + "의 기업정보 조회", Toast.LENGTH_SHORT).show();
            });
        }

        public void onBind(String companyName) {
            this.companyName.setText(companyName);
        }
    }
}
