package com.uni.bau.ui.Fragments;

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
import com.uni.bau.models.CalendarModel;
import com.uni.bau.ui.Adapters.CalendarAdapter;
import com.uni.bau.utilities.CustomTastyToast;
import com.uni.bau.utilities.ProgressUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class StuCalenderFragment extends Fragment {

    private List<CalendarModel> mList = new ArrayList<>();
    RecyclerView calendarRecycler;
    View rootView;
    public StuCalenderFragment() {
    }

    public static StuCalenderFragment newInstance() {
        return new StuCalenderFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_stu_calender, container, false);
        calendarRecycler = (RecyclerView) rootView.findViewById(R.id.calendarRecycler);
        setCalenderAPI();
        return rootView;
    }
    private void setCalenderAPI()
    {
        ProgressUtil.INSTANCE.showLoading(requireActivity());
        new BusinessManager().getCalenderAPI(requireActivity(), new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                ProgressUtil.INSTANCE.hideLoading();
                Document html = Jsoup.parse(responseObject.toString());

                Elements elements = html.getElementById("Details_Details_lblres").child(0).child(0).children();
                if (elements == null)
                {
                    return;
                }
                for (Element element: elements){
                    CalendarModel calendarModel = new CalendarModel();
                    calendarModel.setDay(element.child(1).text());
                    calendarModel.setDate(element.child(0).text());
                    calendarModel.setEvent(element.child(2).text());
                    mList.add(calendarModel);
                }
                ProgressUtil.INSTANCE.hideLoading();
                if (!mList.isEmpty()) {
                    mList.remove(0);
                    calendarRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
                    calendarRecycler.setAdapter(new CalendarAdapter(requireActivity(), mList));
                }else {
                    CustomTastyToast.makeText(requireActivity(), "حدث خطا يرجى المحاولة لاحقا", CustomTastyToast.LENGTH_LONG, CustomTastyToast.ERROR).show();
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