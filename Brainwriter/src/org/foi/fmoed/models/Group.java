package org.foi.fmoed.models;

import org.foi.fmoed.managers.DatabaseManager;
import android.content.ContentValues;

public class Group {
	
	public static String STATUS_IN_PROGRESS = "in progress";
	public static String STATUS_FINISHED = "finished";
	
	private int id;
	private String name;
	private String status;
	private String round;
	
	public Group() {
	}
	
	public Group(String name) {
		super();
		this.name = name;
	}
	
	public Group(String n, String s, String r){
		super();
		this.name = n;
		this.status = s;
		this.round = r;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRound() {
		return round;
	}

	public void setRound(String round) {
		this.round = round;
	}
	
	public ContentValues getValues() {
		ContentValues values = new ContentValues();
	    values.put(DatabaseManager.KEY_NAME, this.getName()); 
	    values.put(DatabaseManager.KEY_STATUS, this.getStatus()); 
	    values.put(DatabaseManager.KEY_ROUND, this.getRound()); 
	    return values;
	}


}