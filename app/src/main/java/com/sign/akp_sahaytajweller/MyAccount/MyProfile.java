package com.sign.akp_sahaytajweller.MyAccount;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.sign.akp_sahaytajweller.Basic.ApiService;
import com.sign.akp_sahaytajweller.Basic.ConnectToRetrofit;
import com.sign.akp_sahaytajweller.Basic.GlobalAppApis;
import com.sign.akp_sahaytajweller.Basic.RetrofitCallBackListenar;
import com.google.android.material.textfield.TextInputEditText;
import com.sign.akp_sahaytajweller.DashboardActivity;
import com.sign.akp_sahaytajweller.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import retrofit2.Call;

import static com.sign.akp_sahaytajweller.Basic.API_Config.getApiClient_ByPost;

public class MyProfile extends AppCompatActivity {
 ImageView menuImg;
 String UserId;
 TextInputEditText sponser_id_et,name_et,mobile_et,email_et,gender_et,usdt_adress_et;
 AppCompatButton btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        SharedPreferences sharedPreferences = getSharedPreferences("login_preference",MODE_PRIVATE);
        UserId= sharedPreferences.getString("U_id", "");

        menuImg=findViewById(R.id.menuImg);
        sponser_id_et=findViewById(R.id.sponser_id_et);
        name_et=findViewById(R.id.name_et);
        mobile_et=findViewById(R.id.mobile_et);
        email_et=findViewById(R.id.email_et);
        gender_et=findViewById(R.id.gender_et);
        usdt_adress_et=findViewById(R.id.usdt_adress_et);

        btn_submit=findViewById(R.id.btn_submit);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usdt_adress_et.getText().toString().equalsIgnoreCase("")){
                    usdt_adress_et.setError("Fields can't be blank!");
                    usdt_adress_et.requestFocus();
                }
                else {
                    UpdateProfileAPI("","",email_et.getText().toString(),name_et.getText().toString(),mobile_et.getText().toString(),
                            "","",sponser_id_et.getText().toString(),usdt_adress_et.getText().toString(),UserId);


                }
            }
        });

        menuImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ProfileAPI("",UserId);
    }




    public void  ProfileAPI(String action,String userid){
        String otp1 = new GlobalAppApis().Profile(action,userid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getProfile(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray Jarray = object.getJSONArray("LoginRes");
                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject jsonobject = Jarray.getJSONObject(i);
                        String bnbAddress       = jsonobject.getString("BNBAddress");
                        String btcAddress    = jsonobject.getString("BTCAddress");
                        String customerId      = jsonobject.getString("CustomerId");
                        String ethAddress       = jsonobject.getString("ETHAddress");

                        String emailId       = jsonobject.getString("EmailId");
                        String gender    = jsonobject.getString("Gender");
                        String mobileNo       = jsonobject.getString("MobileNo");
                        String name       = jsonobject.getString("Name");
                        String sponsorId       = jsonobject.getString("SponsorId");
                        String title    = jsonobject.getString("Title");
                        String usdtAddress       = jsonobject.getString("USDTAddress");

                        sponser_id_et.setText(sponsorId);
                        name_et.setText(name);
                        mobile_et.setText(mobileNo);
                        email_et.setText(emailId);
                        gender_et.setText(gender);
                        usdt_adress_et.setText(usdtAddress);
//                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"UserId and password not matched!",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, MyProfile.this, call1, "", true);
    }
    public void  UpdateProfileAPI(String action, String Country, String Email,String FullName,String MobilNo,String NewPassword,
                                  String OldPassword,String SponsorId,String USDTAddress,String UserId){
        String otp1 = new GlobalAppApis().UpdateProfile(action,Country,Email,FullName,MobilNo,NewPassword,OldPassword,SponsorId,USDTAddress,UserId);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getUpdateAccountProfile(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("resppp",result);
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("Status").equalsIgnoreCase("true")){
                            Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
                            builder.setTitle("Update Successfully!")
                                    .setMessage(object.getString("Message"))
                                    .setCancelable(false)
                                    .setIcon(R.drawable.appicon)
                                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Intent intent=new Intent(getApplicationContext(), DashboardActivity.class);
                                            startActivity(intent);
                                            dialog.cancel();
                                        }
                                    });
                            builder.create().show();
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
                        builder.setTitle("ERROR!")
                                .setMessage(object.getString("Message"))
                                .setCancelable(false)
                                .setIcon(R.drawable.appicon)
                                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        overridePendingTransition(0, 0);
                                        startActivity(getIntent());
                                        overridePendingTransition(0, 0);
                                    }
                                });
                        builder.create().show();
                    }

                } catch (JSONException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
                    builder.setTitle("ERROR!")
                            .setMessage("Something Went Wrong!!")
                            .setCancelable(false)
                            .setIcon(R.drawable.appicon)
                            .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    overridePendingTransition(0, 0);
                                        startActivity(getIntent());
                                        overridePendingTransition(0, 0);
                                }
                            });
                    builder.create().show();
                    e.printStackTrace();
                }
            }
        }, MyProfile.this, call1, "", true);
    }

}