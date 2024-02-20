package com.sign.akp_sahaytajweller.FundArea;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.sign.akp_sahaytajweller.Basic.ApiService;
import com.sign.akp_sahaytajweller.Basic.ConnectToRetrofit;
import com.sign.akp_sahaytajweller.Basic.GlobalAppApis;
import com.sign.akp_sahaytajweller.Basic.RetrofitCallBackListenar;
import com.sign.akp_sahaytajweller.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;

import static com.sign.akp_sahaytajweller.Basic.API_Config.getApiClient_ByPost;

public class PrincipalWithdrawalRequest extends AppCompatActivity {
    ImageView menuImg;
    TextView wallet_amount_tv;
    TextInputEditText activation_id_et;
    String UserId,UserPass,walletBalance;
    AppCompatButton raise_add_tv;
    SearchableSpinner country_et;
    String stateid,statecost; ArrayList<String> StateName = new ArrayList<>();
    ArrayList<String> StateId = new ArrayList<>();
    ArrayList<String> StateCost = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_withdrawal_request);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        UserPass= sharedPreferences.getString("U_pass", "");
        findId();
        OnClick();
        PackageList();

        DashboardAPI(UserId);
//        DashboardAPI(UserId);

    }

    private void OnClick() {
        raise_add_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activation_id_et.getText().toString().equalsIgnoreCase("")){
                    activation_id_et.setError("Fields can't be blank!");
                    activation_id_et.requestFocus();
                }
                else {
                    WithdrawalReQuest(walletBalance,stateid,activation_id_et.getText().toString(),UserId);
                } }});
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findId() {
        country_et=findViewById(R.id.country_et);
        menuImg=findViewById(R.id.menuImg);
        wallet_amount_tv=findViewById(R.id.wallet_amount_tv);
        activation_id_et=findViewById(R.id.activation_id_et);
        raise_add_tv=findViewById(R.id.raise_add_tv);
        country_et.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                if (position > 0) {
                    for (int j = 0; j <= StateId.size(); j++) {
                        // position = i;
                        if (country_et.getSelectedItem().toString().equalsIgnoreCase(StateName.get(j))) {
                            if(country_et.getSelectedItem().toString().equalsIgnoreCase("company (Joining ~5.00)")){
                                activation_id_et.setText("5.00");
                            }
                           else if(country_et.getSelectedItem().toString().equalsIgnoreCase("company (Joining ~10.00)")){
                                activation_id_et.setText("10.00");
                            }
                            stateid = StateId.get(j - 1);


                            break;
                        } } }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }});
    }

    public void WithdrawalReQuest(String Balance,String PacakgeId,String RequestWalletAmt,String UserId) {
        String otp = new GlobalAppApis().PrincipalWithdrawlRequestAPI(Balance,PacakgeId,RequestWalletAmt,UserId);
        ApiService client = getApiClient_ByPost();
        Call<String> call = client.API_PrincipalWithdrawlRequest(otp);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("Status").equalsIgnoreCase("true")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PrincipalWithdrawalRequest.this);
                        builder.setTitle("Message!").setMessage(jsonObject.getString("Message")).setCancelable(false).setIcon(R.drawable.appicon)
                                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) { overridePendingTransition(0, 0);
                                        startActivity(getIntent());
                                        overridePendingTransition(0, 0);  dialog.cancel();
                                    }});
                        builder.create().show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PrincipalWithdrawalRequest.this);
                        builder.setTitle("Message!").setMessage(jsonObject.getString("Message")).setCancelable(false).setIcon(R.drawable.appicon)
                                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }});
                        builder.create().show();
                    } }
                catch (JSONException e) {
                    e.printStackTrace();
                } }
        }, PrincipalWithdrawalRequest.this, call, "", true);
    }

    public void  DashboardAPI(String userid){
        String otp1 = new GlobalAppApis().Dashboard(userid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getBindDashboard(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray Jarray = object.getJSONArray("Response");
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject jsonobject = Jarray.getJSONObject(i);
                        walletBalance  = jsonobject.getString("BalanceAmount");
                        wallet_amount_tv.setText("$ "+walletBalance);
//                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"Something Went Wrong!",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } }
        }, PrincipalWithdrawalRequest.this, call1, "", true);
    }



    public void PackageList() {
        String otp1 = new GlobalAppApis().GetPrinciplePackageList(UserId);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.API_GetPrinciplePackageList(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("res","cxc"+result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getBoolean("Status")==false){
                        String msg       = object.getString("Message");
                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        JSONArray jsonArray = object.getJSONArray("LoginRes");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            StateId.add(jsonObject1.getString("PackageId"));
                            String statename = jsonObject1.getString("PackageName");
                            StateCost.add(jsonObject1.getString("Package_Cost"));
                            StateName.add(statename);
                        }
                        country_et.setAdapter(new ArrayAdapter<String>(PrincipalWithdrawalRequest.this, android.R.layout.simple_spinner_dropdown_item, StateName));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, PrincipalWithdrawalRequest.this, call1, "", true);
    }
}