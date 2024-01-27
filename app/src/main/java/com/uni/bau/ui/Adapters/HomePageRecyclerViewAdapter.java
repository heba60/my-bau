package com.uni.bau.ui.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.uni.bau.R;
import com.uni.bau.managers.ApiCallResponse;
import com.uni.bau.managers.UniBusinessManager;
import com.uni.bau.models.AdvModel;
import com.uni.bau.models.AnouncementModel;
import com.uni.bau.models.ScheduleModel;
import com.uni.bau.utilities.AutoScrollViewPager;
import com.uni.bau.utilities.CirclePageIndicator;
import java.util.List;
public class HomePageRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity context;
    private static final int TYPE_SLIDER = 0;
    private static final int TYPE_RECYCLER = 1;
    int size = 0;
    View viewSlider;
    List<AnouncementModel> anouncementModelsList;
    List<ScheduleModel> scheduleModelsArrayList;
    public HomePageRecyclerViewAdapter(Activity context, int size, List<AnouncementModel> anouncementModelsList, List<ScheduleModel> scheduleModelsArrayList) {
        this.context = context;
        this.size = size;
        this.anouncementModelsList = anouncementModelsList;
        this.scheduleModelsArrayList = scheduleModelsArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_SLIDER) {
            viewSlider = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_catigory_slider, viewGroup, false);
            return new ViewHolderSlider(viewSlider);
        }
        if (viewType == TYPE_RECYCLER) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_catigroy_recyclers, viewGroup, false);
            return new ViewHolderRecyclers(view);
        }
        throw new RuntimeException();
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolderSlider) {
        } else if (holder instanceof ViewHolderRecyclers) {
            ((ViewHolderRecyclers) holder).productRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            ((ViewHolderRecyclers) holder).productRecyclerView.setAdapter(new ScheduleAdapter(context, scheduleModelsArrayList));
            try {
                if (scheduleModelsArrayList.isEmpty())
                {
                    ((ViewHolderRecyclers) holder).noScudle.setVisibility(View.VISIBLE);
                }
            }catch (Exception e)
            {
            }
        }
    }

    @Override
    public int getItemCount() {
        if (size == 0)
            return 0;
        return size;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 1) {
            return TYPE_RECYCLER;
        } else {
            return TYPE_SLIDER;
        }
    }

    public class ViewHolderSlider extends RecyclerView.ViewHolder {
        ViewPager imagesViewPager;
        public ViewHolderSlider(View rootView) {
            super(rootView);
            imagesViewPager = (ViewPager) rootView.findViewById(R.id.imagesViewPager);
        }
    }


    public class ViewHolderRecyclers extends RecyclerView.ViewHolder {
        RecyclerView productRecyclerView;
        TextView noScudle;
        AutoScrollViewPager imagesViewPager;
        CirclePageIndicator imagesPageIndicator;
        public ViewHolderRecyclers(View rootView) {
            super(rootView);
            productRecyclerView = (RecyclerView) rootView.findViewById(R.id.productRecyclerView);
            noScudle = (TextView) rootView.findViewById(R.id.noScudle);
            imagesPageIndicator = (CirclePageIndicator) rootView.findViewById(R.id.imagesPageIndicator);
            imagesViewPager = (AutoScrollViewPager) rootView.findViewById(R.id.imagesViewPager);
            getImgSlider(imagesPageIndicator,imagesViewPager);
        }
    }

    private void getImgSlider(CirclePageIndicator imagesPageIndicator, AutoScrollViewPager imagesViewPager) {
        new UniBusinessManager().getAdv(context, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                AdvModel advModel = (AdvModel) responseObject;
                HomeImageSlideAdapter imageSlideAdapter = new HomeImageSlideAdapter(context, advModel.getData());
                imagesViewPager.setAdapter(imageSlideAdapter);
                imagesPageIndicator.setViewPager(imagesViewPager);
                imagesViewPager.startAutoScroll();
                imagesViewPager.setInterval(3000);
                imagesViewPager.setCycle(true);
                imagesViewPager.setStopScrollWhenTouch(true);
            }
            @Override
            public void onFailure(String errorResponse) {

            }
        });
    }




}
