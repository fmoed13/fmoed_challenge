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
import android.widget.ListView;

/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 */
public class ResultFragment extends Fragment {

	private int resultNumber;
	private View rootView;
	private Context con;

	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	public static final String ARG_RESULT_NUMBER = "result_number";

	public ResultFragment() {
	}
	
	/**
	 * Sets fragments for each round. 
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		resultNumber = getArguments().getInt(ARG_RESULT_NUMBER);

		rootView = inflater.inflate(R.layout.group_results_list, container,
				false);
		ListView results = (ListView) rootView.findViewById(R.id.group_results_list);
		ResultAdapter resultAdapter = new ResultAdapter(getActivity());
		results.setAdapter(resultAdapter);
		return rootView;
	}
}
