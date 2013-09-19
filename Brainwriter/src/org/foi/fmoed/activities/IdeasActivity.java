package org.foi.fmoed.activities;

import org.foi.fmoed.R;
import org.foi.fmoed.canvas.Drawer;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class IdeasActivity {
	
	Button canvas_button = (Button) idea_list.findViewById(R.id.draw);
	canvas_button.setOnClickListener(new View.OnClickListener() {
		public void onClick(View v) {
			Intent intent = new Intent(v.getContext(), Drawer.class);
			startActivityForResult(intent, 0);
		};
	});

}
