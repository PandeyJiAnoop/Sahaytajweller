package com.sign.akp_sahaytajweller.ActivationArea;

import com.google.gson.annotations.SerializedName;
import com.sign.akp_sahaytajweller.ActivationArea.CreditTrans.ResponseItem;

import java.util.List;

public class DataResponseActivationHis{

    @SerializedName("Response")
    private List<ResponseItemHistory> response;

    @SerializedName("Status")
    private boolean status;

    @SerializedName("Message")
    private String message;

    @SerializedName("StatusCode")
    private String statusCode;

    public List<ResponseItemHistory> getResponse(){
        return response;
    }

    public boolean isStatus(){
        return status;
    }

    public String getMessage(){
        return message;
    }

    public String getStatusCode(){
        return statusCode;
    }
}
