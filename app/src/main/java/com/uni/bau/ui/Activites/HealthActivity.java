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

import com.google.gson.JsonObject;
import com.uni.bau.R;
import com.uni.bau.application.MyApplication;
import com.uni.bau.models.GetChatModel;
import com.uni.bau.models.HealthModel;
import com.uni.bau.models.SoapRequestHandler;
import com.uni.bau.ui.Adapters.AdapterSalarry;
import com.uni.bau.ui.Adapters.HealthBodyAdapter;
import com.uni.bau.utilities.ProgressUtil;
import com.uni.bau.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class HealthActivity extends AppCompatActivity {
    private List<String> mList = new ArrayList<>();
    ImageView ivBack;
    RecyclerView calendarRecycler;

    Spinner spinnerYear;
    Spinner spinnerMonth;

    LinearLayout view;
    private List<String> years = new ArrayList();
    private List<String> months = new ArrayList();

    String selectedYear = "";
    String selectedMonth = "";
    private static final String SOAP_ACTION = "https://bauwebservice.bau.edu.jo/GetHealth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.updateLanguage(this);
        setContentView(R.layout.activity_health);
        view = (LinearLayout) findViewById(R.id.view);
        spinnerYear = (Spinner) findViewById(R.id.spinnerYear);
        spinnerMonth = (Spinner) findViewById(R.id.spinnerMonth);
        calendarRecycler = (RecyclerView) findViewById(R.id.recyclerBodyClassifications);
        calendarRecycler.setLayoutManager(new LinearLayoutManager(this));
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        years.add("إختر السنة");
        years.add("2024");
        years.add("2023");
        years.add("2022");
        years.add("2021");
        years.add("2020");

        months.add("إختر الشهر");
        months.add("01");
        months.add("02");
        months.add("03");
        months.add("04");
        months.add("05");
        months.add("06");
        months.add("07");
        months.add("08");
        months.add("09");
        months.add("10");
        months.add("11");
        months.add("12");
        spinnerYear.setAdapter(new ArrayAdapter<String>(this, R.layout.row_marks_spinner, years));
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    if (i != 0)
                        selectedYear = years.get(i).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinnerMonth.setAdapter(new ArrayAdapter<String>(this, R.layout.row_marks_spinner, months));
        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    if (i != 0) {
                        selectedMonth = months.get(i).toString();
                        callApi();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private void callApi() {
        ProgressUtil.INSTANCE.showLoading(this);
        SoapRequestHandler soapRequestHandler = new SoapRequestHandler();
        String usageKey = "balqa2023";
        String userId = new Utils().getUserData(this).getUsername().toString();
        String salaryDate = selectedMonth + "-" + "" + selectedYear;
        soapRequestHandler.sendSoapRequest(usageKey, userId, salaryDate, SOAP_ACTION, new SoapRequestHandler.SoapRequestListener() {
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
                            result = doc.getElementsByTagName("GetHealthResult").item(0).getChildNodes().item(0).getNodeValue();
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
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONObject detResults = jsonObject.getJSONObject("detResults");
                JSONArray detInfoArray = new JSONArray();
                for (int i = 0; ; i++) {
                    String key = "detInfo_" + i;
                    if (detResults.has(key)) {
                        detInfoArray.put(detResults.getJSONObject(key));
                    } else {
                        break; // Exit the loop when there are no more "detInfo" keys
                    }
                }
                List<HealthModel> detInfoList = new ArrayList<>();
                for (int i = 0; i < detInfoArray.length(); i++) {
                    JSONObject detInfo = detInfoArray.getJSONObject(i);
                    String mptypeDesc = detInfo.getString("detInfo_mptype_desc");
                    String subsNetAmt = detInfo.getString("detInfo_subs_net_amt");
                    String patDesc = detInfo.getString("detInfo_pat_desc");
                    String clmDate = detInfo.getString("detInfo_clm_date");
                    HealthModel detInfoItem = new HealthModel(mptypeDesc, subsNetAmt, patDesc, clmDate);
                    detInfoList.add(detInfoItem);
                }
                calendarRecycler.setAdapter(new HealthBodyAdapter(this,detInfoList) );
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}