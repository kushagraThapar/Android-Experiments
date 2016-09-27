package edu.bourne.cs478.googlesalarmsservice;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import static android.app.AlarmManager.ELAPSED_REALTIME;
import static android.os.SystemClock.elapsedRealtime;

/**
 * Created by kushagrathapar on 9/22/16.
 */

public class SimpleIntentService extends IntentService implements Runnable {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SimpleIntentService(String name) {
        super(name);
    }

    public SimpleIntentService() {
        this(SimpleIntentService.class.getSimpleName());
    }

    private void doLogging(String message) {
        Log.i(this.getClass().getSimpleName(), message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        doLogging("********Destroyed*********");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        run();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        doLogging("****** on task removed ******");
        try {
            Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());

            PendingIntent restartServicePendingIntent = PendingIntent.getService(
                    getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
            AlarmManager alarmService = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmService.set(ELAPSED_REALTIME, elapsedRealtime() + 1000,
                    restartServicePendingIntent);
        } catch (Exception e) {
            doLogging(e.getMessage());
        }

        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void run() {
        doLogging("This is service intent");
        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layoutView = li.inflate(R.layout.custom_toast, null);

        Handler mHandler = new Handler(getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast toast = new Toast(getApplicationContext());
                FakeNotifications fakeNotifications = new FakeNotifications(toast, layoutView);
                new Thread(fakeNotifications).start();
            }
        });
        try {
            synchronized (this) {
                this.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //  For now, just open the phones browser with some random website
    class FakeNotifications implements Runnable {

        Toast customToast = null;
        View layoutView = null;

        FakeNotifications(Toast toast, View layoutView) {
            this.customToast = toast;
            this.layoutView = layoutView;
        }

        @Override
        public void run() {
            try {
                int[] drawables = new int[]{R.drawable.pic1, R.drawable.pic2, R.drawable.pic3,
                        R.drawable.pic4, R.drawable.pic5, R.drawable.pic6,
                        R.drawable.pic7, R.drawable.pic8, R.drawable.pic9, R.drawable.pic10,
                        R.drawable.pic11, R.drawable.pic12, R.drawable.pic13,
                        R.drawable.pic14, R.drawable.pic15, R.drawable.pic16, R.drawable.pic17,
                        R.drawable.pic18, R.drawable.pic19, R.drawable.pic20, R.drawable.pic21};
                for (int i = 0; i < 20; i++) {
                    ImageView customImageView = (ImageView) layoutView.findViewById(R.id.custom_image);
                    customImageView.setImageResource(drawables[i % drawables.length]);
                    customToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    customToast.setDuration(Toast.LENGTH_LONG);
                    customToast.setView(layoutView);
                    customToast.show();
                    Thread.sleep(10000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
