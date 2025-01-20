package com.example.assisthub.Retrofit.retrofitFile;

import com.example.assisthub.Retrofit.interfac.HTTP_Method;
import com.example.assisthub.Retrofit.interfac.URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestsRejectedRetrofit {



    private static RequestsRejectedRetrofit Retrofit = null;
    private HTTP_Method httpMethod;

    private RequestsRejectedRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL.RequestsRejected)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        httpMethod = retrofit.create(HTTP_Method.class);
    }

    public static synchronized RequestsRejectedRetrofit getInstance() {
        if (Retrofit == null) {
            Retrofit = new RequestsRejectedRetrofit();
        }
        return Retrofit;
    }

    public HTTP_Method getServicesInterface() {
        return httpMethod;
    }





}
