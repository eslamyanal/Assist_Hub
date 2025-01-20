package com.example.assisthub.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.assisthub.R;


public class SplashScreen extends AppCompatActivity {
   private LinearLayout SpalshScreenLayout;
   private  Animation fadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        // mirror object
         SpalshScreenLayout  = findViewById(R.id.SpalshScreenLayout);

        // Load the fade-in animation
         fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        // Apply the animation to the view
        SpalshScreenLayout.startAnimation(fadeIn);

        // Delay for 10 seconds (10000 milliseconds)
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish(); // Finish the splash screen activity
        }, 10000);
    }


}
