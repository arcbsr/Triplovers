package com.arcadio.triplover.utils;

import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

public class ViewUtils {

    public static boolean togglePasswordView(EditText editText) {
        if (editText.getTransformationMethod() == null) {
            editText.setTransformationMethod(new PasswordTransformationMethod());
            return true;
        } else {
            editText.setTransformationMethod(null);
            return false;
        }
    }
}
