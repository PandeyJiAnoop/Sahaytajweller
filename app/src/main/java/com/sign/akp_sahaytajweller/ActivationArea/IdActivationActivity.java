package com.sign.akp_sahaytajweller.ActivationArea;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.sign.akp_sahaytajweller.Basic.Api_Urls;
import com.sign.akp_sahaytajweller.DashboardActivity;
import com.sign.akp_sahaytajweller.MyAccount.MyVolleySingleton;
import com.sign.akp_sahaytajweller.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class IdActivationActivity extends AppCompatActivity {
    ImageView imgBack;
    ProgressDialog progressDialog;
    String UserId = "";
    TextView tvBalance, tvCredit, tvDebit;
    Spinner spinnerData, spPayMode;
    EditText et_ActivationId;
    Button btnUpdate;

    String accActi = "", walletVal = "0";
    private final ArrayList<HashMap<String, String>> arraySpinnerList = new ArrayList<>();
    SpinnerAdaptor spinnerAdaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_activation2);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference", MODE_PRIVATE);
        UserId = sharedPreferences.getString("U_id", "");
        initViews();
    }

    private void initViews() {

        progressDialog = new ProgressDialog(this);


        imgBack = findViewById(R.id.imgBack);
        tvBalance = findViewById(R.id.tvBalance);
        tvCredit = findViewById(R.id.tvCredit);
        tvDebit = findViewById(R.id.tvDebit);
        spinnerData = findViewById(R.id.spinnerData);
        spPayMode = findViewById(R.id.spPayMode);
        et_ActivationId = findViewById(R.id.et_ActivationId);
        btnUpdate = findViewById(R.id.btnUpdate);

        imgBack.setOnClickListener(v -> finish());
        getPackageList();
        getWalletHistory();

        btnUpdate.setOnClickListener(v -> {
            if (accActi.equals("0")) {
                Toast.makeText(this, "Please select package", Toast.LENGTH_SHORT).show();
            } else if (spPayMode.getSelectedItem().toString().equals("Select Payment Mode")) {
                walletVal = "0";
                Toast.makeText(this, "Please select payment mode", Toast.LENGTH_SHORT).show();
            } else if (et_ActivationId.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter activation Id", Toast.LENGTH_SHORT).show();
            } else {
                walletVal = "Fund Wallet";
                submitId();
            } });
    }

    private void getPackageList() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Api_Urls.PackageList, null, response -> {
            Log.e("TAG", "responseData: " + response);
            try {
                if (response.getBoolean("Status")) {
                    arraySpinnerList.clear();
                    JSONArray jsonArray = response.getJSONArray("Response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        HashMap<String, String> hashMap = new HashMap();
                        hashMap.put("id", jsonObject1.getString("id"));
                        hashMap.put("name", jsonObject1.getString("PackageName"));
                        arraySpinnerList.add(hashMap);
                    }
                    spinnerAdaptor = new SpinnerAdaptor(IdActivationActivity.this, R.layout.custom_spinner_items, arraySpinnerList, getResources());
                    spinnerData.setAdapter(spinnerAdaptor);
                    spinnerData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            accActi = arraySpinnerList.get(position).get("id");
                            Log.e("TAG", "onItemSelected: " + arraySpinnerList.get(position).get("name") + "\n" + arraySpinnerList.get(position).get("id"));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } else {
                    Toast.makeText(this, "Record not found", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }, error -> {
            Log.e("TAG", "onErrorResponse: " + error.toString());
        });
        MyVolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void submitId() {
        progressDialog.show();
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserId", UserId);
            jsonObject.put("PackageId", accActi);
            jsonObject.put("PayMode", walletVal);
            jsonObject.put("ActivationId", et_ActivationId.getText().toString().trim());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Api_Urls.IDActivation, jsonObject, response -> {
            Gson gson = new Gson();
            progressDialog.dismiss();
            Log.e("TAG", "loginResponse: " + response);
            try {
                if (response.getBoolean("Status")) {
                    Toast.makeText(this, "Transferred Successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    finishAffinity();
                    /*JSONArray jsonArray = response.getJSONArray("Response");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    tvWalletBalance.setText(String.valueOf(jsonObject1.getDouble("CashWallet") + " INR"));
                    tvCreditBalance.setText(String.valueOf(jsonObject1.getDouble("FCredit") + " INR"));
                    tvDebitBalance.setText(String.valueOf(jsonObject1.getDouble("FDebit") + " INR"));*/
                } else {
                    Toast.makeText(this, "" + response.getString("Message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, error -> {
            progressDialog.dismiss();
            Log.e("TAG", "onErrorResponse: " + error.toString());
        });
        MyVolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void getWalletHistory() {
        progressDialog.show();
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserId", UserId);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Api_Urls.GetCashWallet, jsonObject, response -> {
            Gson gson = new Gson();
            progressDialog.dismiss();
            Log.e("TAG", "loginResponse: " + response);
            try {
                if (response.getBoolean("Status")) {
                    JSONArray jsonArray = response.getJSONArray("Response");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    tvBalance.setText(String.valueOf(jsonObject1.getDouble("CashWallet") + " INR"));
                    tvCredit.setText(String.valueOf(jsonObject1.getDouble("FCredit") + " INR"));
                    tvDebit.setText(String.valueOf(jsonObject1.getDouble("FDebit") + " INR"));
                } else {
                    Toast.makeText(this, "Login Failed!", Toast.LENGTH_SHORT).show();
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