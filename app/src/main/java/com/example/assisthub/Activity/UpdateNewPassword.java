package com.example.assisthub.Activity;

import static com.example.assisthub.Activity.SignUpActivity.checkPassword;
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
import com.example.assisthub.Retrofit.retrofitFile.updateNewPasswordRetrofit;
import com.example.assisthub.model.CheckModel;
import com.google.android.material.textfield.TextInputLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateNewPassword extends AppCompatActivity {

    private TextInputLayout new_password_input , confirm_new_password_input ;
    private Button confirm_button_newPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_new_password);

        Intent i = getIntent();
        String phone = i.getStringExtra("phone");

        //mirror object
        new_password_input = findViewById(R.id.new_password_input);
        confirm_new_password_input = findViewById(R.id.confirm_new_password_input);
        confirm_button_newPassword = findViewById(R.id.confirm_button_newPassword);

        confirm_button_newPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newPassword = new_password_input.getEditText().getText().toString().trim();
                String confirmPassword = confirm_new_password_input.getEditText().getText().toString().trim();

                if(isNotEmpty(new_password_input) && checkPassword(new_password_input)
                        &&  isNotEmpty(confirm_new_password_input) && checkPassword(confirm_new_password_input))
                {

                    if(newPassword.equals(confirmPassword))
                    {
                        Call<CheckModel> call = updateNewPasswordRetrofit.checkInstance().
                                getServicesInterface().updateNewPassword(phone , newPassword);

                        call.enqueue(new Callback<CheckModel>() {
                            @Override
                            public void onResponse(Call<CheckModel> call, Response<CheckModel> response) {
                                if(!response.body().geterror()){
                                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(UpdateNewPassword.this , LoginActivity.class));
                                }
                                else
                                    Toast.makeText(getApplicationContext(),response.body().getMessage(),Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<CheckModel> call, Throwable t) {
                                Log.d("error" , t.getMessage());

                            }
                        });

                    }
                    else
                        Toast.makeText(getApplicationContext(),"not match value",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}