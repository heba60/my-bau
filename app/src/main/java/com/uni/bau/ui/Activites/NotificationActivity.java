package com.uni.bau.ui.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.uni.bau.R;
import com.uni.bau.managers.ApiCallResponse;
import com.uni.bau.managers.UniBusinessManager;
import com.uni.bau.models.NotificationModel;
import com.uni.bau.ui.Adapters.NotifcationAdapter;
import com.uni.bau.utilities.ProgressUtil;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView jobsRecyclerView;
    ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        jobsRecyclerView = (RecyclerView) findViewById(R.id.jobsRecyclerView);
        jobsRecyclerView.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
        getNotification();
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getNotification ()
    {
        ProgressUtil.INSTANCE.showLoading(NotificationActivity.this);
        new UniBusinessManager().getNotification(NotificationActivity.this, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                ProgressUtil.INSTANCE.hideLoading();
                NotificationModel jobsModel = (NotificationModel) responseObject;
                try {
                    jobsRecyclerView.setAdapter(new NotifcationAdapter(NotificationActivity.this,jobsModel.getData()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorResponse) {
                ProgressUtil.INSTANCE.hideLoading();
            }
        });
    }
}