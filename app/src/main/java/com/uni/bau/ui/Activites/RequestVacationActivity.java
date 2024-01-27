package com.uni.bau.ui.Activites;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.bau.R;
import com.uni.bau.application.MyApplication;
import com.uni.bau.models.HealthModel;
import com.uni.bau.models.OnClickSucess;
import com.uni.bau.models.SoapRequestHandler;
import com.uni.bau.models.VacationSoapRequestHandler;
import com.uni.bau.ui.Adapters.HealthBodyAdapter;
import com.uni.bau.utilities.ProgressUtil;
import com.uni.bau.utilities.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class RequestVacationActivity extends AppCompatActivity {
    private List<String> mList = new ArrayList<>();
    ImageView ivBack;

    Spinner spinnerMonth;

    CheckBox accpet;
    LinearLayout view;
    private List<String> months = new ArrayList();

    String selectedYear = "";
    String VacLeaveType = "";
    private static final String SOAP_ACTION = "https://bauwebservice.bau.edu.jo/RequestVacLeave";

    EditText mangerName;
    String reqType = "vac";
    TextView toDate, fromDate;
    LinearLayout fromDateLinear, toDateLinear;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.updateLanguage(this);
        setContentView(R.layout.request_vacationn);
        mangerName = (EditText) findViewById(R.id.mangerName);
        toDate = (TextView) findViewById(R.id.toDate);
        fromDate = (TextView) findViewById(R.id.fromDate);
        view = (LinearLayout) findViewById(R.id.view);
        accpet = (CheckBox) findViewById(R.id.accpet);
        toDateLinear = (LinearLayout) findViewById(R.id.toDateLinear);
        fromDateLinear = (LinearLayout) findViewById(R.id.fromDateLinear);
        spinnerMonth = (Spinner) findViewById(R.id.spinnerMonth);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        months.add("نوع الإجازة");
        months.add("سنوية");
        months.add("رسمية");
        months.add("إدارية");
        months.add("عرضية بدون راتب");
        spinnerMonth.setAdapter(new ArrayAdapter<String>(this, R.layout.row_marks_spinner, months));
        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    if (i == 0) {
                        VacLeaveType = "";
                    } else {
                        VacLeaveType = i + "";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        fromDateLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDate();
            }
        });
        toDateLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDate();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromDate.getText().equals(""))
                {
                    ProgressUtil.INSTANCE.showWarningPopup(RequestVacationActivity.this,"يرجى تحديد تاريخ بداية الإجازة ");
                    return;
                }
                if (toDate.getText().equals(""))
                {
                    ProgressUtil.INSTANCE.showWarningPopup(RequestVacationActivity.this,"يرجى تحديد تاريخ نهاية الإجازة ");
                    return;
                }
                if (VacLeaveType.equals(""))
                {
                    ProgressUtil.INSTANCE.showWarningPopup(RequestVacationActivity.this,"يرجى تحديد نوع الإجازة ");
                    return;
                }
                if (mangerName.getText().equals(""))
                {
                    ProgressUtil.INSTANCE.showWarningPopup(RequestVacationActivity.this,"يرجى تحديد المسؤول عنك في العمل ");
                    return;
                }
                if (!accpet.isChecked())
                {
                    ProgressUtil.INSTANCE.showWarningPopup(RequestVacationActivity.this,"موافقة المسؤول المباشر");
                    return;
                }
                callApi();
            }
        });
    }


    private void fromDate() {
        int year = 0;
        int month = 0;
        int dayOfMonth = 0;

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, AlertDialog.THEME_HOLO_LIGHT,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String formatDate = dayOfMonth + "-" + (month + 1) + "-" + year;
                        fromDate.setText(formatDate);
                    }
                },
                year, month, dayOfMonth
        );
        calendar.add(Calendar.MONTH, 1);
        datePickerDialog.show();
    }

    private void toDate() {
        int year = 0;
        int month = 0;
        int dayOfMonth = 0;

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, AlertDialog.THEME_HOLO_LIGHT,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String formatDate = dayOfMonth + "-" + (month + 1) + "-" + year;
                        toDate.setText(formatDate);
                    }
                },
                year, month, dayOfMonth
        );
        calendar.add(Calendar.MONTH, 1);

        datePickerDialog.show();
    }

    private void callApi() {
        ProgressUtil.INSTANCE.showLoading(this);
        VacationSoapRequestHandler soapRequestHandler = new VacationSoapRequestHandler();
        String usageKey = "balqa2023";
        String userId = new Utils().getUserData(this).getUsername().toString();
        soapRequestHandler.sendSoapRequest(usageKey, userId, SOAP_ACTION, reqType, VacLeaveType, fromDate.getText().toString(), toDate.getText().toString(), "", "", "", "true", mangerName.getText().toString(),"", new VacationSoapRequestHandler.SoapRequestListener() {
            @Override
            public void onSoapRequestComplete(String value) {
                if (value != null) {
                    try {
                        ProgressUtil.INSTANCE.hideLoading();
                        mList = new ArrayList<>();
                        String result = "";
                        try {
                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            factory.setNamespaceAware(true);
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            Document doc = builder.parse(new InputSource(new StringReader(value)));
                            result = doc.getElementsByTagName("RequestVacLeaveResult").item(0).getChildNodes().item(0).getNodeValue();
                            handleSOAPResponse(result);
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


    public String handleSOAPResponse(String soapResponse) {

        JSONObject jsonObject = null;
        try {
            try {
                jsonObject = new JSONObject(soapResponse);
                ProgressUtil.INSTANCE.showWarningPopupActionCallBack(this, jsonObject.getString("success"), new OnClickSucess() {
                    @Override
                    public void onSccuessClick() {
                        finish();
                    }
                });
            } catch (JSONException e) {
                try {
                    JSONObject errorObject = new JSONObject(soapResponse);
                    Iterator<String> keys = errorObject.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        if (key.startsWith("error_")) {
                            String errorMessage = errorObject.getString(key);
                            ProgressUtil.INSTANCE.showWarningPopup(this,errorMessage);
                        }
                    }
                } catch (Exception eee)
                {

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error: Unknown response format";
    }
}