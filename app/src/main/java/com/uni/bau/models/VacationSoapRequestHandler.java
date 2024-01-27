package com.uni.bau.models;


import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class VacationSoapRequestHandler {


    private static final String NAMESPACE = "https://bauwebservice.bau.edu.jo/";
    private static final String URL = "https://bauwebservice.bau.edu.jo/BAUWebServices.asmx";

    public interface SoapRequestListener {
        void onSoapRequestComplete(String result);
    }

    public void sendSoapRequest(String usageKey, String userId, String SOAP_ACTION,String reqType, String VacLeaveType, String fromDate, String toDate, String LeaveHour, String LeaveMin, String LeavePeriod, String MgrApproved, String MgrName,String taskName, final SoapRequestListener listener) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                String request = createSoapRequest(usageKey, userId, reqType,VacLeaveType,fromDate,toDate,LeaveHour,LeaveMin,LeavePeriod,MgrApproved,MgrName,taskName);
                try {
                    URL url = new URL(URL);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
                    connection.setRequestProperty("SOAPAction", SOAP_ACTION);
                    connection.setDoOutput(true);

                    OutputStream outStream = connection.getOutputStream();
                    outStream.write(request.getBytes("UTF-8"));
                    outStream.close();

                    int responseCode = connection.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = connection.getInputStream();
                        ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = inputStream.read(buffer)) != -1) {
                            resultStream.write(buffer, 0, length);
                        }
                        inputStream.close();
                        connection.disconnect();
                        return resultStream.toString("UTF-8");
                    } else {
                        connection.disconnect();
                        return null;
                    }
                } catch (IOException e) {
                    Log.e("SOAP Request", "Error: " + e.getMessage());
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (listener != null) {
                    listener.onSoapRequestComplete(result);
                }
            }
        }.execute();
    }


    private String createSoapRequest(String usageKey, String userId, String reqType, String VacLeaveType, String fromDate, String toDate, String LeaveHour, String LeaveMin, String LeavePeriod, String MgrApproved, String MgrName,String SleepDesc) {
        String SleepFlag = "false";
        return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "  <soap:Body>\n" +
                "    <RequestVacLeave xmlns=\"" + NAMESPACE + "\">\n" +
                "      <usagekey>" + usageKey + "</usagekey>\n" +
                "      <userid>" + userId + "</userid>\n" +
                "      <reqType>" + reqType + "</reqType>\n" +
                "      <VacLeaveType>" + VacLeaveType + "</VacLeaveType>\n" +
                "      <fromDate>" + fromDate + "</fromDate>\n" +
                "      <toDate>" + toDate + "</toDate>\n" +
                "      <LeaveHour>" + LeaveHour + "</LeaveHour>\n" +
                "      <LeaveMin>" + LeaveMin + "</LeaveMin>\n" +
                "      <LeavePeriod>" + LeavePeriod + "</LeavePeriod>\n" +
                "      <MgrApproved>" + MgrApproved + "</MgrApproved>\n" +
                "      <MgrName>" + MgrName + "</MgrName>\n" +
                "      <SleepFlag>" + SleepFlag + "</SleepFlag>\n" +
                "      <SleepDesc>" + SleepDesc + "</SleepDesc>\n" +
                "    </RequestVacLeave>\n" +
                "  </soap:Body>\n" +
                "</soap:Envelope>";
    }


}
