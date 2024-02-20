package com.sign.akp_sahaytajweller.SlidingMenu;

import static com.sign.akp_sahaytajweller.Basic.API_Config.getApiClient_ByPost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.sign.akp_sahaytajweller.Basic.ApiService;
import com.sign.akp_sahaytajweller.Basic.ConnectToRetrofit;
import com.sign.akp_sahaytajweller.Basic.GlobalAppApis;
import com.sign.akp_sahaytajweller.Basic.LoginScreen;
import com.sign.akp_sahaytajweller.Basic.RetrofitCallBackListenar;
import com.sign.akp_sahaytajweller.DashboardActivity;
import com.sign.akp_sahaytajweller.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

public class GetEMIDetailsReport extends AppCompatActivity {
    ImageView menuImg;
    RecyclerView history_rec;
    String UserId;
    ArrayList<HashMap<String, String>> arrayList2 = new ArrayList<>();
    ImageView norecord_img;
    private String getProfarmaNumber;
    LinearLayout mail_rl;
    private PopupWindow popupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_emidetails_report);
        FindId();
        History();
    }

    private void FindId() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_preference", MODE_PRIVATE);
        UserId = sharedPreferences.getString("U_id", "");
        getProfarmaNumber=getIntent().getStringExtra("ProfarmaNo");
        norecord_img=findViewById(R.id.norecord_img);
        menuImg = findViewById(R.id.menuImg);
        history_rec = findViewById(R.id.history_rec);
        mail_rl = findViewById(R.id.mail_rl);
        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void History() {
        String otp = new GlobalAppApis().GetProfarmaDetails(UserId,getProfarmaNumber);
        ApiService client = getApiClient_ByPost();
        Call<String> call = client.API_GetProfarmaDetails(otp);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("ProfarmaNumber",result);
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
                            hm.put("EMIDate", jsonObject1.getString("EMIDate"));
                            hm.put("InstallmentNo", jsonObject1.getString("InstallmentNo"));
                            hm.put("EMIAmount", jsonObject1.getString("EMIAmount"));
                            hm.put("TotalPaid", jsonObject1.getString("TotalPaid"));
                            hm.put("Status", jsonObject1.getString("Status"));
                            hm.put("PaidDate", jsonObject1.getString("PaidDate"));
                            hm.put("PaymentMode", jsonObject1.getString("PaymentMode"));
                            arrayList2.add(hm);
                        }
                        LinearLayoutManager HorizontalLayout1 = new LinearLayoutManager(GetEMIDetailsReport.this, LinearLayoutManager.VERTICAL, false);
                        GetEMIDetailsReport.DasAdapter customerListAdapter = new GetEMIDetailsReport.DasAdapter(GetEMIDetailsReport.this, arrayList2);
                        history_rec.setLayoutManager(HorizontalLayout1);
                        history_rec.setAdapter(customerListAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }}
        }, GetEMIDetailsReport.this, call, "", true);
    }

    public class DasAdapter extends RecyclerView.Adapter<GetEMIDetailsReport.DasAdapter.VH> {
        Context context;
        List<HashMap<String,String>> arrayList;

        public DasAdapter(Context context, List<HashMap<String,String>> arrayList) {
            this.arrayList=arrayList;
            this.context=context;
        }

        @NonNull
        @Override
        public GetEMIDetailsReport.DasAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.dynamic_getemidetailsreport, viewGroup, false);
            GetEMIDetailsReport.DasAdapter.VH viewHolder = new GetEMIDetailsReport.DasAdapter.VH(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull GetEMIDetailsReport.DasAdapter.VH vh, int i) {
            vh.tv.setText(String.valueOf(i+1));
            vh.tv1.setText(arrayList.get(i).get("EMIDate"));
            vh.tv2.setText(arrayList.get(i).get("InstallmentNo"));
            vh.tv3.setText(arrayList.get(i).get("EMIAmount"));
            vh.tv4.setText(arrayList.get(i).get("TotalPaid"));
            vh.tv5.setText(arrayList.get(i).get("Status"));
            vh.tv6.setText(arrayList.get(i).get("PaidDate"));
            vh.tv7.setText(arrayList.get(i).get("PaymentMode"));


            if (arrayList.get(i).get("Status").equalsIgnoreCase("Paid")){
                vh.tv5.setTextColor(Color.GREEN);
                vh.view_btn.setVisibility(View.INVISIBLE);
            }
            else {
                vh.tv5.setTextColor(Color.RED);
                vh.view_btn.setVisibility(View.VISIBLE);
            }

            vh.view_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PayEMIPopup(arrayList.get(i).get("InstallmentNo"),arrayList.get(i).get("EMIAmount"));
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



    private void PayEMIPopup(String instno,String amount) {
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.pay_emi,null);
        ImageView Goback = (ImageView) customView.findViewById(R.id.Goback);
        EditText performano_et = (EditText) customView.findViewById(R.id.performano_et);
        EditText inst_no_et = (EditText) customView.findViewById(R.id.inst_no_et);
        EditText amount_et = (EditText) customView.findViewById(R.id.amount_et);
        EditText paymode_et = (EditText) customView.findViewById(R.id.paymode_et);
        EditText remark_et = (EditText) customView.findViewById(R.id.remark_et);
        AppCompatButton continue_btn = (AppCompatButton) customView.findViewById(R.id.continue_btn);
        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayEMI(performano_et.getText().toString(),UserId,inst_no_et.getText().toString(),
                        amount_et.getText().toString(),remark_et.getText().toString(),"Fund Wallet");
            }
        });

        performano_et.setText(getProfarmaNumber);
        inst_no_et.setText(instno);
        amount_et.setText(amount);
        //instantiate popup window
        popupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //display the popup window
        popupWindow.showAtLocation(mail_rl, Gravity.CENTER, 10, 0);
        popupWindow.setFocusable(true);
        // Settings disappear when you click somewhere else
        popupWindow.setOutsideTouchable(true);
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.update();

        Goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }



    public void  PayEMI(String ProfarmaNo,String MemberId,String InstallmentNo,String EMIAmount,String Description,String paymentmode){
        String otp1 = new GlobalAppApis().EMIRepayment(ProfarmaNo,MemberId,InstallmentNo,EMIAmount,Description,paymentmode);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.API_EMIRepayment(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("API_EMIRepayment",result);

//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("Status").equalsIgnoreCase("true")){
                        JSONArray Jarray = object.getJSONArray("Response");
                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject jsonobject = Jarray.getJSONObject(i);
                            Toast.makeText(getApplicationContext(), jsonobject.getString("Msg"), Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(), DashboardActivity.class);
                            startActivity(intent);
                        } }
                    else {
//                        FailedPopup(object.getString("Message"));
                        AlertDialog.Builder builder = new AlertDialog.Builder(GetEMIDetailsReport.this);
                        builder.setTitle("Message!").setMessage(object.getString("Message")).setCancelable(false).setIcon(R.drawable.appicon)
                                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }});
                        builder.create().show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"UserId and password not matched!",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } }
        }, GetEMIDetailsReport.this, call1, "", true);
    }

}