package org.foi.fmoed.activities;

import org.foi.fmoed.R;
import org.foi.fmoed.adapters.IdeaAdapter;

import android.os.Bundle;
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
