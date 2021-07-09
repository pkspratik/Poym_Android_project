package com.example.kanthi.projectmonitoring.Network;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Kanthi on 21/2/18.
 */
public interface ServerTimeServices {
    @GET("/utc/now")
    Call<String> getActualServerTime();
}
