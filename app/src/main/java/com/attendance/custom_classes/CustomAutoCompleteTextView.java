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
	//[a-zA-Z0-9\-]
	public String emailPattern = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
			"\\@" +
			"[a-zA-Z0-9]{0,64}" +
			"(" +
			"\\." +
			"[a-zA-Z0-9]{0,25}" +
			")+";

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

	public void setFocusChangeEmailId (CustomTextInputLayout textInputLayout) {
		this.setOnFocusChangeListener((View v, boolean hasFocus) -> {
			if ( !hasFocus ) {
				String _text = this.getText().toString();
				if ( !_text.matches(emailPattern) ) {
					textInputLayout.setErrorMessage("Please enter valid email id");
				}
				else {
					textInputLayout.setErrorEnabled(false);
				}
			}
		});
	}

	@Override
	public String toString() {
		return this.getText().toString();
	}
}
