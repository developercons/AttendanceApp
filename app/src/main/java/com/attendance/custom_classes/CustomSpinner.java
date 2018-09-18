package com.attendance.custom_classes;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
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
				if ( this.toString().equals("") ) {
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

	public boolean isEmpty(){
		return !TextUtils.isEmpty(this.toString());
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
