package com.example.assisthub.Retrofit.retrofitFile;

import com.example.assisthub.Retrofit.interfac.HTTP_Method;
import com.example.assisthub.Retrofit.interfac.URL;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IsReturnPaymentRetrofit {



    private static IsReturnPaymentRetrofit Retrofit = null;
    private HTTP_Method httpMethod;

    private IsReturnPaymentRetrofit() {
       Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL.IsReturnPayment)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        httpMethod = retrofit.create(HTTP_Method.class);
    }

    public static synchronized IsReturnPaymentRetrofit getInstance() {
        if (Retrofit == null)
            Retrofit = new IsReturnPaymentRetrofit();

        return Retrofit;
    }

    public HTTP_Method getServicesInterface() {
        return httpMethod;
    }




}
