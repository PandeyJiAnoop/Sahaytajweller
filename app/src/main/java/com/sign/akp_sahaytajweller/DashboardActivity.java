package com.sign.akp_sahaytajweller;

import static com.sign.akp_sahaytajweller.Basic.API_Config.getApiClient_ByPost;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sign.akp_sahaytajweller.ActivationArea.ActivationHistoryActivity;
import com.sign.akp_sahaytajweller.ActivationArea.AddFundInrActivity;
import com.sign.akp_sahaytajweller.ActivationArea.IdActivationActivity;
import com.sign.akp_sahaytajweller.ActivationArea.TransferHistory;
import com.sign.akp_sahaytajweller.Basic.ApiService;
import com.sign.akp_sahaytajweller.Basic.ConnectToRetrofit;
import com.sign.akp_sahaytajweller.Basic.GlobalAppApis;
import com.sign.akp_sahaytajweller.Basic.LoginScreen;
import com.sign.akp_sahaytajweller.Basic.RetrofitCallBackListenar;
import com.sign.akp_sahaytajweller.Basic.SplashScreen;
import com.sign.akp_sahaytajweller.FundArea.PrincipalWithdrawalRequestReport;
import com.sign.akp_sahaytajweller.MyAccount.ChangePassword;
import com.sign.akp_sahaytajweller.MyAccount.KYCDocuments;
import com.sign.akp_sahaytajweller.MyAccount.UpdateProfile;
import com.sign.akp_sahaytajweller.MyNetwork.DownLine;
import com.sign.akp_sahaytajweller.MyNetwork.LevelIncome;
import com.sign.akp_sahaytajweller.MyNetwork.MyTree;
import com.sign.akp_sahaytajweller.SlidingMenu.EMIDetails;
import com.sign.akp_sahaytajweller.SlidingMenu.InboxActivity;
import com.sign.akp_sahaytajweller.SlidingMenu.OutboxActivity;
import com.sign.akp_sahaytajweller.SlidingMenu.ReferralList;
import com.sign.akp_sahaytajweller.SlidingMenu.Withdraw;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

public class DashboardActivity extends AppCompatActivity {
    LinearLayout one, two, three, four, five, six;
    private ImageView sliding_menu;
    private DrawerLayout mDrawer;
    LinearLayout one_ll, ll_two, ll_three, ll_four, ll_five, ll_six;
    int i = 0;
    LinearLayout logout_ll, dash_ll;
    String UserId, UserPass, UserRole, UserName;
    SwipeRefreshLayout srl_refresh;
    TextView app_name;
    TextView das_totalref,das_activeref,das_activeteam,das_inactiveteam,das_levelincome,das_roylityincome,das_fundwallet,
            das_totalincome,das_totalwithdrawal,das_withdrawalbalance;
    SliderLayout sliderLayout;
    TextView  tvEditProfile,tvChangePass,tvtrxPass, tvKYC,tvAddFundInr,tvIdActivation,tvActivationHis;
    LinearLayout emi_details_ll;
    TextView referral_tv,level_view_tv,mygenealogy_tv,levelincome_tv,royality_income_tv;
    TextView withdrawal_req_tv,withdrawal_history_tv,trx_history_tv;
    TextView ticket_tv,inbox_ll,outbox_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        SharedPreferences sharedPreferences = getSharedPreferences("login_preference", MODE_PRIVATE);
        UserId = sharedPreferences.getString("U_id", "");
        UserPass = sharedPreferences.getString("U_pass", "");
        UserRole = sharedPreferences.getString("U_role", "");
        UserName = sharedPreferences.getString("U_name", "");
        FindId();
        OnClick();
        app_name.setText("Welcome "+UserName+ "!");
        setSliderViews();

