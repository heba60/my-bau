package com.uni.bau.ui.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.uni.bau.R;
import com.uni.bau.models.AnouncementModel;
import com.uni.bau.models.OnLoadMoreListener;
import com.uni.bau.ui.Activites.NewsDetailsActivity;

import java.util.List;


public class LoadMoreNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity mActivity;
    private List<AnouncementModel> anouncementModelsArray;


    public static final int PROGRESS_VIEW_TYPE = 0;
    public static final int ITEM_VIEW_TYPE = 1;

    private int visibleThreshold = 2;
    private int lastVisibleItem, totalItemCount;
    private boolean loading = true;


    private AdapterView.OnItemClickListener mItemClickListener;
    private OnLoadMoreListener onLoadMoreListener;


    public void setLoaded() {
        loading = true;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public LoadMoreNewsAdapter(Activity activity, List<AnouncementModel> productByCategoryModelList, RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();
            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (loading && (totalItemCount < (lastVisibleItem + visibleThreshold))) {
                                loading = false;
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                            }
                        }
                    });

        }
        this.mActivity = activity;
        this.anouncementModelsArray = productByCategoryModelList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case PROGRESS_VIEW_TYPE: {

                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_progress_1, parent, false);
                return new ProgressViewHolder(itemView);
            }
            case ITEM_VIEW_TYPE: {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_news_, parent, false);
                return new ItemViewHolder(v);
            }

        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ProgressViewHolder) {

        } else {
            try {
                ItemViewHolder holderJ = (ItemViewHolder) holder;
                holderJ.textView.setText(anouncementModelsArray.get(position).getTitle());
                Picasso.get().load(anouncementModelsArray.get(position).getImg()).into(holderJ.imageView);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mActivity.startActivity(new Intent(mActivity, NewsDetailsActivity.class).putExtra("detailsUrl",anouncementModelsArray.get(position).getUrl()).putExtra("newsTitle",anouncementModelsArray.get(position).getTitle()).putExtra("newsimg",anouncementModelsArray.get(position).getImg()));

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }



    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        AppCompatImageView imageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.img);
        }


    }

    @Override
    public int getItemViewType(int position) {
        if (anouncementModelsArray.get(position) != null) {
            return ITEM_VIEW_TYPE;
        } else {
            return PROGRESS_VIEW_TYPE;
        }
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
        }
    }

    @Override
    public int getItemCount() {
        if (anouncementModelsArray == null) {
            return 0;
        }
        return anouncementModelsArray.size();
    }

}
