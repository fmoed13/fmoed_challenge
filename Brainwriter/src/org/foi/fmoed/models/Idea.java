package org.foi.fmoed.models;

import org.foi.fmoed.managers.DatabaseManager;

import android.content.ContentValues;

public class Idea {

	private int id;
	private String authorName;
	private String path;
	private int groupID;

	public Idea() {
		
	}

	public Idea(String authorName, String path, int groupID) {
		super();
		this.authorName = authorName;
		this.path = path;
		this.groupID = groupID;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getAuthorName() {
		return authorName;
	}
	
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public int getGroupID() {
		return groupID;
	}
	
	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}
	
	public ContentValues getValues() {
		ContentValues values = new ContentValues();
	    values.put(DatabaseManager.KEY_AUTHOR_NAME, this.getAuthorName());
	    values.put(DatabaseManager.KEY_PATH, this.getPath()); 
	    values.put(DatabaseManager.FK_IDEA_KEY, this.getGroupID()); 
	    return values;
	}
	
}