package com.attendance.custom_classes;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
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
                if ( this.toString().equals("") ) {
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
                if ( this.toString().length() != length ) {
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
                if (!this.toString().matches(phoneExpression)) {
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
				if ( !this.toString().matches(emailPattern) ) {
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


