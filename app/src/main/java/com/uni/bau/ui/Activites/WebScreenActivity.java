package com.uni.bau.ui.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.uni.bau.R;
import com.uni.bau.application.MyApplication;
import com.uni.bau.helpers.SharedPrefConstants;
import com.uni.bau.helpers.SharedPreferencesHelper;
import com.uni.bau.managers.ApiCallResponse;
import com.uni.bau.managers.BusinessManager;
import com.uni.bau.models.StudentData;
import com.uni.bau.models.UserProfileModel;
import com.uni.bau.utilities.MyClient;

import java.net.CookiePolicy;


public class WebScreenActivity extends AppCompatActivity {
    WebView webView;
    String url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.updateLanguage(this);
        setContentView(R.layout.activity_web_screen);
         url = getIntent().getStringExtra("webLink");
         webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        CookieManager.getInstance().setAcceptCookie(true);
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                String javascript = "(function() {"
                        + "  var usernameInput = document.getElementById('usernameInputId');"
                        + "  var passwordInput = document.getElementById('passwordInputId');"
                        + "  var submitButton = document.getElementById('submitButtonId');"
                        + "  if (usernameInput && passwordInput && submitButton) {"
                        + "    usernameInput.value = '32208243160';"
                        + "    passwordInput.value = '2000850186';"
                        + "    submitButton.click();"
                        + "  }"
                        + "})();";

                webView.evaluateJavascript(javascript, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        // Handle the result if needed
                    }
                });
            }
        });

        webView.loadUrl(url);
    }

}