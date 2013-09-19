package org.foi.fmoed.adapters;

import java.util.List;

import org.foi.fmoed.R;
import org.foi.fmoed.activities.ResultsActivity;
import org.foi.fmoed.managers.DatabaseManager;
import org.foi.fmoed.models.Group;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ResultAdapter extends BaseAdapter{
	
	private Context con;
	private DatabaseManager dbManager;
	private List<Group> groupList;
	private int indexGroupList;
	
	public ResultAdapter(Context c) {
		this.con = c;
	}
	
	@Override
	public int getCount() {
		// TODO: return number of users in given round
		return 5;
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
	
	/**
	 * Inflates layout for every user in given round. 
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		LayoutInflater li;
		
		if (convertView == null){
				li = LayoutInflater.from(con);
				v = li.inflate(R.layout.group_result_item, null);
				TextView user = (TextView) v.findViewById(R.id.user);
				user.setText("User");
				// TODO: add users username
				// TODO: inflate imageviews/layouts/textviews or whatever he posted
		}
		return v;
	}


}
