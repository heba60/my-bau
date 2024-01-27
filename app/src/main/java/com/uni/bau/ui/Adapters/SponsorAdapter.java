package com.uni.bau.ui.Adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.uni.bau.R;
import com.uni.bau.models.SponserModel;

import java.util.List;


public class SponsorAdapter extends RecyclerView.Adapter<SponsorAdapter.ViewHolder> {
    private Context mContext;
    private List<SponserModel.Datum> sponserModels;

    public SponsorAdapter(Context mContext, List<SponserModel.Datum> sponserModels) {
        this.mContext = mContext;
        this.sponserModels = sponserModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.row_sponsor , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.get().load(sponserModels.get(position).getLogo()).into(holder.imgSponsor);
    }

    @Override
    public int getItemCount() {
        return sponserModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgSponsor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSponsor = itemView.findViewById(R.id.imgSponsor);

        }

    }
}
