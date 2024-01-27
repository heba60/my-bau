package com.uni.bau.ui.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.uni.bau.R;
import com.uni.bau.helpers.SharedPreferencesHelper;
import com.uni.bau.ui.Activites.EmployeeAttendaceActivity;
import com.uni.bau.ui.Activites.HealthActivity;
import com.uni.bau.ui.Activites.JobsActivity;
import com.uni.bau.ui.Activites.NewsAnnouncementActivity;
import com.uni.bau.ui.Activites.NewsLitterActivity;
import com.uni.bau.ui.Activites.RequestLeaveActivity;
import com.uni.bau.ui.Activites.RequestVacationActivity;
import com.uni.bau.ui.Activites.SalaryActivity;
import com.uni.bau.ui.Activites.SplashActivity;
import com.uni.bau.ui.Activites.StuCalenderActivity;
import com.uni.bau.ui.Activites.VacationActivity;
import com.uni.bau.utilities.MyClient;
import com.uni.bau.utilities.ProgressUtil;
import com.uni.bau.utilities.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import chtgupta.qrutils.qractivity.QRActivity;


public class MoreDrFragment extends Fragment {

    Activity activity;
    View rootView;

    public MoreDrFragment() {
    }

    public static MoreDrFragment newInstance() {
        return new MoreDrFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_more_dr, container, false);
        TextView tvName = rootView.findViewById(R.id.tvName);
        TextView tvNumber = rootView.findViewById(R.id.tvNumber);

        try {
            tvNumber.setText(Html.fromHtml(new Utils().getUserData(getActivity()).getFacility()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            tvName.setText(Html.fromHtml(new Utils().getUserData(getActivity()).getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        rootView.findViewById(R.id.uniLinkBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bau.edu.jo/UserPortal/UserProfile/Login.aspx"));
                activity.startActivity(browserIntent);
            }
        });
        rootView.findViewById(R.id.calenderBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EmployeeAttendaceActivity.class));
            }
        });
        rootView.findViewById(R.id.salary).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SalaryActivity.class));
            }
        });
        rootView.findViewById(R.id.health).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HealthActivity.class));
            }
        });
        rootView.findViewById(R.id.vacations).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), VacationActivity.class));
            }
        });
        rootView.findViewById(R.id.reqVacations).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RequestVacationActivity.class));
            }
        });
        rootView.findViewById(R.id.reqLeave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RequestLeaveActivity.class));
            }
        });
        rootView.findViewById(R.id.btnSignOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                startActivity(new Intent(getActivity(), SplashActivity.class));
                SharedPreferencesHelper.clear(getActivity());
            }
        });
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
    }


}