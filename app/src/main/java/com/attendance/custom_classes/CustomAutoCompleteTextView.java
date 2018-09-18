package com.attendance.custom_classes;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
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
				if ( this.toString().equals("") ) {
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
				if (!this.toString().matches(emailPattern)) {
					textInputLayout.setErrorMessage("Please enter valid Email Id");
				} else {
					textInputLayout.setErrorDisabled();
				}
			}
		});
	}

	@Override
	public String toString() {
		return this.getText().toString();
	}

	public boolean isFieldEmpty( CustomTextInputLayout inputLayout ) {
		if ( !TextUtils.isEmpty(this.toString()) ) {
			inputLayout.setErrorDisabled();
			return true;
		} else {
			inputLayout.setErrorMessage();
			return false;
		}
	}

	public boolean isEmpty(){
		return !TextUtils.isEmpty(this.toString());
	}

	public boolean isFieldEmpty( CustomTextInputLayout inputLayout, String msg ) {
		if ( !TextUtils.isEmpty(this.toString()) ) {
			inputLayout.setErrorDisabled();
			return true;
		} else {
			inputLayout.setErrorMessage(msg);
			return false;
		}
	}

	public int fieldEmpty( CustomTextInputLayout inputLayout ) {
		if ( !TextUtils.isEmpty(this.toString()) ) {
			inputLayout.setErrorDisabled();
			return 1;
		} else {
			inputLayout.setErrorMessage();
			return 0;
		}
	}

	public int fieldEmpty( CustomTextInputLayout inputLayout, String msg ) {
		if ( !TextUtils.isEmpty(this.toString()) ) {
			inputLayout.setErrorDisabled();
			return 1;
		} else {
			inputLayout.setErrorMessage(msg);
			return 0;
		}
	}
}
