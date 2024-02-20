package com.sign.akp_sahaytajweller.IncomeDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static com.sign.akp_sahaytajweller.Basic.API_Config.getApiClient_ByPost;

public class RewardIncome extends AppCompatActivity {
    ImageView menuImg;
    RecyclerView history_rec;
    String UserId;
    ArrayList<HashMap<String, String>> arrayList2 = new ArrayList<>();
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_income);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference", MODE_PRIVATE);
        UserId = sharedPreferences.getString("U_id", "");
        title = findViewById(R.id.title);
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
        String otp = new GlobalAppApis().RewardIncome("", UserId);
        ApiService client = getApiClient_ByPost();
        Call<String> call = client.getRewardIncome(otp);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("Status").equalsIgnoreCase("true")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("RewardIncomeResp");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            title.setText("Reward Income(" + jsonArray.length() + ")");
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Log.d("hh", "" + jsonObject1);
                            HashMap<String, String> hm = new HashMap<>();
                            hm.put("Charges", jsonObject1.getString("Charges"));
                            hm.put("CustomerId", jsonObject1.getString("CustomerId"));
                            hm.put("CustomerName", jsonObject1.getString("CustomerName"));
                            hm.put("Date", jsonObject1.getString("Date"));
                            hm.put("Package", jsonObject1.getString("Package"));
                            hm.put("Payable", jsonObject1.getString("Payable"));
                            hm.put("WalletAmount", jsonObject1.getString("WalletAmount"));
                            arrayList2.add(hm);
                        }
                        LinearLayoutManager HorizontalLayout1 = new LinearLayoutManager(RewardIncome.this, LinearLayoutManager.VERTICAL,
                                false);
                        RewardAdapter customerListAdapter = new RewardAdapter(RewardIncome.this, arrayList2);
                        history_rec.setLayoutManager(HorizontalLayout1);
                        history_rec.setAdapter(customerListAdapter);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RewardIncome.this);
                        builder.setTitle("Message!").setMessage(jsonObject.getString("Message")).setCancelable(false).setIcon(R.drawable.appicon)
                                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                    }
                                });
                        builder.create().show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, RewardIncome.this, call, "", true);
    }

    public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.VH> {
        Context context;
        List<HashMap<String, String>> arrayList;

        public RewardAdapter(Context context, List<HashMap<String, String>> arrayList) {
            this.arrayList = arrayList;
            this.context = context;
        }

        @NonNull
        @Override
        public RewardAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.dynamic_rewardincome, viewGroup, false);
            RewardAdapter.VH viewHolder = new RewardAdapter.VH(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RewardAdapter.VH vh, int i) {
            vh.tv.setText(arrayList.get(i).get("CustomerName")+"("+arrayList.get(i).get("CustomerId")+")");
            vh.tv1.setText(arrayList.get(i).get("Charges"));
            vh.tv2.setText(arrayList.get(i).get("Date"));
            vh.tv3.setText(arrayList.get(i).get("Package"));
            vh.tv4.setText(arrayList.get(i).get("Payable"));
            vh.tv5.setText(arrayList.get(i).get("WalletAmount"));

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class VH extends RecyclerView.ViewHolder {
            TextView tv, tv1, tv2, tv3, tv4, tv5;

            public VH(@NonNull View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.tv);
                tv1 = itemView.findViewById(R.id.tv1);
                tv2 = itemView.findViewById(R.id.tv2);
                tv3 = itemView.findViewById(R.id.tv3);
                tv4 = itemView.findViewById(R.id.tv4);
                tv5 = itemView.findViewById(R.id.tv5);
            }
        }
    }

}