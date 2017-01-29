package edu.kushagrathapar.cs478.audioserver;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.kushagrathapar.cs478.stub.IAudioServiceStub;

public class AudioService extends Service {

    private static final String TAG = AudioService.class.getSimpleName();
    private MediaPlayer customMediaPlayer;
    private List<Integer> audioClips = new ArrayList<>();


    private IAudioServiceStub.Stub iAudioServiceStub = new IAudioServiceStub.Stub() {

        /**
         * Takes the clip number as input and plays the Audio clip with that number.
         * Throws {@link IndexOutOfBoundsException} in case of the audio clip not present with the provided number.
         * @param number
         * @throws RemoteException
         */
        @Override
        public String playAudio(int number) throws RemoteException {
            Log.i(TAG, "******\nplayAudio method called\n******");
            customMediaPlayer = MediaPlayer.create(getApplicationContext(), audioClips.get(number));
            customMediaPlayer.start();
            return "Success";
        }

        /**
         * Pauses the Audio clip running.
         * If the audio server is not playing anything, this would throw an {@link IllegalStateException}
         * @throws RemoteException
         */
        @Override
        public String pauseAudio() throws RemoteException {
            Log.i(TAG, "******\npauseAudio method called\n******");
            if (customMediaPlayer != null && customMediaPlayer.isPlaying()) {
                customMediaPlayer.pause();
                return "Success";
            }
            return "Failure";
        }

        /**
         * Resumes the Audio Clip paused.
         * If there is nothing to be resumed, this would throw an {@link IllegalStateException}
         * @throws RemoteException
         */
        @Override
        public String resumeAudio() throws RemoteException {
            Log.i(TAG, "******\nresumeAudio method called\n******");
            if (customMediaPlayer != null && !customMediaPlayer.isPlaying()) {
                customMediaPlayer.start();
                return "Success";
            }
            return "Failure";
        }

        /**
         * This would stop the Audio Clip.
         * If there is nothing to be stopped, this would throw an {@link IllegalStateException}
         * @throws RemoteException
         */
        @Override
        public String stopAudio() throws RemoteException {
            Log.i(TAG, "******\nstopAudio method called\n******");
            if (customMediaPlayer != null) {
                customMediaPlayer.stop();
                return "Success";
            }
            return "Failure";
        }
    };

    public AudioService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.i(TAG, "onBind method called");
        return iAudioServiceStub;
    }

    @Override
    public void onDestroy() {
        //  Release the resources when the service is destroyed
        if (null != customMediaPlayer) {
            customMediaPlayer.stop();
            customMediaPlayer.release();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        audioClips.add(R.raw.audio1);
        audioClips.add(R.raw.audio2);
        audioClips.add(R.raw.audio3);
        audioClips.add(R.raw.audio4);
    }
}
