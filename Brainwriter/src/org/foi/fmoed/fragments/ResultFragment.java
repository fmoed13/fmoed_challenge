package org.foi.fmoed.fragments;

import org.foi.fmoed.R;
import org.foi.fmoed.activities.ResultsActivity;
import org.foi.fmoed.adapters.GroupAdapter;
import org.foi.fmoed.adapters.ResultAdapter;
import org.foi.fmoed.managers.DatabaseManager;
import org.foi.fmoed.managers.SessionManager;
import org.foi.fmoed.managers.SettingsManager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.io.IOException;

public class ResultFragment extends Fragment {

	private int resultNumber;
	private View rootView;
	private Context con;
    public String groupName;
    private Button searchButton;
    public static JsonObject resultStorage;

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

            SettingsManager settings = new SettingsManager(getActivity());
            final String username = settings.getUserName();

            if (username.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter username in settings.", Toast.LENGTH_LONG).show();
            }
            else {
                SessionManager sm = new SessionManager(getActivity());
                sm.receiveIdea(groupName, username);
                new WaitResponseAsync(getActivity()).execute();
            }

        } else {
            rootView = inflater.inflate(R.layout.group_results_list, container,
                    false);
            ListView results = (ListView) rootView.findViewById(R.id.group_results_list);
            ResultAdapter resultAdapter = new ResultAdapter(getActivity());
            results.setAdapter(resultAdapter);
        }
		return rootView;
	}

    private class WaitResponseAsync extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        public WaitResponseAsync(Context con) {
            progressDialog = new ProgressDialog(con);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Retrieving inspiration, please wait ...");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        protected String doInBackground(String... params) {

            while (true) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if ( ResultFragment.resultStorage != null)
                    break;

            }

            return "ok";
        }

        protected void onPostExecute(String result) {

            if (result.equals("ok")) {
                // TODO: fill list with data
            }
            else {

            }

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }
}
