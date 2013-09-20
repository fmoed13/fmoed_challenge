package org.foi.fmoed.fragments;

import org.foi.fmoed.R;
import org.foi.fmoed.activities.ResultsActivity;
import org.foi.fmoed.adapters.GroupAdapter;
import org.foi.fmoed.adapters.ResultAdapter;
import org.foi.fmoed.managers.DatabaseManager;
import org.foi.fmoed.managers.SessionManager;
import org.foi.fmoed.managers.SettingsManager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ResultFragment extends Fragment {

	private int resultNumber;
	private View rootView;
	private Context con;
    public String groupName;
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_RESULT_NUMBER = "result_number";

	public ResultFragment() {
	}

    public ResultFragment(String groupName) {
        this.groupName = groupName;
    }
	/**
	 * Sets fragments for each round. 
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		resultNumber = getArguments().getInt(ARG_RESULT_NUMBER);
        if (resultNumber == 1){
            rootView = inflater.inflate(R.layout.search_layout, container,
                    false);
            TextView groupTextName = (TextView) rootView.findViewById(R.id.groupNameText);
            groupTextName.setText("Group: " + this.groupName);
            EditText  username = (EditText) rootView.findViewById(R.id.username);
            username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "Call search API/", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            rootView = inflater.inflate(R.layout.group_results_list, container,
                    false);
            ListView results = (ListView) rootView.findViewById(R.id.group_results_list);
            ResultAdapter resultAdapter = new ResultAdapter(getActivity());
            results.setAdapter(resultAdapter);
        }
		return rootView;
	}
}
