package org.foi.fmoed;

import java.util.Locale;

import org.foi.fmoed.adapters.GroupAdapter;
import org.foi.fmoed.adapters.IdeaAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	/**
	 * The adapter that will provide
	 * fragments for each of the sections. 
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The viewPager that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Adapter that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new SectionFragment();
			Bundle args = new Bundle();
			args.putInt(SectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class SectionFragment extends Fragment {
		
		private int sectionNumber;
		private View rootView;
		private Context con;
		
		
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public SectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
			
			if (sectionNumber == 1){
				rootView = inflater.inflate(R.layout.groups_list, container, false);
				ListView groupList = (ListView) rootView.findViewById(R.id.group_list);
				GroupAdapter groupAdapter = new GroupAdapter(getActivity());
				groupList.setAdapter(groupAdapter);
			} else if (sectionNumber == 2){
				rootView = inflater.inflate(R.layout.create_group_layout, container, false);
				ImageButton createGroupButton = (ImageButton) rootView.findViewById(R.id.create_group_button);
				createGroupButton.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Toast.makeText(getActivity(), "Starting group. Create some ideas.", Toast.LENGTH_LONG).show();
						Intent ideasActivity= new Intent(getActivity(), IdeasActivity.class);
	    				startActivity(ideasActivity);
					}
				});
				/*
				ListView groupList = (ListView) rootView.findViewById(R.id.idea_list);
				IdeaAdapter ideaAdapter = new IdeaAdapter(getActivity());
				groupList.setAdapter(ideaAdapter);  */
			} else {
				rootView = inflater.inflate(R.layout.fragment_main, container, false);  
	            TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);  
	            dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));  
			}
			
			//TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
			//dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
			return rootView;
			/*
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
			dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
			return rootView;
			*/
		}
	}

    
}
