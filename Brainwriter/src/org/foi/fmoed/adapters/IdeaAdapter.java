package org.foi.fmoed.adapters;

import org.foi.fmoed.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class IdeaAdapter extends BaseAdapter{
	
	private Context con;
	
	public IdeaAdapter(Context c) {
		this.con = c;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 4;
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
			if (position == 0){
				li = LayoutInflater.from(con);
				v = li.inflate(R.layout.group_status, null);
			} else {
				li = LayoutInflater.from(con);
				v = li.inflate(R.layout.idea_item, null);
			}
		}
		return v;
	}

}
