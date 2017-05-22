package com.ducleminh.lecorp.nasa_gallery.service;

import com.ducleminh.lecorp.nasa_gallery.helper.CONFIG;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Admin on 02/05/2017.
 */
public class ServiceGenerator {
    private static Retrofit sRetrofit = null;
    private static Retrofit.Builder sBuilder = new Retrofit.Builder()
        .baseUrl(CONFIG.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create());
    private final static OkHttpClient.Builder sOkHttpClientBuilder = new OkHttpClient.Builder();
    private final static OkHttpClient sOkHttpClient = sOkHttpClientBuilder.build();

    public static <T> T createService(Class<T> serviceClass) {
        if (sRetrofit == null) {
            sRetrofit = sBuilder.client(sOkHttpClient).build();
        }
        return sRetrofit.create(serviceClass);
    }
}
