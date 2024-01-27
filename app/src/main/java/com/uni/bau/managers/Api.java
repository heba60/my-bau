package com.uni.bau.managers;



import android.app.Activity;
import android.content.Context;
import android.webkit.CookieManager;

import com.uni.bau.utilities.MyClient;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Api {

    private static Api api = null;

    private Context mContext;

    public static void init(Context context) {
        if (api == null)
            api = new Api(context);
    }

    public static Api getInstance() {
        if (api == null)
            throw new IllegalArgumentException("Please init the instance first");
        return api;

    }

    public Api(Context mContext) {
        this.mContext = mContext;
    }

    public void post(String url, RequestBody params, ApiCallResponse connectionCallback) {
        OkHttpClient okHttpClient = MyClient.getmClient(mContext);
        String coockie = MyClient.getCookie();
        try {
            final Request request1 = new Request.Builder()
                    .url(url)
                    .post(params)
                    .addHeader("Cookie",  coockie)
                    .build();
            Response response1 = okHttpClient.newCall(request1).execute();
            String response = response1.body().string();
            ((Activity)mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                        connectionCallback.onSuccess(response.toString(),"");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            ((Activity)mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    connectionCallback.onFailure(e.getLocalizedMessage());
                }
            });

        }
    }


    public String getCookie(String siteName,String cookieName){
        String CookieValue = null;

        CookieManager cookieManager = CookieManager.getInstance();
        String cookies = cookieManager.getCookie(siteName);
        String[] temp=cookies.split(";");
        for (String ar1 : temp ){
            if(ar1.contains(cookieName)){
                String[] temp1=ar1.split("=");
                CookieValue = temp1[1];
                break;
            }
        }
        return CookieValue;
    }

    public void get(String url, ApiCallResponse connectionCallback) {
        OkHttpClient okHttpClient = MyClient.getmClient(mContext);
        String coockie = MyClient.getCookie();

        try {
            final Request.Builder request = new Request.Builder()
                    .url(url)
                    .get();
            if (coockie != null && !coockie.isEmpty()){
                request.addHeader("Cookie",   coockie );
            }

            Response conResponse = okHttpClient.newCall(request.build()).execute();
            String response = conResponse.body().string();
            ((Activity)mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        connectionCallback.onSuccess(response,"");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            ((Activity)mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    connectionCallback.onFailure(e.getLocalizedMessage());
                }
            });

        }
    }

    public void postCalendar(String url, RequestBody params, ApiCallResponse connectionCallback) {
        OkHttpClient okHttpClient = MyClient.getmClient(mContext);
        String coockie = MyClient.getCookie();
        try {
            final Request request1 = new Request.Builder()
                    .url(url)
                    .post(params)
                    .build();
            Response response1 = okHttpClient.newCall(request1).execute();
            String response = response1.body().string();
            ((Activity)mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    connectionCallback.onSuccess(response,"");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            ((Activity)mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    connectionCallback.onFailure(e.getLocalizedMessage());
                }
            });

        }
    }
}
