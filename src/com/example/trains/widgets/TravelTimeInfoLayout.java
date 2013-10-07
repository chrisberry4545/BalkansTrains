package com.example.trains.widgets;

import com.example.trains.R;
import com.example.trains.TravelInfoWrapper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TravelTimeInfoLayout extends LinearLayout{
	
	private LinearLayout leftSide;
	private LinearLayout rightSide;
	private LinearLayout departureLayout;
	private LinearLayout changesLayout;
	private LinearLayout arrivalLayout;
	private LayoutParams standardTextLayout =
			new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT, 
					LinearLayout.LayoutParams.WRAP_CONTENT, 
					0.5f);
	
	private TravelInfoWrapper travelInfo;

	protected TravelTimeInfoLayout(Context context) {
		super(context);
	}
	
	protected TravelTimeInfoLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	protected TravelTimeInfoLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public TravelTimeInfoLayout(Context context, TravelInfoWrapper travelInfo) {
		super(context);
		this.travelInfo = travelInfo;
		addToLeftSide();
		addToRightSide();
	}

	private void setUpLayout() {
		leftSide = new LinearLayout(this.getContext());
		rightSide = new LinearLayout(this.getContext());
		departureLayout = new LinearLayout(this.getContext());
		changesLayout = new LinearLayout(this.getContext());
		arrivalLayout = new LinearLayout(this.getContext());
		
		LayoutParams topLevelParams
				= new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 0.5f);
		leftSide.setLayoutParams(topLevelParams);
		rightSide.setLayoutParams(topLevelParams);
		
		LayoutParams rightElementsParams 
				= new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f);
		departureLayout.setLayoutParams(rightElementsParams);
		changesLayout.setLayoutParams(rightElementsParams);
		arrivalLayout.setLayoutParams(rightElementsParams);
		
		this.setWeightSum(1f);
		this.setOrientation(LinearLayout.HORIZONTAL);
		this.addView(leftSide);
		this.addView(rightSide);
		rightSide.setWeightSum(3);
		rightSide.setOrientation(LinearLayout.VERTICAL);
		rightSide.addView(departureLayout);
		rightSide.addView(changesLayout);
		rightSide.addView(arrivalLayout);
		
	}
	
	public void addToLeftSide() {
		TextView transportTypeText = new TextView(this.getContext());
		transportTypeText.setText(travelInfo.getTransportType());
		transportTypeText.setLayoutParams(standardTextLayout);
		transportTypeText.setGravity(Gravity.CENTER_HORIZONTAL);
		leftSide.addView(transportTypeText);
	}
	
	public void addToRightSide() {
		departureLayout.setOrientation(LinearLayout.HORIZONTAL);
		arrivalLayout.setOrientation(LinearLayout.HORIZONTAL);
		changesLayout.setOrientation(LinearLayout.HORIZONTAL);
		
		//Add titles
		departureLayout.addView(
				setUpTextView(getResources().getString(R.string.leaving),
						Gravity.CENTER_VERTICAL));
		changesLayout.addView(
				setUpTextView(getResources().getString(R.string.changes),
						Gravity.CENTER_VERTICAL));
		arrivalLayout.addView(
				setUpTextView(getResources().getString(R.string.arriving),
						Gravity.CENTER_VERTICAL));
		
		//Add data
		departureLayout.addView(
				setUpTextView(travelInfo.getDepartureTime(),
						Gravity.CENTER_VERTICAL));
		arrivalLayout.addView(
				setUpTextView(travelInfo.getArrivalTime(),
						Gravity.CENTER_VERTICAL));
		
	}
	
	private TextView setUpTextView(String text, int gravity) {
		TextView textView = new TextView(this.getContext());
		textView.setText(text);
		textView.setLayoutParams(standardTextLayout);
		textView.setGravity(gravity);
		return textView;
	}

}
