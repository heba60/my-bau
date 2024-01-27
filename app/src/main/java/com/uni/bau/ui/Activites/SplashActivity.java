package com.uni.bau.ui.Activites;

import static com.uni.bau.helpers.SharedPrefConstants.SponsorsKey;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uni.bau.R;
import com.uni.bau.application.MyApplication;
import com.uni.bau.helpers.SharedPreferencesHelper;
import com.uni.bau.managers.Api;
import com.uni.bau.managers.ApiCallResponse;
import com.uni.bau.managers.BusinessManager;
import com.uni.bau.managers.UniBusinessManager;
import com.uni.bau.models.CacheKeys;
import com.uni.bau.models.SponserModel;
import com.uni.bau.ui.Adapters.SponsorAdapter;
import com.uni.bau.utilities.MyClient;
import com.uni.bau.utilities.Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
public class SplashActivity extends AppCompatActivity {
    private FirebaseRemoteConfig fbRemoteConfig;
    RecyclerView sponserRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.updateLanguage(this);
        setContentView(R.layout.activity_splash);
        MyClient.reset();
        Api.init(this);
        sponserRecycler = (RecyclerView) findViewById(R.id.sponserRecycler);
        sponserRecycler.setLayoutManager(new GridLayoutManager(SplashActivity.this, 2));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Utils.isLogin(SplashActivity.this)) {
                    if (new Utils().getUserData(SplashActivity.this).getUserType() != null) {
                        if (new Utils().getUserData(SplashActivity.this).getUserType().equals("USER")) {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        } else {
                            startActivity(new Intent(SplashActivity.this, DrMainActivity.class));
                        }
                    }else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }, 2500);
        try {
            SponserModel sponserModel = (SponserModel) SharedPreferencesHelper.getSharedPreferencesObject(SplashActivity.this, SponsorsKey, SponserModel.class);
            if (sponserModel != null) {
                sponserRecycler.setAdapter(new SponsorAdapter(SplashActivity.this, sponserModel.getData()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setSponser();
        getCaptcha();
    }
    private void getCaptcha() {
        new BusinessManager().captchaAPI(SplashActivity.this, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                try {
                    Document html = Jsoup.parse(responseObject.toString());
                    String viewState = html.getElementById("j_id1:javax.faces.ViewState:0").attr("value");
                    new Utils().setCaptaValue(viewState, SplashActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(String errorResponse) {
            }
        });
    }

    private void setSponser() {
        new UniBusinessManager().getSponsors(SplashActivity.this, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                SponserModel jobsModel = (SponserModel) responseObject;
                try {
                    sponserRecycler.setAdapter(new SponsorAdapter(SplashActivity.this, jobsModel.getData()));
                    SharedPreferencesHelper.putSharedPreferencesObject(SplashActivity.this, SponsorsKey, jobsModel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(String errorResponse) {
            }
        });
    }
}