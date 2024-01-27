package com.uni.bau.ui.Activites;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
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

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class RequestLeaveActivity extends AppCompatActivity {
    private List<String> mList = new ArrayList<>();
    ImageView ivBack;

    Spinner spinnerMonth;
    Spinner spinnerDurationn;
    Spinner spinnerMintus;
    Spinner spinnerHour;

    CheckBox accpet;
    private List<String> months = new ArrayList();
    private List<String> duration = new ArrayList();
    private List<String> mintus = new ArrayList();
    private List<String> hours = new ArrayList();

    String selectedDuration = "";
    String VacLeaveType = "";
    String txtMintus = "";
    String txtHours = "";
    private static final String SOAP_ACTION = "https://bauwebservice.bau.edu.jo/RequestVacLeave";

    String reqType = "leave";
    TextView toDate, fromDate;
    LinearLayout fromDateLinear, toDateLinear;
    Button btnLogin;
    EditText mangerName;
    EditText taskName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.updateLanguage(this);
        setContentView(R.layout.request_leave);
        mangerName = (EditText) findViewById(R.id.mangerName);
        taskName = (EditText) findViewById(R.id.taskName);
        toDate = (TextView) findViewById(R.id.toDate);
        fromDate = (TextView) findViewById(R.id.fromDate);
        accpet = (CheckBox) findViewById(R.id.accpet);
        toDateLinear = (LinearLayout) findViewById(R.id.toDateLinear);
        fromDateLinear = (LinearLayout) findViewById(R.id.fromDateLinear);
        spinnerHour = (Spinner) findViewById(R.id.spinnerHour);
        spinnerMonth = (Spinner) findViewById(R.id.spinnerMonth);
        spinnerDurationn = (Spinner) findViewById(R.id.spinnerDduration);
        spinnerMintus = (Spinner) findViewById(R.id.spinnerMintus);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        months.add("نوع المغادرة");
        months.add("شخصية");
        months.add("رسمية");
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


        duration.add("المدة");
        duration.add("ربع ساعة");
        duration.add("نصف ساعة");
        duration.add("ثلاثة ارباع الساعة");
        duration.add("ساعة");
        duration.add("ساعة وربع");
        duration.add("ساعة ونصف");
        duration.add("ساعة وثلاثة ارباع الساعة");
        duration.add("ساعتين");
        duration.add("ساعتين وربع");
        duration.add("ساعتين ونصف");
        duration.add("ساعتين وثلاثة ارباع الساعة");
        duration.add("ثلاث ساعات");
        duration.add("اربع ساعات");
        spinnerDurationn.setAdapter(new ArrayAdapter<String>(this, R.layout.row_marks_spinner, duration));
        spinnerDurationn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    if (i == 0) {
                        selectedDuration = "";
                    } else if (i == 1) {
                        selectedDuration = "0.25";
                    } else if (i == 2) {
                        selectedDuration = "0.5";
                    } else if (i == 3) {
                        selectedDuration = "0.75";
                    } else if (i == 4) {
                        selectedDuration = "1.00";
                    } else if (i == 5) {
                        selectedDuration = "1.25";
                    } else if (i == 6) {
                        selectedDuration = "1.50";
                    } else if (i == 7) {
                        selectedDuration = "1.75";
                    } else if (i == 8) {
                        selectedDuration = "2.00";
                    } else if (i == 9) {
                        selectedDuration = "2.25";
                    } else if (i == 10) {
                        selectedDuration = "2.50";
                    } else if (i == 11) {
                        selectedDuration = "2.75";
                    } else if (i == 12) {
                        selectedDuration = "3.00";
                    } else if (i == 13) {
                        selectedDuration = "4.00";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        mintus.add("00");
        mintus.add("05");
        mintus.add("10");
        mintus.add("15");
        mintus.add("20");
        mintus.add("25");
        mintus.add("30");
        mintus.add("35");
        mintus.add("40");
        mintus.add("45");
        mintus.add("50");
        mintus.add("55");
        spinnerMintus.setAdapter(new ArrayAdapter<String>(this, R.layout.row_marks_spinner, mintus));
        spinnerMintus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    txtMintus = i + "";

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        hours.add("8");
        hours.add("9");
        hours.add("10");
        hours.add("11");
        hours.add("12");
        hours.add("1");
        hours.add("2");
        hours.add("3");
        hours.add("4");
        spinnerHour.setAdapter(new ArrayAdapter<String>(this, R.layout.row_marks_spinner, hours));
        spinnerHour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    if (i == 0) {
                        txtHours = "08";
                    } else if (i == 1) {
                        txtHours = "09";
                    } else if (i == 2) {
                        txtHours = "10";
                    } else if (i == 3) {
                        txtHours = "11";
                    } else if (i == 4) {
                        txtHours = "12";
                    } else if (i == 5) {
                        txtHours = "13";
                    } else if (i == 6) {
                        txtHours = "14";
                    } else if (i == 7) {
                        txtHours = "15";
                    }else if (i == 8) {
                        txtHours = "16";
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
                    ProgressUtil.INSTANCE.showWarningPopup(RequestLeaveActivity.this,"يرجى تحديد التاريخ ");
                    return;
                }
                if (VacLeaveType.equals(""))
                {
                    ProgressUtil.INSTANCE.showWarningPopup(RequestLeaveActivity.this,"يرجى تحديد نوع المغادرة ");
                    return;
                }
                if (mangerName.getText().equals(""))
                {
                    ProgressUtil.INSTANCE.showWarningPopup(RequestLeaveActivity.this,"يرجى تحديد المسؤول عنك في العمل ");
                    return;
                }
                if (taskName.getText().equals(""))
                {
                    ProgressUtil.INSTANCE.showWarningPopup(RequestLeaveActivity.this,"يرجى تحديد وصف المهمة ");
                    return;
                }
                if (selectedDuration.equals(""))
                {
                    ProgressUtil.INSTANCE.showWarningPopup(RequestLeaveActivity.this,"يرجى تحديد المدة ");
                    return;
                }
                if (txtHours.equals(""))
                {
                    ProgressUtil.INSTANCE.showWarningPopup(RequestLeaveActivity.this,"يرجى تحديد الساعة ");
                    return;
                }
                if (txtMintus.equals(""))
                {
                    ProgressUtil.INSTANCE.showWarningPopup(RequestLeaveActivity.this,"يرجى تحديد الدقيقة ");
                    return;
                }
                if (!accpet.isChecked())
                {
                    ProgressUtil.INSTANCE.showWarningPopup(RequestLeaveActivity.this,"موافقة المسؤول المباشر");
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
        soapRequestHandler.sendSoapRequest(usageKey, userId, SOAP_ACTION, reqType, VacLeaveType, fromDate.getText().toString(), "", txtHours, txtMintus, selectedDuration, "true", mangerName.getText().toString(),taskName.getText().toString(), new VacationSoapRequestHandler.SoapRequestListener() {
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
                } catch (Exception ee)
                {

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error: Unknown response format";
    }


}