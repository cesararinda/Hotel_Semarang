package com.riyan.hotelsemarang.helper;

import com.riyan.hotelsemarang.model.ListHotel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;



public interface ServiceClient {
    @GET("exec")
    Call<ListHotel> getHotel(@Query("sheet") String namaSheet);
}