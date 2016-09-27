package edu.bourne.cs478.googlesalarmsservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent simpleIntent = new Intent(context, HomeActivity.class);
        simpleIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(simpleIntent);
    }
}
