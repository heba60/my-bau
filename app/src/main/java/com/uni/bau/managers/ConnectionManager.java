package com.uni.bau.managers;

import android.content.Context;
import android.util.Log;


import com.moczul.ok2curl.CurlInterceptor;
import com.moczul.ok2curl.logger.Loggable;
import com.uni.bau.BuildConfig;
import com.uni.bau.utilities.MyClient;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

import static com.uni.bau.managers.ConnectionManager.APIService.BASE_URL;


/**
 * Created by moayed on 12/10/17.
 */

public class ConnectionManager {


    public static void GET(Context context, String URl, final ApiCallResponse callResponse) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> call = service.GET(URl);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body() == null) {
                            callResponse.onSuccess(response.body().toString(), response.message());
                        } else {
                            callResponse.onSuccess(response.body().string(), response.message());
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

    public static void GETParamsWithCookeis(Context context, String URl,Map<String, String> Params, final ApiCallResponse callResponse) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(new ConnectionManager().addCookiesHeader(context,URl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> call = service.GETPARAMS(URl,Params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body() == null) {
                            callResponse.onSuccess(response.body().toString(), response.message());
                        } else {
                            callResponse.onSuccess(response.body().string(), response.message());
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

    public OkHttpClient addCookiesHeader(Context context,String URL) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptorJ = new HttpLoggingInterceptor();
        httpClient.addInterceptor(interceptorJ);
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS).addInterceptor(new CurlInterceptor(new Loggable() {
            @Override
            public void log(String message) {
                if (BuildConfig.DEBUG)
                    Log.d(URL, message);
            }
        }));
        OkHttpClient clientookie = MyClient.getmClient(context);
        String coockie = MyClient.getCookie();
        if (coockie == null)
        {
            coockie = "";
        }
        String finalCoockie = coockie;
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder();
                builder.method(original.method(), original.body());
                builder.header("Cookie",finalCoockie);
                return chain.proceed(builder.build());
            }
        });
        OkHttpClient client = httpClient.build();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        return client;

    }

    public static void GETParams(Context context, String URl,Map<String, String> Params, final ApiCallResponse callResponse) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService service = retrofit.create(APIService.class);
        Call<ResponseBody> call = service.GETPARAMS(URl,Params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        if (response.body() == null) {
                            callResponse.onSuccess(response.body().toString(), response.message());
                        } else {
                            callResponse.onSuccess(response.body().string(), response.message());
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

    public void PostRAW(Context context , String URl , Map<String, String> Params , final ApiCallResponse callResponse) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        OkHttpClient okHttpClient = MyClient.getmClient(context);
        String coockie = MyClient.getCookie();
        ConnectionManager.APIService  service = retrofit.create(ConnectionManager.APIService .class);
        Call<ResponseBody> call = service.POST_FormUrlEncoded(URl, Params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful())
                    {
                        callResponse.onSuccess(response.body().string(), response.code() + "");
                    }else {
                        callResponse.onFailure(response.code()+"");
                    }

                } catch (Exception e) {
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

        public static String BASE_URL = "https://aau.edu.jo/ar/";

        @GET()
        public Call<ResponseBody> GET(@Url String url);

        @GET()
        public Call<ResponseBody> GETCOOKIES(@Url String url);

        @GET()
        public Call<ResponseBody> GETPARAMS(@Url String url, @QueryMap Map<String, String> params);

        @POST
        public Call<ResponseBody> POST_FormUrlEncoded(@Url String url, @QueryMap Map<String, String> params);
    }

}
