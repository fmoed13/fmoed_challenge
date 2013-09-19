package org.foi.fmoed.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.foi.fmoed.R;
import org.foi.fmoed.activities.ResultsActivity;
import org.foi.fmoed.managers.DatabaseManager;
import org.foi.fmoed.models.Group;

import java.util.List;

public class GroupAdapter extends BaseAdapter{
	
	private Context con;
	private DatabaseManager dbManager;
	private List<Group> groupList;
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
		final TextView textName, textStatus, textRound;
		LayoutInflater li;
		ImageButton results, addIdea;
		
		if (convertView == null){
				li = LayoutInflater.from(con);
				v = li.inflate(R.layout.group_item, null);
				textName = (TextView)v.findViewById(R.id.groupName);
				textStatus = (TextView)v.findViewById(R.id.status);
				textRound = (TextView)v.findViewById(R.id.round);
				results = (ImageButton)v.findViewById(R.id.results);
				addIdea = (ImageButton)v.findViewById(R.id.bulb);
				
				
				if (groupList.size() > indexGroupList) {
					group = this.groupList.get(indexGroupList++);
					textName.setText(group.getName());
					textStatus.setText(group.getStatus());
					textRound.setText(group.getRound());
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