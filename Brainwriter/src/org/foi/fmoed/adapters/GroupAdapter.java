package org.foi.fmoed.adapters;

import java.util.List;

import org.foi.fmoed.R;
import org.foi.fmoed.managers.DatabaseManager;
import org.foi.fmoed.models.Group;
import org.w3c.dom.Text;

import android.content.Context;
import android.sax.RootElement;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GroupAdapter extends BaseAdapter{
	
	private Context con;
	private DatabaseManager dbManager;
	private List<Group> groupList;
	private int indexGroupList;
	
	public GroupAdapter(Context c) {
		this.con = c;
		this.indexGroupList = 0;
		this.dbManager = new DatabaseManager(c);
		this.groupList = this.dbManager.getGroupRecords();
		Group gr = new Group("new_group", "finished", "2");
		this.dbManager.addRecord(gr.getValues(), DatabaseManager.TABLE_GROUP);
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
		
		if (convertView == null){
				li = LayoutInflater.from(con);
				v = li.inflate(R.layout.group_item, null);
				textName = (TextView)v.findViewById(R.id.groupName);
				textStatus = (TextView)v.findViewById(R.id.status);
				textRound = (TextView)v.findViewById(R.id.round);
				
				if (groupList.size() > indexGroupList) {
					group = this.groupList.get(indexGroupList++);
					textName.setText(group.getName());
					textStatus.setText(group.getStatus());
					textRound.setText(group.getRound());
				}
		}
		return v;
	}

}
