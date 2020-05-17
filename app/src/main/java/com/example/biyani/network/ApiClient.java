package com.example.biyani.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Juned on 7/26/2017.
 */

public class   ApiClient
{

    private static final int DEFAULT_TIMEOUT = 3000;
    private static Retrofit mRetrofit = null;


    public static Retrofit getClient()
    {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
         // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // init cookie manager
        //CookieHandler cookieHandler = new CookieManager();

       /* CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);*/

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(logging);
        //builder.cookieJar(new JavaNetCookieJar(cookieHandler));
        builder.cookieJar(new CookieJar() {
            private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                cookieStore.put(url, cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url);
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        });


        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
       // builder.writeTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS);
       // builder.interceptors().add(new AddCookiesInterceptor());
       // builder.interceptors().add(new ReceivedCookiesInterceptor());



        if (mRetrofit == null)
        {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Urls.BASE_URL)
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

}
