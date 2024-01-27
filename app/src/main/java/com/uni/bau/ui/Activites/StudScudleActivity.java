package com.uni.bau.ui.Activites;

import static com.uni.bau.helpers.ApiConstants.uniNews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.uni.bau.R;
import com.uni.bau.managers.ApiCallResponse;
import com.uni.bau.managers.ConnectionManager;
import com.uni.bau.ui.Adapters.AttenndEmpHistoryAdapter;
import com.uni.bau.ui.Adapters.StudentScudlaeAdapter;
import com.uni.bau.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

public class StudScudleActivity extends AppCompatActivity {
    RecyclerView calendarRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_scudle);
        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        calendarRecycler = (RecyclerView) findViewById(R.id.calendarRecycler);
        calendarRecycler.setLayoutManager(new LinearLayoutManager(this));

      //  calendarRecycler.setVisibility(View.INVISIBLE);


        String url = "https://bauwebservice.bau.edu.jo/BauWebAcademicServices.asmx/GetBauAcademicJSON?usageKey=balqa2023&serviceName=STUDENT_REGISTRATION&param="+ new Utils().getUserData(this).getUsername();
        ConnectionManager.GET(this, url, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                try {
                    ProgressBar Bar=findViewById(R.id.Bar);
                    Bar.setVisibility(View.GONE);





                    parseCourses(responseObject.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorResponse) {
            }
        });
    }

    public  void parseCourses(String xmlString) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlString));
            int eventType = parser.getEventType();
            String jsonString = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && "string".equals(parser.getName())) {
                    parser.next(); // Move to text inside <string> element
                    jsonString = parser.getText();
                    break;
                }
                eventType = parser.next();
            }


            JSONArray courseArray = new JSONArray(jsonString);
            calendarRecycler.setAdapter(new StudentScudlaeAdapter(StudScudleActivity.this,courseArray));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}