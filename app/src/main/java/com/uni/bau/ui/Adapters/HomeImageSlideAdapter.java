package com.uni.bau.ui.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;
import com.uni.bau.R;
import com.uni.bau.models.AdvModel;

import java.util.List;

public class HomeImageSlideAdapter extends PagerAdapter {
    private Activity activity;
    List<AdvModel.Datum> slider;
    public HomeImageSlideAdapter(Activity context, List<AdvModel.Datum> slider) {
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
        View view = inflater.inflate(R.layout.home_slider, container, false);

        AppCompatImageView imageView = view.findViewById(R.id.img);
        try {
            Picasso.get().load(slider.get(position).getImage()).into(imageView);
        }
        catch (Exception e)
        {

        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = null;
                try {
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(slider.get(position).getUrl()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    activity.startActivity(browserIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

