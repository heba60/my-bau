package com.uni.bau.ui.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uni.bau.BuildConfig;
import com.uni.bau.R;
import com.uni.bau.application.MyApplication;
import com.uni.bau.helpers.SharedPrefConstants;
import com.uni.bau.helpers.SharedPreferencesHelper;
import com.uni.bau.managers.Api;
import com.uni.bau.managers.ApiCallResponse;
import com.uni.bau.managers.BusinessManager;
import com.uni.bau.models.CacheKeys;
import com.uni.bau.models.IsValidUserResponse;
import com.uni.bau.models.UserProfileModel;
import com.uni.bau.utilities.CustomTastyToast;
import com.uni.bau.utilities.MyClient;
import com.uni.bau.utilities.ProgressUtil;
import com.uni.bau.utilities.Utils;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class LoginActivity extends AppCompatActivity {
    private TextView tvForgetPassword, tvGest, tvNewUser;
    private Button btnLogin;
    private ImageView ivBack;
    private Context context;

    EditText etUserName;
    EditText etPassword;

    String viewState = "";
    private FirebaseRemoteConfig fbRemoteConfig;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.updateLanguage(this);
        setContentView(R.layout.activity_login);
        MyClient.reset();
        Api.init(this);
        context = this;
        tvForgetPassword = findViewById(R.id.tvForgetPassword);
        btnLogin = findViewById(R.id.btnLogin);
        ivBack = findViewById(R.id.ivBack);
        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        try {
            FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                    .setFetchTimeoutInSeconds(0)
                    .build();
            fbRemoteConfig = FirebaseRemoteConfig.getInstance();
            fbRemoteConfig.setConfigSettingsAsync(configSettings);
            fbRemoteConfig.fetch(0).addOnCompleteListener(this,
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                fbRemoteConfig.fetchAndActivate();
                                String object = fbRemoteConfig.getString("settings");
                                Gson gson = new GsonBuilder().create();
                                CacheKeys cacheKeys = gson.fromJson(object, CacheKeys.class);
                                SharedPreferencesHelper.putSharedPreferencesObject(LoginActivity.this, "cascheKeys", cacheKeys);
                            } else {
                            }
                        }
                    });
        } catch (Exception e) {

        }
//        if (BuildConfig.DEBUG) {
//            etUserName.setText("96608");
//            etPassword.setText("abc123456789");
//        }
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etUserName.getText().toString().equals("")) {
                    ProgressUtil.INSTANCE.showWarningPopup(context, getString(R.string.enter_username));
                    return;
                }
                if (etPassword.getText().toString().equals("")) {
                    ProgressUtil.INSTANCE.showWarningPopup(context, getString(R.string.enter_password));
                    return;
                }

                ProgressUtil.INSTANCE.showLoading(LoginActivity.this);
                new SoapRequestTask().execute();

