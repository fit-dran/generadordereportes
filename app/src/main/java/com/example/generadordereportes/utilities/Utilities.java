package com.example.generadordereportes.utilities;

public class Utilities {

    private Utilities() {
    }

    public static void disableEditText(android.widget.EditText editText) {
        editText.setFocusable(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setEnabled(false);
        editText.setBackgroundColor(android.graphics.Color.TRANSPARENT);
    }
}
