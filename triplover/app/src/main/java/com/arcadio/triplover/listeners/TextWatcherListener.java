package com.arcadio.triplover.listeners;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class TextWatcherListener {

    public interface onTextedChangedListener {
        void onChanged(String text);
    }

    public static void onTextListener(EditText editText, onTextedChangedListener listener) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listener.onChanged(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
