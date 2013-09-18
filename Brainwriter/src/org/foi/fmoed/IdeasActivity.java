package org.foi.fmoed;

import org.foi.fmoed.adapters.IdeaAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class IdeasActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ideas_list);
		
//		ListView groupList = (ListView) rootView.findViewById(R.id.idea_list);
//		IdeaAdapter ideaAdapter = new IdeaAdapter(getActivity());
//		groupList.setAdapter(ideaAdapter);
	}
	
}
