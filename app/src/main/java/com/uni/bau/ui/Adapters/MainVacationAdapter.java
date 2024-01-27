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
import com.uni.bau.models.DetInfoMainItem;
import com.uni.bau.models.HealthModel;

import java.util.List;

public class MainVacationAdapter extends RecyclerView.Adapter<MainVacationAdapter.ViewHolder> {
    private Context mContext;
    private List<DetInfoMainItem> mList;

    public MainVacationAdapter(Context mContext, List<DetInfoMainItem> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_vacation, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.tvName.setText(Html.fromHtml(mList.get(position).getLokDesc()));
            holder.tvHours.setText(Html.fromHtml(mList.get(position).getYearlyBalance()));
            holder.tvResult.setText(Html.fromHtml(mList.get(position).getUsedBalance()));
            holder.tvNumber.setText(Html.fromHtml(mList.get(position).getYear()));
            holder.balance.setText(Html.fromHtml(mList.get(position).getRemainBalance()));
        } catch (Exception e) {
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNumber, tvHours, tvName, tvResult, balance;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvHours = itemView.findViewById(R.id.tvHours);
            tvName = itemView.findViewById(R.id.tvName);
            tvResult = itemView.findViewById(R.id.tvResult);
            balance = itemView.findViewById(R.id.balance);


        }
    }
}
