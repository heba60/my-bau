package com.uni.bau.ui.Adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.bau.R;
import com.uni.bau.models.NewsLitterModel;
import com.uni.bau.models.StudentClassificationsModel;

import java.util.List;

public class NewsLitterBodyAdapter extends RecyclerView.Adapter<NewsLitterBodyAdapter.ViewHolder> {
    private Context mContext;
    private NewsLitterModel[] mList;

    public NewsLitterBodyAdapter(Context mContext, NewsLitterModel[] mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_student_classification_body, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.tvName.setText(Html.fromHtml(mList[position].getName()));
            holder.tvHours.setText(Html.fromHtml(mList[position].getLecturers()+""));
            holder.tvResult.setText(Html.fromHtml(mList[position].getTimes()+""));
            holder.tvNumber.setText(Html.fromHtml(mList[position].getNo()));
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        if (mList == null)
        {
            return 0;
        }
        return mList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumber, tvHours, tvName, tvResult;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvHours = itemView.findViewById(R.id.tvHours);
            tvName = itemView.findViewById(R.id.tvName);
            tvResult = itemView.findViewById(R.id.tvResult);


        }
    }
}
