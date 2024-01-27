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
import com.uni.bau.models.DetInfoDetItem;
import com.uni.bau.models.HealthModel;

import java.util.List;

public class VacationBodyAdapter extends RecyclerView.Adapter<VacationBodyAdapter.ViewHolder> {
    private Context mContext;
    private List<DetInfoDetItem> mList;

    public VacationBodyAdapter(Context mContext, List<DetInfoDetItem> mList) {
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
            holder.tvName.setText(Html.fromHtml(mList.get(position).getDetType()));
            holder.tvHours.setText(Html.fromHtml(mList.get(position).getStartDate()));
            holder.tvResult.setText(Html.fromHtml(mList.get(position).getEndDate()));
            holder.tvNumber.setText(Html.fromHtml(mList.get(position).getPeriod()));

            holder.tvHours.setTextSize(11f);
            holder.tvResult.setTextSize(11f);
            holder.tvNumber.setTextSize(11f);
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
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
