package com.example.trains;

import java.util.ArrayList;

import com.example.trains.widgets.TravelTimeInfoLayout;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DisplayTimes extends Activity  {

	public static final String TIMES_NAME = "timesString";

	@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.display_times);
	        ArrayList<String[]> allTimes = (ArrayList<String[]>) getIntent().getSerializableExtra(DisplayTimes.TIMES_NAME);
	        TableLayout timesLayout = (TableLayout)
        			findViewById(R.id.displaytimeslayout);
	        
	        //Create title row.
	        TableRow titleRow = new TableRow(this);
	        titleRow.addView(createTitleTextView(getString(R.string.arriving)));
	        titleRow.addView(createTitleTextView(getString(R.string.arriving)));
	        titleRow.addView(createTitleTextView(getString(R.string.transportType)));
	        timesLayout.addView(titleRow);
	        
	        //Create individual rows.
	        for (String[] time : allTimes) {
	        	TableRow row = new TableRow(this);
	        	row.addView(createTextView(time[NotesDbAdapter.JINFO_DEPARTURE]));
	        	row.addView(createTextView(time[NotesDbAdapter.JINFO_ARRIVAL]));
	        	row.addView(createTextView(time[NotesDbAdapter.JINFO_TRAVELTYPE]));
	        	timesLayout.addView(row);
	        	
	        	TravelInfoWrapper travelInfo = new TravelInfoWrapper
        				(time[NotesDbAdapter.JINFO_TRAVELTYPE],
        				time[NotesDbAdapter.JINFO_DEPARTURE],
        				time[NotesDbAdapter.JINFO_ARRIVAL]);
	        	timesLayout.addView(new TravelTimeInfoLayout(this, travelInfo));
	        }
	        
	    }
	
		private TextView createTextView(String str) {
			TextView tv = new TextView(this);
			tv.setGravity(Gravity.CENTER);
			tv.setText(str);
			return tv;
		}
		
		private TextView createTitleTextView(String str) {
			TextView title = createTextView(str);
			title.setTypeface(null, Typeface.BOLD);
			return title;
		}

	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.display_times, menu);
	        return true;
	    }
	
}