//
//                if (etUserName.getText().length() == 5) {
//                    ProgressUtil.INSTANCE.showLoading(LoginActivity.this);
//                    new SoapRequestTask().execute();
//                } else {
//                    postLogin();
//                }

            }
        });
        captcha();
    }

    private class SoapRequestTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String soapResponse = null;

            try {
                String url = "https://bauwebservice.bau.edu.jo/BAUWebServices.asmx";
                String soapXML =
                        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                                "  <soap:Body>\n" +
                                "    <IsValidUser xmlns=\"https://bauwebservice.bau.edu.jo/\">\n" +
                                "      <usagekey>balqa2023</usagekey>\n" +
                                "      <username>" + etUserName.getText().toString() + "</username>\n" +
                                "      <password>" + etPassword.getText().toString() + "</password>\n" +
                                "    </IsValidUser>\n" +
                                "  </soap:Body>\n" +
                                "</soap:Envelope>";

                URL soapUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) soapUrl.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
                connection.setRequestProperty("SOAPAction", "https://bauwebservice.bau.edu.jo/IsValidUser");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(soapXML.getBytes());
                }
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    return response.toString();
                } else {
                    ProgressUtil.INSTANCE.hideLoading();
                    ProgressUtil.INSTANCE.showWarningPopup(LoginActivity.this, "حدث خطأ");
                    return "Error Response Code: " + responseCode;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return soapResponse;
        }

        @Override
        protected void onPostExecute(String result) {
            ProgressUtil.INSTANCE.hideLoading();
            if (result != null && result.contains("true")) {
                Log.d("SOAP Response", "User is valid.");
                new SoapRequestTaskProfile().execute();
            } else {
                ProgressUtil.INSTANCE.showWarningPopup(context, "خطأ في اسم المستخدم او كلمة السر");
            }
        }
    }

    private void drLogin(String name, String Facility, String userType, String collage) {
        UserProfileModel userProfileModel = new UserProfileModel();
        userProfileModel.setUsername(etUserName.getText().toString());
        userProfileModel.setPassword(etPassword.getText().toString());
        userProfileModel.setName(name);
        userProfileModel.setFacility(Facility);
        userProfileModel.setUserType(userType);
        userProfileModel.setCollage(collage);

        //TODO Here the main change of the code

        if (etUserName.getText().toString().length() == 5) {
            SharedPreferencesHelper.putSharedPreferencesObject(context, SharedPrefConstants.login, userProfileModel);
            Intent intent = new Intent(LoginActivity.this, DrMainActivity.class);
            startActivity(intent);
            finish();
        } else {
            userProfileModel.setUserType("USER");
            SharedPreferencesHelper.putSharedPreferencesObject(context, SharedPrefConstants.login, userProfileModel);
            finish();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class).putExtra("userProfileModel", userProfileModel);
            startActivity(intent);
        }

    }

    @Override
    protected void onRestart() {
        count = 0;
        super.onRestart();
    }

    @Override
    protected void onResume() {
        count = 0;
        super.onResume();
    }


    private void postLogin() {
        ProgressUtil.INSTANCE.showLoading(LoginActivity.this);
        new BusinessManager().doLogin(LoginActivity.this, etUserName.getText().toString(), etPassword.getText().toString(), new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                Document doc = Jsoup.parse(responseObject.toString());
                Elements errorElements = doc.select("h3[style=color:red]"); // Select the <h3> elements with red color style

                ProgressUtil.INSTANCE.hideLoading();
                if (!errorElements.isEmpty()) {
                    Element errorElement = errorElements.first(); // Get the first matching element
                    String errorMessage = errorElement.text(); // Extract the text of the element
                    ProgressUtil.INSTANCE.showWarningPopup(context, errorMessage);
                } else if (responseObject.toString().contains(getErrorLogin()) || responseObject.toString().contains(getErrorLogin2()) || responseObject.toString().contains("هل نسيت كلمة المرور")) {
                    ProgressUtil.INSTANCE.showWarningPopup(context, "خطأ في اسم المستخدم او كلمة السر");
                } else {
                    UserProfileModel userProfileModel = new UserProfileModel();
                    userProfileModel.setUsername(etUserName.getText().toString());
                    userProfileModel.setPassword(etPassword.getText().toString());
                    userProfileModel.setUserType("USER");
                    SharedPreferencesHelper.putSharedPreferencesObject(context, SharedPrefConstants.login, userProfileModel);

                    finish();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class).putExtra("userProfileModel", userProfileModel);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(String errorResponse) {
                ProgressUtil.INSTANCE.hideLoading();
            }
        });
    }

    private void captcha() {
        ProgressUtil.INSTANCE.showLoading(LoginActivity.this);
        new BusinessManager().captchaAPI(LoginActivity.this, new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                ProgressUtil.INSTANCE.hideLoading();
            }

            @Override
            public void onFailure(String errorResponse) {
                ProgressUtil.INSTANCE.hideLoading();
            }
        });
    }

    String getErrorLogin() {
        return getString(R.string.errorLogin);
    }

    String getErrorLogin2() {
        return getString(R.string.errorLogin2);
    }


    private class SoapRequestTaskProfile extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            String soapResponse = null;

            try {
                String url = "https://bauwebservice.bau.edu.jo/BAUWebServices.asmx";
                String soapXML =
                        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                                "  <soap:Body>\n" +
                                "    <GetUserInfoMin xmlns=\"https://bauwebservice.bau.edu.jo/\">\n" +
                                "      <usagekey>balqa2023</usagekey>\n" +
                                "      <username>" + etUserName.getText().toString() + "</username>\n" +
                                "      <password>" + etPassword.getText().toString() + "</password>\n" +
                                "    </GetUserInfoMin>\n" +
                                "  </soap:Body>\n" +
                                "</soap:Envelope>";

                URL soapUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) soapUrl.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
                connection.setRequestProperty("SOAPAction", "https://bauwebservice.bau.edu.jo/GetUserInfoMin");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(soapXML.getBytes());
                }
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    return response.toString();
                } else {
                    ProgressUtil.INSTANCE.hideLoading();
                    ProgressUtil.INSTANCE.showWarningPopup(LoginActivity.this, "حدث خطأ");
                    return "Error Response Code: " + responseCode;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return soapResponse;
        }

        @Override
        protected void onPostExecute(String value) {
            ProgressUtil.INSTANCE.hideLoading();
            String result = "";
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setNamespaceAware(true);
                DocumentBuilder builder = factory.newDocumentBuilder();
                org.w3c.dom.Document doc = builder.parse(new InputSource(new StringReader(value)));
                result = doc.getElementsByTagName("GetUserInfoMinResult").item(0).getChildNodes().item(0).getNodeValue();
                JSONObject jsonObject = new JSONObject(result);

                drLogin(jsonObject.getString("userfullname"), jsonObject.getString("userspecName"),
                        jsonObject.getString("usertype"), jsonObject.getString("usercolName"));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
