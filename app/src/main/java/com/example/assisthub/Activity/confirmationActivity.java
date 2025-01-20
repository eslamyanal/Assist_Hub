package com.example.assisthub.Activity;

import static com.example.assisthub.Activity.SignUpActivity.isNotEmpty;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.assisthub.R;
import com.example.assisthub.Retrofit.retrofitFile.bookingRequestsRetrofit;
import com.example.assisthub.Shared_pereferences_profile.SharedPrefManager;
import com.example.assisthub.model.CheckModel;
import com.google.android.material.textfield.TextInputLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class confirmationActivity extends AppCompatActivity {

   private TextView txtPayment  , warningTextView;
   private TextInputLayout CardNumber , ExpiryDate , CVV , CardHolderName;
   private Button BookingRequestsButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirmation);

        //mirror object
        txtPayment = findViewById(R.id.txtPayment);
        BookingRequestsButton = findViewById(R.id.BookingRequestsButton);
        warningTextView = findViewById(R.id.warningTextView);
        CardNumber = findViewById(R.id.CardNumber);
        ExpiryDate = findViewById(R.id.ExpiryDate);
        CVV = findViewById(R.id.CVV);
        CardHolderName = findViewById(R.id.CardHolderName);

        Intent i = getIntent();
        int payment        = i.getIntExtra("cost" , -1);
        int idBed          = i.getIntExtra("idBed" , -1);
        int IdDoctor       = i.getIntExtra("IdDoctor" , -1);
        int idHospital     = i.getIntExtra("idHopital" , -1);
        int IdSection      = i.getIntExtra("SectionInHospital" , -1);
        int  cost          = i.getIntExtra("cost" , -1);
        String dateForBed  = i.getStringExtra("dateForBed");
        String periodBed   = i.getStringExtra("periodBed");


        txtPayment.setText("Payment Amount :  "+payment + "JOD");


        if (IdSection == 0){
            BookingRequestsButton.setEnabled(false);
            warningTextView.setVisibility(View.VISIBLE);
        }

        else{
            //enable the button
            BookingRequestsButton.setEnabled(true);
            warningTextView.setVisibility(View.GONE);

                BookingRequestsButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (isNotEmpty(CardNumber) && checkNumber(CardNumber , 16) && isNotEmpty(ExpiryDate)
                                && validateExpiryDate(ExpiryDate) && isNotEmpty(CVV) && isNotEmpty(CardHolderName)){


                            Call<CheckModel> call  = bookingRequestsRetrofit.
                                    checkInstance().getServicesInterface().checkBookingRequests(
                                            idHospital, IdSection, IdDoctor, idBed,periodBed, dateForBed, cost,
                                            SharedPrefManager.getInstance(getApplicationContext()).getUserData().getId()
                                    );

                            call.enqueue(new Callback<CheckModel>() {
                                @Override
                                public void onResponse(Call<CheckModel> call, Response<CheckModel> response) {
                                    if(!response.body().geterror()){
                                        Toast.makeText(confirmationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext() , HomeActivity.class));

                                    }else{
                                        Toast.makeText(confirmationActivity.this,  response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<CheckModel> call, Throwable t) {

                                }
                            });

                        }
                    }
                });





        }

    }


    private boolean validateExpiryDate(TextInputLayout expiryDateInput) {
        String expiryDate = expiryDateInput.getEditText().getText().toString().trim();

        if (!expiryDate.matches("^(0[1-9]|1[0-2])\\/\\d{2}$")) {
            expiryDateInput.setError("write it in correct way");
            return false;
        }else
            expiryDateInput.setErrorEnabled(false);

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yy");
            dateFormat.setLenient(false);
            Calendar expiry = Calendar.getInstance();
            expiry.setTime(dateFormat.parse(expiryDate));
            expiry.set(Calendar.DAY_OF_MONTH, expiry.getActualMaximum(Calendar.DAY_OF_MONTH));
            Calendar today = Calendar.getInstance();
            return expiry.after(today);
        } catch (Exception e) {
            return false; // Parsing failed
        }
    }

    public static boolean checkNumber (TextInputLayout textInputLayout  , int i){

        String  textInput = textInputLayout.getEditText().getText().toString().trim();

        if(textInput.length() < i){
            textInputLayout.setError("The Field Should  not be less than 16 number >_< ");
            return false;
        }else{
            textInputLayout.setErrorEnabled(false);
            return true;
        }

    }



}