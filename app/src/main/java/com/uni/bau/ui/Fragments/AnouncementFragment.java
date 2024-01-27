package com.uni.bau.ui.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.bau.R;
import com.uni.bau.application.MyApplication;
import com.uni.bau.managers.ApiCallResponse;
import com.uni.bau.managers.BusinessManager;
import com.uni.bau.models.AnouncementModel;
import com.uni.bau.utilities.ParsingUtils;

import java.util.List;


public class AnouncementFragment extends Fragment {

    Activity activity;
    View rootView;
    RecyclerView newsAnnocumentReyclerView;
    public AnouncementFragment() {
    }

    public static AnouncementFragment newInstance() {
        return new AnouncementFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_news, container, false);
        newsAnnocumentReyclerView = (RecyclerView) rootView.findViewById(R.id.newsAnnocumentReyclerView);
//        getNewsData();
        return rootView;
    }

    private void getNewsData() {
        new BusinessManager().getAnouncmentAPI(getActivity(), new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                List<AnouncementModel> list = new ParsingUtils().getAnnouncement(responseObject.toString());
                if (!list.isEmpty()) {
//                    AnoucmentAdapter adapter = new AnoucmentAdapter(getActivity(), list);
//                    newsAnnocumentReyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//                    newsAnnocumentReyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(String errorResponse) {

            }
        });
    }

    @Override
    public void onResume() {
        MyApplication.updateLanguage(requireActivity());
        super.onResume();
    }



}