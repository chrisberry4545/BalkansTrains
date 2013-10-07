package com.example.trains;

import android.content.ContentValues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;
import java.util.ArrayList;

public class NotesDbAdapter {

	private static final String CSV_FILE_NAME = "EuroTrains.csv";
	
    private static final int DATABASE_VERSION = 2;

    public static final int JINFO_DEPARTURE = 0;
    public static final int JINFO_ARRIVAL = 1;
    public static final int JINFO_TRAVELTYPE = 2;
    public static final int JINFO_VARIABLE_COUNT = 3;
    
    private static final String AND = " AND ";
    private final Context mCtx;
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DATABASE_NAME = "data";
    private static final String DATABASE_TABLE = "trains";

    public static final String KEY_ROWID = "id";
    public static final String KEY_START = "start";
    public static final String KEY_END = "end";
    public static final String KEY_TRANSPORT_TYPE = "transport_type";
    public static final String KEY_DEPARTURE_TIME = "depature_time";
    public static final String KEY_ARRIVAL_TIME = "arrival_time";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_RUNNING_DAYS = "running_days";
    public static final String KEY_ORI_PRICE = "price_original_currency";
    public static final String KEY_GBP_PRICE = "price_gbp";
    public static final String KEY_ORI_PRICE_FIRST = "price_ori_first";
    public static final String KEY_GBP_PRICE_FIRST = "price_gbp_first";
    public static final String KEY_ORI_RETURN = "price_ori_return";
    public static final String KEY_GBP_RETURN = "price_gbp_return";
    public static final String KEY_ORI_RETURN_FIRST = "price_ori_return_first";
    public static final String KEY_GBP_RETURN_FIRST = "price_gbp_return_first";
    public static final String KEY_ORI_COUSHETTE = "price_ori_coushette";
    public static final String KEY_GBP_COUSHETTE = "price_gbp_coushette";
    public static final String KEY_ORI_T3SLEEPER = "price_ori_t3sleeper";
    public static final String KEY_GBP_T3SLEEPER = "price_gbp_t3sleeper";
    public static final String KEY_ORI_DOUBLESLEEPER = "price_ori_doublesleeper";
    public static final String KEY_GBP_DOUBLESLEEPER = "price_gbp_doublesleeper";
    public static final String KEY_ORI_SINGLESLEEPER = "price_ori_singlesleeper";
    public static final String KEY_GBP_SINGLESLEEPER = "price_gbp_singlesleeper";
    public static final String KEY_BORDERCROSS = "bordercross";
    public static final String KEY_TIME_SRC = "time_src";
    public static final String KEY_PRICE_SRC = "price_src";
    public static final String KEY_CALLING_AT = "calling_at";
    public static final String KEY_CALLING_AT_ORI = "calling_at_ori_lang";
    public static final String KEY_TRANSPORT_NUMBER = "transport_number";
    public static final String KEY_EST_PRICE = "estimated_price";
    public static final String KEY_EST_ARRIVAL = "estimated_arrival";
    public static final String KEY_TRANSPORT_COMPANY = "transport_company";
    public static final String KEY_MONDAY = "monday";
    public static final String KEY_TUESDAY = "tuesday";
    public static final String KEY_WEDNESDAY = "wednesday";
    public static final String KEY_THURSDAY = "thursday";
    public static final String KEY_FRIDAY = "friday";
    public static final String KEY_SATURDAY = "saturday";
    public static final String KEY_SUNDAY = "sunday";
    
    
    private int id = 0;
    private int begining = id + 1;
    private int destination = begining + 1;
    private int transportType = destination + 1;
    private int departureTime = transportType + 1;
    private int arrivalTime = departureTime + 1;
    private int duration = arrivalTime + 1;
    private int runningDays = duration + 1;
    private int priceOriCur = runningDays + 1;
    private int priceGDP = priceOriCur + 1;
    private int priceFirstClassOriCur = priceGDP + 1;
    private int priceFirstClassGDP = priceFirstClassOriCur + 1;
    private int priceReturnOriCur = priceFirstClassGDP + 1;
    private int priceReturnGBP = priceReturnOriCur + 1;
    private int priceReturnFirstClassOriCur = priceReturnGBP + 1;
    private int priceReturnFirstClassGDP = priceReturnFirstClassOriCur + 1;
    private int priceCoushetteOriCur = priceReturnFirstClassGDP + 1;
    private int priceCoushetteGDP = priceCoushetteOriCur + 1;
    private int priceT3SleeperOriCur = priceCoushetteGDP + 1;
    private int priceT3SleeperGDP = priceT3SleeperOriCur + 1;
    private int priceDoubleSleeperOriCur = priceT3SleeperGDP + 1;
    private int priceDoubleSleeperGDP = priceDoubleSleeperOriCur + 1;
    private int priceSingleSleeperOriCur = priceDoubleSleeperGDP + 1;
    private int priceSingleSleeperGDP = priceSingleSleeperOriCur + 1;
    private int borderCross = priceSingleSleeperGDP + 1;
    private int timeSource = borderCross + 1;
    private int priceSource = timeSource + 1;
    private int callingat = priceSource + 1;
    private int callingatOriLang = callingat + 1;
    private int transportNumber = callingatOriLang + 1;
    private int estimatedPrice = transportNumber + 1;
    private int estimatedArrival = estimatedPrice + 1;
    private int transportCompany = estimatedArrival + 1;
    private int monday = transportCompany + 1;
    private int tuesday = monday + 1;
    private int wednesday = tuesday + 1;
    private int thursday = wednesday + 1;
    private int friday = thursday + 1;
    private int saturday = friday + 1;
    private int sunday = saturday + 1;
    
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        	String createString = getDatabaseCreateString();
            db.execSQL(createString);
        }
        
        private String getDatabaseCreateString() {
        	String startString =
                    "create table " + DATABASE_TABLE +" ("+KEY_ROWID + " integer primary key autoincrement, ";
            String nonNullText = " text not null, ";
            String normalText = " text, ";
            String lastText = " text not null);";
            
            StringBuilder builder = new StringBuilder(startString);
            builder.append(KEY_START + nonNullText);
            builder.append(KEY_END + nonNullText);
            builder.append(KEY_TRANSPORT_TYPE + nonNullText);
            builder.append(KEY_DEPARTURE_TIME + nonNullText);
            builder.append(KEY_ARRIVAL_TIME + nonNullText);
            builder.append(KEY_DURATION + nonNullText);
            builder.append(KEY_RUNNING_DAYS + nonNullText);
            builder.append(KEY_ORI_PRICE + normalText);
            builder.append(KEY_GBP_PRICE + normalText);
            builder.append(KEY_ORI_PRICE_FIRST + normalText);
            builder.append(KEY_GBP_PRICE_FIRST + normalText);
            builder.append(KEY_ORI_RETURN + normalText);
            builder.append(KEY_GBP_RETURN + normalText);
            builder.append(KEY_ORI_RETURN_FIRST + normalText);
            builder.append(KEY_GBP_RETURN_FIRST + normalText);
            builder.append(KEY_ORI_COUSHETTE + normalText);
            builder.append(KEY_GBP_COUSHETTE + normalText);
            builder.append(KEY_ORI_T3SLEEPER + normalText);
            builder.append(KEY_GBP_T3SLEEPER + normalText);
            builder.append(KEY_ORI_DOUBLESLEEPER + normalText);
            builder.append(KEY_GBP_DOUBLESLEEPER + normalText);
            builder.append(KEY_ORI_SINGLESLEEPER + normalText);
            builder.append(KEY_GBP_SINGLESLEEPER + normalText);
            builder.append(KEY_BORDERCROSS + normalText);
            builder.append(KEY_TIME_SRC + normalText);
            builder.append(KEY_PRICE_SRC + normalText);
            builder.append(KEY_CALLING_AT + normalText);
            builder.append(KEY_CALLING_AT_ORI + normalText);
            builder.append(KEY_TRANSPORT_NUMBER + normalText);
            builder.append(KEY_EST_PRICE + normalText);
            builder.append(KEY_EST_ARRIVAL + normalText);
            builder.append(KEY_MONDAY + normalText);
            builder.append(KEY_TUESDAY + normalText);
            builder.append(KEY_WEDNESDAY + normalText);
            builder.append(KEY_THURSDAY + normalText);
            builder.append(KEY_FRIDAY + normalText);
            builder.append(KEY_SATURDAY + normalText);
            builder.append(KEY_SUNDAY + normalText);
            builder.append(KEY_TRANSPORT_COMPANY + lastText);
            
            
            String finishedString = builder.toString();

            
            return finishedString;
            
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
//                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
    public NotesDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public NotesDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }
    
    public long addResult(String[] data) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_START, data[begining]);
        initialValues.put(KEY_END, data[destination]);
        initialValues.put(KEY_TRANSPORT_TYPE, data[transportType]);
        initialValues.put(KEY_DEPARTURE_TIME, data[departureTime]);
        initialValues.put(KEY_ARRIVAL_TIME, data[arrivalTime]);
        initialValues.put(KEY_DURATION, data[duration]);
        initialValues.put(KEY_RUNNING_DAYS, data[runningDays]);
        initialValues.put(KEY_ORI_PRICE, data[priceOriCur]);
        initialValues.put(KEY_GBP_PRICE, data[priceGDP]);
        initialValues.put(KEY_ORI_PRICE_FIRST, data[priceFirstClassOriCur]);
        initialValues.put(KEY_GBP_PRICE_FIRST, data[priceFirstClassGDP]);
        initialValues.put(KEY_ORI_RETURN, data[priceReturnOriCur]);
        initialValues.put(KEY_GBP_RETURN, data[priceReturnFirstClassOriCur]);
        initialValues.put(KEY_ORI_RETURN_FIRST, data[priceReturnFirstClassOriCur]);
        initialValues.put(KEY_GBP_RETURN_FIRST, data[priceReturnFirstClassGDP]);
        initialValues.put(KEY_ORI_COUSHETTE, data[priceCoushetteOriCur]);
        initialValues.put(KEY_GBP_COUSHETTE, data[priceCoushetteGDP]);
        initialValues.put(KEY_ORI_T3SLEEPER, data[priceT3SleeperOriCur]);
        initialValues.put(KEY_GBP_T3SLEEPER, data[priceT3SleeperGDP]);
        initialValues.put(KEY_ORI_DOUBLESLEEPER, data[priceDoubleSleeperOriCur]);
        initialValues.put(KEY_GBP_DOUBLESLEEPER, data[priceDoubleSleeperGDP]);
        initialValues.put(KEY_ORI_SINGLESLEEPER, data[priceSingleSleeperOriCur]);
        initialValues.put(KEY_GBP_SINGLESLEEPER, data[priceSingleSleeperGDP]);
        initialValues.put(KEY_BORDERCROSS, data[borderCross]);
        initialValues.put(KEY_TIME_SRC, data[timeSource]);
        initialValues.put(KEY_PRICE_SRC, data[priceSource]);
        initialValues.put(KEY_CALLING_AT, data[callingat]);
        initialValues.put(KEY_CALLING_AT_ORI, data[callingatOriLang]);
        initialValues.put(KEY_TRANSPORT_NUMBER, data[transportNumber]);
        initialValues.put(KEY_EST_PRICE, data[estimatedPrice]);
        initialValues.put(KEY_EST_ARRIVAL, data[estimatedArrival]);
        initialValues.put(KEY_TRANSPORT_COMPANY, data[transportCompany]);
        initialValues.put(KEY_MONDAY, data[monday]);
        initialValues.put(KEY_TUESDAY, data[tuesday]);
        initialValues.put(KEY_WEDNESDAY, data[wednesday]);
        initialValues.put(KEY_THURSDAY, data[thursday]);
        initialValues.put(KEY_FRIDAY, data[friday]);
        initialValues.put(KEY_SATURDAY, data[saturday]);
        initialValues.put(KEY_SUNDAY, data[sunday]);

        return mDb.insertOrThrow(DATABASE_TABLE, null, initialValues);
    }



    public ArrayList<String[]> getJourneyInfo(String start, String end, String date,
    		String time, boolean returnData, String returnDate, String returnTime) {
    	String[] columns = {KEY_DEPARTURE_TIME, KEY_ARRIVAL_TIME, KEY_TRANSPORT_TYPE};
    	int[] dateInts = dateStringToInts(date);
    	Calendar c = Calendar.getInstance();
    	c.set(dateInts[2], dateInts[1], dateInts[0]);
    	int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
    	String dayCode = convertIntToDay(dayOfWeek);
    	String selection = 
    			   KEY_START + " = " + "'" + start + "'" 
    			 + AND 
    			 + KEY_END + " = " + "'" + end + "'"
    			 + AND 
    			 + dayCode + " = " + "'" + "TRUE" + "'";
    	Cursor result = mDb.query(DATABASE_TABLE, columns, selection,
    			null, null, null, null);
    	ArrayList<String[]> times = new ArrayList<String[]>();
    	if (result.moveToFirst()) {
    		do {
    			String data[] = new String[JINFO_VARIABLE_COUNT];
    			data[JINFO_DEPARTURE] 
    					= result.getString(result.getColumnIndex(KEY_DEPARTURE_TIME));
    			data[JINFO_ARRIVAL]
    					= result.getString(result.getColumnIndex(KEY_ARRIVAL_TIME));
    			data[JINFO_TRAVELTYPE]
    					= result.getString(result.getColumnIndex(KEY_TRANSPORT_TYPE));
    			times.add(data);
    		} while (result.moveToNext());
    	}
    	return times;	
    }
    

	
	private int[] dateStringToInts(String dateStr) {
		String[] dateSplit = dateStr.split(" ");
		for (String date : dateSplit) {
			Log.d("Dates", date);
		}
		Date date = null;
		try {
			date = new SimpleDateFormat("MMM", Locale.ENGLISH).parse(dateSplit[1]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    int month = cal.get(Calendar.MONTH);
	    
		Log.d("Dates", Integer.valueOf(dateSplit[0]) + "");
		Log.d("Dates", month + "");
		Log.d("Dates", Integer.valueOf(dateSplit[2]) + "");
		int[] dateInts = {Integer.valueOf(dateSplit[0]),
						    month,
							Integer.valueOf(dateSplit[2])};
		return dateInts;
	}
    
    private String convertIntToDay(int dayNum) {
    	switch (dayNum) {
    	case 0 : return KEY_SUNDAY;
    	case 1 : return KEY_MONDAY;
    	case 2 : return KEY_TUESDAY;
    	case 3 : return KEY_WEDNESDAY;
    	case 4 : return KEY_THURSDAY;
    	case 5 : return KEY_FRIDAY;
    	case 6 : return KEY_SATURDAY;
    	default : return null;
    	}
    }

    /**
     * Return a Cursor over the list of all notes in the database
     * 
     * @return Cursor over all notes
     */
    public Cursor fetchAllNotes() {
    	String orderBy = KEY_START + " ASC";
    	return mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_START,
                KEY_END}, null, null, null, null, orderBy, null);
     }
    
    public Set<String> getDestinations() {
    	Set<String> destinations = new TreeSet<String>();
    	Cursor cursor = fetchAllNotes();
    	if (cursor.moveToFirst()){
    		   do{
    		      String data = cursor.getString(cursor.getColumnIndex(KEY_START));
    		      destinations.add(data);
    		   }while(cursor.moveToNext());
    		}
    	cursor.close();
    	return destinations;
    }
    
    public Set<String> getEndPoints() {
    	Set<String> endPoints = new TreeSet<String>();
    	Cursor cursor = fetchAllNotes();
    	if (cursor.moveToFirst()) {
    		do {
    			String data = cursor.getString(cursor.getColumnIndex(KEY_END));
    			endPoints.add(data);
    		} while (cursor.moveToNext());
    	}
    	return endPoints;
    }
    
    public Set<String> getEndPointsFromStartPoint(String startPoint) {
    	Set<String> endPoints = new TreeSet<String>();
    	String whereClause = KEY_START + " = ?";
    	String[] whereArgs = new String[] {startPoint};
    	Cursor cursor = mDb.query(DATABASE_TABLE, new String[] {KEY_END},
    			whereClause, whereArgs, null, null, null);
    	if (cursor.moveToFirst()){
 		   do{
 		      String data = cursor.getString(cursor.getColumnIndex(KEY_END));
 		      endPoints.add(data);
 		   }while(cursor.moveToNext());
 		}
    	cursor.close();
    	return endPoints;
    }
    
    public void setUpDatabase() {
    	AssetManager am = mCtx.getAssets();
    	try {
    		InputStream input = am.open(CSV_FILE_NAME);
    		BufferedReader r = new BufferedReader(new InputStreamReader(input));
    		
    		String line = null;
    		while ((line = r.readLine()) != null) {
    			String[] splitLine = line.split(",");
    			if (splitLine.length < 40) {
    				Log.d("Split line error causer!", splitLine[0]);
    			}
    			if (splitLine[1] != "N/A") {
    				addResult(splitLine);
    			}   			
    		}
    	} catch (IOException e) {
    		//Do nothing
    	}
    }

//    /**
//     * Return a Cursor positioned at the note that matches the given rowId
//     * 
//     * @param rowId id of note to retrieve
//     * @return Cursor positioned to matching note, if found
//     * @throws SQLException if note could not be found/retrieved
//     */
//    public Cursor fetchNote(long rowId) throws SQLException {
//
//        Cursor mCursor =
//
//            mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
//                    KEY_TITLE, KEY_BODY}, KEY_ROWID + "=" + rowId, null,
//                    null, null, null, null);
//        if (mCursor != null) {
//            mCursor.moveToFirst();
//        }
//        return mCursor;
//
//    }

//    /**
//     * Update the note using the details provided. The note to be updated is
//     * specified using the rowId, and it is altered to use the title and body
//     * values passed in
//     * 
//     * @param rowId id of note to update
//     * @param title value to set note title to
//     * @param body value to set note body to
//     * @return true if the note was successfully updated, false otherwise
//     */
//    public boolean updateNote(long rowId, String title, String body) {
//        ContentValues args = new ContentValues();
//        args.put(KEY_TITLE, title);
//        args.put(KEY_BODY, body);
//
//        return mDb.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
//    }
	
}
