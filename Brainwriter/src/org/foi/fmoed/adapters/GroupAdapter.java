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
import android.widget.TextView;
import android.widget.Toast;

public class GroupAdapter extends BaseAdapter{
	
	private Context con;
	private DatabaseManager dbManager;
	private List<Group> groupList;
	public static HashMap<String, TextView> txtTimersMap;
	private int indexGroupList;
	
	public void generateGroupFixtures() {
		Group gr = new Group("first", "finished", "6");
		this.dbManager.addRecord(gr.getValues(), DatabaseManager.TABLE_GROUP);
		gr = new Group("second", "not started", "0");
		this.dbManager.addRecord(gr.getValues(), DatabaseManager.TABLE_GROUP);
		gr = new Group("thirs", "in progress", "2");
		this.dbManager.addRecord(gr.getValues(), DatabaseManager.TABLE_GROUP);
	}
	
	public GroupAdapter(Context c) {
		this.con = c;
		this.indexGroupList = 0;
		this.dbManager = new DatabaseManager(c);
		txtTimersMap = new HashMap<String, TextView>();
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
		final TextView textName, textStatus, textTimer;
		LayoutInflater li;
		ImageButton results, addIdea;
		if (convertView == null){
				li = LayoutInflater.from(con);
				v = li.inflate(R.layout.group_item, null);
				textName = (TextView)v.findViewById(R.id.groupName);
				textStatus = (TextView)v.findViewById(R.id.status);
				textTimer = (TextView)v.findViewById(R.id.timer);
				results = (ImageButton)v.findViewById(R.id.results);
				addIdea = (ImageButton)v.findViewById(R.id.bulb);
				
				if (groupList.size() > indexGroupList) {
					group = this.groupList.get(indexGroupList++);
					textName.setText(group.getName());
					textStatus.setText(group.getStatus());
					textTimer.setText(group.getRound());
					txtTimersMap.put(group.getName(), textStatus);
				}
				
				results.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent resultsActivity = new Intent(con, ResultsActivity.class);
                        resultsActivity.putExtra("group_name", textName.getText().toString());
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