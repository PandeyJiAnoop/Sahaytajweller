package com.sign.akp_sahaytajweller.SlidingMenu;

import static com.sign.akp_sahaytajweller.Basic.API_Config.getApiClient_ByPost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.List;

import retrofit2.Call;

public class EMIDetails extends AppCompatActivity {
    ImageView menuImg;
    RecyclerView history_rec;
    String UserId;
    ArrayList<HashMap<String, String>> arrayList2 = new ArrayList<>();

    ImageView norecord_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emidetails);
        FindId();
        History();
    }

    private void FindId() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference", MODE_PRIVATE);
        UserId = sharedPreferences.getString("U_id", "");
        norecord_img=findViewById(R.id.norecord_img);
        menuImg = findViewById(R.id.menuImg);
        history_rec = findViewById(R.id.history_rec);
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void History() {
        String otp = new GlobalAppApis().EMIChartDetails(UserId);
        ApiService client = getApiClient_ByPost();
        Call<String> call = client.API_EMIChartDetails(otp);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("API_EMIChartDetails",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("Status").equals("false")){
                        norecord_img.setVisibility(View.VISIBLE);
                    }
                    else {
                        norecord_img.setVisibility(View.GONE);
                        JSONArray jsonArray = jsonObject.getJSONArray("Response");
                        Log.d("hh",""+jsonArray);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Log.d("hh",""+jsonObject1);
                            HashMap<String, String> hm = new HashMap<>();
                            hm.put("Member_ID", jsonObject1.getString("Member_ID"));
                            hm.put("CustomerName", jsonObject1.getString("CustomerName"));
                            hm.put("ProfarmaNo", jsonObject1.getString("ProfarmaNo"));
                            hm.put("AddedDate", jsonObject1.getString("AddedDate"));
                            hm.put("MaturityDate", jsonObject1.getString("MaturityDate"));
                            hm.put("ValidityDate", jsonObject1.getString("ValidityDate"));
                            hm.put("LoanPlan", jsonObject1.getString("LoanPlan"));
                            hm.put("EMIAmount", jsonObject1.getString("EMIAmount"));
                            arrayList2.add(hm);
                        }
                        LinearLayoutManager HorizontalLayout1 = new LinearLayoutManager(EMIDetails.this, LinearLayoutManager.VERTICAL, false);
                        DasAdapter customerListAdapter = new DasAdapter(EMIDetails.this, arrayList2);
                        history_rec.setLayoutManager(HorizontalLayout1);
                        history_rec.setAdapter(customerListAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }}
        }, EMIDetails.this, call, "", true);
    }

    public class DasAdapter extends RecyclerView.Adapter<DasAdapter.VH> {
        Context context;
        List<HashMap<String,String>> arrayList;

        public DasAdapter(Context context, List<HashMap<String,String>> arrayList) {
            this.arrayList=arrayList;
            this.context=context;
        }

        @NonNull
        @Override
        public DasAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.dynamic_emidetails, viewGroup, false);
            DasAdapter.VH viewHolder = new DasAdapter.VH(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull DasAdapter.VH vh, int i) {
            vh.tv.setText(String.valueOf(i+1));
            vh.tv1.setText(arrayList.get(i).get("ProfarmaNo"));
            vh.tv2.setText(arrayList.get(i).get("EMIAmount"));
            vh.tv3.setText(arrayList.get(i).get("Member_ID"));
            vh.tv4.setText(arrayList.get(i).get("CustomerName"));
            vh.tv5.setText(arrayList.get(i).get("AddedDate"));
            vh.tv6.setText(arrayList.get(i).get("MaturityDate"));
            vh.tv7.setText(arrayList.get(i).get("ValidityDate"));

            vh.view_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(),GetEMIDetailsReport.class);
                    intent.putExtra("ProfarmaNo",arrayList.get(i).get("ProfarmaNo"));
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class VH extends RecyclerView.ViewHolder {
            TextView tv,tv1,tv2,tv3,tv4,tv5,tv6,tv7;
            TextView view_btn;

            public VH(@NonNull View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.tv);
                tv1 = itemView.findViewById(R.id.tv1);
                tv2 = itemView.findViewById(R.id.tv2);
                tv3 = itemView.findViewById(R.id.tv3);
                tv4 = itemView.findViewById(R.id.tv4);
                tv5 = itemView.findViewById(R.id.tv5);
                tv6 = itemView.findViewById(R.id.tv6);
                tv7 = itemView.findViewById(R.id.tv7);
                view_btn = itemView.findViewById(R.id.view_btn);
            }
        } }
}