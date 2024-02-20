package com.sign.akp_sahaytajweller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.sign.akp_sahaytajweller.Basic.ApiService;
import com.sign.akp_sahaytajweller.Basic.Api_Urls;
import com.sign.akp_sahaytajweller.Basic.ConnectToRetrofit;
import com.sign.akp_sahaytajweller.Basic.GlobalAppApis;
import com.sign.akp_sahaytajweller.Basic.RetrofitCallBackListenar;
import com.sign.akp_sahaytajweller.MyAccount.MyVolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;


import retrofit2.Call;

import static com.sign.akp_sahaytajweller.Basic.API_Config.getApiClient_ByPost;

public class Tickets extends AppCompatActivity {

    ImageView imgBack;
    ProgressDialog progressDialog;
    String userId = "";
    TextInputEditText etSubject, et_Message;
    AppCompatButton btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tickets);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        userId= sharedPreferences.getString("U_id", "");
        initViews();
    }

    private void initViews() {

        progressDialog = new ProgressDialog(this);

        imgBack = findViewById(R.id.imgBack);
        etSubject = findViewById(R.id.etSubject);
        et_Message = findViewById(R.id.et_Message);
        btnSubmit = findViewById(R.id.btn_submit);

        imgBack.setOnClickListener(v -> finish());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etSubject.getText().toString().trim().isEmpty()) {
                    Toast.makeText(Tickets.this, "Please enter subject", Toast.LENGTH_SHORT).show();
                } else if (et_Message.getText().toString().trim().isEmpty()) {
                    Toast.makeText(Tickets.this, "Please enter message", Toast.LENGTH_SHORT).show();
                } else {
                    submitTicket();
                }
            }
        });
    }

    private void submitTicket() {
        progressDialog.show();
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserId", userId);
            jsonObject.put("Subject", etSubject.getText().toString().trim());
            jsonObject.put("Message", et_Message.getText().toString().trim());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Api_Urls.GenerateTicket, jsonObject, response -> {
            Gson gson = new Gson();
            progressDialog.dismiss();
            Log.e("TAG", "loginResponse: " + response);
            try {
                if (response.getBoolean("Status")) {
                    Toast.makeText(this, "Ticket submitted Successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    finishAffinity();
                    /*JSONArray jsonArray = response.getJSONArray("Response");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    tvWalletBalance.setText(String.valueOf(jsonObject1.getDouble("CashWallet") + " INR"));
                    tvCreditBalance.setText(String.valueOf(jsonObject1.getDouble("FCredit") + " INR"));
                    tvDebitBalance.setText(String.valueOf(jsonObject1.getDouble("FDebit") + " INR"));*/
                } else {
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
                Log.e("TAG", "doLogin: " + response);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, error -> {
            progressDialog.dismiss();
            Log.e("TAG", "onErrorResponse: " + error.toString());
        });
        MyVolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}