package org.foi.fmoed.adapters;

import org.foi.fmoed.R;
import org.foi.fmoed.activities.FragmentDialogActivity;
import org.foi.fmoed.activities.IdeasActivity;
import org.foi.fmoed.fragments.ModalIdeaTextFragment;
import org.foi.fmoed.fragments.ModalIdeaTextFragment.EditNameDialogListener;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;



public class IdeaAdapter extends BaseAdapter {
	
	private Context con;
	private Button addTextBtn;
	private Button addCanvasImageBtn;
	private Button addPhotoImageBtn;
	
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
				
				addTextBtn = (Button)v.findViewById(R.id.text_button);
				addTextBtn.setOnClickListener(addTextBtnListener);
			}
		}
		return v;
	}

	private OnClickListener addTextBtnListener = new OnClickListener() {

	    @Override
	    public void onClick(final View v) {
	    	Context ctx = v.getContext();
	    	IdeasActivity ideasActivity = (IdeasActivity)ctx;
	    	ideasActivity.showEditDialog();
	    }
	};
	
}
