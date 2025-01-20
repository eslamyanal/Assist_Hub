package com.example.assisthub.Activity;

import static com.example.assisthub.Activity.SignUpActivity.isNotEmpty;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.assisthub.R;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Random;

public class VerificationCode extends AppCompatActivity {

    static int counter = 1;
    static String NumberRandom, code;
    private TextView timerText, resend_text;
    private TextInputLayout number1, number2, number3, number4;
    private Button confirm_button_verification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

        //mirror object
        timerText                   = findViewById(R.id.timer_text);
        resend_text                 = findViewById(R.id.resend_text);
        number1                     = findViewById(R.id.number1);
        number2                     = findViewById(R.id.number2);
        number3                     = findViewById(R.id.number3);
        number4                     = findViewById(R.id.number4);
        confirm_button_verification = findViewById(R.id.confirm_button_verification);

        Intent i = getIntent();
        String phone = i.getStringExtra("phone");

        // Disable the Resend text initially
        resend_text.setEnabled(false);

        // Generate a random 4-digit number
        Random random = new Random();
        NumberRandom = String.valueOf(random.nextInt(9000) + 1000);

        // Simulate sending the code via an external server/service
        sendVerificationCodeToServer(phone,  "1234"); // NumberRandom replace 1234

        confirm_button_verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNotEmpty(number1) && isNotEmpty(number2) && isNotEmpty(number3) && isNotEmpty(number4)) {

                    code = number1.getEditText().getText().toString() +
                            number2.getEditText().getText().toString() +
                            number3.getEditText().getText().toString() +
                            number4.getEditText().getText().toString();


                    if (code.equals("1234")) { //NumberRandom
                        Intent intent = new Intent(VerificationCode.this, UpdateNewPassword.class);
                        intent.putExtra("phone", phone);
                        startActivity(intent);
                    } else
                        Toast.makeText(VerificationCode.this, "Invalid code, please try again", Toast.LENGTH_SHORT).show();

                }
            }
        });

        // Start the countdown timer
        startCountdown();

        resend_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter < 4) {
                    NumberRandom = String.valueOf(random.nextInt(9000) + 1000);
                    // Simulate sending a new code
                    sendVerificationCodeToServer(phone, NumberRandom);
                    startCountdown();
                    resend_text.setEnabled(false);
                } else
                    Toast.makeText(VerificationCode.this, "Try later", Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Simulate sending verification code to a server
    private void sendVerificationCodeToServer(String phone, String randomNumber) {

        // Send broadcast to simulate SMS receipt
        Intent intent = new Intent("com.example.assisthub.SMS_RECEIVED_ACTION");
        intent.putExtra("phone", phone);
        intent.putExtra("message", "Your verification code is: " + randomNumber);
        sendBroadcast(intent);
    }


    private void startCountdown() {

        long totalTime = 60000; // Total time for 1 minute in milliseconds
        long interval = 1000;  // Interval in milliseconds (1 second)

        new CountDownTimer(totalTime, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                timerText.setText(String.format("0:%02d", secondsRemaining));
            }

            @Override
            public void onFinish() {
                timerText.setText("Time expired");
                resend_text.setEnabled(true);
                counter++;
            }
        }.start();
    }
}
