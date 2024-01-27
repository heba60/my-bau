package com.uni.bau.ui.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.uni.bau.R;


import java.util.List;


public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.CustomViewHolder> {
    Activity mActivity;
    List<String> anouncementModelsArray;
    public ImagesAdapter(Activity activity, List<String> countries) {
        this.mActivity = activity;
        this.anouncementModelsArray = countries;
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_images, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        try {
            Picasso.get().load(anouncementModelsArray.get(position)).into(holder.imageView);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = null;
                    try {
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(anouncementModelsArray.get(position)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        mActivity.startActivity(browserIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
        AppCompatImageView imageView;
        CustomViewHolder(View itemView) {
            super(itemView);

             imageView = itemView.findViewById(R.id.img);
        }
    }
}
