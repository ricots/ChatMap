package im.brianoneill.chatmap.controller.map_chat;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

/**
 * Created by brianoneill on 15/04/16.
 */
public class RecordChat extends AsyncTask<Void, Double, Void> {

    private static String mFileName = null;
    private static final String LOG_TAG = "AudioRecordTest";
    //private RecordButton mRecordButton = null;

    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;

    boolean isRecording = false;

    //vars to calculate allowable record time set in startRecording


    //my method
    //constructor
    public RecordChat() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";
    }


    private void onRecord(boolean start) {
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

        //move stop recording

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

   /* Async Task methods
   ---------------------------------------------------------------------------------- */



    @Override
    protected Void doInBackground(Void... params) {
        //execute will call do in background from main thread and start recording
        //startRecording();
        return null;
    }


}
