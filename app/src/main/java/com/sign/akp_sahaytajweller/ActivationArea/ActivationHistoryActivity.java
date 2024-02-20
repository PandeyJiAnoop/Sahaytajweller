package com.sign.akp_sahaytajweller.ActivationArea;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sign.akp_sahaytajweller.Basic.Api_Urls;
import com.sign.akp_sahaytajweller.MyAccount.MyVolleySingleton;
import com.sign.akp_sahaytajweller.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ActivationHistoryActivity extends AppCompatActivity {
    ImageView imgBack;
    ProgressDialog progressDialog;
    String UserId = "";

    RecyclerView recyclerActivationHis;
    AdaptorActivationHistory adaptorActivationHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation_history);
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference", MODE_PRIVATE);
        UserId = sharedPreferences.getString("U_id", "");
        initViews();
    }

    private void initViews() {

        progressDialog = new ProgressDialog(this);

        imgBack = findViewById(R.id.imgBack);
        recyclerActivationHis = findViewById(R.id.recyclerActivationHis);
        recyclerActivationHis.setLayoutManager(new LinearLayoutManager(this));
        recyclerActivationHis.setHasFixedSize(true);

        imgBack.setOnClickListener(v -> finish());

        getActivationHis();
    }

    private void getActivationHis() {
        progressDialog.show();
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserId", UserId);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Api_Urls.ActivationHistory, jsonObject, response -> {
            Gson gson = new Gson();
            progressDialog.dismiss();
            Log.e("TAG", "creditResponse: " + response);
            try {
                DataResponseActivationHis activationHis = gson.fromJson(response.toString(), DataResponseActivationHis.class);
                if (activationHis.isStatus()) {
                    adaptorActivationHistory = new AdaptorActivationHistory(getApplicationContext(), activationHis.getResponse());
                    recyclerActivationHis.setAdapter(adaptorActivationHistory);
                } else {
                    Toast.makeText(this, "Record not found", Toast.LENGTH_SHORT).show();
                }
            } catch (JsonSyntaxException e) {
                throw new RuntimeException(e);
            }

        }, error -> {
            progressDialog.dismiss();
            Log.e("TAG", "onErrorResponse: " + error.toString());
        });
        MyVolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }


    public class AdaptorActivationHistory extends RecyclerView.Adapter<AdaptorActivationHistory.MyActiHolder>{
        Context context;
        List<ResponseItemHistory> list;

        public AdaptorActivationHistory(Context context, List<ResponseItemHistory> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public AdaptorActivationHistory.MyActiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdaptorActivationHistory.MyActiHolder(LayoutInflater.from(context).inflate(R.layout.activation_hitory_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull AdaptorActivationHistory.MyActiHolder holder, int position) {
            holder.tvID.setText(String.valueOf(list.get(position).getMemberID()));
            holder.tvPackage.setText(String.valueOf(list.get(position).getTopUpName()));
            holder.tvAmount.setText(String.valueOf(list.get(position).getAmount()));
            holder.tvTransactionDate.setText(String.valueOf(list.get(position).getTxnDate()));
            holder.tvServicePoint.setText(String.valueOf(list.get(position).getAsarincome()));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyActiHolder extends RecyclerView.ViewHolder {
            TextView tvID, tvPackage, tvAmount, tvTransactionDate, tvServicePoint;

            public MyActiHolder(@NonNull View itemView) {
                super(itemView);
                tvID =itemView.findViewById(R.id.tvID);
                tvPackage =itemView.findViewById(R.id.tvPackage);
                tvAmount =itemView.findViewById(R.id.tvAmount);
                tvTransactionDate =itemView.findViewById(R.id.tvTransactionDate);
                tvServicePoint =itemView.findViewById(R.id.tvServicePoint);
            } }}
}