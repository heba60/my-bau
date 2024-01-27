package com.uni.bau.managers;


import static com.uni.bau.managers.ChatConnectionManager.APIService.BASE_URL;

import android.content.Context;

import com.uni.bau.utilities.Utils;

import java.io.IOException;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


/**
 * Created by moayed on 12/10/17.
 */

public class ChatConnectionManager {


    public static void GET(Context context, String URl,Map<String, String> Params, final ApiCallResponse callResponse) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
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
        ChatConnectionManager.APIService  service = retrofit.create(ChatConnectionManager.APIService .class);
        Call<ResponseBody> call = service.POST_FormUrlEncoded(URl, Params,new Utils().getUserToken(context));
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

        public static String BASE_URL = "http://167.86.67.52/HashStudents/Api//";

        @GET()
        public Call<ResponseBody> GET(@Url String url, @QueryMap Map<String, String> params,@Header("Authorization") String Authorization);

        @GET()
        public Call<ResponseBody> GETPARAMS(@Url String url, @QueryMap Map<String, String> params);

        @FormUrlEncoded
        @POST
        public Call<ResponseBody> POST_FormUrlEncoded(@Url String url, @FieldMap Map<String, String> params, @Header("Authorization") String Authorization);
    }

}
