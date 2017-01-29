// IAudioServiceStub.aidl
package edu.kushagrathapar.cs478.stub;

// Declare any non-default types here with import statements

interface IAudioServiceStub {

    /**
    * API to play the audio present at the server with this number
    */
    String playAudio(int number);

    /**
    * API to pause the running audio clip.
    */
    String pauseAudio();

    /**
    * API to resume the paused audio clip.
    */
    String resumeAudio();

    /**
    * API to stop the running audio clip.
    */
    String stopAudio();
}
