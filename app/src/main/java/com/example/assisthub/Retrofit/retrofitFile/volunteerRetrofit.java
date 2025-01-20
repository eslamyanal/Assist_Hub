package com.example.assisthub.Retrofit.retrofitFile;

import com.example.assisthub.Retrofit.interfac.HTTP_Method;
import com.example.assisthub.Retrofit.interfac.URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class volunteerRetrofit {



    private static volunteerRetrofit VolunteerRetrofit = null ;
    private HTTP_Method HTTP_Method ;

    private volunteerRetrofit() {
       Retrofit retrofit = new Retrofit.Builder().baseUrl(URL.volunteer)
                .addConverterFactory(GsonConverterFactory.create()).build();
        HTTP_Method = retrofit .create (HTTP_Method .class);
    }

    public static synchronized volunteerRetrofit checkInstance() {
        if (VolunteerRetrofit== null)
            VolunteerRetrofit  = new volunteerRetrofit();

        return  VolunteerRetrofit;
    }

    public HTTP_Method  getServicesInterface() {
        return HTTP_Method ;
    }





}
