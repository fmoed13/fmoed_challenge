package org.foi.fmoed.managers;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingsManager {
	
	SharedPreferences settings;
	SharedPreferences.Editor editor;

	public SettingsManager(Context context) {
		this.settings = context.getSharedPreferences("settings", Context.MODE_PRIVATE); 
		this.editor = this.settings.edit();
	}
	
	public void setUserName(String userName) {
		this.editor.putString("username", userName);
		this.editor.commit();
	}
	
	public String getUserName() {
		return this.settings.getString("username", "error");
	}
}
