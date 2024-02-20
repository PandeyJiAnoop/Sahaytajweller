package com.sign.akp_sahaytajweller.MyNetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sign.akp_sahaytajweller.R;


public class DownlineLeftRight extends AppCompatActivity {
    ImageView menuImg;
    TextView left,right;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downline_left_right);
        menuImg=findViewById(R.id.menuImg);
        left=findViewById(R.id.left);
        right=findViewById(R.id.right);
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                left.setBackgroundResource(R.color.golden);
                left.setTextColor(Color.WHITE);
                right.setBackgroundResource(R.drawable.rounded_edittext_black);
                right.setTextColor(Color.WHITE); }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                right.setBackgroundResource(R.color.golden);
                right.setTextColor(Color.WHITE);
                left.setBackgroundResource(R.drawable.rounded_edittext_black);
                left.setTextColor(Color.WHITE);
            }
        });
    }
}