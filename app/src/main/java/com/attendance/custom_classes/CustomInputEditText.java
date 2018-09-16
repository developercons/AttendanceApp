package com.attendance.custom_classes;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;

public class CustomInputEditText extends TextInputEditText {

    public CustomInputEditText (Context context) {
        super(context);
        this.setOnKeyListener((v, keyCode, event) -> event.getKeyCode() == KeyEvent.KEYCODE_SOFT_RIGHT || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER ||
                event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT ||
                event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT);
        this.setTextSize(14.0f);
        this.setTextColor(Color.BLACK);
    }

    public CustomInputEditText (Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnKeyListener((v, keyCode, event) -> event.getKeyCode() == KeyEvent.KEYCODE_SOFT_RIGHT || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER ||
                event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT ||
                event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT);
        this.setTextSize(14.0f);
        this.setTextColor(Color.BLACK);
    }

    public CustomInputEditText (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOnKeyListener((v, keyCode, event) -> event.getKeyCode() == KeyEvent.KEYCODE_SOFT_RIGHT || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER ||
                event.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT ||
                event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT);
        this.setTextSize(14.0f);
        this.setTextColor(Color.BLACK);
    }

	public String phoneExpression = "^[6-9][0-9]{9}$";
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

	public void setFocusChange (CustomTextInputLayout textInputLayout, String msg, int length)  {
        this.setOnFocusChangeListener((View v, boolean hasFocus) -> {
            if (!hasFocus) {
                if ( this.getText().toString().length() != length ) {
                    textInputLayout.setErrorMessage(msg);
                }
                else {
                    textInputLayout.setErrorEnabled(false);
                }
            } 
        });
    }

    public void setFocusChangeMobileNo (CustomTextInputLayout textInputLayout) {
        this.setOnFocusChangeListener((View v, boolean hasFocus) -> {
            if ( !hasFocus ) {
                String _mobile = this.getText().toString();
                if (!_mobile.matches(phoneExpression)) {
                    textInputLayout.setErrorMessage("Please Enter Valid Phone No.");
                } else{
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
		return this.getText().toString().trim();
	}
}


