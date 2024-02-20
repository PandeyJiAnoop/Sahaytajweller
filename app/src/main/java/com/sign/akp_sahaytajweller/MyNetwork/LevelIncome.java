package com.sign.akp_sahaytajweller.MyNetwork;

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
import com.sign.akp_sahaytajweller.IncomeDetails.LevelIncomeAdapter;
import com.sign.akp_sahaytajweller.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


import retrofit2.Call;

import static com.sign.akp_sahaytajweller.Basic.API_Config.getApiClient_ByPost;

public class LevelIncome extends AppCompatActivity {
    ImageView menuImg;
    RecyclerView history_rec;
    String UserId;
    ArrayList<HashMap<String, String>> arrayList2 = new ArrayList<>();
TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_income);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference", MODE_PRIVATE);
        UserId = sharedPreferences.getString("U_id", "");
        menuImg = findViewById(R.id.menuImg);
        title= findViewById(R.id.title);
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
        String otp = new GlobalAppApis().LevelIncomeAPI("", UserId);
        ApiService client = getApiClient_ByPost();
        Call<String> call = client.getLevelincome(otp);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("aafafaf",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("Status").equalsIgnoreCase("true")){
                        JSONArray jsonArray = jsonObject.getJSONArray("ContractIncomeRes");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            title.setText("Level Income  Report("+jsonArray.length()+")");
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Log.d("hh",""+jsonObject1);
                            HashMap<String, String> hm = new HashMap<>();
                            hm.put("Date", jsonObject1.getString("Date"));
                            hm.put("Income", jsonObject1.getString("Income"));
                            hm.put("Levels", jsonObject1.getString("Levels"));
                            hm.put("MemberId", jsonObject1.getString("MemberId"));
                            hm.put("MemberName", jsonObject1.getString("MemberName"));
                            hm.put("Package", jsonObject1.getString("Package"));
                            arrayList2.add(hm);
                        }
                        LinearLayoutManager HorizontalLayout1 = new LinearLayoutManager(LevelIncome.this, LinearLayoutManager.VERTICAL,
                                false);
                        LevelIncomeAdapter customerListAdapter = new LevelIncomeAdapter(LevelIncome.this, arrayList2);
                        history_rec.setLayoutManager(HorizontalLayout1);
                        history_rec.setAdapter(customerListAdapter);
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LevelIncome.this);
                        builder.setTitle("Message!").setMessage(jsonObject.getString("Message")).setCancelable(false).setIcon(R.drawable.appicon)
                                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                      finish();
                                    }});
                        builder.create().show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } }
        }, LevelIncome.this, call, "", true);
    }


}