package org.foi.fmoed.models;

public class Idea {

	private String id;
	private String authorName;
	private String path;
	private String groupID;


	public Idea(String id, String authorName, String path, String groupID) {
		super();
		this.id = id;
		this.authorName = authorName;
		this.path = path;
		this.groupID = groupID;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
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
	
	public String getGroupID() {
		return groupID;
	}
	
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	
}
