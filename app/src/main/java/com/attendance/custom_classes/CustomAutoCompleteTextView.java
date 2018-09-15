package com.attendance.custom_classes;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

public class CustomAutoCompleteTextView extends android.support.v7.widget.AppCompatAutoCompleteTextView {
	public CustomAutoCompleteTextView (Context context) {
		super(context);
		this.setTextSize(14.0f);
		this.setTextColor(Color.BLACK);
	}

	public CustomAutoCompleteTextView (Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setTextSize(14.0f);
		this.setTextColor(Color.BLACK);
	}

	public CustomAutoCompleteTextView (Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.setTextSize(14.0f);
		this.setTextColor(Color.BLACK);
	}

	public void setFocusChange (CustomTextInputLayout textInputLayout) {
		this.setOnFocusChangeListener((View v, boolean hasFocus) -> {
			if (!hasFocus) {
				if ( this.getText().toString().equals("") ) {
					textInputLayout.setErrorMessage();
				}
				else {
					textInputLayout.setErrorEnabled(false);
				}
			}
		});
	}

}
