package com.example.trains;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends Activity {

	private NotesDbAdapter database;
	private static SharedPreferences settings;
	public static final String PREFERENCES = "preferences";
	public static final String DB_CREATED = "dbcreated";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = getSharedPreferences(PREFERENCES,0);
    	this.database = new NotesDbAdapter(this);
		this.database.open();
		Log.d("DBCreated?", "" + settings.getBoolean(DB_CREATED, false));
		if (!settings.getBoolean(DB_CREATED, false)) {
			this.database.setUpDatabase();
			SharedPreferences.Editor editor = settings.edit();
			editor.putBoolean(DB_CREATED, true);
			editor.commit();
		}
		generalSetUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void generalSetUp() {
    	setUplayout();
		startPointSetUp();
		endPointSetUp();
		setUpTimes();
		setUpDates();
		setUpButtons();
		setReturnEnabled(false);
	}
    
    public void onReturnCheckboxClicked(View view) {
    	CheckBox checkBox = (CheckBox)findViewById(R.id.returnCheckbox);
    	boolean checked = checkBox.isChecked();
    	setReturnEnabled(checked);
    }
    
    public void setReturnEnabled(boolean enabled) {
    	LinearLayout returnLayout = (LinearLayout) findViewById(R.id.returnLayout);
    	for (int i = 0; i < returnLayout.getChildCount(); i++) {
    		View child = returnLayout.getChildAt(i);
    		child.setEnabled(enabled);
    		final GradientDrawable backgroundShape;
    		if (enabled) {
    			backgroundShape = (GradientDrawable) 
    					getResources().getDrawable(R.drawable.button_shape);
    		} else {
    			backgroundShape = (GradientDrawable) 
    					getResources().getDrawable(R.drawable.button_disabled);
    		}
    		setViewBackground(backgroundShape, child);
    	}
    }
    
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setViewBackground(GradientDrawable background, View view) {
    	view.setBackground(background);
    }
    
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setUpButtons() {
//    	final Button goButton = (Button)findViewById(R.id.goButton);
//    	goButton.setOnTouchListener(new OnTouchListener() {
//    	final GradientDrawable unclickedColor = 
//    			(GradientDrawable) getResources().getDrawable(R.drawable.button_shape);
//    	final GradientDrawable clickedColor = 
//    			(GradientDrawable) getResources().getDrawable(R.drawable.button_clicked);
//			@Override
//			public boolean onTouch(View arg0, MotionEvent event) {
//				if (event.getAction() == MotionEvent.ACTION_DOWN) {
//		            goButton.setBackground(clickedColor);
//		    }
//		    else if (event.getAction() == MotionEvent.ACTION_UP) {
//		    	goButton.setBackground(unclickedColor);
//		    	goClicked(goButton);
//		    }
//
//		    return true;
//			}
//    		});
    }
    
    public void setUpAButton(int buttonId, final Method buttonMethod) {
//    	final Button goButton = (Button)findViewById(buttonId);
//    	goButton.setOnTouchListener(new OnTouchListener() {
//    	final GradientDrawable unclickedColor = 
//    			(GradientDrawable) getResources().getDrawable(R.drawable.button_shape);
//    	final GradientDrawable clickedColor = 
//    			(GradientDrawable) getResources().getDrawable(R.drawable.button_clicked);
//			@Override
//			public boolean onTouch(View arg0, MotionEvent event) {
//				if (event.getAction() == MotionEvent.ACTION_DOWN) {
//					setViewBackground(clickedColor, goButton);
//		    }
//		    else if (event.getAction() == MotionEvent.ACTION_UP) {
//		    	setViewBackground(unclickedColor, goButton);
//		    	try {
//					buttonMethod.invoke(null, goButton);
//				} catch (IllegalArgumentException e) {
//					e.printStackTrace();
//				} catch (IllegalAccessException e) {
//					e.printStackTrace();
//				} catch (InvocationTargetException e) {
//					e.printStackTrace();
//				}
//		    }
//
//		    return true;
//			}
//    	});
    }
	
	public void startPointSetUp() {
		int startSpinnerId = R.id.startSpinner;
		List<String> destinations = new ArrayList<String>();
		destinations.addAll(database.getDestinations());
		setUpSpinner(destinations, startSpinnerId);
		addSpinnerChangeListener(startSpinnerId, R.id.endSpinner);
	}
	
	public void endPointSetUp() {
//		int endSpinnerId = R.id.endSpinner;
//		List<String> endPoints = new ArrayList<String>();
//		endPoints.addAll(database.getEndPoints());
//		setUpSpinner(endPoints, endSpinnerId);
//		Spinner endSpinner = (Spinner) findViewById(R.id.endSpinner);
//		endSpinner.setEnabled(false);
//		endSpinner.setClickable(false);
	}
	
	public void addSpinnerChangeListener(final int spinnerId, final int otherSpinner) {
		final Spinner spinner = (Spinner) findViewById(spinnerId);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
	    {
	        public void onItemSelected(AdapterView<?> arg0, View v, int position, long id)
	        {
	        	String startLocation = (String) spinner.getSelectedItem();
	        	List<String> possibleEndPoints = new ArrayList<String>();
	        	possibleEndPoints.addAll(database.getEndPointsFromStartPoint(startLocation));
	        	setUpSpinner(possibleEndPoints, otherSpinner);
	            Log.v("routes", "route selected");
	        }

	        public void onNothingSelected(AdapterView<?> arg0)
	        {
	            Log.v("routes", "nothing selected");
	        }
	    });
		
	}
	
	public void setUpTimes() {
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		TextView firstTime = (TextView)findViewById(R.id.timeText);
		firstTime.setText(hour + ":" + minute);
		
		TextView returnTime = (TextView)findViewById(R.id.timeReturnText);
		returnTime.setText(hour + ":" + minute);
	}
	
	public void setUpDates() {
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_MONTH);
		int month = c.get(Calendar.MONTH);
		int year = c.get(Calendar.YEAR);
		String monthString = new DateFormatSymbols().getMonths()[month];
		
		TextView firstMonth = (TextView)findViewById(R.id.dateText);
		firstMonth.setText(day + " " + monthString + " " + year);
		
		TextView returnMonth = (TextView)findViewById(R.id.dateReturnText);
		returnMonth.setText(day + " " + monthString + " " + year);
	}
	
	public void setUpSpinner(List<String> contents, int spinnerID) {
		Spinner spinner = (Spinner)findViewById(spinnerID);
		String[] strings = new String[contents.size()];
		contents.toArray(strings);
		ArrayAdapter adapter = new ArrayAdapter(this,
		        android.R.layout.simple_spinner_item, strings);
		spinner.setAdapter(adapter);
//		spinner.setEnabled(true);
//		spinner.setClickable(true);
	}
	
	public void setUplayout() {
		LinearLayout layout = (LinearLayout)findViewById(R.id.mainLayout);
		
		for (int i = 0; i < layout.getChildCount(); i++) {
			View v = layout.getChildAt(i);
			int padding = 25;
			v.setPadding(v.getPaddingLeft(), padding,
					v.getPaddingRight(), padding);
		}
	}
	
	public void getUserToEnterStartDate(View view) {
		getUserToEnterDate(R.id.dateText);
	}
	
	public void getUserToEnterReturnDate(View view) {
		getUserToEnterDate(R.id.dateReturnText);
	}
	
	public void getUserToEnterDate(final int componentID) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.enterDate);
		final DatePicker datePicker = new DatePicker(this);
		builder.setView(datePicker);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {  
	        public void onClick(DialogInterface dialog, int whichButton) {
	        	int day = datePicker.getDayOfMonth();
	        	int month = datePicker.getMonth() + 1;
	        	String monthString = new DateFormatSymbols().getMonths()[month-1];
	        	int year = datePicker.getYear();
	        	String newDate = day + " " + monthString + " " + year;
	        	setTextOnComponent(newDate, componentID);
	            return;                  
	           }  
	         });
		
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				return;
				
			}
		});
		builder.show();
	}
	
	public void getUserToEnterStartTime(View view) {
		getUserToEnterTime(R.id.timeText);
	}
	
	public void getUserToEnterReturnTime(View view) {
		getUserToEnterTime(R.id.timeReturnText);
	}
	
	public void getUserToEnterTime(final int componentId) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.enterTime);
		final TimePicker timePicker = new TimePicker(this);
		builder.setView(timePicker);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {  
	        public void onClick(DialogInterface dialog, int whichButton) {
	        	int hour = timePicker.getCurrentHour();
	        	int minute = timePicker.getCurrentMinute();
	        	String newTime = hour + ":" + minute;
	        	setTextOnComponent(newTime, componentId);
	            return;                  
	           }  
	         });
		
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				return;
			}
		});
		builder.show();
	}
	
	public void setTextOnComponent(String text, int componentId) {
		TextView component = (TextView)findViewById(componentId);
		component.setText(text);
	}
	
	public void goClicked(View view) {
		Spinner startPointSpinner = (Spinner) findViewById(R.id.startSpinner);
		String startPoint = (String) startPointSpinner.getSelectedItem();
		
		Spinner endPointSpinner = (Spinner) findViewById(R.id.endSpinner);
		String endPoint = (String) endPointSpinner.getSelectedItem();
		
		Button dateButton = (Button)findViewById(R.id.dateText);
		String date = dateButton.getText().toString();
		
		Button timeButton = (Button)findViewById(R.id.timeText);
		String time = timeButton.getText().toString();
		
		CheckBox returnCheckBox = (CheckBox)findViewById(R.id.returnCheckbox);
		
		String returnDate = null;
		String returnTime = null;
				
		if (returnCheckBox.isChecked()) {
			Button returnDateButton = (Button)findViewById(R.id.dateReturnText);
			returnDate = returnDateButton.getText().toString();
			Button returnTimeButton = (Button)findViewById(R.id.timeReturnText);
			returnTime = returnTimeButton.getText().toString();
		}
		
		ArrayList<String[]> times = 
				this.database.getJourneyInfo(startPoint, endPoint, date, time,
						returnCheckBox.isChecked(), returnDate, returnTime);
		if (times.isEmpty()) {
			showNoTimesAlert();
		} else {
			showTimes(times);
		}
	}
	
	public void showNoTimesAlert() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		// set title
		alertDialogBuilder.setTitle(getString(R.string.notimesavaliabletitle));
		// set dialog message
		alertDialogBuilder
				.setMessage(getString(R.string.notimesavaliabletext))
				.setCancelable(true)
				.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// if this button is clicked, close
						// current activity
						dialog.cancel();
					}
				  });
//				.setNegativeButton("No",new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog,int id) {
//						// if this button is clicked, just close
//						// the dialog box and do nothing
//						dialog.cancel();
//					}
//				});
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
	}
	
	public void showTimes(ArrayList<String[]> times) {
		Intent timesIntent = new Intent(MainActivity.this, DisplayTimes.class);
		timesIntent.putExtra(DisplayTimes.TIMES_NAME,times);
		startActivity(timesIntent);
	}
	
	public static SharedPreferences getPreferences() {
		return settings;
	}
	
	
    
}
