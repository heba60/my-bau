package com.uni.bau.ui.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.uni.bau.R;
import com.uni.bau.application.MyApplication;
import com.uni.bau.helpers.SharedPrefConstants;
import com.uni.bau.helpers.SharedPreferencesHelper;
import com.uni.bau.managers.ApiCallResponse;
import com.uni.bau.managers.BusinessManager;
import com.uni.bau.managers.UniBusinessManager;
import com.uni.bau.models.UniLoginModel;
import com.uni.bau.models.UserProfileModel;
import com.uni.bau.utilities.CustomTastyToast;
import com.uni.bau.utilities.ProgressUtil;
import com.uni.bau.utilities.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ActiveAccountActivity extends AppCompatActivity {

    TextView tvName;
    TextView tvNumberUniversty;
    TextView tvSpeciality;
    EditText etPhoneNumber;
    EditText etEmail;
    EditText etLinkedIn;
    Button btnLogin;
    UniLoginModel uniLoginModel = null;
    boolean isRegister = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.updateLanguage(this);
        setContentView(R.layout.activity_active_account);
        getScheduleApiCall();
        tvName = (TextView) findViewById(R.id.tvName);
        tvNumberUniversty = (TextView) findViewById(R.id.tvNumberUniversty);
        tvSpeciality = (TextView) findViewById(R.id.tvSpeciality);

        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        try {
            etPhoneNumber.setText(new Utils().getUserData(ActiveAccountActivity.this).getMobile());
            etPhoneNumber.setEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ;
        etEmail = (EditText) findViewById(R.id.etEmail);
        etLinkedIn = (EditText) findViewById(R.id.etLinkedIn);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRegister) {
                    updateUser();
                } else {
                    registerUser();
                }
            }
        });
        try {
            uniLoginModel = (UniLoginModel) SharedPreferencesHelper.getSharedPreferencesObject(ActiveAccountActivity.this, SharedPrefConstants.loginUni, UniLoginModel.class);
            if (uniLoginModel == null) {
                isRegister = false;
            } else {
                isRegister = true;
                try {
                    tvName.setText(uniLoginModel.getData().getFullName().toString());
                    tvSpeciality.setText(uniLoginModel.getData().getCollege().toString());
                    etPhoneNumber.setText(uniLoginModel.getData().getPhoneNumber().toString());
                    etEmail.setText(uniLoginModel.getData().getEmail().toString());
                    etLinkedIn.setText(uniLoginModel.getData().getLinkedinAccount().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    tvNumberUniversty.setText(new Utils().getUserData(ActiveAccountActivity.this).getUsername().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            isRegister = false;
        }
    }

    private void registerUser() {
        ProgressUtil.INSTANCE.showLoading(ActiveAccountActivity.this);
        if (etPhoneNumber.getText().toString().equals("")) {
            CustomTastyToast.makeText(ActiveAccountActivity.this, "رقم الهاتف لا يمكن تركه فارغا", CustomTastyToast.LENGTH_LONG, CustomTastyToast.ERROR).show();

            return;
        }
        if (etEmail.getText().toString().equals("")) {
            CustomTastyToast.makeText(ActiveAccountActivity.this, "البريد الالكتروني لا يمكن تركه فارغا", CustomTastyToast.LENGTH_LONG, CustomTastyToast.ERROR).show();
            return;
        }
        new UniBusinessManager().userRegistraion(ActiveAccountActivity.this, new Utils().getUserData(ActiveAccountActivity.this).getName().toString(), etPhoneNumber.getText().toString(), etEmail.getText().toString(), new Utils().getUserData(ActiveAccountActivity.this).getMajor().toString(), new Utils().getUserData(ActiveAccountActivity.this).getFacility().toString(), etLinkedIn.getText().toString(), new Utils().getUserData(ActiveAccountActivity.this).getUsername().toString(), "", new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                ProgressUtil.INSTANCE.hideLoading();
                UniLoginModel uniLoginModel = null;
                try {
                    uniLoginModel = (UniLoginModel) responseObject;
                    SharedPreferencesHelper.putSharedPreferencesObject(ActiveAccountActivity.this, SharedPrefConstants.loginUni, uniLoginModel);
                    CustomTastyToast.makeText(ActiveAccountActivity.this, "تم التسجيل بنجاح", CustomTastyToast.LENGTH_LONG, CustomTastyToast.SUCCESS).show();
                    finish();
                    startActivity(new Intent(ActiveAccountActivity.this, MainActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorResponse) {
                CustomTastyToast.makeText(ActiveAccountActivity.this, "حدث خطا يرجى المحاولة لاحقا ", CustomTastyToast.LENGTH_LONG, CustomTastyToast.ERROR).show();

            }
        });
    }

    private void updateUser() {
        ProgressUtil.INSTANCE.showLoading(ActiveAccountActivity.this);
        if (etPhoneNumber.getText().toString().equals("")) {
            CustomTastyToast.makeText(ActiveAccountActivity.this, "رقم الهاتف لا يمكن تركه فارغا", CustomTastyToast.LENGTH_LONG, CustomTastyToast.ERROR).show();

            return;
        }
        if (etEmail.getText().toString().equals("")) {
            CustomTastyToast.makeText(ActiveAccountActivity.this, "البريد الالكتروني لا يمكن تركه فارغا", CustomTastyToast.LENGTH_LONG, CustomTastyToast.ERROR).show();
            return;
        }
        new UniBusinessManager().userUpdateProfile(ActiveAccountActivity.this, new Utils().getUserData(ActiveAccountActivity.this).getName().toString(), etPhoneNumber.getText().toString(), etEmail.getText().toString(), new Utils().getUserData(ActiveAccountActivity.this).getMajor().toString(), new Utils().getUserData(ActiveAccountActivity.this).getFacility().toString(), etLinkedIn.getText().toString(), "", new ApiCallResponse() {
            @Override
            public void onSuccess(Object responseObject, String responseMessage) {
                ProgressUtil.INSTANCE.hideLoading();
                UniLoginModel uniLoginModel = null;
                try {
                    uniLoginModel = (UniLoginModel) responseObject;
                    SharedPreferencesHelper.putSharedPreferencesObject(ActiveAccountActivity.this, SharedPrefConstants.loginUni, uniLoginModel);
                    CustomTastyToast.makeText(ActiveAccountActivity.this, "تم التحديث بنجاح", CustomTastyToast.LENGTH_LONG, CustomTastyToast.SUCCESS).show();
                    finish();
                    startActivity(new Intent(ActiveAccountActivity.this, MainActivity.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorResponse) {
                ProgressUtil.INSTANCE.hideLoading();
                CustomTastyToast.makeText(ActiveAccountActivity.this, "حدث خطا يرجى المحاولة لاحقا ", CustomTastyToast.LENGTH_LONG, CustomTastyToast.ERROR).show();
            }
        });
    }

    private void getScheduleApiCall() {

        ProgressUtil.INSTANCE.showLoading(this);
        new BusinessManager().getSchedule(this, new ApiCallResponse() {
            @Override
            public void onSuccess(Object response, String responseMessage) {
                ProgressUtil.INSTANCE.hideLoading();
                UserProfileModel userLoginModel = new UserProfileModel();
                userLoginModel = (UserProfileModel) SharedPreferencesHelper.getSharedPreferencesObject(ActiveAccountActivity.this, SharedPrefConstants.login, UserProfileModel.class);

                Document html = Jsoup.parse(response.toString());
                try {
                    String name = html.getElementById(new Utils().getKeys(ActiveAccountActivity.this).getUserkey().toString()).child(0).child(1).child(2).text();
                    userLoginModel.setName(name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    String major = html.getElementById(new Utils().getKeys(ActiveAccountActivity.this).getUserkey().toString()).child(0).child(4).child(2).text();
                    userLoginModel.setMajor(major);
                } catch (Exception e) {
                    userLoginModel.setMajor("app");
                    e.printStackTrace();
                }
                try {
                    String facility = html.getElementById(new Utils().getKeys(ActiveAccountActivity.this).getUserkey().toString()).child(0).child(3).child(2).text();
                    userLoginModel.setFacility(facility);
                } catch (Exception e) {
                    userLoginModel.setFacility("app");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorResponse) {
                ProgressUtil.INSTANCE.hideLoading();
            }
        });
    }
}