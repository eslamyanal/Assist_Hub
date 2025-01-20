package com.example.assisthub.Activity;

import static com.example.assisthub.Activity.SignUpActivity.isNotEmpty;
import static com.example.assisthub.Activity.confirmationActivity.checkNumber;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.assisthub.R;
import com.example.assisthub.Retrofit.retrofitFile.IsReturnPaymentRetrofit;
import com.example.assisthub.Retrofit.retrofitFile.deleteReturnMoneyRetrofit;
import com.example.assisthub.model.CheckModel;
import com.google.android.material.textfield.TextInputLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReturnMoney extends AppCompatActivity {

   private TextView RefundAmountTextView;
   private TextInputLayout AccountNumber;

   private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_money);


        //mirror object
        RefundAmountTextView = findViewById(R.id.RefundAmount);
        AccountNumber = findViewById(R.id.AccountNumber);
        sendButton = findViewById(R.id.sendButton);


        Intent intent = getIntent();
        int BackMoneyRejectedActivity = intent.getIntExtra("BackMoneyRejectedActivity", 0);
        int Id = intent.getIntExtra("Id", 0);
        int RefundAmount = intent.getIntExtra("RefundAmount", 0);

        RefundAmountTextView.setText(RefundAmount+ "JOD");

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(isNotEmpty(AccountNumber))
                   if (checkNumber(AccountNumber, 14) ){

                       if(BackMoneyRejectedActivity == 1){

                           Call<CheckModel> call = IsReturnPaymentRetrofit
                                   .getInstance().getServicesInterface().IsReturnPayment(Id);
                           call.enqueue(new Callback<CheckModel>() {
                               @Override
                               public void onResponse(Call<CheckModel> call, Response<CheckModel> response) {
                                   if(!response.body().geterror()){

                                       Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                                       startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                       finish();
                                   }
                                   else
                                       Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();

                               }
                               @Override
                               public void onFailure(Call<CheckModel> call, Throwable t) {
                                   Log.e("API Failure", "Throwable: ", t);
                               }
                           });

                       }
                       else{
                           Call<CheckModel> call = deleteReturnMoneyRetrofit.checkInstance()
                                   .getServicesInterface().deleteReturnMoney(Id);

                           call.enqueue(new Callback<CheckModel>() {
                               @Override
                               public void onResponse(Call<CheckModel> call, Response<CheckModel> response) {
                                   if(!response.body().geterror()){
                                       Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();

                                       startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                       finish();
                                   }
                                   else
                                       Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();

                               }
                               @Override
                               public void onFailure(Call<CheckModel> call, Throwable t) {
                                   Log.e("API Failure", "Throwable: ", t);
                               }
                           });
                       }

                   }else
                       Toast.makeText(getApplicationContext(),"enter a valid bank account number",Toast.LENGTH_LONG).show();
            }
        });
    }
}