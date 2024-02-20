package com.sign.akp_sahaytajweller.Walet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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


public class WalletHistoy extends AppCompatActivity {
ImageView menuImg;
AppCompatButton pending_btn,approved_btn,rejected_btn;
RelativeLayout pending_ll,approve_ll,rejected_ll;

String UserId;
RecyclerView pending_rec,approved_rec,rejected_rec;

    ArrayList<HashMap<String, String>> arrayList2 = new ArrayList<>();
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> arrayList1 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_histoy);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        FindId();
        OnClick();

        HistoryRejected();
        HistoryApproved();
        HistoryPending();

    }

    private void OnClick() {
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pending_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pending_ll.setVisibility(View.VISIBLE);
                approve_ll.setVisibility(View.GONE);
                rejected_ll.setVisibility(View.GONE);
                pending_btn.setBackgroundResource(R.color.yellow);
                approved_btn.setBackgroundResource(R.color.skyblue);
                rejected_btn.setBackgroundResource(R.color.skyblue);
            }
        });
        approved_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pending_ll.setVisibility(View.GONE);
                approve_ll.setVisibility(View.VISIBLE);
                rejected_ll.setVisibility(View.GONE);
                pending_btn.setBackgroundResource(R.color.skyblue);
                approved_btn.setBackgroundResource(R.color.yellow);
                rejected_btn.setBackgroundResource(R.color.skyblue);
            }
        });
        rejected_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pending_ll.setVisibility(View.GONE);
                approve_ll.setVisibility(View.GONE);
                rejected_ll.setVisibility(View.VISIBLE);
                pending_btn.setBackgroundResource(R.color.skyblue);
                approved_btn.setBackgroundResource(R.color.skyblue);
                rejected_btn.setBackgroundResource(R.color.yellow);
            }
        });
    }

    private void FindId() {
        menuImg=findViewById(R.id.menuImg);
        pending_btn=findViewById(R.id.pending_btn);
        approved_btn=findViewById(R.id.approved_btn);
        rejected_btn=findViewById(R.id.rejected_btn);
        pending_ll=findViewById(R.id.pending_ll);
        approve_ll=findViewById(R.id.approve_ll);
        rejected_ll=findViewById(R.id.rejected_ll);
        pending_rec=findViewById(R.id.pending_rec);
        approved_rec=findViewById(R.id.approved_rec);
        rejected_rec=findViewById(R.id.rejected_rec);
    }


    public void HistoryPending(){
        String otp = new GlobalAppApis().WalletHistoryAPI("", UserId);
        ApiService client = getApiClient_ByPost();
        Call<String> call = client.getWalletHistory(otp);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray("PendingWithdrawalResp");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Log.v("sdadd",""+jsonObject1);
                            HashMap<String, String> hm = new HashMap<>();
                            hm.put("CurrentWallet", jsonObject1.getString("CurrentWallet"));
                            hm.put("Date", jsonObject1.getString("Date"));
                            hm.put("MemberId", jsonObject1.getString("MemberId"));
                            hm.put("MemberName", jsonObject1.getString("MemberName"));
                            hm.put("Remark", jsonObject1.getString("Remark"));
                            hm.put("ReqWallet", jsonObject1.getString("ReqWallet"));
                            arrayList.add(hm);
                        }
                        LinearLayoutManager HorizontalLayout1 = new LinearLayoutManager(
                                WalletHistoy.this,
                                LinearLayoutManager.VERTICAL,
                                false);
                        PendingAdapter customerListAdapter1 = new PendingAdapter(WalletHistoy.this, arrayList);
                        pending_rec.setLayoutManager(HorizontalLayout1);
                        pending_rec.setAdapter(customerListAdapter1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        },WalletHistoy.this, call, "", true);
    }

    public void HistoryApproved(){
        String otp = new GlobalAppApis().WalletHistoryAPI("", UserId);
        ApiService client = getApiClient_ByPost();
        Call<String> call = client.getWalletHistory(otp);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.v("ddadada",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray("ApprovedWithdrawalResp");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            HashMap<String, String> hm = new HashMap<>();
                            hm.put("CurrentWallet", jsonObject1.getString("CurrentWallet"));
                            hm.put("Date", jsonObject1.getString("Date"));
                            hm.put("MemberId", jsonObject1.getString("MemberId"));
                            hm.put("MemberName", jsonObject1.getString("MemberName"));
                            hm.put("Remark", jsonObject1.getString("Remark"));
                            hm.put("ReqWallet", jsonObject1.getString("ReqWallet"));
                            arrayList1.add(hm);
                        }
                        LinearLayoutManager HorizontalLayout1 = new LinearLayoutManager(
                                WalletHistoy.this,
                                LinearLayoutManager.VERTICAL,
                                false);
                        ApprovedAdapter customerListAdapter2 = new ApprovedAdapter(WalletHistoy.this, arrayList1);
                        approved_rec.setLayoutManager(HorizontalLayout1);
                        approved_rec.setAdapter(customerListAdapter2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        },WalletHistoy.this, call, "", true);
    }


    public void HistoryRejected(){
        String otp = new GlobalAppApis().WalletHistoryAPI("", UserId);
        ApiService client = getApiClient_ByPost();
        Call<String> call = client.getWalletHistory(otp);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.v("ddadada",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                        JSONArray jsonArray = jsonObject.getJSONArray("RejectedWithdrawalResp");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            HashMap<String, String> hm = new HashMap<>();
                            hm.put("CurrentWallet", jsonObject1.getString("CurrentWallet"));
                            hm.put("Date", jsonObject1.getString("Date"));
                            hm.put("MemberId", jsonObject1.getString("MemberId"));
                            hm.put("MemberName", jsonObject1.getString("MemberName"));
                            hm.put("Remark", jsonObject1.getString("Remark"));
                            hm.put("ReqWallet", jsonObject1.getString("ReqWallet"));
                            arrayList2.add(hm);
                        }
                        LinearLayoutManager HorizontalLayout1 = new LinearLayoutManager(
                                WalletHistoy.this,
                                LinearLayoutManager.VERTICAL,
                                false);
                        RejectedAdapter customerListAdapter = new RejectedAdapter(WalletHistoy.this, arrayList2);
                        rejected_rec.setLayoutManager(HorizontalLayout1);
                        rejected_rec.setAdapter(customerListAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        },WalletHistoy.this, call, "", true);
    }


}