package com.sign.akp_sahaytajweller.Basic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sign.akp_sahaytajweller.R;


public class MainActivity extends AppCompatActivity {
    TextView signup_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signup_tv=findViewById(R.id.signup_tv);
        signup_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Coming Soon!",Toast.LENGTH_LONG).show();
            }
        });
    }
}