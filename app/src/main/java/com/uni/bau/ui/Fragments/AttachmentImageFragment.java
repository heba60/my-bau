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
import com.uni.bau.managers.UniBusinessManager;
import com.uni.bau.models.AttachmentModel;
import com.uni.bau.ui.Adapters.FilesAdapter;
import com.uni.bau.ui.Adapters.ImagesAdapter;
import com.uni.bau.ui.Adapters.LinksAdapter;
import com.uni.bau.utilities.ProgressUtil;

public class AttachmentImageFragment extends Fragment {

    Activity activity;
    View rootView;
    RecyclerView newsAnnocumentReyclerView;
    String lecID = "";
    String type = "";

    public AttachmentImageFragment() {
    }

    public static AttachmentImageFragment newInstance() {
        return new AttachmentImageFragment();
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
        newsAnnocumentReyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        lecID = getArguments().getString("lecID");
        type = getArguments().getString("typeID");
        getAttachment();
        return rootView;
    }

    private void getAttachment() {

        new UniBusinessManager().getAttachemnt(getActivity(), lecID, type, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                try {
                    ProgressUtil.INSTANCE.hideLoading();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                AttachmentModel attachmentModel = (AttachmentModel) responseObject;
                if (type.equals("1")) {
                    newsAnnocumentReyclerView.setAdapter(new ImagesAdapter(getActivity(), attachmentModel.getData()));
                } else if (type.equals("2")) {
                    newsAnnocumentReyclerView.setAdapter(new FilesAdapter(getActivity(), attachmentModel.getData()));
                } else if (type.equals("3")) {
                    newsAnnocumentReyclerView.setAdapter(new LinksAdapter(getActivity(), attachmentModel.getData()));
                }

            }

            @Override
            public void onFailure(String errorResponse) {
                try {
                    ProgressUtil.INSTANCE.hideLoading();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onResume() {
        MyApplication.updateLanguage(requireActivity());
        super.onResume();
    }


}