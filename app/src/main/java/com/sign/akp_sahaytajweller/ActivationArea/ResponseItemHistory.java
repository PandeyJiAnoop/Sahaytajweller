package com.sign.akp_sahaytajweller.ActivationArea;


import com.google.gson.annotations.SerializedName;

public class ResponseItemHistory{

    @SerializedName("TopUpName")
    private String topUpName;

    @SerializedName("Member_ID")
    private String memberID;

    @SerializedName("amount")
    private double amount;

    @SerializedName("FranchiseStatus")
    private String franchiseStatus;

    @SerializedName("PayMode")
    private String payMode;

    @SerializedName("txnDate")
    private String txnDate;

    @SerializedName("asarincome")
    private double asarincome;

    public String getTopUpName(){
        return topUpName;
    }

    public String getMemberID(){
        return memberID;
    }

    public double getAmount(){
        return amount;
    }

    public String getFranchiseStatus(){
        return franchiseStatus;
    }

    public String getPayMode(){
        return payMode;
    }

    public String getTxnDate(){
        return txnDate;
    }

    public double getAsarincome(){
        return asarincome;
    }
}