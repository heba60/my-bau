package com.uni.bau.ui.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.uni.bau.R;
import com.uni.bau.application.MyApplication;
import com.uni.bau.helpers.SharedPrefConstants;
import com.uni.bau.helpers.SharedPreferencesHelper;
import com.uni.bau.managers.ApiCallResponse;
import com.uni.bau.managers.BusinessManager;
import com.uni.bau.managers.UniBusinessManager;
import com.uni.bau.models.AnouncementModel;
import com.uni.bau.models.GetStudentLecutresModel;
import com.uni.bau.models.ScheduleModel;
import com.uni.bau.models.UnReadCount;
import com.uni.bau.models.UniLoginModel;
import com.uni.bau.models.UserProfileModel;
import com.uni.bau.ui.Activites.NotificationActivity;
import com.uni.bau.ui.Activites.StuCalenderActivity;
import com.uni.bau.ui.Adapters.HomePageRecyclerViewAdapter;
import com.uni.bau.ui.Adapters.ImageSlideAdapter;
import com.uni.bau.utilities.CustomTastyToast;
import com.uni.bau.utilities.LoginingHandler;
import com.uni.bau.utilities.MyClient;
import com.uni.bau.utilities.ParsingUtils;
import com.uni.bau.utilities.ProgressUtil;
import com.uni.bau.utilities.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DrHomeFragment extends Fragment {
    Activity activity;
    View rootView;
    RecyclerView recyclerHome;
    List<ScheduleModel> scheduleModelsArrayList = new ArrayList<>();
    public static List<GetStudentLecutresModel.Datum> lecutresModel = new ArrayList<>();
    List<AnouncementModel> listNews = new ArrayList<>();
    ImageView noticicationClick;
    ImageView uniwebSite;
    HomePageRecyclerViewAdapter adapter;
    public static int year = 0;
    public static int semster = 0;
    TextView dateTime;
    TextView uniNews;
    TextView annownsment;
    SwipeRefreshLayout swipeRefreshHome;
    ViewPager imagesViewPager;
    List<AnouncementModel> list = new ArrayList<>();

    public DrHomeFragment() {
    }

    public static DrHomeFragment newInstance() {
        return new DrHomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerHome = (RecyclerView) rootView.findViewById(R.id.recyclerHome);
        noticicationClick = (ImageView) rootView.findViewById(R.id.noticicationClick);
        uniwebSite = (ImageView) rootView.findViewById(R.id.uniwebSite);
        dateTime = (TextView) rootView.findViewById(R.id.dateTime);
        annownsment = (TextView) rootView.findViewById(R.id.annownsment);
        uniNews = (TextView) rootView.findViewById(R.id.uniNews);
        imagesViewPager = (ViewPager) rootView.findViewById(R.id.imagesViewPager);
        swipeRefreshHome = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshHome);
        setNewsSlider();
        try {
            String weekday_name = new SimpleDateFormat("EEEE", Locale.forLanguageTag("ar")).format(System.currentTimeMillis());
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.forLanguageTag("ar"));
            String formattedDate = df.format(c);
            dateTime.setText(weekday_name + "  " + formattedDate);
            dateTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), StuCalenderActivity.class));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        noticicationClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.startActivity(new Intent(getActivity(), NotificationActivity.class));
            }
        });
        uniwebSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://app1.bau.edu.jo:7799/reg_new/index.jsp"));
                activity.startActivity(browserIntent);
            }
        });
        swipeRefreshHome.setRefreshing(true);
        swipeRefreshHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshHome.setRefreshing(false);
            }
        });
        swipeRefreshHome.setVisibility(View.GONE);
        annownsment.setVisibility(View.GONE);
        getUnReadCount();
        return rootView;
    }

    @Override
    public void onResume() {
        MyApplication.updateLanguage(requireActivity());
        super.onResume();
    }

    private void setNewsSlider() {
        new BusinessManager().getNewsAPI(getActivity(), new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                list = new ArrayList<>();
                list.addAll(new ParsingUtils().getNews(responseObject.toString()));
                ImageSlideAdapter imageSlideAdapter = new ImageSlideAdapter(getActivity(), list);
                imagesViewPager.setAdapter(imageSlideAdapter);
                imagesViewPager.setRotationY(180);
                recyclerHome.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onFailure(String errorResponse) {
            }
        });
    }


    private void getNewsDataPaging() {
        new BusinessManager().getNewsAPIPaging(getActivity(), new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {

                list.addAll(new ParsingUtils().getNews(responseObject.toString()));
                ImageSlideAdapter imageSlideAdapter = new ImageSlideAdapter(getActivity(), list);
                imagesViewPager.setAdapter(imageSlideAdapter);
                imagesViewPager.setRotationY(180);
            }
            @Override
            public void onFailure(String errorResponse) {

            }
        });
    }
    private void getUnReadCount() {
        new UniBusinessManager().getUnReadCount(getActivity(), new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                UnReadCount unReadCount = (UnReadCount) responseObject;
                if (unReadCount.getData() != 0) {
                    noticicationClick.setImageResource(R.drawable.notification_red);
                } else {
                    noticicationClick.setImageResource(R.drawable.notificaiton_icon);
                }
            }
            @Override
            public void onFailure(String errorResponse) {

            }
        });
    }
}