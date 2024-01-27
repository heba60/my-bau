package com.uni.bau.ui.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
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
import com.uni.bau.models.OnLoadMoreListener;
import com.uni.bau.ui.Adapters.LoadMoreNewsAdapter;
import com.uni.bau.utilities.ParsingUtils;
import com.uni.bau.utilities.ProgressUtil;

import java.util.ArrayList;
import java.util.List;


public class NewsFragment extends Fragment implements OnLoadMoreListener {

    Activity activity;
    View rootView;
    RecyclerView newsAnnocumentReyclerView;
    List<AnouncementModel> list = new ArrayList<>();
    LoadMoreNewsAdapter adapter;
    int page = 2;
    OnLoadMoreListener onLoadMoreListener = (OnLoadMoreListener) this;
    public NewsFragment() {
    }

    public static NewsFragment newInstance() {
        return new NewsFragment();
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
        getNewsData();
        newsAnnocumentReyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new LoadMoreNewsAdapter(getActivity(), list, newsAnnocumentReyclerView);
        newsAnnocumentReyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(onLoadMoreListener);
        return rootView;
    }

    private void getNewsData() {
        ProgressUtil.INSTANCE.showLoading(getActivity());
        new BusinessManager().getNewsAPI(getActivity(), new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                list = new ArrayList<>();
                list.addAll(new ParsingUtils().getNews(responseObject.toString()));
                adapter = new LoadMoreNewsAdapter(getActivity(), list, newsAnnocumentReyclerView);
                newsAnnocumentReyclerView.setAdapter(adapter);
                try {
                    ProgressUtil.INSTANCE.hideLoading();
                } catch (Exception e) {
                    e.printStackTrace();
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

    private void getNewsDataPaging() {
        new BusinessManager().getNewsAPIPaging(getActivity(), new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                try {
                    ProgressUtil.INSTANCE.hideLoading();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                list.addAll(new ParsingUtils().getNews(responseObject.toString()));
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

    private void getNewsDataPagingMore() {
        new BusinessManager().getNewsAPIPagingAPI(getActivity(),page+"", new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                try {
                    ProgressUtil.INSTANCE.hideLoading();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                list.remove(list.size() - 1);
                adapter.notifyItemRemoved(list.size());

                list.addAll(new ParsingUtils().getNews(responseObject.toString()));
                adapter.notifyDataSetChanged();
                adapter.setLoaded();
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

    @Override
    public void onLoadMore() {
        list.add(null);
        adapter.notifyDataSetChanged();
        adapter.setOnLoadMoreListener(onLoadMoreListener);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    page++;
                    getNewsDataPagingMore();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1000);

    }
}