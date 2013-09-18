package org.foi.fmoed.adapters;

import org.foi.fmoed.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class GroupAdapter extends BaseAdapter{
	
	private Context con;
	
	public GroupAdapter(Context c) {
		this.con = c;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 12;
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
		final int pos = position;
		View v = convertView;
		LayoutInflater li;
		if (convertView == null){
				li = LayoutInflater.from(con);
				v = li.inflate(R.layout.group_item, null);
		}
		return v;
	}

}
