package com.sign.akp_sahaytajweller.IncomeDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.sign.akp_sahaytajweller.Adapterclass.MyTeamAdapter;
import com.sign.akp_sahaytajweller.Basic.ApiService;
import com.sign.akp_sahaytajweller.Basic.ConnectToRetrofit;
import com.sign.akp_sahaytajweller.Basic.GlobalAppApis;
import com.sign.akp_sahaytajweller.Basic.RetrofitCallBackListenar;
import com.sign.akp_sahaytajweller.NetworkConnectionHelper;
import com.sign.akp_sahaytajweller.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


import retrofit2.Call;

import static com.sign.akp_sahaytajweller.Basic.API_Config.getApiClient_ByPost;


public class Referralncome extends AppCompatActivity {
    ImageView menuImg;
    String UserId;
    SwipeRefreshLayout srl_refresh;
    RecyclerView chat_recyclerView;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referralncome);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        srl_refresh = findViewById(R.id.srl_refresh);
        chat_recyclerView = findViewById(R.id.chat_recyclerView);
        menuImg=findViewById(R.id.menuImg);
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkConnectionHelper.isOnline(Referralncome.this)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                            srl_refresh.setRefreshing(false);
                        }
                    }, 2000);
                } else {
                    Toast.makeText(Referralncome.this, "Please check your internet connection! try again...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TopupDetails("",UserId);

    }



    public void  TopupDetails(String action,String userid){
        String otp1 = new GlobalAppApis().MyTeam(action,userid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getMyTeam(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray Jarray = object.getJSONArray("LoginRes");
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject jsonObject1 = Jarray.getJSONObject(i);
                        HashMap<String, String> hm = new HashMap<>();
                        hm.put("Activedate", jsonObject1.getString("Activedate"));
                        hm.put("Amount", jsonObject1.getString("Amount"));
                        hm.put("CustomerId", jsonObject1.getString("CustomerId"));
                        hm.put("EmailId", jsonObject1.getString("EmailId"));
                        hm.put("Levels", jsonObject1.getString("Levels"));
                        hm.put("MobileNo", jsonObject1.getString("MobileNo"));
                        hm.put("Name", jsonObject1.getString("Name"));
                        hm.put("RegDate", jsonObject1.getString("RegDate"));
                        hm.put("SponsorId", jsonObject1.getString("SponsorId"));
                        hm.put("Status", jsonObject1.getString("Status"));





                        arrayList.add(hm);
//                        JSONObject getDetails = jsonArrayr.getJSONObject(i);
//                        String experiance = getDetails.getString("AstroAccountId");
//                        String images = getDetails.getString("ChatId");
//                        String language = getDetails.getString("CustomerAccountId");
                    }
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(Referralncome.this, 1);
                    MyTeamAdapter walletHistoryAdapter = new MyTeamAdapter(Referralncome.this, arrayList);
                    chat_recyclerView.setLayoutManager(gridLayoutManager);
                    chat_recyclerView.setAdapter(walletHistoryAdapter);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"UserId and password not matched!",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, Referralncome.this, call1, "", true);
    }


}