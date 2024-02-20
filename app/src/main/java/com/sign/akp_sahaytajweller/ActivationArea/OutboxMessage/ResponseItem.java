package com.sign.akp_sahaytajweller.ActivationArea.OutboxMessage;

import com.google.gson.annotations.SerializedName;

public class ResponseItem{

	@SerializedName("RecieverId")
	private String recieverId;

	@SerializedName("EntryBy")
	private String entryBy;

	@SerializedName("TicketId")
	private String ticketId;

	@SerializedName("IsActive")
	private boolean isActive;

	@SerializedName("EntryDate")
	private String entryDate;

	@SerializedName("TcktDescr")
	private String tcktDescr;

	@SerializedName("Id")
	private int id;

	@SerializedName("SenderId")
	private String senderId;

	@SerializedName("Entdate")
	private String entdate;

	@SerializedName("Subject")
	private String subject;

	public String getRecieverId(){
		return recieverId;
	}

	public String getEntryBy(){
		return entryBy;
	}

	public String getTicketId(){
		return ticketId;
	}

	public boolean isIsActive(){
		return isActive;
	}

	public String getEntryDate(){
		return entryDate;
	}

	public String getTcktDescr(){
		return tcktDescr;
	}

	public int getId(){
		return id;
	}

	public String getSenderId(){
		return senderId;
	}

	public String getEntdate(){
		return entdate;
	}

	public String getSubject(){
		return subject;
	}
}