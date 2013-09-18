package org.foi.fmoed;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;

import org.foi.managers.*;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class MainActivity extends Activity {

	SessionManager sessionManager;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	sessionManager = new SessionManager();
    	sessionManager.startSession("testGroup", this);
    	sessionManager.checkSession("testGroup", this);
    	sessionManager.submitIdea("testGroup", "ivpusic", this);
    	sessionManager.receiveIdea("testGroup", "ivpusic", this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
