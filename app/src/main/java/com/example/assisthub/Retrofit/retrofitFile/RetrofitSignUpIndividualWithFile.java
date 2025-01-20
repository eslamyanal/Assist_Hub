package com.example.assisthub.Retrofit.retrofitFile;

import com.example.assisthub.Retrofit.interfac.HTTP_Method;
import com.example.assisthub.Retrofit.interfac.URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSignUpIndividualWithFile {

    private static RetrofitSignUpIndividualWithFile RetrofitSignUpIndividualWithFile = null ;
    private HTTP_Method HTTP_Method ;

    private RetrofitSignUpIndividualWithFile() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL.SignupIndividualUserWithFile)
                .addConverterFactory(GsonConverterFactory.create()).build();
        HTTP_Method = retrofit .create (HTTP_Method .class);
    }

    public static synchronized RetrofitSignUpIndividualWithFile checkInstance() {
        if ( RetrofitSignUpIndividualWithFile == null)
            RetrofitSignUpIndividualWithFile = new RetrofitSignUpIndividualWithFile();

        return RetrofitSignUpIndividualWithFile;
    }

    public HTTP_Method  getServicesInterface() {
        return HTTP_Method ;
    }


}