        GetBindDashboard();
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        sliding_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//                mDrawer.openDrawer(Gravity.START);
                mDrawer.openDrawer(Gravity.START);
//                close.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mDrawer.closeDrawer(Gravity.START);
//                    }
//                });
            }
        });
    }

    private void GetBindDashboard() {
        String otp1 = new GlobalAppApis().Dashboard(UserId);
        ApiService client1 = getApiClient_ByPost();
        Call<String> call1 = client1.getBindDashboard(otp1);
        new ConnectToRetrofit(new RetrofitCallBackListenar() {
            @Override
            public void RetrofitCallBackListenar(String result, String action) throws JSONException {
                Log.d("res_das",result);
//                Toast.makeText(getApplicationContext(),""+result,Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = new JSONObject(result);
                    if (object.getString("Status").equalsIgnoreCase("true")){
                        JSONArray Jarray = object.getJSONArray("Response");
                        for (int i = 0; i < Jarray.length(); i++) {
                            JSONObject jsonobject = Jarray.getJSONObject(i);
                            Toast.makeText(getApplicationContext(), object.getString("Message"), Toast.LENGTH_SHORT).show();
                            das_totalref.setText(jsonobject.getString("TotalReferral"));
                            das_activeref.setText(jsonobject.getString("ActiveReferral"));
                            das_activeteam.setText(jsonobject.getString("TotalActiveTeam"));
                            das_inactiveteam.setText(jsonobject.getString("TotalInActiveTeam"));
                            das_levelincome.setText(jsonobject.getString("LevelIncome"));
                            das_roylityincome.setText(jsonobject.getString("RoyaltyIncome"));
                            das_fundwallet.setText(jsonobject.getString("FundWallet"));
                            das_totalincome.setText(jsonobject.getString("TotalIncome"));
                            das_totalwithdrawal.setText(jsonobject.getString("WithDrwlAmt"));
                            das_withdrawalbalance.setText(jsonobject.getString("BalanceAmount"));
                        }

                    }
                    else {
                        Toast.makeText(getApplicationContext(),object.getString("Message"),Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"UserId and password not matched!",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } }
        }, DashboardActivity.this, call1, "", true);
    }

    private void OnClick() {
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    one_ll.setVisibility(View.GONE);
                    i++;
                } else if (i == 1) {
                    one_ll.setVisibility(View.VISIBLE);
                    i = 0;
                }
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    ll_two.setVisibility(View.GONE);
                    i++;
                } else if (i == 1) {
                    ll_two.setVisibility(View.VISIBLE);
                    i = 0;
                }
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    ll_three.setVisibility(View.GONE);
                    i++;
                } else if (i == 1) {
                    ll_three.setVisibility(View.VISIBLE);
                    i = 0;
                }
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    ll_four.setVisibility(View.GONE);
                    i++;
                } else if (i == 1) {
                    ll_four.setVisibility(View.VISIBLE);
                    i = 0;
                }
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    ll_five.setVisibility(View.GONE);
                    i++;
                } else if (i == 1) {
                    ll_five.setVisibility(View.VISIBLE);
                    i = 0;
                }
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == 0) {
                    ll_six.setVisibility(View.GONE);
                    i++;
                } else if (i == 1) {
                    ll_six.setVisibility(View.VISIBLE);
                    i = 0;
                }
            }
        });
        logout_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this).setTitle("Logout")
                        .setMessage("Are you sure want to Logout").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences myPrefs = getSharedPreferences("login_preference", MODE_PRIVATE);
                                SharedPreferences.Editor editor = myPrefs.edit();
                                editor.clear();
                                editor.commit();
                                Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
                                startActivity(intent);
                                Intent i = new Intent();
                                i.putExtra("finish", true);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                                //startActivity(i);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                overridePendingTransition(0, 0);
                                startActivity(getIntent());
                                overridePendingTransition(0, 0);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        dash_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(Gravity.START);
            }
        });
        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkConnectionHelper.isOnline(DashboardActivity.this)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                            srl_refresh.setRefreshing(false);
                        }
                    }, 2000);
                } else {
                    Toast.makeText(DashboardActivity.this, "Please check your internet connection! try again...", Toast.LENGTH_SHORT).show();
                }
            }
        });


        tvEditProfile.setOnClickListener(v -> {
            mDrawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), UpdateProfile.class));
        });
        tvtrxPass.setOnClickListener(v -> {
            mDrawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), ChangePassword.class));
        });
        tvChangePass.setOnClickListener(v -> {
            mDrawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), ChangePassword.class));
        });
        tvKYC.setOnClickListener(v -> {
            mDrawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), KYCDocuments.class));
        });

        tvAddFundInr.setOnClickListener(v -> {
            mDrawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), AddFundInrActivity.class));
        });
        tvIdActivation.setOnClickListener(v -> {
            mDrawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), IdActivationActivity.class));
        });

        tvActivationHis.setOnClickListener(v -> {
            mDrawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), ActivationHistoryActivity.class));
        });
        emi_details_ll.setOnClickListener(v -> {
            mDrawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), EMIDetails.class));
        });


        referral_tv.setOnClickListener(v -> {
            mDrawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), ReferralList.class));
        });
        level_view_tv.setOnClickListener(v -> {
            mDrawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), DownLine.class));
        });
        mygenealogy_tv.setOnClickListener(v -> {
            mDrawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), MyTree.class));
        });
        levelincome_tv.setOnClickListener(v -> {
            mDrawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), LevelIncome.class));
        });
        royality_income_tv.setOnClickListener(v -> {
            mDrawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), LevelIncome.class));
        });
        withdrawal_req_tv.setOnClickListener(v -> {
            mDrawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), Withdraw.class));
        });
        withdrawal_history_tv.setOnClickListener(v -> {
            mDrawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), PrincipalWithdrawalRequestReport.class));
        });
        trx_history_tv.setOnClickListener(v -> {
            mDrawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), TransferHistory.class));
        });

        ticket_tv.setOnClickListener(v -> {
            mDrawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), Tickets.class));
        });
        inbox_ll.setOnClickListener(v -> {
            mDrawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), InboxActivity.class));
        });
        outbox_tv.setOnClickListener(v -> {
            mDrawer.closeDrawer(GravityCompat.START);
            startActivity(new Intent(getApplicationContext(), OutboxActivity.class));
        });
    }

    private void FindId() {

        srl_refresh = findViewById(R.id.srl_refresh);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);

        one_ll = findViewById(R.id.one_ll);
        ll_two = findViewById(R.id.ll_two);
        ll_three = findViewById(R.id.ll_three);
        ll_four = findViewById(R.id.ll_four);
        ll_five = findViewById(R.id.ll_five);
        ll_six = findViewById(R.id.ll_six);
        logout_ll = findViewById(R.id.logout_ll);
        dash_ll = findViewById(R.id.dash_ll);
        app_name = findViewById(R.id.app_name);
        tvEditProfile = findViewById(R.id.tvEditProfile);
        tvChangePass = findViewById(R.id.tvChangePass);
        tvtrxPass = findViewById(R.id.tvtrxPass);
        tvKYC = findViewById(R.id.tvKYC);
        sliding_menu = (ImageView) findViewById(R.id.sliding_menu);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        das_totalref = findViewById(R.id.das_totalref);
        das_activeref = findViewById(R.id.das_activeref);
        das_activeteam = findViewById(R.id.das_activeteam);
        das_inactiveteam = findViewById(R.id.das_inactiveteam);
        das_levelincome = findViewById(R.id.das_levelincome);
        das_roylityincome = findViewById(R.id.das_roylityincome);
        das_fundwallet = findViewById(R.id.das_fundwallet);
        das_totalincome = findViewById(R.id.das_totalincome);
        das_totalwithdrawal = findViewById(R.id.das_totalwithdrawal);
        das_withdrawalbalance = findViewById(R.id.das_withdrawalbalance);

        tvAddFundInr = findViewById(R.id.tvAddFundInr);
        tvIdActivation = findViewById(R.id.tvIdActivation);
        tvActivationHis = findViewById(R.id.tvActivationHis);
        emi_details_ll = findViewById(R.id.emi_details_ll);

        referral_tv = findViewById(R.id.referral_tv);
        level_view_tv = findViewById(R.id.level_view_tv);
        mygenealogy_tv = findViewById(R.id.mygenealogy_tv);
        royality_income_tv = findViewById(R.id.royality_income_tv);
        levelincome_tv = findViewById(R.id.levelincome_tv);
        withdrawal_req_tv = findViewById(R.id.withdrawal_req_tv);
        withdrawal_history_tv = findViewById(R.id.withdrawal_history_tv);
        trx_history_tv = findViewById(R.id.trx_history_tv);
        ticket_tv = findViewById(R.id.ticket_tv);
        inbox_ll = findViewById(R.id.inbox_ll);
        outbox_tv = findViewById(R.id.outbox_tv);

        sliderLayout = findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setScrollTimeInSec(3); //set scroll delay in seconds :


    }
    private void setSliderViews() {
        for (int i = 0; i <= 5; i++) {
            SliderView sliderView = new SliderView(DashboardActivity.this);
            switch (i) {
                case 0:
                    sliderView.setImageDrawable(R.drawable.goldbanner);
                    sliderView.setDescription("Welcome To\n" + "SahaytaJweller");
                    break;
                case 1:
                    sliderView.setImageDrawable(R.drawable.ban);
//                    sliderView.setDescription("सच होगा सपना");
                    break;
                case 2:
                    sliderView.setImageDrawable(R.drawable.ban1);
//                    sliderView.setDescription("सोचो  एक  नयी  दुनिया ");
                    break;
                case 3:
                    sliderView.setImageDrawable(R.drawable.ban2);
//                    sliderView.setDescription("खुशियां  हो  जहाँ  ");
                    break;
                case 4:
                    sliderView.setImageDrawable(R.drawable.ban);
//                    sliderView.setDescription("खुशियां  हो  जहाँ  ");
                    break;
                case 5:
                    sliderView.setImageDrawable(R.drawable.ban3);
//                    sliderView.setDescription("खुशियां  हो  जहाँ  ");
                    break;
            }
            sliderView.setImageScaleType(ImageView.ScaleType.FIT_XY);
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    Toast.makeText(DashboardActivity.this, "Welcome To UniqPay " + (finalI + 1), Toast.LENGTH_SHORT).show();
                } });
            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        } }

}