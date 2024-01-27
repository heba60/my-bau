package com.uni.bau.ui.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.uni.bau.R;
import com.uni.bau.models.JobsModel;

import java.util.List;


public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder> {


    private Context mContext;
    private List<JobsModel.Datum> mList;

    public JobsAdapter(Context mContext, List<JobsModel.Datum> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_jobs , parent , false) );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            Picasso.get().load(mList.get(position).getImage().toString()).into(holder.img);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            holder.title.setText(mList.get(position).getTitle().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            holder.des.setText(mList.get(position).getDescription().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            String dateS[] = mList.get(position).getDate().toString().split("T");
            holder.date.setText(dateS[0].toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse( mList.get(position).getLink()));
                    mContext.startActivity(browserIntent);
                } catch (Exception e) {
                    e.printStackTrace();

                }

            }
        });
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
        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            des = itemView.findViewById(R.id.des);
            date = itemView.findViewById(R.id.date);
            img = itemView.findViewById(R.id.img);
        }


    }
}
