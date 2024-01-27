package com.uni.bau.ui.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.bau.R;
import com.uni.bau.models.AnouncementModel;
import com.uni.bau.models.GetAnnouncementsModel;
import com.uni.bau.ui.Activites.AnnouncmentDetailsActivity;
import com.uni.bau.utilities.MyWebView;

import java.util.List;


public class AnoucmentAdapter extends RecyclerView.Adapter<AnoucmentAdapter.CustomViewHolder> {
    Activity mActivity;
    GetAnnouncementsModel[] anouncementModelsArray;
    public AnoucmentAdapter(Activity activity, GetAnnouncementsModel[] countries) {
        this.mActivity = activity;
        this.anouncementModelsArray = countries;
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_my_web, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder,  int position) {
        try {
            holder.myWebView.loadDataWithBaseURL(null, anouncementModelsArray[position].getText(), "text/html", "UTF-8", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (anouncementModelsArray == null) {
            return 0;
        }
        return anouncementModelsArray.length;
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {
        MyWebView myWebView;

        CustomViewHolder(View itemView) {
            super(itemView);
            myWebView = itemView.findViewById(R.id.myWebView);
            WebSettings webSettings = myWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
        }
    }
}
