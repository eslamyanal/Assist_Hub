package com.example.assisthub.Activity;

import static com.example.assisthub.Activity.SignUpActivity.isNotEmpty;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.assisthub.R;
import com.example.assisthub.Retrofit.retrofitFile.loginUserRetrofit;
import com.example.assisthub.Shared_pereferences_profile.SharedPrefManager;
import com.example.assisthub.model.Result_User_Model;
import com.example.assisthub.model.User_model;
import com.google.android.material.textfield.TextInputLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout phoneUser, password;
    private Button loginButton;
    private LinearLayout resetPassword, signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //mirror object
        phoneUser = findViewById(R.id.PhoneUser);
        password = findViewById(R.id.Password);
        loginButton = findViewById(R.id.loinButton);
        resetPassword = findViewById(R.id.resetPassword);
        signUp = findViewById(R.id.sinUP);

        //set methods
        loginButton.setOnClickListener(this);
        signUp.setOnClickListener(this);
        resetPassword.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.loinButton){
            if (isNotEmpty(phoneUser) && isNotEmpty(password))
                loginUser();

        }else if (v.getId() == R.id.sinUP)
            startActivity(new Intent(this, SignUpActivity.class));

        else if (v.getId() == R.id.resetPassword)
            startActivity(new Intent(this, VerifyNumber.class));
    }



    private void loginUser() {
        String phone = phoneUser.getEditText().getText().toString().trim();
        String pass = password.getEditText().getText().toString().trim();

        Call<Result_User_Model> call = loginUserRetrofit.getInstance()
                .getServicesInterface().loginUser(phone, pass);

        call.enqueue(new Callback<Result_User_Model>() {
            @Override
            public void onResponse(Call<Result_User_Model> call, Response<Result_User_Model> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Result_User_Model result = response.body();
                    if (result.isError()) {
                        Toast.makeText(LoginActivity.this,  result.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        handleSuccessfulLogin(result);
                    }
                } else {
                    Log.e("API Error", "Response Code: " + response.code());
                    Toast.makeText(LoginActivity.this, "Failed to log in. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Result_User_Model> call, Throwable t) {
                Log.e("API Failure", "Error: " + t.getMessage(), t);
                Toast.makeText(LoginActivity.this, "Network error, please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleSuccessfulLogin(Result_User_Model result) {

        User_model userModel = new User_model(
                result.getUser().getId(),
                result.getUser().getName_User(),
                result.getUser().getPhone(),
                result.getUser().getNationality(),
                result.getUser().getPassword_User(),
                result.getUser().getNationaltyOrPassportNumber(),
                result.getUser().getHealth_Insurance(),
                result.getUser().getFile_Name()
        );

        Toast.makeText(this, "Welcome " + result.getUser().getName_User(), Toast.LENGTH_LONG).show();
        SharedPrefManager.getInstance(this).userLogin(userModel);

        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


}
