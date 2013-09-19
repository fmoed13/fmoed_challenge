package org.foi.fmoed.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

import org.foi.fmoed.R;
import org.foi.fmoed.fragments.ResultFragment;
import org.foi.fmoed.managers.SessionManager;
import org.foi.fmoed.managers.SettingsManager;

import java.util.Locale;

public class ResultsActivity extends FragmentActivity {

    String groupName;
	RoundsPagerAdapter mRoundsPagerAdapter;
	SessionManager sessionManager;
	/**
	 * The viewPager that will host the section contents.
	 */
	ViewPager mViewPager;
	
	SettingsManager settingsManager;

	public ResultsActivity() {
		// TODO Auto-generated constructor stub
		sessionManager = null;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_results);
        Bundle groupNameBundle = getIntent().getExtras();
        groupName = groupNameBundle.getString("group_name");

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mRoundsPagerAdapter = new RoundsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mRoundsPagerAdapter);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Adapter that returns a fragment corresponding to one of the
	 * sections/tabs/pages.
	 */
	public class RoundsPagerAdapter extends FragmentPagerAdapter {

		public RoundsPagerAdapter(FragmentManager fm) {
			super(fm);
		}
		
		@Override
		public Fragment getItem(int position) {

			// getItem is called to instantiate the fragment for the given page.
			Fragment fragment = new ResultFragment(groupName);
			Bundle args = new Bundle();
			args.putInt(ResultFragment.ARG_RESULT_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			//TODO: number of rounds must be returned here
			return 6;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			return "ROUND " + (position + 1);
		}
	}

	
}
