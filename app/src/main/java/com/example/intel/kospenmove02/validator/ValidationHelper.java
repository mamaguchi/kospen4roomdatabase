package com.example.intel.kospenmove02.validator;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ValidationHelper {

    private Context context;

    /**
     * constructor
     * @param context
     */
    public ValidationHelper(Context context) {
        this.context = context;
    }


    /**
     * method to check EditText filled .
     * @param editText
     * @param textInputLayout
     * @param message
     * @return
     */
    public Boolean isEditTextFilled(EditText editText, TextInputLayout textInputLayout, String message) {
        String value = editText.getText().toString().trim();
        if (value.isEmpty()) {
            textInputLayout.setError(message);
            hideKeyboardFrom(editText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }

        return true;
    }


    /**
     * method to check valid email inEditText .
     * @param editText
     * @param textInputLayout
     * @param message
     * @return
     */
    public Boolean isEditTextEmail(EditText editText, TextInputLayout textInputLayout, String message) {
        String value = editText.getText().toString().trim();
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            textInputLayout.setError(message);
            hideKeyboardFrom(editText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }


    /**
     * method to Hide keyboard
     * @param view
     */
    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    /**
     * static method to get current DateTime
     * @return DateTime(String)
     */
    public static String getCurrentDateTime() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("YYYY-MM-dd hh:mm:ss");
        String timestamp = LocalDateTime.now().format(df);
        return timestamp;
    }


    /**
     * static method to convert Gender String to int
     * @return int
     */
    public static int genderStringToInt(String genderStr) {
        switch (genderStr) {
            case "Male":
                return 1;
            case "Female":
                return 2;
            default:
                return 1;
        }
    }


    /**
     * static method to convert State String to int
     * @return int
     */
    public static int stateStringToInt(String stateStr) {
        switch (stateStr) {
            case "PAHANG":
                return 1;
            case "NONPAHANG":
                return 2;
            default:
                return 1;
        }
    }


    /**
     * static method to convert Region String to int
     * @return int
     */
    public static int regionStringToInt(String regionStr) {
        switch (regionStr) {
            case "MARAN":
                return 1;
            case "JERANTUT":
                return 2;
            default:
                return 1;
        }
    }


    /**
     * static method to convert Subregion String to int
     * @return int
     */
    public static int subregionStringToInt(String subregionStr) {
        switch (subregionStr) {
            case "JENGKA2":
                return 1;
            case "MARAN":
                return 2;
            default:
                return 1;
        }
    }


    /**
     * static method to convert Locality String to int
     * @return int
     */
    public static int localityStringToInt(String localityStr) {
        switch (localityStr) {
            case "ULUJEMPOL":
                return 1;
            case "JENGKA6":
                return 2;
            default:
                return 1;
        }
    }

}
