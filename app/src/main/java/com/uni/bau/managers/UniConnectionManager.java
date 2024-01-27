package com.uni.bau.managers;

import static com.uni.bau.managers.UniConnectionManager.APIService.BASE_URL;

import android.content.Context;
import android.util.Log;

import com.moczul.ok2curl.CurlInterceptor;
import com.moczul.ok2curl.logger.Loggable;
import com.uni.bau.BuildConfig;
import com.uni.bau.helpers.ApiConstants;
import com.uni.bau.models.RegisterDeviceParams;
import com.uni.bau.models.SchedulePost;
import com.uni.bau.utilities.MyClient;
import com.uni.bau.utilities.Utils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public class UniConnectionManager {


    public  void POST(Context context, String URl, Map<String, String> Params, final ApiCallResponse callResponse) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> call = service.POST_FormUrlEncoded(URl, Params,new Utils().getUserToken(context));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callResponse.onSuccess(response.body().toString(), response.code() + "");
                    } else {
                        try {
                            callResponse.onSuccess(response.body().string(), response.code() + "");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    callResponse.onFailure("error");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callResponse.onFailure(t.toString());
            }
        });
    }

    public  void POSTURLUNI(Context context, String URl, Map<String, String> Params, final ApiCallResponse callResponse) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://app1.bau.edu.jo:7799/")
                .client(new ConnectionManager().addCookiesHeader(context,URl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        String coockie = MyClient.getCookie();
        if (coockie == null)
        {
            coockie = "";
        }
        Call<ResponseBody> call = service.POST_FormUrlEncodedUNI(URl, Params,coockie);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callResponse.onSuccess(response.body().toString(), response.code() + "");
                    } else {
                        try {
                            callResponse.onSuccess(response.body().string(), response.code() + "");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    callResponse.onFailure("error");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callResponse.onFailure(t.toString());
            }
        });
    }

    public  void POSTRaw(Context context, String URl, SchedulePost Params, final ApiCallResponse callResponse) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> call = service.POSTRAW(URl, Params,new Utils().getUserToken(context));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callResponse.onSuccess(response.body().toString(), response.code() + "");
                    } else {
                        try {
                            callResponse.onSuccess(response.body().string(), response.code() + "");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    callResponse.onFailure("error");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callResponse.onFailure(t.toString());
            }
        });
    }

    public  void POSTRawDevice(Context context, String URl, RegisterDeviceParams Params, final ApiCallResponse callResponse) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> call = service.POSTRAWDevice(URl, Params,new Utils().getUserToken(context));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        callResponse.onSuccess(response.body().toString(), response.code() + "");
                    } else {
                        try {
                            callResponse.onSuccess(response.body().string(), response.code() + "");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    callResponse.onFailure("error");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callResponse.onFailure(t.toString());
            }
        });
    }

    public  void GET(Context context, String URl,Map<String, String> Params, final ApiCallResponse callResponse) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> call = service.GET(URl,Params,new Utils().getUserToken(context));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body() == null) {
                            callResponse.onSuccess(response.body().toString(), response.code()+"");
                        } else {
                            callResponse.onSuccess(response.body().string(),response.code()+"");
                        }
                    } else {
                        callResponse.onFailure("error");
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callResponse.onFailure(t.toString());
            }
        });

    }


    public interface APIService {

        public static String BASE_URL = "http://hashtechsllc.com/HashStudents/Api/";

        @GET()
        public Call<ResponseBody> GET(@Url String url, @QueryMap Map<String, String> params, @Header("Authorization") String Authorization);

        @GET()
        public Call<ResponseBody> GETPARAMS(@Url String url, @QueryMap Map<String, String> params);

        @POST
        public Call<ResponseBody> POSTRAW(@Url String url, @Body SchedulePost requestBody, @Header("Authorization") String Authorization);

        @POST
        public Call<ResponseBody> POSTRAWDevice(@Url String url, @Body RegisterDeviceParams requestBody, @Header("Authorization") String Authorization);


        @FormUrlEncoded
        @POST
        public Call<ResponseBody> POST_FormUrlEncoded(@Url String url, @FieldMap Map<String, String> params, @Header("Authorization") String Authorization);


        @FormUrlEncoded
        @POST
        public Call<ResponseBody> POST_FormUrlEncodedUNI(@Url String url, @FieldMap Map<String, String> params,@Header("Cookie") String Cookie);
    }

}
