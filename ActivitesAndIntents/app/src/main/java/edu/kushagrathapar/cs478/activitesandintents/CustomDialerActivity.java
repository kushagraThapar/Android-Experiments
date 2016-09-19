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

    private final static String CUSTOM_DIALER_ACTIVITY = "CustomDialerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logMessage("On Create Called");
        setContentView(R.layout.activity_custom_dialer);
    }


    /**
     * Called when user presses Open Dialer button
     * This method checks reads the text and checks if it is possible to open the dialer
     *
     * @param view
     */
    public void opeDialer(View view) {
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
            CharSequence text = "Please enter a valid phone number (xxx) yyy-zzzz";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }


    /**
     * Validates and returns the formatted string for a valid phone number
     * <p/>
     * pattern - (xxx)yyy-zzzz or (xxx) yyy-zzzz
     *
     * @param text
     * @return Null if phone number is not valid
     */
    private String formatNumber(String text) {
        //  First validate the phone number, PhoneNumberUtils is lenient in checking the phone numbers
        return PhoneNumberUtils.formatNumber(text, "US");
    }

    @Override
    protected void onStop() {
        super.onStop();
        logMessage("On Stop Called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        logMessage("On Start Called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        logMessage("On Start Called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logMessage("On Destroy Called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        logMessage("On Resume Called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        logMessage("On Restart Called");
    }

    private void logMessage(String message) {
        Log.i(CUSTOM_DIALER_ACTIVITY, message);
    }
}
