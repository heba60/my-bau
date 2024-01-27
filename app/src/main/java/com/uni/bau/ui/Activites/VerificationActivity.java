package com.uni.bau.ui.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.uni.bau.R;
import com.uni.bau.application.MyApplication;
import com.uni.bau.helpers.SharedPreferencesHelper;
import com.uni.bau.managers.ApiCallResponse;
import com.uni.bau.managers.UniBusinessManager;
import com.uni.bau.models.CacheKeys;
import com.uni.bau.models.CheckNumberModel;
import com.uni.bau.models.UserProfileModel;
import com.uni.bau.utilities.ProgressUtil;
import com.uni.bau.utilities.Utils;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class VerificationActivity extends AppCompatActivity {
    ImageView ivBack;
    Button btnVerfiy;
    EditText etPhoneNumber;
    public FirebaseAuth fbAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    String phoneNumber = "";
    String phoneVerificationId = "";
    Context context;
    UserProfileModel userProfileModel;
    private FirebaseRemoteConfig fbRemoteConfig;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.updateLanguage(this);
        setContentView(R.layout.activity_verification);
        userProfileModel = (UserProfileModel) getIntent().getSerializableExtra("userProfileModel");
        context = this;
        if (new Utils().isGMSAvailable(VerificationActivity.this)) {
            fbAuth = FirebaseAuth.getInstance();
        }
        ivBack = findViewById(R.id.ivBack);
        btnVerfiy = findViewById(R.id.btnVerfiy);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnVerfiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressUtil.INSTANCE.showLoading(VerificationActivity.this);
                if (new Utils().isGMSAvailable(VerificationActivity.this)) {
                    sendNumberFirebase();
                } else {
//                    sendNumberByHms();
                }
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void sendNumberFirebase() {

        if (etPhoneNumber.getText().toString().length() > 0) {
            phoneNumber = etPhoneNumber.getText().toString();
            if (phoneNumber.startsWith("0"))
                phoneNumber = phoneNumber.replaceFirst("0", "");
            setUpVerificatonCallbacks();
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+962" + phoneNumber,
                    0,
                    TimeUnit.SECONDS,
                    VerificationActivity.this,
                    verificationCallbacks);

        } else {
            ProgressUtil.INSTANCE.showWarningPopup(context, getString(R.string.enter_valid_phone));
        }
    }

//    private void sendNumberByHms() {
//        ProgressUtil.INSTANCE.showLoading(VerificationActivity.this);
////        VerifyCodeSettings settings = new VerifyCodeSettings.Builder()
////                .action(VerifyCodeSettings.ACTION_REGISTER_LOGIN)
////                .sendInterval(10)
////                .locale(Locale.ENGLISH)
////                .build();
//        if (etPhoneNumber.getText().toString().length() > 0) {
//            phoneNumber = etPhoneNumber.getText().toString();
//            if (phoneNumber.startsWith("0"))
//                phoneNumber = phoneNumber.replaceFirst("0", "");
//            com.huawei.hmf.tasks.Task<VerifyCodeResult> task = com.huawei.agconnect.auth.PhoneAuthProvider.requestVerifyCode("+" + "962",
//                    phoneNumber, settings);
//            task.addOnSuccessListener(TaskExecutors.uiThread(), new OnSuccessListener<VerifyCodeResult>() {
//                @Override
//                public void onSuccess(VerifyCodeResult verifyCodeResult) {
//                    Intent intent = new Intent(VerificationActivity.this, VerificationOTPActivity.class);
//                    intent.putExtra("phoneVerificationId", phoneVerificationId);
//                    intent.putExtra("phoneNumber", phoneNumber);
//                    userProfileModel.setMobile(phoneNumber);
//                    intent.putExtra("userProfileModel", userProfileModel);
//
//                    startActivity(intent);
//                    finish();
//                }
//            }).addOnFailureListener(TaskExecutors.uiThread(), new OnFailureListener() {
//                @Override
//                public void onFailure(Exception e) {
//                    ProgressUtil.INSTANCE.hideLoading();
//                    ProgressUtil.INSTANCE.showWarningPopup(context, getString(R.string.enter_valid_phone));
//                }
//            });
//        } else {
//            ProgressUtil.INSTANCE.hideLoading();
//            ProgressUtil.INSTANCE.showWarningPopup(context, getString(R.string.enter_valid_phone));
//        }
//    }


    private void setUpVerificatonCallbacks() {
        verificationCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(

                            PhoneAuthCredential credential) {
                        ProgressUtil.INSTANCE.hideLoading();

                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        ProgressUtil.INSTANCE.hideLoading();

                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            ProgressUtil.INSTANCE.showWarningPopup(context, getString(R.string.enter_valid_phone));
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            ProgressUtil.INSTANCE.showWarningPopup(context, getString(R.string.enter_valid_phone));
                        }
                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                        ProgressUtil.INSTANCE.hideLoading();
                        phoneVerificationId = verificationId;
                        Intent intent = new Intent(VerificationActivity.this, VerificationOTPActivity.class);
                        intent.putExtra("phoneVerificationId", phoneVerificationId);
                        intent.putExtra("phoneNumber", phoneNumber);
                        userProfileModel.setMobile(phoneNumber);
                        intent.putExtra("userProfileModel", userProfileModel);

                        startActivity(intent);
                        finish();

                    }
                };
    }


}