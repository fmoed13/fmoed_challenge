package org.foi.fmoed.models;

import org.foi.fmoed.managers.DatabaseManager;
import android.content.ContentValues;

public class Group {
	
	private int id;
	private String name;
	
	public Group() {
		
	}
	
	public Group(String name) {
		super();
		this.name = name;
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
	
	public ContentValues getValues() {
		ContentValues values = new ContentValues();
	    values.put(DatabaseManager.KEY_NAME, this.getName()); 
	    return values;
	}
}
