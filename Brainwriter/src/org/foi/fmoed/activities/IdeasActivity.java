package org.foi.fmoed.activities;

import org.foi.fmoed.R;
import org.foi.fmoed.adapters.IdeaAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.sax.RootElement;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class IdeasActivity extends FragmentDialogActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.ideas_list);
		
		ListView groupList = (ListView) findViewById(R.id.idea_list);
		IdeaAdapter ideaAdapter = new IdeaAdapter(IdeasActivity.this);
		groupList.setAdapter(ideaAdapter);
	}
}
