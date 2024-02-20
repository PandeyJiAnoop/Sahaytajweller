package com.sign.akp_sahaytajweller.ActivationArea.CreditTrans;

import com.google.gson.annotations.SerializedName;

public class ResponseItem{

	@SerializedName("Type")
	private String type;

	@SerializedName("Member_Id")
	private String memberId;

	@SerializedName("Is_Active")
	private String isActive;

	@SerializedName("Amount")
	private double amount;

	@SerializedName("Id")
	private int id;

	@SerializedName("Entry_Date")
	private String entryDate;

	@SerializedName("GenDate")
	private String genDate;

	@SerializedName("Payable")
	private Object payable;

	@SerializedName("Remark")
	private String remark;

	public String getType(){
		return type;
	}

	public String getMemberId(){
		return memberId;
	}

	public String isIsActive(){
		return isActive;
	}

	public double getAmount(){
		return amount;
	}

	public int getId(){
		return id;
	}

	public String getEntryDate(){
		return entryDate;
	}

	public String getGenDate(){
		return genDate;
	}

	public Object getPayable(){
		return payable;
	}

	public String getRemark(){
		return remark;
	}
}