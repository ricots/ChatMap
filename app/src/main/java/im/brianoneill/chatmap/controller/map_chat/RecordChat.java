package im.brianoneill.chatmap.controller.map_chat;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by brianoneill on 15/04/16.
 */
public class RecordChat {

    private static String mFileName = null;
    private static final String LOG_TAG = "AudioRecordTest";
    //private RecordButton mRecordButton = null;

    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;

    boolean isRecording = false;

    //variable to calculate a maximum recording time of five seconds
    long currentTime;
    long currentTimePlus5;
    final long FIVE_SECONDS = 5000;
    DecimalFormat df;

    //my method
    //constructor
    public RecordChat() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";
        //when the object gets created compute the current time and five seconds from now
        currentTime = System.currentTimeMillis();
        currentTimePlus5 = currentTime + FIVE_SECONDS;

        //format time remaining to two decimal places
        df = new DecimalFormat("#.00");
    }


    public void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
        //for max 5 seconds
        // continually update the current time unit it exceeds five seconds
        while(currentTime < currentTimePlus5){
            currentTime = System.currentTimeMillis();
            Log.e("CURRENTTIME", String.valueOf(currentTime));
        }
        //move stop recording
        stopRecording();

    }

    public void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }


    public void onPause() {
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    //getter for the time left on record
    public String getRecordTimeLeft(){
        long recordTimeRemaining = (currentTimePlus5 - currentTime)/1000;
        Double doubleValueOFTimeRemaining = Double.valueOf(recordTimeRemaining);
        return df.format(doubleValueOFTimeRemaining);
    }


}
