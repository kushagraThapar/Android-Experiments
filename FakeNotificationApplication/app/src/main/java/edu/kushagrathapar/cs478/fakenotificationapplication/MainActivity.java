package edu.kushagrathapar.cs478.fakenotificationapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startSimpleIntentService(View view) {
        Intent simpleIntent = new Intent(MainActivity.this, SimpleIntentService.class);
        simpleIntent.putExtra("ToastMessage", "This is service running in the back end");
        startService(simpleIntent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        doLogging("********Stopped*********");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doLogging("********Destroyed*********");
    }

    public void doLogging(String message) {
        Log.i(this.getClass().getSimpleName(), message);
    }
}
