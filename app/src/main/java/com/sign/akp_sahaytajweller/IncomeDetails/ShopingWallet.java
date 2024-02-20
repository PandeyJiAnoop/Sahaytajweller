package com.sign.akp_sahaytajweller.IncomeDetails;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sign.akp_sahaytajweller.Basic.ApiService;
import com.sign.akp_sahaytajweller.Basic.ConnectToRetrofit;
import com.sign.akp_sahaytajweller.Basic.GlobalAppApis;
import com.sign.akp_sahaytajweller.Basic.RetrofitCallBackListenar;
import com.sign.akp_sahaytajweller.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


import retrofit2.Call;

import static com.sign.akp_sahaytajweller.Basic.API_Config.getApiClient_ByPost;


public class ShopingWallet extends AppCompatActivity {
    ImageView menuImg;
    RecyclerView history_rec;
    String UserId;
    ArrayList<HashMap<String, String>> arrayList2 = new ArrayList<>();

    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoping_wallet);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference", MODE_PRIVATE);
        UserId = sharedPreferences.getString("U_id", "");
        title=findViewById(R.id.title);
        menuImg = findViewById(R.id.menuImg);
        history_rec = findViewById(R.id.history_rec);
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        History();
    }


    public void History() {
        String otp = new GlobalAppApis().GetClubLeaderIncomeReport("", UserId);
        ApiService client = getApiClient_ByPost();
        Call<String> call = client.getClubLeaderIncomeReport(otp);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("Status").equalsIgnoreCase("true")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("ClubLeaderIncomeRes");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            title.setText("Treading Club Leader Income("+jsonArray.length()+")");
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Log.d("hh", "" + jsonObject1);
                            HashMap<String, String> hm = new HashMap<>();
                            hm.put("Date", jsonObject1.getString("Date"));
                            hm.put("Income", jsonObject1.getString("Income"));
                            hm.put("Package", jsonObject1.getString("Package"));
                            arrayList2.add(hm);
                        }
                        LinearLayoutManager HorizontalLayout1 = new LinearLayoutManager(ShopingWallet.this, LinearLayoutManager.VERTICAL,
                                false);
                        ContractIncomeAdapter customerListAdapter = new ContractIncomeAdapter(ShopingWallet.this, arrayList2);
                        history_rec.setLayoutManager(HorizontalLayout1);
                        history_rec.setAdapter(customerListAdapter);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ShopingWallet.this);
                        builder.setTitle("Message!").setMessage(jsonObject.getString("Message")).setCancelable(false).setIcon(R.drawable.appicon)
                                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                       finish();
                                        }
                                });
                        builder.create().show();
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, ShopingWallet.this, call, "", true);
    }


}