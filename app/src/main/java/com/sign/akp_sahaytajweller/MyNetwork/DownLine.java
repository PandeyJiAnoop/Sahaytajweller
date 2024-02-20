package com.sign.akp_sahaytajweller.MyNetwork;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sign.akp_sahaytajweller.Basic.Api_Urls;
import com.sign.akp_sahaytajweller.MyAccount.MyVolleySingleton;
import com.sign.akp_sahaytajweller.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class DownLine extends AppCompatActivity {
    ImageView imgBack;
    ProgressDialog progressDialog;
    String userId = "", levelVale = "";
    RecyclerView recyclerTotalActiveTeam;
    private final ArrayList<HashMap<String, String>> activeTeamList = new ArrayList<>();
    AdaptorActiveTeam adaptorActiveTeam;
    private Spinner spinnerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_line);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        userId= sharedPreferences.getString("U_id", "");
        initViews();
    }

    private void initViews() {
        progressDialog = new ProgressDialog(this);

        imgBack = findViewById(R.id.imgBack);

        recyclerTotalActiveTeam = findViewById(R.id.recyclerTotalActiveTeam);
        recyclerTotalActiveTeam.setLayoutManager(new LinearLayoutManager(this));
        recyclerTotalActiveTeam.setHasFixedSize(true);
        spinnerData = findViewById(R.id.spinnerData);
        imgBack.setOnClickListener(v -> finish());
        spinnerData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinnerData.getSelectedItem().toString().equals("Select Level")) {
                    levelVale = "0";
                     getActiveTeamList(levelVale);
                } else if (spinnerData.getSelectedItem().toString().equals("Level 1")) {
                    levelVale = "1";
                     getActiveTeamList(levelVale);
                } else if (spinnerData.getSelectedItem().toString().equals("Level 2")) {
                    levelVale = "2";
                     getActiveTeamList(levelVale);
                } else if (spinnerData.getSelectedItem().toString().equals("Level 3")) {
                    levelVale = "3";
                     getActiveTeamList(levelVale);
                } else if (spinnerData.getSelectedItem().toString().equals("Level 4")) {
                    levelVale = "4";
                     getActiveTeamList(levelVale);
                } else if (spinnerData.getSelectedItem().toString().equals("Level 5")) {
                    levelVale = "5";
                     getActiveTeamList(levelVale);
                } else if (spinnerData.getSelectedItem().toString().equals("Level 6")) {
                    levelVale = "6";
                     getActiveTeamList(levelVale);
                } else if (spinnerData.getSelectedItem().toString().equals("Level 7")) {
                    levelVale = "7";
                     getActiveTeamList(levelVale);
                } else if (spinnerData.getSelectedItem().toString().equals("Level 8")) {
                    levelVale = "8";
                     getActiveTeamList(levelVale);
                } else if (spinnerData.getSelectedItem().toString().equals("Level 9")) {
                    levelVale = "9";
                     getActiveTeamList(levelVale);
                } else if (spinnerData.getSelectedItem().toString().equals("Level 10")) {
                    levelVale = "10";
                     getActiveTeamList(levelVale);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void getActiveTeamList(String levelVale) {
        progressDialog.show();
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserId", userId);
            jsonObject.put("Level", levelVale);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Api_Urls.DownlineReport, jsonObject, response -> {
            progressDialog.dismiss();
            Log.d("downline_res", String.valueOf(response));

            try {
                if(response.getBoolean("Status")){
                    activeTeamList.clear();
                    JSONArray jsonArray=response.getJSONArray("Response");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
//                        if(jsonObject1.getString("Status").equals("Active")){
                            HashMap<String, String> hashMap = new HashMap();
                            hashMap.put("CustomerId", jsonObject1.getString("CustomerId"));
                            hashMap.put("CustomerName", jsonObject1.getString("CustomerName"));
                            hashMap.put("Levels", String.valueOf(jsonObject1.getInt("Levels")));
                            hashMap.put("RegDate", jsonObject1.getString("RegDate"));
                            hashMap.put("ActiveDate", jsonObject1.getString("ActiveDate"));
                            hashMap.put("Package", jsonObject1.getString("Package"));
                            hashMap.put("Status", jsonObject1.getString("Status"));
                            hashMap.put("IntroId", jsonObject1.getString("IntroId"));
                            activeTeamList.add(hashMap);
//                        }
                    }
                    adaptorActiveTeam = new AdaptorActiveTeam(getApplicationContext(), activeTeamList);
                    recyclerTotalActiveTeam.setAdapter(adaptorActiveTeam);

                } else {
                    activeTeamList.clear();
                    Toast.makeText(this, "Record not found", Toast.LENGTH_SHORT).show();
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


    public class AdaptorActiveTeam extends RecyclerView.Adapter<AdaptorActiveTeam.MyActiveHolder>{

        Context context;
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        public AdaptorActiveTeam(Context context, ArrayList<HashMap<String, String>> data) {
            this.context = context;
            this.data = data;
        }

        @NonNull
        @Override
        public MyActiveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyActiveHolder(LayoutInflater.from(context).inflate(R.layout.dynamic_downline, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyActiveHolder holder, int position) {
            holder.tvMemberID.setText("Member ID: " + data.get(position).get("CustomerId"));
            holder.tvMemName.setText("Member Name: " + data.get(position).get("CustomerName"));
            holder.tvLevel.setText(String.valueOf("Level: " + data.get(position).get("Levels")));
            holder.tvRegistrationDate.setText("Registration Date: " + data.get(position).get("RegDate"));
            holder.tvActivationDate.setText("Activation Date: " + data.get(position).get("ActiveDate"));
            holder.tvAmount.setText("Amount: " + data.get(position).get("Package"));
            holder.tvStatus.setText("Status: " + data.get(position).get("Status"));
            holder.tvSponsorId.setText("Sponsor Id: " + data.get(position).get("IntroId"));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class MyActiveHolder extends RecyclerView.ViewHolder {
            TextView tvMemberID, tvMemName, tvLevel, tvRegistrationDate, tvActivationDate, tvAmount, tvStatus, tvSponsorId;
            public MyActiveHolder(@NonNull View itemView) {
                super(itemView);
                tvMemberID = itemView.findViewById(R.id.tvMemberID);
                tvMemName = itemView.findViewById(R.id.tvMemName);
                tvLevel = itemView.findViewById(R.id.tvLevel);
                tvRegistrationDate = itemView.findViewById(R.id.tvRegistrationDate);
                tvActivationDate = itemView.findViewById(R.id.tvActivationDate);
                tvAmount = itemView.findViewById(R.id.tvAmount);
                tvStatus = itemView.findViewById(R.id.tvStatus);
                tvSponsorId = itemView.findViewById(R.id.tvSponsorId);
            }
        }
    }
}
