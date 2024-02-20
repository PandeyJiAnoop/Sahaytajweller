package com.sign.akp_sahaytajweller.SlidingMenu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.sign.akp_sahaytajweller.Basic.ApiService;
import com.sign.akp_sahaytajweller.Basic.ConnectToRetrofit;
import com.sign.akp_sahaytajweller.Basic.GlobalAppApis;
import com.sign.akp_sahaytajweller.Basic.RetrofitCallBackListenar;
import com.sign.akp_sahaytajweller.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import retrofit2.Call;

import static com.sign.akp_sahaytajweller.Basic.API_Config.getApiClient_ByPost;

public class Withdraw extends AppCompatActivity {

    ImageView menuImg;
    TextView wallet_amount_tv,credit_tv,debit_tv;
    String UserId,walletBalance,UserPass;
    AppCompatButton raise_add_tv;
    TextInputEditText amount_et;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        UserPass= sharedPreferences.getString("U_pass", "");
        findId();
        OnClick();
//        WalletAPI("",UserId);
        DashboardAPI(UserId);
    }

    private void OnClick() {
        raise_add_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount_et.getText().toString().equalsIgnoreCase("")){
                    amount_et.setError("Fields can't be blank!");
                    amount_et.requestFocus();
                }
                else {
                    WithdrawlRequestAPI(amount_et.getText().toString(),UserId);
                } }
        });
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findId() {
        amount_et=findViewById(R.id.amount_et);
        menuImg=findViewById(R.id.menuImg);
        wallet_amount_tv=findViewById(R.id.wallet_amount_tv);
        credit_tv=findViewById(R.id.credit_tv);
        debit_tv=findViewById(R.id.debit_tv);
        raise_add_tv=findViewById(R.id.raise_add_tv);
    }


   /* public void  WalletAPI(String action,String userid){
        String otp1 = new GlobalAppApis().Wallet(action,userid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getWallet(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray Jarray = object.getJSONArray("LoginRes");
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject jsonobject = Jarray.getJSONObject(i);
                        String credit       = jsonobject.getString("Credit");
                        String debit    = jsonobject.getString("Debit");
                        walletBalance      = jsonobject.getString("WalletBalance");
                        wallet_amount_tv.setText("$ "+walletBalance);
                        credit_tv.setText("$ "+credit);
                        debit_tv.setText("$ "+debit);
//                        activation_id_et.setText(UserId);
//                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"UserId and password not matched!",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, Withdraw.this, call1, "", true);
    }*/

    public void  DashboardAPI(String userid){
        String otp1 = new GlobalAppApis().Dashboard(userid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getBindDashboard(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("response",result);
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray Jarray = object.getJSONArray("Response");
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject jsonobject = Jarray.getJSONObject(i);
                        walletBalance  = jsonobject.getString("BalanceAmount");
                        wallet_amount_tv.setText("Rs "+walletBalance);
//                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                    } } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"Something Went Wrong!",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } }
        }, Withdraw.this, call1, "", true);
    }


    public void WithdrawlRequestAPI(String RequestWalletAmt,String UserId) {
        String otp = new GlobalAppApis().WithdrawlRequest("INR",RequestWalletAmt,UserId);
        ApiService client = getApiClient_ByPost();
        Call<String> call = client.getWithdrawlRequest(otp);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("Status").equalsIgnoreCase("true")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("Response");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        AlertDialog.Builder builder = new AlertDialog.Builder(Withdraw.this);
                        builder.setTitle("Message!").setMessage(jsonObject1.getString("msg")).setCancelable(false).setIcon(R.drawable.appicon)
                                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }});
                        builder.create().show();
                    } }else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Withdraw.this);
                        builder.setTitle("Message!").setMessage(jsonObject.getString("Message")).setCancelable(false).setIcon(R.drawable.appicon)
                                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) { finish();  }});
                        builder.create().show();
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, Withdraw.this, call, "", true);
    }


}