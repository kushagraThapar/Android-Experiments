package edu.kushagrathapar.cs478.playerclient;

import android.app.Service;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.kushagrathapar.cs478.stub.IAudioServiceStub;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private IAudioServiceStub iAudioServiceStub;
    private boolean isBound = false;
    private static final String intentAction = "edu.kushagrathapar.cs478.stub.IAudioServiceStub";
    private static ArrayAdapter<String> customArrayAdapter;
    private int clipNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listItem);
        List<String> customList = new ArrayList<>();
        customArrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.single_row, customList);
        listView.setAdapter(customArrayAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isBound) {
            Intent customIntent = new Intent(intentAction);

            //  Writing Explicit Intent for the connection to the Remote Stub
            ResolveInfo info = getPackageManager().resolveService(customIntent, PackageManager.MATCH_ALL);
            customIntent.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));

            //  Binding to remote service
            isBound = bindService(customIntent, serviceConnection, Service.BIND_AUTO_CREATE);
            if (isBound) {
                Log.i(TAG, "AudioService bounded successfully");
            } else {
                Log.i(TAG, "AudioService bounding unsuccessful");
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {

        private static final String SERVICE_TAG = "ServiceConnection";

        public void onServiceConnected(ComponentName className, IBinder iAudioService) {
            Log.i(SERVICE_TAG, "onServiceConnected method called");
            iAudioServiceStub = IAudioServiceStub.Stub.asInterface(iAudioService);
            isBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.i(SERVICE_TAG, "onServiceDisconnected method called");
            iAudioServiceStub = null;
            isBound = false;
        }
    };

    private void playAudioClip() {
        final AlertDialog.Builder customBuilder = new AlertDialog.Builder(this);
        customBuilder.setTitle("Enter a number between 0 - 3");

        // Set the input
        final EditText customInput = new EditText(this);
        // Specify the type of input expected; which in this case is number
        customInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        customBuilder.setView(customInput);

        // Set up the buttons
        customBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int clipNumber = Integer.parseInt(customInput.getText().toString());
                if (clipNumber > 3 || clipNumber < 0) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid number between 0 - 3", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    iAudioServiceStub.playAudio(clipNumber);
                    customArrayAdapter.insert("Played Audio Clip [" + clipNumber + "]", 0);
                    setCurrentTrackInfo(clipNumber);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        customBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        customBuilder.show();
    }

    public void playAudioClip(View buttonView) {
        playAudioClip();
    }

    public void pauseAudioClip(View buttonView) {
        try {
            iAudioServiceStub.pauseAudio();
            customArrayAdapter.insert("Paused Audio Clip [" + this.clipNumber + ']', 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void resumeAudioClip(View buttonView) {
        try {
            iAudioServiceStub.resumeAudio();
            customArrayAdapter.insert("Resumed Audio Clip [" + this.clipNumber + ']', 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void stopAudioClip(View buttonView) {
        try {
            iAudioServiceStub.stopAudio();
            customArrayAdapter.insert("Stopped Audio Clip [" + this.clipNumber + ']', 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentTrackInfo(int clipNumber) {
        this.clipNumber = clipNumber;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
