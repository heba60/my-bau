package com.uni.bau.ui.Activites;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.bau.R;
import com.uni.bau.application.MyApplication;
import com.uni.bau.models.DetInfoDetItem;
import com.uni.bau.models.DetInfoMainItem;
import com.uni.bau.models.HealthModel;
import com.uni.bau.models.SoapRequestHandler;
import com.uni.bau.ui.Adapters.HealthBodyAdapter;
import com.uni.bau.ui.Adapters.MainVacationAdapter;
import com.uni.bau.ui.Adapters.VacationBodyAdapter;
import com.uni.bau.utilities.ProgressUtil;
import com.uni.bau.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class VacationActivity extends AppCompatActivity {
    private List<String> mList = new ArrayList<>();
    ImageView ivBack;
    RecyclerView calendarRecycler;
    RecyclerView recycler;
    LinearLayout view;

    private static final String SOAP_ACTION = "https://bauwebservice.bau.edu.jo/GetVLInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.updateLanguage(this);
        setContentView(R.layout.activity_vaction);
        view = (LinearLayout) findViewById(R.id.view);
        calendarRecycler = (RecyclerView) findViewById(R.id.recyclerBodyClassifications);
        calendarRecycler.setLayoutManager(new LinearLayoutManager(this));

        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        callApi();

    }

    private void callApi() {
        ProgressUtil.INSTANCE.showLoading(this);
        SoapRequestHandler soapRequestHandler = new SoapRequestHandler();
        String usageKey = "balqa2023";
        String userId = new Utils().getUserData(this).getUsername().toString();
        soapRequestHandler.sendSoapRequest(usageKey, userId, "", SOAP_ACTION, new SoapRequestHandler.SoapRequestListener() {
            @Override
            public void onSoapRequestComplete(String value) {
                if (value != null) {
                    try {
                        ProgressUtil.INSTANCE.hideLoading();
                        view.setVisibility(View.VISIBLE);
                        mList = new ArrayList<>();
                        String result = "";
                        try {
                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            factory.setNamespaceAware(true);
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            Document doc = builder.parse(new InputSource(new StringReader(value)));
                            result = doc.getElementsByTagName("GetVLInfoResult").item(0).getChildNodes().item(0).getNodeValue();
                            handleJSON(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void handleJSON(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            // Extract "detInfoDet" and "detInfoMain" objects
            JSONObject detInfoDet = jsonObject.getJSONObject("detInfoDet");
            JSONObject detInfoMain = jsonObject.getJSONObject("detInfoMain");

            List<DetInfoDetItem> detInfoDetList = new ArrayList<>();
            List<DetInfoMainItem> detInfoMainList = new ArrayList<>();

            // Populate detInfoDetList
            for (int i = 0; ; i++) {
                String key = "detInfo_" + i;
                if (detInfoDet.has(key)) {
                    JSONObject detInfo = detInfoDet.getJSONObject(key);
                    String detType = detInfo.getString("detInfo_DLEV_DET_TYPE_NAME");
                    String endDate = detInfo.getString("detInfo_DLEV_END_DATE2");
                    String startDate = detInfo.getString("detInfo_DLEV_START_DATE2");
                    String period = detInfo.getString("detInfo_DLEV_PERIOD");
                    DetInfoDetItem item = new DetInfoDetItem(detType, endDate, startDate, period);
                    detInfoDetList.add(item);
                } else {
                    break;
                }
            }

            // Populate detInfoMainList
            for (int i = 0; ; i++) {
                String key = "mainInfo_" + i;
                if (detInfoMain.has(key)) {
                    JSONObject mainInfo = detInfoMain.getJSONObject(key);
                    String lokDesc = mainInfo.getString("genInfo_LOK_DESC_A");
                    String usedBalance = mainInfo.getString("genInfo_LEV_USED_BALANCE");
                    String remainBalance = mainInfo.getString("genInfo_LEV_REMAIN_BALANCE");
                    String yearlyBalance = mainInfo.getString("genInfo_LEV_YEARLY_BALANCE");
                    String year = mainInfo.getString("genInfo_LEV_YEAR");
                    DetInfoMainItem item = new DetInfoMainItem(lokDesc, usedBalance, remainBalance, yearlyBalance, year);
                    detInfoMainList.add(item);
                } else {
                    break;
                }
            }
            calendarRecycler.setAdapter(new MainVacationAdapter(this,detInfoMainList));
            recycler.setAdapter(new VacationBodyAdapter(this,detInfoDetList));

            // Now, you have populated lists for "detInfoDet" and "detInfoMain"
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}