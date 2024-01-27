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

public class StuCalenderActivity extends AppCompatActivity {
    private List<CalendarModel> mList = new ArrayList<>();
    RecyclerView calendarRecycler;
    ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.updateLanguage(this);
        setContentView(R.layout.activity_stu_calender);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        calendarRecycler = (RecyclerView) findViewById(R.id.calendarRecycler);
        setCalenderAPI();
    }
    private void setCalenderAPI()
    {
        ProgressUtil.INSTANCE.showLoading(StuCalenderActivity.this);
        new BusinessManager().getCalenderAPI(StuCalenderActivity.this, new ApiCallResponse() {
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
                    calendarRecycler.setLayoutManager(new LinearLayoutManager(StuCalenderActivity.this));
                    calendarRecycler.setAdapter(new CalendarAdapter(StuCalenderActivity.this, mList));
                }else {
                    CustomTastyToast.makeText(StuCalenderActivity.this, "حدث خطا يرجى المحاولة لاحقا", CustomTastyToast.LENGTH_LONG, CustomTastyToast.ERROR).show();
                }
            }

            @Override
            public void onFailure(String errorResponse) {

            }
        });
    }
}