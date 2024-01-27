package com.uni.bau.ui.Adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.bau.R;
import com.uni.bau.models.MarksApiModel;
import com.uni.bau.models.StudentClassificationsModel;

import java.util.List;

public class StudentClassificationHeaderAdapter extends RecyclerView.Adapter<StudentClassificationHeaderAdapter.ViewHolder> {
    private Context mContext;
    private List<StudentClassificationsModel> mList;
    public StudentClassificationHeaderAdapter(Context mContext, List<StudentClassificationsModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_student_classification_header , parent , false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.tvTitle.setText(Html.fromHtml(mList.get(position).getArabicName() ));
            holder.recyclerBodyClassifications.setLayoutManager(new LinearLayoutManager(mContext));
            holder.recyclerBodyClassifications.setAdapter(new StudentClassificationBodyAdapter(mContext,mList.get(position).getMarks()));
        }catch (Exception e){
        }
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        RecyclerView recyclerBodyClassifications;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            recyclerBodyClassifications = itemView.findViewById(R.id.recyclerBodyClassifications);
        }
    }
}
