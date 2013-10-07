package com.example.trains.buttons;

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

public class EditedButton extends android.widget.Button {
	
	public EditedButton(Context context) {
		super(context);
	}
	
	public EditedButton (Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public EditedButton (Context context, AttributeSet attrs, int defStyle) {
		super(context,attrs,defStyle);
		
	}
	
	public GradientDrawable getUnClickedBackground() {
		return (GradientDrawable) getResources().getDrawable(R.drawable.button_shape);
	}
	
	public GradientDrawable getClickedColor() {
		return (GradientDrawable) getResources().getDrawable(R.drawable.button_clicked);
	}
	
}
