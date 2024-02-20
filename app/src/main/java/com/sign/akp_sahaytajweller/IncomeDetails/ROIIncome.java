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

public class ROIIncome extends AppCompatActivity {
    ImageView menuImg;
    RecyclerView history_rec;
    String UserId;
    ArrayList<HashMap<String, String>> arrayList2 = new ArrayList<>();

    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_o_i_income);
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
        String otp = new GlobalAppApis().ContractIncomeAPI("", UserId);
        ApiService client = getApiClient_ByPost();
        Call<String> call = client.getContractincome(otp);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("Status").equalsIgnoreCase("true")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("ContractIncomeRes");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            title.setText("Trading Income("+jsonArray.length()+")");
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            Log.d("hh", "" + jsonObject1);
                            HashMap<String, String> hm = new HashMap<>();
                            hm.put("Date", jsonObject1.getString("Date"));
                            hm.put("Income", jsonObject1.getString("Income"));
                            arrayList2.add(hm);
                        }
                        LinearLayoutManager HorizontalLayout1 = new LinearLayoutManager(ROIIncome.this, LinearLayoutManager.VERTICAL,
                                false);
                        ROIAdapter customerListAdapter = new ROIAdapter(ROIIncome.this, arrayList2);
                        history_rec.setLayoutManager(HorizontalLayout1);
                        history_rec.setAdapter(customerListAdapter);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ROIIncome.this);
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

        }, ROIIncome.this, call, "", true);
    }


    public class ROIAdapter extends RecyclerView.Adapter<ROIAdapter.VH> {
        Context context;
        List<HashMap<String,String>> arrayList;
        public ROIAdapter(Context context, List<HashMap<String,String>> arrayList) {
            this.arrayList=arrayList;
            this.context=context;
        }

        @NonNull
        @Override
        public ROIAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.dynamic_roi, viewGroup, false);
            ROIAdapter.VH viewHolder = new ROIAdapter.VH(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ROIAdapter.VH vh, int i) {
            vh.srtv.setText(String.valueOf(" "+(i+1)));
            vh.tv.setText(arrayList.get(i).get("Date"));
            vh.tv1.setText(arrayList.get(i).get("Income"));


        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
        public class VH extends RecyclerView.ViewHolder {
            TextView srtv,tv,tv1;

            public VH(@NonNull View itemView) {
                super(itemView);
                srtv = itemView.findViewById(R.id.srtv);
                tv = itemView.findViewById(R.id.tv);
                tv1 = itemView.findViewById(R.id.tv1);

            }
            }
        }
    }
