package com.example.assisthub.Retrofit.retrofitFile;

import com.example.assisthub.Retrofit.interfac.HTTP_Method;
import com.example.assisthub.Retrofit.interfac.URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class bookingRequestsRetrofit {
    private static bookingRequestsRetrofit bookingRequest = null ;
    private  HTTP_Method  HTTP_Method ;

    private bookingRequestsRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL.bookingRequests)
                .addConverterFactory(GsonConverterFactory.create()).build();
        HTTP_Method = retrofit .create (HTTP_Method .class);
    }

    public static synchronized bookingRequestsRetrofit checkInstance() {
        if ( bookingRequest == null)
            bookingRequest  = new bookingRequestsRetrofit();

        return  bookingRequest;
    }

    public HTTP_Method  getServicesInterface() {
        return HTTP_Method ;
    }







}
