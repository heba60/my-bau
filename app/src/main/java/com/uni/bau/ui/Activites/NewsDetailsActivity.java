package com.uni.bau.ui.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;
import com.uni.bau.R;
import com.uni.bau.application.MyApplication;
import com.uni.bau.managers.ApiCallResponse;
import com.uni.bau.managers.BusinessManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class NewsDetailsActivity extends AppCompatActivity {
    ShimmerFrameLayout mShimmerViewContainer;
    ImageView ivBack;
    ImageView shareICon;
    ImageView img;
    TextView newsTitle;
    TextView newsDesc;
    String newsURl = "";
    String newsTitleString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.updateLanguage(this);
        setContentView(R.layout.activity_news_details);
        mShimmerViewContainer = (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        mShimmerViewContainer.startShimmerAnimation();
        ivBack = (ImageView) findViewById(R.id.ivBack);
        shareICon = (ImageView) findViewById(R.id.shareICon);
        img = (ImageView) findViewById(R.id.img);
        newsTitle = (TextView) findViewById(R.id.newsTitle);
        newsDesc = (TextView) findViewById(R.id.newsDesc);
        newsURl = getIntent().getStringExtra("detailsUrl");
        newsTitleString = getIntent().getStringExtra("newsTitle");
        newsTitle.setText(newsTitleString);
        Picasso.get().load(getIntent().getStringExtra("newsimg")).into(img);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        shareICon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "");
                    i.putExtra(Intent.EXTRA_TEXT, newsURl);
                    startActivity(Intent.createChooser(i, ""));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        setNewsDetails();

    }

    private void setNewsDetails() {

        new BusinessManager().getNewsDetailsAPI(this, newsURl, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                mShimmerViewContainer.setVisibility(View.GONE);
                Document html = Jsoup.parse(responseObject.toString());
                String desc = "";
                try {
                    desc =  html.getElementsByClass("newsText").get(1).text();
                }catch (Exception r){
                }
                newsDesc.setText(desc);
            }
            @Override
            public void onFailure(String errorResponse) {

            }
        });
    }
}