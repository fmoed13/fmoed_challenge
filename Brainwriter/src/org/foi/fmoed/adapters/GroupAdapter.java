package org.foi.fmoed.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.foi.fmoed.R;
import org.foi.fmoed.activities.IdeasActivity;
import org.foi.fmoed.activities.ResultsActivity;
import org.foi.fmoed.managers.DatabaseManager;
import org.foi.fmoed.models.Group;
import org.w3c.dom.Text;

import android.content.Context;
import android.content.Intent;
import android.sax.RootElement;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GroupAdapter extends BaseAdapter{
	
	private Context con;
	private DatabaseManager dbManager;
	private List<Group> groupList;
	public static HashMap<String, TextView> txtTimersMap;
	public static HashMap<String, TextView> txtRoundMap;
	public static HashMap<String, ImageView> imgResultsMap;
	
	private int indexGroupList;
	
	public void generateGroupFixtures() {
		Group gr = new Group("first", "finished", "0");
		this.dbManager.addRecord(gr.getValues(), DatabaseManager.TABLE_GROUP);
		gr = new Group("second", "not started", "0");
		this.dbManager.addRecord(gr.getValues(), DatabaseManager.TABLE_GROUP);
		gr = new Group("thirs", "in progress", "0");
		this.dbManager.addRecord(gr.getValues(), DatabaseManager.TABLE_GROUP);
	}
	
	public GroupAdapter(Context c) {
		this.con = c;
		this.indexGroupList = 0;
		this.dbManager = new DatabaseManager(c);
		txtTimersMap = new HashMap<String, TextView>();
		txtRoundMap = new HashMap<String, TextView>();
		imgResultsMap = new HashMap<String, ImageView>();
		
		if(this.dbManager.getRecordsCount(DatabaseManager.TABLE_GROUP) <= 0){
			this.generateGroupFixtures();
		}
		
		this.groupList = this.dbManager.getGroupRecords();
	}
	
	@Override
	public int getCount() {
		return this.dbManager.getRecordsCount(DatabaseManager.TABLE_GROUP);
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		Group group;
		TextView textName, textStatus, textRound;
		LayoutInflater li;
		ImageButton results, addIdea;
		if (convertView == null){
				li = LayoutInflater.from(con);
				v = li.inflate(R.layout.group_item, null);
				textName = (TextView)v.findViewById(R.id.groupName);
				textStatus = (TextView)v.findViewById(R.id.status);
				textRound = (TextView)v.findViewById(R.id.round);
				results = (ImageButton)v.findViewById(R.id.results);
				results.setVisibility(View.INVISIBLE);
				addIdea = (ImageButton)v.findViewById(R.id.bulb);
				
				if (groupList.size() > indexGroupList) {
					group = this.groupList.get(indexGroupList++);
					textName.setText(group.getName());
					textStatus.setText(group.getStatus());
					textRound.setText("Round: " + group.getRound());
					
					txtTimersMap.put(group.getName(), textStatus);
					txtRoundMap.put(group.getName(), textRound);
					imgResultsMap.put(group.getName(), results);
				}
				
				results.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent resultsActivity = new Intent(con, ResultsActivity.class);
						con.startActivity(resultsActivity);
					}
				});
				
				addIdea.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						Toast.makeText(con, "Start idea activity", Toast.LENGTH_SHORT).show();
					}
				});
		}
		return v;
	}

}