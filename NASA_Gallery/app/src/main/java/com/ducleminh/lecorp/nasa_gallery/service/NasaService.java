package com.ducleminh.lecorp.nasa_gallery.service;

import com.ducleminh.lecorp.nasa_gallery.model.NasaModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Admin on 02/05/2017.
 */
public interface NasaService {
    @GET("planetary/apod")
    Call<NasaModel> getNasa(@Query("date") String date, @Query("api_key") String key);
}
