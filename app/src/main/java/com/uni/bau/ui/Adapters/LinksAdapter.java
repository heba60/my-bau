package com.uni.bau.ui.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.bau.R;

import java.util.List;


public class LinksAdapter extends RecyclerView.Adapter<LinksAdapter.CustomViewHolder> {
    Activity mActivity;
    List<String> anouncementModelsArray;
    public LinksAdapter(Activity activity, List<String> countries) {
        this.mActivity = activity;
        this.anouncementModelsArray = countries;
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_link, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, int position) {
        try {
            try {
                String str=anouncementModelsArray.get(position).toString();
                holder.fileName.setText(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        TextView  fileName;
        CustomViewHolder(View itemView) {
            super(itemView);

            fileName = itemView.findViewById(R.id.tvMessage);
        }
    }
}
