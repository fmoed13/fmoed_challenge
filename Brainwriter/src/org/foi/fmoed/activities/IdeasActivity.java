package org.foi.fmoed.activities;
import java.util.ArrayList;
import java.util.List;

import org.foi.fmoed.R;
import org.foi.fmoed.adapters.IdeaAdapter;
import org.foi.fmoed.managers.DatabaseManager;
import org.foi.fmoed.managers.SessionManager;
import org.foi.fmoed.managers.SettingsManager;
import org.foi.fmoed.utilities.CountDown;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class IdeasActivity extends FragmentDialogActivity {
	
	private Button btnSubmitIdeas;
	private SessionManager sessionManager;
	private DatabaseManager dbManager;
	
	public ProgressDialog progressDialog;
	public SettingsManager settingsManager;
	public Boolean submitted;
	public static String groupName;
	public static String idea1Text;
	public static String idea2Text;
	public static String idea3Text;
	public static String idea1Image;
	public static String idea2Image;
	public static String idea3Image;
	
	public static void resetStaticVariables() {
		groupName = "";
		idea1Text = "";
		idea2Text = "";
		idea3Text = "";
		idea1Image = "";
		idea2Image = "";
		idea3Image = "";
	}
	
	public List<String> getIdeasTextsList(){
    	List<String> ideasTextList = new ArrayList<String>();
    	ideasTextList.add(idea1Text);
    	ideasTextList.add(idea2Text);
    	ideasTextList.add(idea3Text);
    	
    	return ideasTextList;
	}
	
	public List<String> getIdeasPictureList(){
    	List<String> ideasImageList = new ArrayList<String>();
    	ideasImageList.add(idea1Image);
    	ideasImageList.add(idea2Image);
    	ideasImageList.add(idea3Image);
    	
    	return ideasImageList;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.ideas_list);
		submitted = false;
		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(true);
		progressDialog.setMessage("Uploading ideas ...");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setIndeterminate(false);
		
		btnSubmitIdeas = (Button)findViewById(R.id.btn_submit_ideas);
		btnSubmitIdeas.setOnClickListener(onBtnSubmitIdeasClick);
		
		ListView groupList = (ListView) findViewById(R.id.idea_list);
		IdeaAdapter ideaAdapter = new IdeaAdapter(IdeasActivity.this);
		groupList.setAdapter(ideaAdapter);
		
		sessionManager = new SessionManager(this);
		settingsManager = new SettingsManager(this);
		dbManager = new DatabaseManager(this);
	}
	private OnClickListener onBtnSubmitIdeasClick = new OnClickListener() {

	    @Override
	    public void onClick(final View v) {
	    	submitted = true;
			progressDialog.show();
	    	sessionManager.submitIdea(groupName, settingsManager.getUserName(), getIdeasTextsList(), getIdeasPictureList(), progressDialog);
	    }
	};
}