package im.brianoneill.chatmap.controller.map_chat;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import im.brianoneill.chatmap.R;
import im.brianoneill.chatmap.controller.map_list.MapList;
import im.brianoneill.chatmap.utils.Constants;

public class MapChatActivity extends AppCompatActivity{

    TextView chatMapNameTextView;
    Bundle extras;
    RecyclerView recyclerView;
    ImageButton backToMapListBtn;
    Intent intent;
    FragmentManager fragmentManager;
    ChatFragment chatFragment;

    ImageButton micBtn;
    ImageView mic_anim_circle;
    TextView record_timer;


    RecordChat recordChat;
    CountDownTimer countDownTimer;


    private static String mFileName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_chat);

        Firebase.setAndroidContext(this);

        //custom method to initialize buttons
        initializeButtons();
        mic_anim_circle = (ImageView)findViewById(R.id.mic_anim_view);
        record_timer = (TextView)findViewById(R.id.record_timer);



        //custom method sets the map name
        //TODO: get date from Firebase and set appropriate textView
        setMapName();

        //set up bottom fragment with listView
        initializeFragment();

        //set the adapter for the recycler
        recyclerView = (RecyclerView)findViewById(R.id.chatMapRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        ChatMapUserIconAdapter chatMapUserIconAdapter = new ChatMapUserIconAdapter(this);
        recyclerView.setAdapter(chatMapUserIconAdapter);


        //artificial population of the adapter
        //TODO: populate from Firebase map instance
        for(int i = 0; i < 9; i++){
            chatMapUserIconAdapter.addUserIcon(this);
        }


    }//onCreate(Bundle savedInstanceState)

    // get the map name from the intent extra and set it to the textView
    private void setMapName(){
        chatMapNameTextView = (TextView)findViewById(R.id.chatMapNameTextView);
        extras = getIntent().getExtras();
        if(extras == null){
            return;
        }
        String mapName = extras.getString("MAP_NAME");
        chatMapNameTextView.setText(mapName);
    }//setMapName()

    private void initializeButtons(){

        //on touch start mic animation, make countdown visible and start recording
        //on action up stop recording stop animation, hide and reset countdown
        micBtn = (ImageButton)findViewById(R.id.micBtn);
        micBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Animation a;
                if(event.getAction() == MotionEvent.ACTION_DOWN) {

                    record_timer.setVisibility(View.VISIBLE);
                    a = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
                    mic_anim_circle.startAnimation(a);

                    //prepare the RecordChat object and star recording

                    recordChat = new RecordChat();
                    recordChat.onRecord(true);

                    countDownTimer = new CountDownTimer(6000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            record_timer.setText(String.valueOf(millisUntilFinished / 1000));
                        }

                        public void onFinish() {
                            //if time has run out, stop the recording automatically
                            if (recordChat != null) {
                                recordChat.onRecord(false);
                            }
                            record_timer.setVisibility(View.INVISIBLE);
                            mic_anim_circle.clearAnimation();

                            sendAudioToFireBase();
                        }
                    }.start();


                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    // stop the recording manually
                    if(recordChat != null){
                        recordChat.onRecord(false);
                        recordChat = null;
                    }

                    record_timer.setVisibility(View.INVISIBLE);
                    countDownTimer = null;
                    mic_anim_circle.clearAnimation();

                    sendAudioToFireBase();

                }
                return  false;
            }
        });


        backToMapListBtn = (ImageButton)findViewById(R.id.backToMapListBtn);
        backToMapListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), MapList.class);
                startActivity(intent);
            }
        });

    }//initializeButtons()

    private void initializeFragment(){
        chatFragment = new ChatFragment();
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().add(R.id.chatFragContainer, chatFragment).commit();
    }//initializeFragment()


    // get the audio file from the device
    private String getAudioFile(){
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";
        return mFileName;
    }


    // prepare audio file for Firebase
    private String encodedAudio(String audioFilePath){

        //http://stackoverflow.com/questions/36307464/android-base64-audio-file-encode-decode/36571318

        byte[] audioByteArray;
        String encodedAudio = "File not found";

        try{
            File chatFile = new File(audioFilePath);
            long fileSize = chatFile.length();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            FileInputStream fis = new FileInputStream(new File(audioFilePath));
            byte[] buf = new byte[1024];
            int n;
            while (-1 != (n = fis.read(buf)))
                byteArrayOutputStream.write(buf, 0, n);
            audioByteArray = byteArrayOutputStream.toByteArray();

            // Here goes the Base64 string
            encodedAudio = Base64.encodeToString(audioByteArray, Base64.DEFAULT);

            return encodedAudio;
        }catch (Exception e){
            e.printStackTrace();
        }
        // if try catch doesn't return the file, return "File not found"
        return encodedAudio;
    }


    private void sendAudioToFireBase(){
        //send audio to Firebase *****TEST******
        String encodedAudio = encodedAudio(getAudioFile());

        Firebase ref = new Firebase(Constants.FIREBASE_URL);
        ref.child("encodedAudio").setValue(encodedAudio);
        Log.e("TESTSTRING", "TRIGGERED");
        Log.e("ENCODED", String.valueOf(encodedAudio.length()));
    }


//    try {
//
//        FileOutputStream fos = new FileOutputStream(fileName);
//        fos.write(Base64.decode(base64AudioData.getBytes(), Base64.DEFAULT));
//        fos.close();
//
//        try {
//
//            mp = new MediaPlayer();
//            mp.setDataSource(path);
//            mp.prepare();
//            mp.start();
//
//        } catch (Exception e) {
//
//            DiagnosticHelper.writeException(e);
//
//        }
//
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//
//
//}

}// EOF