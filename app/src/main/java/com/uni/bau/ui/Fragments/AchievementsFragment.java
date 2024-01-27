package com.uni.bau.ui.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.bau.R;
import com.uni.bau.managers.ApiCallResponse;
import com.uni.bau.managers.BusinessManager;
import com.uni.bau.models.MarksApiModel;
import com.uni.bau.models.MarksModel;
import com.uni.bau.models.PlanInfo;
import com.uni.bau.models.StudentClassificationsModel;
import com.uni.bau.ui.Activites.MainActivity;
import com.uni.bau.ui.Adapters.ExpandPlanAdapter;
import com.uni.bau.ui.Adapters.MainMarksAdapter;
import com.uni.bau.ui.Adapters.MarksAdapter;
import com.uni.bau.ui.Adapters.StudentClassificationHeaderAdapter;
import com.uni.bau.utilities.LoginingHandler;
import com.uni.bau.utilities.ProgressUtil;
import com.uni.bau.utilities.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AchievementsFragment extends Fragment {
    Activity activity;
    View view;
   RecyclerView recyclerHeaderClassifications;

    public AchievementsFragment() {
    }

    public static AchievementsFragment newInstance() {
        return new AchievementsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_achievements, container, false);
        recyclerHeaderClassifications = (RecyclerView) view.findViewById(R.id.recyclerHeaderClassifications);

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
    }


    String getErrorLogin() {
        return getString(R.string.errorLogin);
    }

    String getErrorLogin2() {
        return getString(R.string.errorLogin2);
    }

    String getNoPlanError() {
        return getString(R.string.noPlan);
    }

    private void getStudentClassifications() {
        ProgressUtil.INSTANCE.showLoading(MainActivity.mainActivity);
        new BusinessManager().getStudentClassifications(getActivity(), new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                ProgressUtil.INSTANCE.hideLoading();

                StudentClassificationsModel[] studentClassificationsModel = (StudentClassificationsModel[]) responseObject;

                ArrayList<StudentClassificationsModel> list = new ArrayList<StudentClassificationsModel>();
                list.addAll(Arrays.asList(studentClassificationsModel));
                recyclerHeaderClassifications.setLayoutManager(new LinearLayoutManager(requireActivity()));
               recyclerHeaderClassifications.setAdapter(new StudentClassificationHeaderAdapter(requireActivity(),list));
            }
            @Override
            public void onFailure(String errorResponse) {
                ProgressUtil.INSTANCE.hideLoading();
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            getStudentClassifications();
        } else {
        }
    }

}