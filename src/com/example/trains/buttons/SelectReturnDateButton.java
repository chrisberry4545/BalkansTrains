package com.example.trains.buttons;

import com.example.trains.MainActivity;
import com.example.trains.R;
import com.example.trains.R.drawable;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class SelectReturnDateButton extends EditedButton {
	
	public SelectReturnDateButton(Context context) {
		super(context);
		setUpTouch();
	}
	
	public SelectReturnDateButton (Context context, AttributeSet attrs) {
		super(context, attrs);
		setUpTouch();
	}

	public SelectReturnDateButton (Context context, AttributeSet attrs, int defStyle) {
		super(context,attrs,defStyle);
		setUpTouch();
	}
	
	public void setUpTouch() {
		final Button button = this;
		final GradientDrawable unclickedColor = super.getUnClickedBackground();
		final GradientDrawable clickedColor = super.getClickedColor();
		final MainActivity activity = (MainActivity)this.getContext();
		final SelectReturnDateButton thisButton = this;
		this.setOnTouchListener(new OnTouchListener() {
			@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
				@Override
				public boolean onTouch(View arg0, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						button.setBackground(clickedColor);
			    }
			    else if (event.getAction() == MotionEvent.ACTION_UP) {
			    	button.setBackground(unclickedColor);
			    	activity.getUserToEnterReturnDate(thisButton);
			    }

			    return true;
				}
			});
	}
	
}
