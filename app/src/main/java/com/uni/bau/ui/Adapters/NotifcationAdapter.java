package com.uni.bau.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.bau.R;
import com.uni.bau.models.NotificationModel;

import java.util.List;


public class NotifcationAdapter extends RecyclerView.Adapter<NotifcationAdapter.ViewHolder> {


    private Context mContext;
    private List<NotificationModel.Datum> mList;

    public NotifcationAdapter(Context mContext, List<NotificationModel.Datum> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_notification , parent , false) );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            holder.title.setText(mList.get(position).getTitle().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            holder.des.setText(mList.get(position).getBody().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String dateS[] = mList.get(position).getNotificationDate().toString().split("T");
            holder.date.setText(dateS[0].toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        if (mList == null)
        {
            return 0;
        }
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView des;
        TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            des = itemView.findViewById(R.id.des);
            date = itemView.findViewById(R.id.date);

        }


    }
}
