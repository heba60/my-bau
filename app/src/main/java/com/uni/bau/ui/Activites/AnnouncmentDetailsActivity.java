package com.uni.bau.ui.Activites;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.uni.bau.R;
import com.uni.bau.application.MyApplication;
import com.uni.bau.utilities.MyClient;


public class AnnouncmentDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.updateLanguage(this);
        setContentView(R.layout.annsoucment);
        TextView details = findViewById(R.id.details);
        String url = getIntent().getStringExtra("webLink");
        details.setText(Html.fromHtml(url));
    }
}