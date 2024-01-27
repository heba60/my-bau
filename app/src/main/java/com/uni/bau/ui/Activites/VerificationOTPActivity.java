package com.uni.bau.ui.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import com.uni.bau.R;
import com.uni.bau.application.MyApplication;
import com.uni.bau.helpers.SharedPrefConstants;
import com.uni.bau.helpers.SharedPreferencesHelper;
import com.uni.bau.managers.ApiCallResponse;
import com.uni.bau.managers.BusinessManager;
import com.uni.bau.managers.UniBusinessManager;
import com.uni.bau.models.UniLoginModel;
import com.uni.bau.models.UserProfileModel;
import com.uni.bau.utilities.ProgressUtil;
import com.uni.bau.utilities.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class VerificationOTPActivity extends AppCompatActivity {
    TextView tvReSend;
    Button btnConfirm;
    PinView pinView;
    String phoneNumber = "";
    String phoneVerificationId = "";
    public FirebaseAuth fbAuth;
    Context context;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    UserProfileModel userProfileModel;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.updateLanguage(this);
        setContentView(R.layout.activity_verification_otpactivity);
        userProfileModel = (UserProfileModel) getIntent().getSerializableExtra("userProfileModel");

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                phoneVerificationId = "";
            } else {
                phoneVerificationId = extras.getString("phoneVerificationId");
                phoneNumber = extras.getString("phoneNumber");
            }
        } else {
            phoneVerificationId = (String) savedInstanceState.getSerializable("phoneVerificationId");
        }
        context = this;
        if (new Utils().isGMSAvailable(VerificationOTPActivity.this)) {
            fbAuth = FirebaseAuth.getInstance();
        }
        pinView = findViewById(R.id.pinView);
        tvReSend = findViewById(R.id.tvReSend);
        btnConfirm = findViewById(R.id.btnConfirm);
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvReSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new Utils().isGMSAvailable(context)) {
                    sendNumberFirebase();
                } else {
//                    sendNumberByHms();
                }
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressUtil.INSTANCE.showLoading(context);
                if (pinView.getText().toString().length() == 6) {
                    if (new Utils().isGMSAvailable(context)) {
                        String code = pinView.getText().toString();
                        PhoneAuthCredential credential =
                                PhoneAuthProvider.getCredential(phoneVerificationId, code);
                        signInWithPhoneAuthCredential(credential);
                    } else {
//                        verfiyByHMS();
                    }
                } else {
                    ProgressUtil.INSTANCE.hideLoading();
                    ProgressUtil.INSTANCE.showWarningPopup(context, getString(R.string.enter_valid_otp));
                }
            }
        });
    }
    private void sendNumberFirebase() {

        if (phoneNumber.length() > 0) {

            if (phoneNumber.startsWith("0"))
                phoneNumber = phoneNumber.replaceFirst("0", "");
            setUpVerificatonCallbacks();
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+962" + phoneNumber,
                    0,
                    TimeUnit.SECONDS,
                    VerificationOTPActivity.this,
                    verificationCallbacks);

        } else {
            ProgressUtil.INSTANCE.showWarningPopup(context, getString(R.string.enter_valid_phone));
            // enter_valid_phone
        }
    }



    private void setUpVerificatonCallbacks() {
        verificationCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(
                            PhoneAuthCredential credential) {
                        signInWithPhoneAuthCredential(credential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            ProgressUtil.INSTANCE.showWarningPopup(context, getString(R.string.enter_valid_phone));
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            ProgressUtil.INSTANCE.showWarningPopup(context, getString(R.string.enter_valid_phone));
                        }
                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                        phoneVerificationId = verificationId;


                    }
                };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        ProgressUtil.INSTANCE.hideLoading();
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerificationOTPActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            ProgressUtil.INSTANCE.hideLoading();

                            applyVerifySuccessCode();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                ProgressUtil.INSTANCE.hideLoading();
                                ProgressUtil.INSTANCE.showWarningPopup(context, getString(R.string.enter_valid_otp));
                            }
                        }
                    }
                });
    }

    private void applyVerifySuccessCode() {
        SharedPreferencesHelper.putSharedPreferencesObject(context, SharedPrefConstants.login, userProfileModel);
        new Utils().hideKeyboardOnSubmit(this);
        ProgressUtil.INSTANCE.hideLoading();
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }
}