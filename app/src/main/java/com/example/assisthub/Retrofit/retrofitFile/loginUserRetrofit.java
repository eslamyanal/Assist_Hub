package com.example.assisthub.Retrofit.retrofitFile;

import com.example.assisthub.Retrofit.interfac.HTTP_Method;
import com.example.assisthub.Retrofit.interfac.URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class loginUserRetrofit {

    private static loginUserRetrofit instance = null; // Renamed variable to avoid conflict
    private HTTP_Method httpMethod;

    private loginUserRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL.SignInIndividualUser) // Ensure this ends with a trailing slash ("/").
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        httpMethod = retrofit.create(HTTP_Method.class);
    }

    public static synchronized loginUserRetrofit getInstance() {
        if (instance == null) {
            instance = new loginUserRetrofit(); // Proper initialization
        }
        return instance;
    }

    public HTTP_Method getServicesInterface() {
        return httpMethod;
    }
}
