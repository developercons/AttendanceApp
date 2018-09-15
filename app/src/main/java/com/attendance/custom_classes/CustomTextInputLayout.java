package com.attendance.custom_classes;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.View;

public class CustomTextInputLayout extends TextInputLayout {
    public CustomTextInputLayout(Context context) {
        super(context);
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setErrorMessage(){
        this.setErrorEnabled(true);
        this.setError(null);
        this.setError("This field is required");
    }

    public void setErrorMessage(@Nullable CharSequence errorMessage){
        this.setErrorEnabled(true);
        this.setError(null);
        this.setError(errorMessage);
    }

}
