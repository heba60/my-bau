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
import com.uni.bau.models.MarksModel;

import java.util.List;

public class MainMarksAdapter extends RecyclerView.Adapter<MainMarksAdapter.ViewHolder> {
    private Context mContext;
    private MarksApiModel[] mList;
    public MainMarksAdapter(Context mContext, MarksApiModel[] mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_mark_main , parent , false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.semsText.setText("الفصل الدراسي : "+ Html.fromHtml(mList[position].getSemester()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            holder.year.setText("السنة الدراسية : "+Html.fromHtml(mList[position].getYear()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            holder.hours.setText("عدد الساعات : "+Html.fromHtml(mList[position].getHours()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            holder.gpa.setText("االمعدل : "+Html.fromHtml(mList[position].getAverage()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.marksRV.setLayoutManager(new LinearLayoutManager(mContext));
        holder.marksRV.setAdapter(new MarksAdapter(mContext,mList[position].getMarks()));
    }
    @Override
    public int getItemCount() {
        if (mList == null)
        {
            return 0;
        }
        return mList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView semsText;
        TextView year;
        TextView hours;
        TextView gpa;
        RecyclerView marksRV ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            semsText = itemView.findViewById(R.id.semsText);
            year = itemView.findViewById(R.id.year);
            hours = itemView.findViewById(R.id.hours);
            gpa = itemView.findViewById(R.id.gpa);
            marksRV = itemView.findViewById(R.id.marksRV);
        }
    }
}
