package edu.kushagrathapar.cs478.activitesandintents;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String BROWSER_URL = "https://developer.android.com/index.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Called when the user clicks the Open Browser button
     */
    public void openBrowser(View view) {
        Uri browserUri = Uri.parse(BROWSER_URL);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, browserUri);
        this.startActivity(browserIntent);
    }

    /**
     * Called when the user clicks on the Open Custom Dialer Activity Button
     *
     * @param view
     */
    public void openCustomDialerActivity(View view) {
        Intent customIntent = new Intent(MainActivity.this, CustomDialerActivity.class);
        startActivity(customIntent);
    }
}
