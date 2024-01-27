package com.uni.bau.ui.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.uni.bau.R;
import com.uni.bau.application.MyApplication;
import com.uni.bau.managers.ApiCallResponse;
import com.uni.bau.managers.UniBusinessManager;
import com.uni.bau.models.JobsModel;
import com.uni.bau.ui.Adapters.JobsAdapter;
import com.uni.bau.utilities.ProgressUtil;

public class JobsActivity extends AppCompatActivity {

    RecyclerView jobsRecyclerView;
    ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.updateLanguage(this);
        setContentView(R.layout.activity_jobs);
        jobsRecyclerView = (RecyclerView) findViewById(R.id.jobsRecyclerView);
        jobsRecyclerView.setLayoutManager(new LinearLayoutManager(JobsActivity.this));
        getJobs();
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getJobs ()
    {
        ProgressUtil.INSTANCE.showLoading(JobsActivity.this);
        new UniBusinessManager().getJobs(JobsActivity.this, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                ProgressUtil.INSTANCE.hideLoading();
                JobsModel jobsModel = (JobsModel) responseObject;
                try {
                    jobsRecyclerView.setAdapter(new JobsAdapter(JobsActivity.this,jobsModel.getData()));
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