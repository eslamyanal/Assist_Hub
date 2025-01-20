package com.example.assisthub.Activity;

import static com.example.assisthub.Activity.SignUpActivity.checkPhone;
import static com.example.assisthub.Activity.SignUpActivity.isNotEmpty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assisthub.R;
import com.example.assisthub.Retrofit.retrofitFile.VerificationPhoneNumberRetrofit;
import com.example.assisthub.model.CheckModel;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VerifyNumber extends AppCompatActivity {

    private TextInputLayout VerificationPhoneNumberTextInputrLayout ;
    private Button VerificationPhoneNumberButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.verify_number);

        //mirror object
        VerificationPhoneNumberTextInputrLayout = findViewById(R.id.VerificationPhoneNumber);
        VerificationPhoneNumberButton           = findViewById(R.id.VerificationPhoneNumberButton);


        VerificationPhoneNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isNotEmpty(VerificationPhoneNumberTextInputrLayout)){

                    if(checkPhone(VerificationPhoneNumberTextInputrLayout)){

                        Call<CheckModel> call  = VerificationPhoneNumberRetrofit.checkInstance()
                                .getServicesInterface().VerificationPhoneNumber(
                                        VerificationPhoneNumberTextInputrLayout.getEditText().getText().toString());


                        call.enqueue(new Callback<CheckModel>() {
                            @Override
                            public void onResponse(Call<CheckModel> call, Response<CheckModel> response) {

                                if(!response.body().geterror()){

                                    Toast.makeText(VerifyNumber.this, response.body().getMessage() , Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(VerifyNumber.this , VerificationCode.class);
                                    i.putExtra("phone" , VerificationPhoneNumberTextInputrLayout.getEditText().getText().toString());
                                    startActivity(i);
                                    finish();
                                }
                                else
                                    Toast.makeText(VerifyNumber.this, response.body().getMessage() , Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<CheckModel> call, Throwable t) {
                                Log.d("error verification " , t.getMessage());


                            }
                        });

                    }


                }

            }
        });



    }


}