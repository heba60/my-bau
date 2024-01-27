package com.uni.bau.ui.Adapters;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;


import com.uni.bau.R;

import java.util.ArrayList;

public class SlidingImage_Adapter extends PagerAdapter {


    private ArrayList<Integer> IMAGES;
    private LayoutInflater inflater;
    private ArrayList<String> TitleArray;
    private ArrayList<String> DescriptionArray;
    private Context context;


    public SlidingImage_Adapter(Context context, ArrayList<Integer> IMAGES, ArrayList<String> TitleArray, ArrayList<String> DescriptionArray) {
        this.context = context;
        this.IMAGES = IMAGES;
        this.TitleArray = TitleArray;
        this.DescriptionArray = DescriptionArray;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);
        ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.tutorialImg);
        TextView title = (TextView) imageLayout
                .findViewById(R.id.title);
        TextView details = (TextView) imageLayout
                .findViewById(R.id.details);
        imageView.setImageResource(IMAGES.get(position));
        title.setText(TitleArray.get(position));
        details.setText(DescriptionArray.get(position));
        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}