package com.uni.bau.ui.Activites;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.uni.bau.R;
import com.uni.bau.application.MyApplication;
import com.uni.bau.helpers.SharedPrefConstants;
import com.uni.bau.helpers.SharedPreferencesHelper;
import com.uni.bau.managers.ApiCallResponse;
import com.uni.bau.managers.BusinessManager;
import com.uni.bau.models.Education;
import com.uni.bau.models.NewsLitterModel;
import com.uni.bau.models.StudentData;
import com.uni.bau.models.UserProfileModel;
import com.uni.bau.ui.Adapters.NewsLitterBodyAdapter;
import com.uni.bau.ui.Fragments.AnouncementFragment;
import com.uni.bau.ui.Fragments.NewsFragment;

import java.util.ArrayList;
import java.util.List;

public class NewsLitterActivity extends AppCompatActivity {

    Spinner spinnerLevel;
    Spinner spinnerCollage;
    Spinner spinnerDep;

    private List<String> mLevels;
    Education[] education;
    String educationID = "";

    private List<String> mCollage;
    Education[] educationCollage;
    String collageID = "";

    private List<String> mDeps;
    Education[] DepsArray;
    String depsID = "";

    RecyclerView marksRecyclerView;

    View viewId;
    LinearLayout sectionView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.updateLanguage(this);
        setContentView(R.layout.acitivity_news_litter);
        spinnerLevel = (Spinner) findViewById(R.id.spinnerLevel);
        spinnerCollage = (Spinner) findViewById(R.id.spinnerCollage);
        spinnerDep = (Spinner) findViewById(R.id.spinnerDep);
        marksRecyclerView = (RecyclerView) findViewById(R.id.marksRecyclerView);
        viewId = (View) findViewById(R.id.viewId);
        sectionView = (LinearLayout) findViewById(R.id.sectionView);
        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        marksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getLevels();
        getCollage();

     }
     private void getLevels()
     {
         new BusinessManager().getDegrees(this, new ApiCallResponse() {
             @Override
             public void onSuccess(Object responseObject, String responseMessage) {
                 education = (Education[]) responseObject;
                 mLevels = new ArrayList<>();
                 for (int count = 0 ; count < education.length ; count ++)
                 {
                     mLevels.add(education[count].getName());
                 }
                 spinnerLevel.setAdapter(new ArrayAdapter<String>(NewsLitterActivity.this, R.layout.row_marks_spinner, mLevels));
                 spinnerLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                     @Override
                     public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                         try {
                             educationID = education[i].getId();
                             Log.d("educationID",educationID);
                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                     }

                     @Override
                     public void onNothingSelected(AdapterView<?> adapterView) {

                     }
                 });
             }
             @Override
             public void onFailure(String errorResponse) {
             }
         });
     }

     private void getCollage()
     {
         new BusinessManager().getColleges(this, new ApiCallResponse() {
             @Override
             public void onSuccess(Object responseObject, String responseMessage) {
                 educationCollage = (Education[]) responseObject;
                 mCollage = new ArrayList<>();
                 for (int count = 0 ; count < educationCollage.length ; count ++)
                 {
                     mCollage.add(educationCollage[count].getName());
                 }
                 spinnerCollage.setAdapter(new ArrayAdapter<String>(NewsLitterActivity.this, R.layout.row_marks_spinner, mCollage));
                 spinnerCollage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                     @Override
                     public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                         try {
                             collageID = educationCollage[i].getId();
                             getDeps(collageID);
                             Log.d("collageID",collageID);
                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                     }

                     @Override
                     public void onNothingSelected(AdapterView<?> adapterView) {

                     }
                 });
             }
             @Override
             public void onFailure(String errorResponse) {
             }
         });
     }

     private void getDeps(String collageID)
     {
         new BusinessManager().getDeps(this,collageID, new ApiCallResponse() {
             @Override
             public void onSuccess(Object responseObject, String responseMessage) {
                 DepsArray = (Education[]) responseObject;
                 mDeps = new ArrayList<>();
                 for (int count = 0 ; count < DepsArray.length ; count ++)
                 {
                     mDeps.add(DepsArray[count].getName());
                 }
                 spinnerDep.setAdapter(new ArrayAdapter<String>(NewsLitterActivity.this, R.layout.row_marks_spinner, mDeps));
                 spinnerDep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                     @Override
                     public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                         try {
                             depsID = DepsArray[i].getId();
                             getCourse();
                             Log.d("depsID",depsID);
                         } catch (Exception e) {
                             e.printStackTrace();
                         }
                     }

                     @Override
                     public void onNothingSelected(AdapterView<?> adapterView) {

                     }
                 });
             }
             @Override
             public void onFailure(String errorResponse) {
             }
         });
     }

     private void getCourse()
     {
         new BusinessManager().getCourses(this,educationID,collageID,depsID, new ApiCallResponse() {
             @Override
             public void onSuccess(Object responseObject, String responseMessage) {
                 NewsLitterModel[] DepsArray = (NewsLitterModel[]) responseObject;
                 if (DepsArray.length > 0)
                 {
                     marksRecyclerView.setAdapter(new NewsLitterBodyAdapter(NewsLitterActivity.this,DepsArray));
                     viewId.setVisibility(View.VISIBLE);
                     sectionView.setVisibility(View.VISIBLE);
                 }else {
                     marksRecyclerView.setAdapter(new NewsLitterBodyAdapter(NewsLitterActivity.this,null));
                     viewId.setVisibility(View.GONE);
                     sectionView.setVisibility(View.GONE);
                     Toast.makeText(NewsLitterActivity.this, "لا يوجد بيانات", Toast.LENGTH_SHORT).show();

                 }

                 Log.d("ASDASD","ASDA");

             }
             @Override
             public void onFailure(String errorResponse) {
             }
         });
     }

}