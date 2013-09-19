package org.foi.fmoed.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.foi.fmoed.R;
import org.foi.fmoed.activities.IdeasActivity;
import org.foi.fmoed.adapters.GroupAdapter;
import org.foi.fmoed.managers.DatabaseManager;
import org.foi.fmoed.managers.SessionManager;
import org.foi.fmoed.managers.SettingsManager;

/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 */
public class SectionFragment extends Fragment {

	private int sectionNumber;
	private View rootView;
	private Context con;

	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";

	SessionManager sessionManager;
	SettingsManager settingsManager;
	DatabaseManager databaseManager;

	public SectionFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (sessionManager == null) {
			sessionManager = new SessionManager(getActivity());
		}

		sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);

		if (sectionNumber == 1) {
			rootView = inflater.inflate(R.layout.groups_list, container,
					false);
			ListView groupList = (ListView) rootView
					.findViewById(R.id.group_list);
			GroupAdapter groupAdapter = new GroupAdapter(getActivity());
			groupList.setAdapter(groupAdapter);
		} else if (sectionNumber == 2) {
			rootView = inflater.inflate(R.layout.create_group_layout,
					container, false);
			
			ImageButton createGroupButton = (ImageButton) rootView
					.findViewById(R.id.create_group_button);
			
			createGroupButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					EditText groupNameInput = (EditText) rootView.findViewById(R.id.editText1);
					sessionManager.startSession(groupNameInput.getText().toString());
					Intent ideasActivity = new Intent(getActivity(), IdeasActivity.class);
					startActivity(ideasActivity);
				}
			});

		} else {
			rootView = inflater.inflate(R.layout.settings, container, false);
			TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
			dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
		}

		return rootView;
	}
}
