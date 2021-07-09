package com.example.kanthi.projectmonitoring.Network;

import com.example.kanthi.projectmonitoring.Utils.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/***
 * Created by Kanthi on 21/2/18.
 */
public class RetrofitHelper {

    public static RetrofitHelper mInstance;
    public ProjectMonitorNetworkServices mServices;
    public ServerTimeServices mServerTimeServices;

    private RetrofitHelper() {
        mInstance = this;
    }

    public static RetrofitHelper getInstance() {
        return mInstance == null ? new RetrofitHelper() : mInstance;
    }

    public ProjectMonitorNetworkServices getProjectMonitorNetworkService() {
        if (mServices == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            //logging.setLevel(HttpLoggingInterceptor.Level.NONE);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                // add your other interceptors …

                // add logging as last interceptor
            httpClient.addInterceptor(logging);  // <-- this is the important line!

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
            mServices = retrofit.create(ProjectMonitorNetworkServices.class);
        }
        return mServices;
    }
    public ServerTimeServices getServerTimeService(Converter.Factory converterFactory) {
        if (mServerTimeServices == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.SERVERTIME_BASE_URL)
                    .addConverterFactory(converterFactory)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mServerTimeServices = retrofit.create(ServerTimeServices.class);
        }
        return mServerTimeServices;
    }
}
