package com.uni.bau.ui.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.uni.bau.models.StudentData;
import com.uni.bau.ui.Activites.MainActivity;
import com.uni.bau.ui.Adapters.MainMarksAdapter;
import com.uni.bau.ui.Adapters.MarksAdapter;
import com.uni.bau.utilities.CustomTastyToast;
import com.uni.bau.utilities.LoginingHandler;
import com.uni.bau.utilities.ProgressUtil;
import com.uni.bau.utilities.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarksFragment extends Fragment {
    Activity activity;
    View view;
    RecyclerView marksRecyclerView;
    private Map<String, List<MarksModel>> mList = new HashMap<>();
    private List<String> mTitles;
    private Map<String, PlanInfo> infoMap = new HashMap<>();
    TextView noMakrs;

    LinearLayout Views;
    public MarksFragment() {
    }

    public static MarksFragment newInstance() {
        return new MarksFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_marks, container, false);
        noMakrs = (TextView) view.findViewById(R.id.noMakrs);
        marksRecyclerView = (RecyclerView) view.findViewById(R.id.marksRecyclerView);
        Views = (LinearLayout) view.findViewById(R.id.Views);
        marksRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getMarks() {
        ProgressUtil.INSTANCE.showLoading(MainActivity.mainActivity);
        new BusinessManager().getMarks(getActivity(), new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                ProgressUtil.INSTANCE.hideLoading();
                MarksApiModel[] uniLoginModel = (MarksApiModel[]) responseObject;
                marksRecyclerView.setAdapter(new MainMarksAdapter(getActivity(), uniLoginModel));
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
            getMarks();
        } else {
        }
    }

}