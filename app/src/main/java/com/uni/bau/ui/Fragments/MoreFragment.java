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
import com.uni.bau.ui.Activites.ActiveAccountActivity;
import com.uni.bau.ui.Activites.JobsActivity;
import com.uni.bau.ui.Activites.NewsAnnouncementActivity;
import com.uni.bau.ui.Activites.NewsLitterActivity;
import com.uni.bau.ui.Activites.SplashActivity;
import com.uni.bau.ui.Activites.StuCalenderActivity;
import com.uni.bau.ui.Activites.StudScudleActivity;
import com.uni.bau.ui.Activites.WebScreenActivity;
import com.uni.bau.utilities.MyClient;
import com.uni.bau.utilities.ProgressUtil;
import com.uni.bau.utilities.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import chtgupta.qrutils.qractivity.QRActivity;


public class MoreFragment extends Fragment {

    Activity activity;
    View rootView;

    public MoreFragment() {
    }

    public static MoreFragment newInstance() {
        return new MoreFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_more, container, false);
        TextView tvName = rootView.findViewById(R.id.tvName);
        TextView tvNumber = rootView.findViewById(R.id.tvNumber);
        TextView tvCollage = rootView.findViewById(R.id.tvCollage);

        try {
            tvCollage.setText(Html.fromHtml(new Utils().getUserData(getActivity()).getCollage()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            tvNumber.setText(Html.fromHtml(new Utils().getUserData(getActivity()).getUsername()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            tvName.setText(Html.fromHtml(new Utils().getUserData(getActivity()).getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        rootView.findViewById(R.id.profilePage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        rootView.findViewById(R.id.housingBank).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://hbtf.com"));
                activity.startActivity(browserIntent);
            }
        });
        rootView.findViewById(R.id.registrationBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://app1.bau.edu.jo:7799/reg_new/index.jsp"));
                activity.startActivity(browserIntent);
            }
        });
        rootView.findViewById(R.id.scudleBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), StudScudleActivity.class));
            }
        });
        rootView.findViewById(R.id.uniLinkBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bau.edu.jo/EBillService/EFawatercomInfo.aspx"));
                activity.startActivity(browserIntent);
            }
        });
        rootView.findViewById(R.id.newsBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewsAnnouncementActivity.class));

            }
        });
        rootView.findViewById(R.id.newsLitter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewsLitterActivity.class));
            }
        });


       rootView.findViewById(R.id.courcees).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://live.bau.edu.jo/BauLivePortal/NewsByDetail.aspx?userid=26"));
                activity.startActivity(browserIntent);
            }
        });




        /*rootView.findViewById(R.id.jobs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getActivity(), JobsActivity.class));
                Intent browserIntentY = new Intent(Intent.ACTION_VIEW, Uri.parse("activity.startActivity(browserIntent);"));
                activity.startActivity(browserIntentY);


            }
        });*/

        rootView.findViewById(R.id.jobs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bau.edu.jo/industryoffice/Test.aspx"));
                activity.startActivity(browserIntent);
            }
        });


        rootView.findViewById(R.id.calenderBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), StuCalenderActivity.class));
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
        rootView.findViewById(R.id.attednnadce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQRCode();

            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 11)
            {
                assert data != null;
                String qrValue =  data.getStringExtra("qrData");
                String stnoValue = new Utils().getUserData(getActivity()).getUsername().toString();
                if (!qrValue.equals("")){
                    new SoapRequestTask().execute(qrValue, stnoValue);
                }
            }
        }
    }
    public void openQRCode() {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            new AlertDialog.Builder(requireActivity())
                    .setTitle("Camera Permission")
                    .setMessage("You denied camera permission, please enable permission from settings")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(
                                    requireActivity(),
                                    new String[]{Manifest.permission.CAMERA},
                                    0
                            );
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            scanQrCode();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                scanQrCode();
            }
        }
    }

    public void scanQrCode() {
        Intent intent = new Intent(requireActivity(), QRActivity.class);
        intent.putExtra("fullScreen", false);
        intent.putExtra("autoFocusInterval", 2000);
        intent.putExtra("focusOnTouchEnabled", true);
        intent.putExtra("imagePickerEnabled", false);
        startActivityForResult(intent, 11);
    }
    public class SoapRequestTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            if (params.length != 2) {
                return "Invalid parameters";
            }
            String qrValue = params[0];
            String stnoValue = params[1];

            try {
                URL url = new URL("https://www.bau.edu.jo/BAUWebServices.asmx");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                String coockie = MyClient.getCookie();
                connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
                connection.setRequestProperty("SOAPAction", "https://www.bau.edu.jo/SaveStudentAttendance");
                connection.setRequestProperty("Cookie", coockie);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                String soapRequestBody = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                        "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<soap:Body>" +
                        "<SaveStudentAttendance xmlns=\"https://www.bau.edu.jo/\">" +
                        "<usagekey>balqa2023</usagekey>" +
                        "<stno>" + stnoValue + "</stno>" +
                        "<QR>" + qrValue + "</QR>" +
                        "</SaveStudentAttendance>" +
                        "</soap:Body>" +
                        "</soap:Envelope>";

                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(soapRequestBody.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
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
                    ProgressUtil.INSTANCE.showWarningPopup(requireActivity(),"حدث خطأ");
                    return "Error Response Code: " + responseCode;
                }
            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null && result.contains("true")) {
                ProgressUtil.INSTANCE.showSuccessPopup(requireActivity(),"تم تسجيل الحضور بنجاح");
            } else {
                ProgressUtil.INSTANCE.showWarningPopup(requireActivity(),"الرمز غير صحيح");
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}