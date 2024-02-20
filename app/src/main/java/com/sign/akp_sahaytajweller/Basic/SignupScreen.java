package com.sign.akp_sahaytajweller.Basic;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sign.akp_sahaytajweller.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

import retrofit2.Call;

import static com.sign.akp_sahaytajweller.Basic.API_Config.getApiClient_ByPost;


public class SignupScreen extends AppCompatActivity {
//    public class SignupScreen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
        String[] courses = { "Direction", "Left", "Right" };
    EditText sponserid_et,name_et,email_et,mob_et,c_pass_et,pass_et,spname_et;
    TextView signin_tv;

    AppCompatButton signup_btn;


    SearchableSpinner country_et;
    String stateid; ArrayList<String> StateName = new ArrayList<>();
    ArrayList<String> StateId = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);

        findId();
        OnClick();
        CountryList();

//        Spinner spin = findViewById(R.id.direction_sp);
//        spin.setOnItemSelectedListener(this);
//        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
//        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spin.setAdapter(ad);
    }

    private void OnClick() {
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sponserid_et.getText().toString().equalsIgnoreCase("")){
                    sponserid_et.setError("Fields can't be blank!");
                    sponserid_et.requestFocus();
                }
                else if (spname_et.getText().toString().equalsIgnoreCase("")){
                    spname_et.setError("Fields can't be blank!");
                    spname_et.requestFocus();
                }

                else if (name_et.getText().toString().equalsIgnoreCase("")){
                    name_et.setError("Fields can't be blank!");
                    name_et.requestFocus();
                }
                else if (email_et.getText().toString().equalsIgnoreCase("")){
                    email_et.setError("Fields can't be blank!");
                    email_et.requestFocus();
                }
                else if (mob_et.getText().toString().equalsIgnoreCase("")){
                    mob_et.setError("Fields can't be blank!");
                    mob_et.requestFocus();
                }


                else if (pass_et.getText().toString().equalsIgnoreCase("")){
                    pass_et.setError("Fields can't be blank!");
                    pass_et.requestFocus();
                }
                else if (c_pass_et.getText().toString().equalsIgnoreCase("")){
                    c_pass_et.setError("Fields can't be blank!");
                    c_pass_et.requestFocus();
                }
                else if (!pass_et.getText().toString().equalsIgnoreCase(c_pass_et.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Password Not Matched!",Toast.LENGTH_LONG).show();

                }
                else {
                    getRegAPI(stateid,email_et.getText().toString(),name_et.getText().toString(),mob_et.getText().toString(),
                            pass_et.getText().toString(),sponserid_et.getText().toString());
                } }});
        signin_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LoginScreen.class);
                startActivity(intent);
            }
        });
    }

    private void findId() {
        country_et=findViewById(R.id.country_et);
        sponserid_et=findViewById(R.id.sponserid_et);
        name_et=findViewById(R.id.name_et);
        email_et=findViewById(R.id.email_et);
        mob_et=findViewById(R.id.mob_et);
        pass_et=findViewById(R.id.pass_et);
        c_pass_et=findViewById(R.id.c_pass_et);
        signup_btn=findViewById(R.id.signup_btn);
        signin_tv=findViewById(R.id.signin_tv);
        spname_et=findViewById(R.id.spname_et);
        sponserid_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        country_et.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                if (position > 0) {
                    for (int j = 0; j <= StateId.size(); j++) {
                        if (country_et.getSelectedItem().toString().equalsIgnoreCase(StateName.get(j))) {
                            // position = i;
                            stateid = StateId.get(j);
//                            stateid = StateId.get(j - 1);
                            break;
                        } } } }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
//    // Performing action when ItemSelected
//    // from spinner, Overriding onItemSelected method
//    @Override
//    public void onItemSelected(AdapterView arg0, View arg1, int position, long id)
//    {
//        Toast.makeText(getApplicationContext(),courses[position], Toast.LENGTH_LONG).show();
//    }
//    @Override
//    public void onNothingSelected(AdapterView arg0)
//    {
//        // Auto-generated method stub
//    }

    @Override
    public void onBackPressed() {
        finish();
    }




    public void  getRegAPI( String Country, String Email,String FullName,String MobilNo,String Password,String SponsorId){
        String otp1 = new GlobalAppApis().NewAccount(Country,Email,FullName,MobilNo,Password,SponsorId);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getNewAccount(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("res_reg",result);
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("Status").equalsIgnoreCase("true")){
                        JSONArray Jarray = object.getJSONArray("Response");
                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject jsonobject = Jarray.getJSONObject(i);
                            Toast.makeText(getApplicationContext(), jsonobject.getString("MSG"), Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignupScreen.this);
                            builder.setTitle("Registration Successfully!")
                                    .setMessage(jsonobject.getString("MSG"))
                                    .setCancelable(false)
                                    .setIcon(R.drawable.appicon)
                                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Intent intent=new Intent(getApplicationContext(),LoginScreen.class);
                                            startActivity(intent);
                                            overridePendingTransition(0, 0);
                                        startActivity(getIntent());
                                        overridePendingTransition(0, 0);
                                        }
                                    });
                            builder.create().show();
                    }}
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignupScreen.this);
                        builder.setTitle("ERROR!")
                                .setMessage(object.getString("Message"))
                                .setCancelable(false)
                                .setIcon(R.drawable.appicon)
                                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent=new Intent(getApplicationContext(),LoginScreen.class);
                                        startActivity(intent);
                                        dialog.cancel();
                                    }
                                });
                        builder.create().show();
                    }

                } catch (JSONException e) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignupScreen.this);
                    builder.setTitle("ERROR!")
                            .setMessage("Something Went Wrong!!")
                            .setCancelable(false)
                            .setIcon(R.drawable.appicon)
                            .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent=new Intent(getApplicationContext(),LoginScreen.class);
                                    startActivity(intent);
                                    dialog.cancel();
                                }
                            });
                    builder.create().show();
                    e.printStackTrace();
                }
            }
        }, SignupScreen.this, call1, "", true);
    }




    public void CountryList() {
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.API_GetCountryList();
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("API_GetCountryList","cxc"+result);
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getBoolean("Status")==false){
                        String msg       = object.getString("Message");
                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        JSONArray jsonArray = object.getJSONArray("Response");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            StateId.add(jsonObject1.getString("DialingCode"));
                            String statename = jsonObject1.getString("Country");
                            StateName.add(statename);
                        }
                        country_et.setAdapter(new ArrayAdapter<String>(SignupScreen.this, android.R.layout.simple_spinner_dropdown_item, StateName));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, SignupScreen.this, call1, "", true);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        getDataFromSponser(sponserid_et.getText().toString());
    }


    private void getDataFromSponser(String refid)
    {
        String otp1 = new GlobalAppApis().CheckSponsor(refid);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.GetCheckSponsor(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("API_result","cxc"+result);
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("Status").equals("false")){
                        sponserid_et.setText("");
                        spname_et.setText("");
                        AlertDialog.Builder builder = new AlertDialog.Builder(SignupScreen.this);
                        builder.setTitle("Failed!- Invalid")
                                .setMessage(object.getString("Message"))
                                .setCancelable(false)
                                .setIcon(R.drawable.logo)
                                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                }); builder.create().show();
                    } else {
                        JSONArray Jarray = object.getJSONArray("Response");
                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject jsonobject = Jarray.getJSONObject(i);
                            String SponserName = jsonobject.getString("CustomerName");
                            spname_et.setText(SponserName);
                        }

//                        Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "UserId and password not matched!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } } }, SignupScreen.this, call1, "", true);
    }
}