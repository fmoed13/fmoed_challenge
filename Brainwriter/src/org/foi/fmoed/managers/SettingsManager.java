package org.foi.fmoed.managers;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsManager {
	
	SharedPreferences settings;
	SharedPreferences.Editor editor;

	/**
	 * Main constructor
	 * @param context Context of app
	 */
	public SettingsManager(Context context) {
		this.settings = context.getSharedPreferences("settings", Context.MODE_PRIVATE); 
		this.editor = this.settings.edit();
	}
	
	/**
	 * Method for saving username into SharedPreferences
	 * @param userName Username to save
	 */
	public void setUserName(String userName) {
		this.editor.putString("username", userName);
		this.editor.commit();
	}
	
	/**
	 * Method for getting username from SharedPreferences
	 * @return String username
	 */
	public String getUserName() {
		return this.settings.getString("username", "error");
	}
}
