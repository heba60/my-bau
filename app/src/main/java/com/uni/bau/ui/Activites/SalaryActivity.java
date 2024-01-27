package com.uni.bau.ui.Activites;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uni.bau.R;
import com.uni.bau.application.MyApplication;
import com.uni.bau.models.SoapRequestHandler;
import com.uni.bau.ui.Adapters.AdapterSalarry;
import com.uni.bau.utilities.ProgressUtil;
import com.uni.bau.utilities.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class SalaryActivity extends AppCompatActivity {
    private List<String>  mList1= new ArrayList<>();
    public List<String> mList2 = new ArrayList<>();


    public List<String> mList3 = new ArrayList<>();
    public List<String> mList4 = new ArrayList<>();





    LinearLayout add,minus,minuseinner,addinner;


    ImageView ivBack;
    RecyclerView calendarRecycler1,calendarRecycler2;

    Spinner spinnerYear;
    Spinner spinnerMonth;

    private List<String> years = new ArrayList();
    private List<String> months = new ArrayList();



    TextView netsal,soial,bank,date;

    String selectedYear = "";
    String selectedMonth = "";
    private static final String SOAP_ACTION = "https://bauwebservice.bau.edu.jo/GetSalary";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.updateLanguage(this);
        setContentView(R.layout.activity_salary);
        spinnerYear = (Spinner) findViewById(R.id.spinnerYear);
        spinnerMonth = (Spinner) findViewById(R.id.spinnerMonth);
        calendarRecycler1 = (RecyclerView) findViewById(R.id.calendarRecycler1);
        calendarRecycler2 = (RecyclerView) findViewById(R.id.calendarRecycler2);

        calendarRecycler2.setLayoutManager(new LinearLayoutManager(this));
        calendarRecycler1.setLayoutManager(new LinearLayoutManager(this));



        LinearLayout add,minus,minuseinner,addinner;

        add = (LinearLayout) findViewById(R.id.add);
        minuseinner = (LinearLayout) findViewById(R.id.minuseinner);


        minus = (LinearLayout) findViewById(R.id.minuse);
        addinner = (LinearLayout) findViewById(R.id.addinner);


        add.setVisibility(View.VISIBLE);
        addinner.setVisibility(View.GONE);
        minus.setVisibility(View.VISIBLE);
        minuseinner.setVisibility(View.GONE);
        calendarRecycler2.setVisibility(View.GONE);
        calendarRecycler1.setVisibility(View.GONE);

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (minuseinner.getVisibility() == View.GONE) {
                    addinner.setVisibility(View.GONE);
                    minus.setVisibility(View.VISIBLE);
                    minuseinner.setVisibility(View.VISIBLE);
                    calendarRecycler1.setVisibility(View.GONE);
                    calendarRecycler2.setVisibility(View.VISIBLE);

                }else{
                    minuseinner.setVisibility(View.GONE);
                    calendarRecycler2.setVisibility(View.GONE);
                }




            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (addinner.getVisibility() == View.GONE) {

                    add.setVisibility(View.VISIBLE);
                    addinner.setVisibility(View.VISIBLE);
                    minuseinner.setVisibility(View.GONE);

                    calendarRecycler2.setVisibility(View.INVISIBLE);
                    calendarRecycler1.setVisibility(View.VISIBLE);
                }else{

                    addinner.setVisibility(View.GONE);
                    calendarRecycler1.setVisibility(View.GONE);
                }

            }
        });



       /* calendarRecycler1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        calendarRecycler2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
*/


        netsal = (TextView) findViewById(R.id.netsal);
        soial = (TextView) findViewById(R.id.soial);
        bank = (TextView) findViewById(R.id.bank);
        date = (TextView) findViewById(R.id.date);


        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        years.add("إختر السنة");
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
                        mList1 = new ArrayList<>();
                        mList2 = new ArrayList<>();

                        mList3 = new ArrayList<>();
                        mList4 = new ArrayList<>();


                        String result = "";
                        try {
                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                            factory.setNamespaceAware(true);
                            DocumentBuilder builder = factory.newDocumentBuilder();
                            Document doc = builder.parse(new InputSource(new StringReader(value)));
                            result = doc.getElementsByTagName("GetSalaryResult").item(0).getChildNodes().item(0).getNodeValue();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        ProgressUtil.INSTANCE.hideLoading();
                        JSONObject jsonObject = new JSONObject(result);

                        JSONObject saldetails = jsonObject.getJSONObject("salinfo_alwInfo");
                        JSONObject salinfo_dedInfo = jsonObject.getJSONObject("salinfo_dedInfo");

                        String [] info=saldetails.toString().split(",");
                        for(int i=0;i<info.length;i++){
                            info[i]=info[i].replace("alw0_"," ");
                            info[i]=info[i].replace("alw1_"," ");
                            info[i]=info[i].replace("alw2_"," ");
                            info[i]=info[i].replace("alw3_"," ");
                            info[i]=info[i].replace("alw4_"," ");
                            info[i]=info[i].replace("alw5_"," ");
                            info[i]=info[i].replace("alw6_"," ");
                            info[i]=info[i].replace("\"","");
                            info[i]=info[i].replace("{","");
                            info[i]=info[i].replace("}","");

                            String [] infosplit=  info[i].split(":");
                            mList1.add(infosplit[0]);
                            mList2.add(infosplit[1]);

                        }
                        String [] info1=salinfo_dedInfo.toString().split(",");
                        for(int i=0;i<info1.length;i++){
                            info1[i]=info1[i].replace("ded1_"," ");
                            info1[i]=info1[i].replace("ded0_"," ");
                            info1[i]=info1[i].replace("ded2_"," ");
                            info1[i]=info1[i].replace("ded3_"," ");
                            info1[i]=info1[i].replace("ded4_"," ");
                            info1[i]=info1[i].replace("ded5_"," ");
                            info1[i]=info1[i].replace("ded6_"," ");
                            info1[i]=info1[i].replace("\"","");
                            info1[i]=info1[i].replace("{","");
                            info1[i]=info1[i].replace("}","");

                            String [] info1split=  info1[i].split(":");
                            mList3.add(info1split[0]);
                            mList4.add(info1split[1]);

                        }





                        System.out.println("-----------------");
                        System.out.println(saldetails.toString());
                        System.out.println("-----------------");


                        // Extract data from the JSON object
                        //JSONObject salinfo_dedInfo = jsonObject.getJSONObject("salinfo_dedInfo");
                        try {
                            String salinfo_tot_sal_text = jsonObject.getString("salinfo_tot_sal_text");
                            String salinfo_tot_sal = jsonObject.getString("salinfo_tot_sal");
                            String salinfo_net_ded = jsonObject.getString("salinfo_net_ded");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //  Toast.makeText(NewsLitterActivity.this, "لا يوجد بيانات", Toast.LENGTH_SHORT).show();
                        JSONObject salinfo_alwInfo = jsonObject.getJSONObject("salinfo_alwInfo");


                        try {
                            String salinfo_date = jsonObject.getString("salinfo_date");
                            date.setText(salinfo_date);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            String salinfo_cash_place = jsonObject.getString("salinfo_cash_place");

                            bank.setText(salinfo_cash_place);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            String ded7 = salinfo_dedInfo.getString("ded7_صندوق الادخار");
                            mList3.add("صندوق الادخار");
                            mList4.add(ded7);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            String ded1 = salinfo_dedInfo.getString("ded1_اشتراك نادي الجامعه");
                            mList3.add("اشتراك نادي الجامعة" );
                            mList4.add( ded1);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                      /*  try {
                            String ded0 = salinfo_dedInfo.getString("ded0_الضمان الاجتماعي");
                            mList3.add("الضمان الاجتماعي");
                            mList4.add( ded0);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }*/
                        try {
                            String ded4 = salinfo_dedInfo.getString("ded4_اقتطاعات نادي الجامعه");
                            mList3.add("اقتطاعات نادي الجامعه");
                            mList4.add( ded4);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            String ded9 = salinfo_dedInfo.getString("ded9_اورانج");
                            mList3.add("اورانج " );
                            mList4.add(ded9);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            String ded2 = salinfo_dedInfo.getString("ded2_ضريبة دخل عمل اضافي و مكافات");
                            mList3.add("ضريبة دخل عمل اضافي و مكافات");
                            mList4.add(ded2);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            String ded3 = salinfo_dedInfo.getString("ded3_تامين صحي اشتراك");
                            mList3.add("تامين صحي اشتراك ");
                            mList4.add( ded3);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            String ded5 = salinfo_dedInfo.getString("ded5_مؤسسة ادارة وتنمية اموال الايتام");
                            mList3.add("مؤسسة ادارة وتنمية اموال الايتام " );
                            mList4.add(ded5);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            String ded6 = salinfo_dedInfo.getString("ded6_شركة زين للاتصالات");

                            mList3.add("شركة زين للاتصالات ");
                            mList4.add(ded6);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            String ded8 = salinfo_dedInfo.getString("ded8_ضريبة دخل");
                            mList3.add("ضريبة دخل" );
                            mList4.add(ded8);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            String alw0 = salinfo_alwInfo.getString("alw0_عمل اضافي و مكافات");
                            mList1.add("عمل اضافي و مكافات ");
                            mList2.add(alw0);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            String alw1 = salinfo_alwInfo.getString("alw1_علاوة الادارة");
                            mList1.add("علاوة الإدارة");
                            mList2.add(  alw1);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            String alw2 = jsonObject.getString("salinfo_net_sal");
                            //  mList1.add("الراتب الإجمالي ");
                            //  mList2.add(alw2);
                            soial.setText(alw2);



                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            String salinfo_tot_sal = jsonObject.getString("salinfo_tot_sal");

                            netsal.setText(salinfo_tot_sal);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                        calendarRecycler1.setAdapter(new AdapterSalarry(SalaryActivity.this, mList1,mList2));
                        calendarRecycler2.setAdapter(new AdapterSalarry(SalaryActivity.this,  mList3,mList4));



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }


}