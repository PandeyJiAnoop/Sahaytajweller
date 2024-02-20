package com.sign.akp_sahaytajweller.MyAccount;


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
import com.sign.akp_sahaytajweller.DashboardActivity;
import com.sign.akp_sahaytajweller.R;

import org.json.JSONException;
import org.json.JSONObject;


import retrofit2.Call;

import static com.sign.akp_sahaytajweller.Basic.API_Config.getApiClient_ByPost;


public class ChangePassword extends AppCompatActivity {
    TextInputEditText edt_new_pass;
    private TextInputEditText edt_old_pass,edt_conf_pass,t_edt_old_pin;
    private AppCompatButton btn_sendotp,t_btn_sendotp;
    String UserId;
    private TextInputEditText t_edt_new_pass,t_edt_old_pass,t_edt_conf_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");

        t_edt_new_pass=findViewById(R.id.t_edt_new_pass);
        t_edt_old_pass=findViewById(R.id.t_edt_old_pass);
        t_edt_old_pin=findViewById(R.id.t_edt_old_pin);
        t_edt_conf_pass=findViewById(R.id.t_edt_conf_pass);

        edt_new_pass=findViewById(R.id.edt_new_pass);
        edt_old_pass=findViewById(R.id.edt_old_pass);
        edt_conf_pass=findViewById(R.id.edt_conf_pass);
        btn_sendotp= findViewById(R.id.btn_sendotp);
        t_btn_sendotp=findViewById(R.id.t_btn_sendotp);
        findViewById(R.id.back_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_old_pass.getText().toString().equalsIgnoreCase("")){
                    edt_old_pass.setError("Fields can't be blank!");
                    edt_old_pass.requestFocus();
                }
                else if (edt_new_pass.getText().toString().equalsIgnoreCase("")){
                    edt_new_pass.setError("Fields can't be blank!");
                    edt_new_pass.requestFocus();
                }
                else if (edt_conf_pass.getText().toString().equalsIgnoreCase("")){
                    edt_conf_pass.setError("Fields can't be blank!");
                    edt_conf_pass.requestFocus();
                }
                else if(!edt_new_pass.getText().toString().equals(edt_conf_pass.getText().toString())){
                    //Toast is the pop up message
                    Toast.makeText(getApplicationContext(), "New and Confirm Password Not matched!", Toast.LENGTH_LONG).show();
                }
                else {
                    changePassword(edt_new_pass.getText().toString(),edt_old_pass.getText().toString(),UserId);
                    // Toast.makeText(getApplicationContext(),"Password Changed Successfully!",Toast.LENGTH_LONG).show();
                }
            }
        });

        t_btn_sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (t_edt_old_pin.getText().toString().equalsIgnoreCase("")){
                    t_edt_old_pin.setError("Fields can't be blank!");
                    t_edt_old_pin.requestFocus();
                }
                else if (t_edt_new_pass.getText().toString().equalsIgnoreCase("")){
                    t_edt_new_pass.setError("Fields can't be blank!");
                    t_edt_new_pass.requestFocus();
                }
                else if (t_edt_conf_pass.getText().toString().equalsIgnoreCase("")){
                    t_edt_conf_pass.setError("Fields can't be blank!");
                    t_edt_conf_pass.requestFocus();
                }
//                else if(t_edt_new_pass.getText().toString().length()>4){
//                    //Toast is the pop up message
//                    Toast.makeText(getApplicationContext(), "T-Pin Must Be 4 Digit!", Toast.LENGTH_LONG).show();
//                }
                else if(!t_edt_new_pass.getText().toString().equals(t_edt_conf_pass.getText().toString())){
                    //Toast is the pop up message
                    Toast.makeText(getApplicationContext(), "T-Pin Not matched!", Toast.LENGTH_LONG).show();
                }
                else {
                    T_changePassword(t_edt_new_pass.getText().toString(),t_edt_old_pin.getText().toString(),UserId);
                    // Toast.makeText(getApplicationContext(),"Password Changed Successfully!",Toast.LENGTH_LONG).show();
                } }});
    }



    public void changePassword(String newpass, String oldpass, String uid) {
        String otp1 = new GlobalAppApis().ChangePassword(newpass,oldpass,uid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getChangePassword(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("API_ChangePassword","cxc"+result);
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getString("Status");
                    if (status.equalsIgnoreCase("true")) {
//                        JSONArray Response = object.getJSONArray("Response");
//                        for (int i = 0; i < Response.length(); i++) {
//                            JSONObject jsonObject = Response.getJSONObject(i);
                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder11 = new AlertDialog.Builder(ChangePassword.this);
                        builder11.setTitle("Response:-")
                                .setMessage(object.getString("Message"))
                                .setCancelable(false)
                                .setIcon(R.drawable.logo)
                                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                        overridePendingTransition(0, 0);
                                        startActivity(getIntent());
                                        overridePendingTransition(0, 0);
                                        dialog.cancel();
                                    } });
                        builder11.create().show();
                    }
//                    }
                    else {
                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } }
        }, ChangePassword.this, call1, "", true);
    }


    public void T_changePassword(String newpass, String oldpass, String uid) {
        String otp1 = new GlobalAppApis().ChangeTransactionPassword(newpass,oldpass,uid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getChangeTransactionPassword(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("API_ChangeTpin","cxc"+result);
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getString("Status");
                    if (status.equalsIgnoreCase("true")) {
//                        JSONArray Response = object.getJSONArray("Response");
//                        for (int i = 0; i < Response.length(); i++) {
//                            JSONObject jsonObject = Response.getJSONObject(i);
//                            Toast.makeText(getApplicationContext(), jsonObject.getString("Msg"), Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder11 = new AlertDialog.Builder(ChangePassword.this);
                        builder11.setTitle("Success")
                                .setMessage(object.getString("Message"))
                                .setCancelable(false)
                                .setIcon(R.drawable.logo)
                                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                        overridePendingTransition(0, 0);
                                        startActivity(getIntent());
                                        overridePendingTransition(0, 0);
                                        dialog.cancel();
                                    } });
                        builder11.create().show();
                    }
//                    }
                    else {
                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, ChangePassword.this, call1, "", true);
    }
}