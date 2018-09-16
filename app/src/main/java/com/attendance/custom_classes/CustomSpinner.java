package com.attendance.custom_classes;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class CustomSpinner extends MaterialBetterSpinner {
	public CustomSpinner (Context context) {
		super(context);
		this.setTextSize(14.0f);
		this.setTextColor(Color.BLACK);
	}

	public CustomSpinner (Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
		this.setTextSize(14.0f);
		this.setTextColor(Color.BLACK);
	}

	public CustomSpinner (Context arg0, AttributeSet arg1, int arg2) {
		super(arg0, arg1, arg2);
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

	@Override
	public String toString() {
		return this.getText().toString().trim();
	}
}
