package com.uni.bau.ui.Activites;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.bau.R;
import com.uni.bau.application.MyApplication;
import com.uni.bau.models.SoapRequestHandler;
import com.uni.bau.ui.Adapters.AdapterSalarry;
import com.uni.bau.ui.Adapters.AttenndEmpHistoryAdapter;
import com.uni.bau.utilities.ProgressUtil;
import com.uni.bau.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class EmployeeAttendaceActivity extends AppCompatActivity {
    private List<String> mList = new ArrayList<>();
    ImageView ivBack;
    RecyclerView calendarRecycler;

    Spinner spinnerYear;
    Spinner spinnerMonth;

    private List<String> years = new ArrayList();
    private List<String> months = new ArrayList();

    String selectedYear = "";
    String selectedMonth = "";
    private static final String SOAP_ACTION = "https://bauwebservice.bau.edu.jo/GetEmpAttendenceArchiveJSON";

    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.updateLanguage(this);
        setContentView(R.layout.employe);
        title = (TextView) findViewById(R.id.title);
        title.setText("بصمة الموظف");
        spinnerYear = (Spinner) findViewById(R.id.spinnerYear);
        spinnerMonth = (Spinner) findViewById(R.id.spinnerMonth);
        calendarRecycler = (RecyclerView) findViewById(R.id.calendarRecycler);
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
                    if (i != 0)
                    {
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

    private void callApi()
    {
        ProgressUtil.INSTANCE.showLoading(this);
        SoapRequestHandler soapRequestHandler = new SoapRequestHandler();
        String usageKey = "balqa2023";
        String userId = new Utils().getUserData(this).getUsername().toString();
        String salaryDate = selectedMonth+"-"+""+selectedYear;
        soapRequestHandler.sendSoapRequest(usageKey, userId, salaryDate,SOAP_ACTION, new SoapRequestHandler.SoapRequestListener() {
            @Override
            public void onSoapRequestComplete(String value) {
                if (value != null) {
                    try {
                        mList = new ArrayList<>();
                        String result = "";
                        try {
                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            factory.setNamespaceAware(true);
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            Document doc = builder.parse(new InputSource(new StringReader(value)));
                            result = doc.getElementsByTagName("GetEmpAttendenceArchiveJSONResult").item(0).getChildNodes().item(0).getNodeValue();

                            try {
                                JSONArray attendanceArray = new JSONArray(result);
                                calendarRecycler.setAdapter(new AttenndEmpHistoryAdapter(EmployeeAttendaceActivity.this,attendanceArray));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        ProgressUtil.INSTANCE.hideLoading();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }


}