package com.sign.akp_sahaytajweller.Basic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sign.akp_sahaytajweller.DashboardActivity;
import com.sign.akp_sahaytajweller.R;


public class SplashScreen extends AppCompatActivity {
    //Handler
    private final Handler handler = new Handler();
    //SPLASHTIMEOUT
    private static int SPLASHTIMEOUT = 3000; // Splash screen timer
    String UserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
//        Toast.makeText(getApplicationContext(),UserId,Toast.LENGTH_LONG).show();

        ImageView ivsplash=findViewById(R.id.ivsplash);
        RelativeLayout rl=findViewById(R.id.rl);

        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
        ivsplash.startAnimation(animation1);
        navigte();



    }

    public void navigte() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                if (UserId.equalsIgnoreCase("")){
                    startActivity(new Intent(SplashScreen.this, LoginScreen.class));
                    finish();
                }
                else {
                    startActivity(new Intent(SplashScreen.this, DashboardActivity.class));
                    finish();
                }
            }
        }, SPLASHTIMEOUT);
    }
}