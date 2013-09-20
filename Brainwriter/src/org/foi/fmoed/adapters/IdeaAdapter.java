package org.foi.fmoed.adapters;

import org.foi.fmoed.R;
import org.foi.fmoed.activities.CameraActivity;
import org.foi.fmoed.activities.IdeasActivity;
import org.foi.fmoed.canvas.Drawer;
import org.foi.fmoed.utilities.CountDown;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class IdeaAdapter extends BaseAdapter {

	private Context con;
	private Button addTextBtn;
	private Button addCameraBtn;
	private Button addCanvasBtn;
	TextView boxGroupName;

	public static int tmpIdeaId;
	
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
		View v = convertView;
		LayoutInflater li;

		if (convertView == null) {
			li = LayoutInflater.from(con);
			if (position == 0) {

				v = li.inflate(R.layout.group_status, null);

				boxGroupName = (TextView) v.findViewById(R.id.group_name);
				boxGroupName.append(IdeasActivity.groupName);

				CountDown countDown = new CountDown(v.getContext());
				countDown.start();
			} else {
				v = li.inflate(R.layout.idea_item, null);
				addTextBtn = new Button(parent.getContext());
				addTextBtn = (Button) v.findViewById(R.id.text_button);
				addTextBtn.setOnClickListener(addTextBtnListener);
				addTextBtn.setTag(position);
				
				addCameraBtn = new Button(parent.getContext());
				addCameraBtn = (Button) v.findViewById(R.id.camera_button);
				addCameraBtn.setOnClickListener(addCameraBtnListener);
				addCameraBtn.setTag(position);
				
				addCanvasBtn = new Button(parent.getContext());
				addCanvasBtn = (Button) v.findViewById(R.id.canvas_button);
				addCanvasBtn.setOnClickListener(addCanvasBtnListener);
				addCanvasBtn.setTag(position);
			}
		}
		return v;
	}

	private OnClickListener addTextBtnListener = new OnClickListener() {

		@Override
		public void onClick(final View v) {
			Button btn = (Button) v;
			Context ctx = v.getContext();
			IdeasActivity ideasActivity = (IdeasActivity) ctx;
			ideasActivity.idea_id = (Integer) btn.getTag();
			ideasActivity.showEditDialog();
		}

	};

	private OnClickListener addCameraBtnListener = new OnClickListener() {

		@Override
		public void onClick(final View v) {
			Button btn = (Button) v;
			//Context ctx = v.getContext();
			tmpIdeaId = (Integer)btn.getTag();
			Intent camera = new Intent(con, CameraActivity.class);
			Context ctx = v.getContext();
			IdeasActivity ideasActivity = (IdeasActivity) ctx;
			ideasActivity.startActivity(camera);
		}

	};

	private OnClickListener addCanvasBtnListener = new OnClickListener() {

		@Override
		public void onClick(final View v) {
			Button btn = (Button) v;
			//Context ctx = v.getContext();
			tmpIdeaId = (Integer)btn.getTag();
			Intent canvas = new Intent(con, Drawer.class);
			Context ctx = v.getContext();
			IdeasActivity ideasActivity = (IdeasActivity) ctx;
			ideasActivity.startActivity(canvas);
		}

	};

}