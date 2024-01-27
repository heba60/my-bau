package com.uni.bau.ui.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;
import com.uni.bau.R;
import com.uni.bau.models.AnouncementModel;
import com.uni.bau.ui.Activites.NewsDetailsActivity;

import java.util.List;

public class ImageSlideAdapter extends PagerAdapter {
    private Activity activity;
    List<AnouncementModel> slider;
    public ImageSlideAdapter(Activity context, List<AnouncementModel> slider) {
        this.activity = context;
        this.slider = slider;
    }
    @Override
    public int getCount() {
        if (slider == null)
        {
            return 0;
        }
        return slider.size();
    }
    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.adapter_image_slider_row, container, false);
        TextView textView = view.findViewById(R.id.textView);
        AppCompatImageView imageView = view.findViewById(R.id.img);
        try {
            Picasso.get().load(slider.get(position).getImg()).into(imageView);
            textView.setText(slider.get(position).getTitle());
            textView.setScaleX(-1);
            imageView.setScaleX(-1);
        }
        catch (Exception e)
        {

        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(activity, NewsDetailsActivity.class).putExtra("detailsUrl",slider.get(position).getUrl()).putExtra("newsimg",slider.get(position).getImg()).putExtra("newsTitle",slider.get(position).getTitle()));
            }
        });
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }}

