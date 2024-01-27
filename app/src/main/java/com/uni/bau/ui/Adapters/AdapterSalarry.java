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
import com.uni.bau.models.SoapRequestHandler;
import com.uni.bau.ui.Activites.NewsDetailsActivity;

import java.util.List;


public class AdapterSalarry extends RecyclerView.Adapter<AdapterSalarry.CustomViewHolder> {
    Activity mActivity;
    List<String> anouncementModelsArray;
    List<String> anouncementModelsArray2;
    public AdapterSalarry(Activity activity, List<String> countries, List<String> countries2) {
        this.mActivity = activity;
        this.anouncementModelsArray = countries;
        this.anouncementModelsArray2 = countries2;

    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_salary, parent, false);
        return new CustomViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder,  int position) {
        try {
            holder.textView.setText(anouncementModelsArray.get(position));
            holder.textView2.setText(anouncementModelsArray2.get(position));


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
        TextView textView,textView2;
        CustomViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);

        }
    }
}
