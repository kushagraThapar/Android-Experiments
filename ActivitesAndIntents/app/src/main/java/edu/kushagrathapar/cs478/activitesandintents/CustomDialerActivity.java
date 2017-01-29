package edu.kushagrathapar.cs478.activitesandintents;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CustomDialerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dialer);
    }

    /**
     * Called when user presses Open Dialer button
     * This method checks reads the text and checks if it is possible to open the dialer
     *
     * @param view
     */
    public void openDialer(View view) {
        EditText editText = (EditText) findViewById(R.id.customDialerText);
        String textEntered = editText.getText().toString();

        //  Verify Text entered here and accordingly open the dialer application
        Intent dialerIntent = new Intent(Intent.ACTION_DIAL);
        String phoneNumber = formatNumber(textEntered);
        if (phoneNumber != null) {
            dialerIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(dialerIntent);
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Couldn't find any valid phone number in the text";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }


    /**
     * Validates and returns the formatted string for a valid phone number if present
     * <p/>
     * Returns the pattern - (xxx)yyy-zzzz or (xxx) yyy-zzzz
     *
     * @param text
     * @return Null if phone number is not present in the input text
     */
    private String formatNumber(String text) {
        //  First validate the phone number, PhoneNumberUtils is lenient in checking the phone numbers
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '(' && text.charAt(i + 4) == ')') {
                //  Now expect a phone number and go through various tests
                try {
                    int endIndex = i + 13;
                    if (text.charAt(i + 5) == ' ') {
                        endIndex = i + 14;
                    }
                    String phoneNumberString = text.substring(i, endIndex);
                    if (isValidPhoneNumber(phoneNumberString)) {
                        return phoneNumberString;
                    }
                } catch (IndexOutOfBoundsException e) {
                    Log.e(this.getClass().getSimpleName(), e.getMessage());
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * This method checks if the number is of the following pattern based on the length of the input
     * (xxx)yyy-zzzz or (xxx) yyy-zzzz
     *
     * @param number
     * @return true if the number matches the pattern otherwise false.
     */
    private boolean isValidPhoneNumber(String number) {
        //  These are the basic validations.
        if (number.length() != 13 && number.length() != 14) {
            return false;
        }
        if (!number.contains("-")) {
            return false;
        }

        //  Check each and every character to be a digit except for (, ), - and space ' '
        for (char c : number.substring(1, 4).toCharArray()) {
            if (c < '0' || c > '9') {
                return false;
            }
        }
        String onlyDigits = null;
        if (number.length() == 13) {
            if (number.charAt(8) != '-') {
                return false;
            }
            onlyDigits = number.substring(5, 8) + number.substring(9);
        }
        if (number.length() == 14) {
            if (number.charAt(5) != ' ' || number.charAt(9) != '-') {
                return false;
            }
            onlyDigits = number.substring(6, 9) + number.substring(10);
        }
        for (char c : onlyDigits.toCharArray()) {
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return PhoneNumberUtils.formatNumber(number, "US") != null;
    }
}
