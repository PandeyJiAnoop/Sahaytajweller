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
import com.sign.akp_sahaytajweller.ActivationArea.CreditTrans.DataResponseCreditTrans;
import com.sign.akp_sahaytajweller.ActivationArea.CreditTrans.ResponseItem;
import com.sign.akp_sahaytajweller.ActivationArea.DebitTrans.DataResponseDebitTrans;
import com.sign.akp_sahaytajweller.Basic.Api_Urls;
import com.sign.akp_sahaytajweller.MyAccount.MyVolleySingleton;
import com.sign.akp_sahaytajweller.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TransferHistory extends AppCompatActivity {
    ImageView imgBack;
    ProgressDialog progressDialog;
    String userId = "";
    AdaptorCreditTransHistory adaptorCreditTransHistory;
    AdaptorDebitTransHistory adaptorDebitTransHistory;
    RecyclerView recyclerCredit, recyclerDebit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_history);

        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        userId= sharedPreferences.getString("U_id", "");
        initViews();
    }

    private void initViews() {

        progressDialog = new ProgressDialog(this);

        imgBack = findViewById(R.id.imgBack);
        recyclerCredit = findViewById(R.id.recyclerCredit);
        recyclerDebit = findViewById(R.id.recyclerDebit);
        recyclerCredit.setLayoutManager(new LinearLayoutManager(this));
        recyclerDebit.setLayoutManager(new LinearLayoutManager(this));
        recyclerCredit.setHasFixedSize(true);
        recyclerDebit.setHasFixedSize(true);

        imgBack.setOnClickListener(v -> finish());

        getCreditList();
        getDebitList();
    }

    private void getDebitList() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserId", userId);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Api_Urls.DebitTransactionHistory, jsonObject, response -> {
            Gson gson = new Gson();
            progressDialog.dismiss();
            Log.e("TAG", "creditResponse: " + response);
            try {
                DataResponseDebitTrans responseDebitTrans = gson.fromJson(response.toString(), DataResponseDebitTrans.class);
                if (responseDebitTrans.isStatus()) {
                    adaptorDebitTransHistory = new AdaptorDebitTransHistory(getApplicationContext(), responseDebitTrans.getResponse());
                    recyclerDebit.setAdapter(adaptorDebitTransHistory);
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

    private void getCreditList() {
        progressDialog.show();
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserId", userId);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Api_Urls.CreditTransactionHistory, jsonObject, response -> {
            Gson gson = new Gson();
            progressDialog.dismiss();
            Log.e("TAG", "creditResponse: " + response);
            try {
                DataResponseCreditTrans dataResponseCreditTrans = gson.fromJson(response.toString(), DataResponseCreditTrans.class);
                if (dataResponseCreditTrans.isStatus()) {
                    adaptorCreditTransHistory = new AdaptorCreditTransHistory(getApplicationContext(), dataResponseCreditTrans.getResponse());
                    recyclerCredit.setAdapter(adaptorCreditTransHistory);
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

    public class AdaptorCreditTransHistory extends RecyclerView.Adapter<AdaptorCreditTransHistory.MyCredit> {

        Context context;
        List<ResponseItem> itemList;

        public AdaptorCreditTransHistory(Context context, List<ResponseItem> itemList) {
            this.context = context;
            this.itemList = itemList;
        }

        @NonNull
        @Override
        public MyCredit onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyCredit(LayoutInflater.from(context).inflate(R.layout.transaction_history_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyCredit holder, int position) {
            holder.tvDate.setText(itemList.get(position).getGenDate());
            holder.tvType.setText(itemList.get(position).getType());
            holder.tvAmount.setText(String.valueOf(itemList.get(position).getAmount()));
            holder.tvRemark.setText(itemList.get(position).getRemark());
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        public class MyCredit extends RecyclerView.ViewHolder {
            TextView tvDate, tvType, tvAmount, tvRemark;

            public MyCredit(@NonNull View itemView) {
                super(itemView);

                tvDate = itemView.findViewById(R.id.tvDate);
                tvType = itemView.findViewById(R.id.tvType);
                tvAmount = itemView.findViewById(R.id.tvAmount);
                tvRemark = itemView.findViewById(R.id.tvRemark);
            }
        }
    }


    public class AdaptorDebitTransHistory extends RecyclerView.Adapter<AdaptorDebitTransHistory.MyDebit> {

        Context context;
        List<ResponseItem> list;

        public AdaptorDebitTransHistory(Context context, List<ResponseItem> list) {
            this.context = context;
            this.list = list;
        }



        @NonNull
        @Override
        public MyDebit onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyDebit(LayoutInflater.from(context).inflate(R.layout.transaction_history_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyDebit holder, int position) {
            holder.tvDate.setText(list.get(position).getGenDate());
            holder.tvType.setText(list.get(position).getType());
            holder.tvAmount.setText(String.valueOf(list.get(position).getAmount()));
            holder.tvRemark.setText(list.get(position).getRemark());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyDebit extends RecyclerView.ViewHolder {
            TextView tvDate, tvType, tvAmount, tvRemark;

            public MyDebit(@NonNull View itemView) {
                super(itemView);

                tvDate = itemView.findViewById(R.id.tvDate);
                tvType = itemView.findViewById(R.id.tvType);
                tvAmount = itemView.findViewById(R.id.tvAmount);
                tvRemark = itemView.findViewById(R.id.tvRemark);
            }
        }
    }
}