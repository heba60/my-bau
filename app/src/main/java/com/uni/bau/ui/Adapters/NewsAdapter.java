package com.uni.bau.ui.Adapters;

import android.app.Activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.uni.bau.R;
import com.uni.bau.models.AnouncementModel;
import com.uni.bau.ui.Activites.NewsDetailsActivity;

import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.CustomViewHolder> {
    Activity mActivity;
    List<AnouncementModel> anouncementModelsArray;
    public NewsAdapter(Activity activity, List<AnouncementModel> countries) {
        this.mActivity = activity;
        this.anouncementModelsArray = countries;
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_news_, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder,  int position) {
        try {
            holder.textView.setText(anouncementModelsArray.get(position).getTitle());

            Picasso.get().load(anouncementModelsArray.get(position).getImg().replace("styles/news_outside/public/","")).into(holder.imageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.startActivity(new Intent(mActivity, NewsDetailsActivity.class).putExtra("detailsUrl",anouncementModelsArray.get(position).getUrl()).putExtra("newsTitle",anouncementModelsArray.get(position).getTitle()));

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (anouncementModelsArray == null) {
            return 0;
        }
        return anouncementModelsArray.size();
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        AppCompatImageView imageView;
        CustomViewHolder(View itemView) {
            super(itemView);
             textView = itemView.findViewById(R.id.textView);
             imageView = itemView.findViewById(R.id.img);
        }
    }
}
