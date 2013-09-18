package org.foi.fmoed;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;

import org.foi.fmoed.managers.*;
import org.foi.fmoed.models.Group;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class MainActivity extends Activity {

	SessionManager sessionManager;
	SettingsManager settingsManager;
	DatabaseManager databaseManager;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    	sessionManager = new SessionManager(MainActivity.this);
    	settingsManager = new SettingsManager(MainActivity.this);

    	
    	sessionManager.startSession("testGroup");
    	sessionManager.checkSession("testGroup");
    	sessionManager.submitIdea("testGroup", "ivpusic");
    	sessionManager.receiveIdea("testGroup", "ivpusic");
    	
    	settingsManager.setUserName("some_user");
    	settingsManager.getUserName();
    	databaseManager = new DatabaseManager(MainActivity.this);
    	Group group = new Group("test group");
    	databaseManager.addRecord(group.getValues(), DatabaseManager.TABLE_GROUP);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
