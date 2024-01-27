package com.uni.bau.utilities;

import android.content.Context;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyClient {

    private static OkHttpClient mClient;
    private static String phpsessid;

    public static void reset(){
        mClient = null;
        phpsessid = "";
    }
    public static OkHttpClient getmClient(Context context){
        if (mClient==null) {
            return createMine(context);
        }else {
            return mClient;
        }
    }

    public static String  getCookie(){
        return phpsessid;
    }

    private static OkHttpClient createMine(Context context){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(new CookieJar() {

            private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                if (phpsessid != null && !phpsessid.isEmpty()){
                    return;
                }
                if (cookies != null) {
                    for (int i = 0, len = cookies.size(); i < len; i++) {
//                        if ("PHPSESSID".equals(cookies.get(i).name())) {
                            phpsessid = cookies.get(0).toString();
//                            Log.e("Heeeey" , phpsessid);
                        builder
                                    .addInterceptor(new Interceptor() {
                                        @Override
                                        public Response intercept(Chain chain) throws IOException {
                                            final Request original = chain.request();

                                            final Request authorized = original.newBuilder()
                                        .addHeader("Accept-Encoding", "gzip, deflate, br")
                                        .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
//                                        .addHeader("Cache-Control", "no-cache")
//                                        .addHeader("Connection", "keep-alive")
                                        .addHeader("Cookie", cookies.get(0).value())
//                                        .addHeader("Host", "portals.philadelphia.edu.jo")
//                                        .addHeader("Pragma", "no-cache")
//                                        .addHeader("Upgrade-Insecure-Requests", "1")
                                                    .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.96 Safari/537.36")
                                                    .build();


                                            return chain.proceed(authorized);
                                        }
                                    })
                                    .cache(new Cache(context.getCacheDir(), 25 * 1024 * 1024))
                                    ;


                        }
//                    }
                }
                cookieStore.put(url, cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url);
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        });

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();


            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            mClient = builder.build();

        } catch (Exception e) {
            mClient = builder.build();
            throw new RuntimeException(e);
        }
        return mClient;
    }
    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
