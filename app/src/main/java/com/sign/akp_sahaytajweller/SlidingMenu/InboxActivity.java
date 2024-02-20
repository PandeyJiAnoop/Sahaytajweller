package com.sign.akp_sahaytajweller.SlidingMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
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
import com.sign.akp_sahaytajweller.ActivationArea.Inbox.DataResponseInbox;
import com.sign.akp_sahaytajweller.ActivationArea.Inbox.ResponseItem;
import com.sign.akp_sahaytajweller.Basic.ApiService;
import com.sign.akp_sahaytajweller.Basic.Api_Urls;
import com.sign.akp_sahaytajweller.Basic.ConnectToRetrofit;
import com.sign.akp_sahaytajweller.Basic.GlobalAppApis;
import com.sign.akp_sahaytajweller.Basic.RetrofitCallBackListenar;
import com.sign.akp_sahaytajweller.MyAccount.MyVolleySingleton;
import com.sign.akp_sahaytajweller.NetworkConnectionHelper;
import com.sign.akp_sahaytajweller.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import retrofit2.Call;

import static com.sign.akp_sahaytajweller.Basic.API_Config.getApiClient_ByPost;

public class InboxActivity extends AppCompatActivity {
    ImageView imgBack;
    ProgressDialog progressDialog;
    String UserId = "";
    RecyclerView reycylerInbox;
    AdaptorInboxList adaptorInboxList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");
        initViews();
    }

    private void initViews() {

        progressDialog = new ProgressDialog(this);

        imgBack = findViewById(R.id.imgBack);
        reycylerInbox = findViewById(R.id.reycylerInbox);
        reycylerInbox.setLayoutManager(new LinearLayoutManager(this));
        reycylerInbox.setHasFixedSize(true);

        imgBack.setOnClickListener(v -> finish());

        getInboxList();
    }

    private void getInboxList() {
        progressDialog.show();
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("UserId", UserId);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Api_Urls.Inbox, jsonObject, response -> {
            Gson gson = new Gson();
            progressDialog.dismiss();
            Log.e("TAG", "creditResponse: " + response);
            try {
                DataResponseInbox dataResponseInbox = gson.fromJson(response.toString(), DataResponseInbox.class);
                if (dataResponseInbox.isStatus()) {
                    adaptorInboxList = new AdaptorInboxList(getApplicationContext(), dataResponseInbox.getResponse());
                    reycylerInbox.setAdapter(adaptorInboxList);
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

    public class AdaptorInboxList extends RecyclerView.Adapter<AdaptorInboxList.MyInbox> {

        Context context;
        List<ResponseItem> list;

        public AdaptorInboxList(Context context, List<ResponseItem> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MyInbox onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyInbox(LayoutInflater.from(context).inflate(R.layout.dynamic_inbox, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MyInbox holder, int position) {
            holder.tvSubject.setText(list.get(position).getSubject());
            holder.tvMessage.setText(Html.fromHtml(list.get(position).getTcktDescr()));
            holder.tvRecDate.setText(list.get(position).getEntdate());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyInbox extends RecyclerView.ViewHolder {
            TextView tvSubject, tvMessage, tvRecDate;

            public MyInbox(@NonNull View itemView) {
                super(itemView);
                tvSubject = itemView.findViewById(R.id.tvSubject);
                tvMessage = itemView.findViewById(R.id.tvMessage);
                tvRecDate = itemView.findViewById(R.id.tvRecDate);
            }
        }
    }
}