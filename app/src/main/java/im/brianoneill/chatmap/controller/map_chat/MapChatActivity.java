package im.brianoneill.chatmap.controller.map_chat;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import im.brianoneill.chatmap.R;
import im.brianoneill.chatmap.controller.map_list.MapList;

public class MapChatActivity extends AppCompatActivity implements Runnable {

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

    long currentTime;
    long currentTimePlus5;
    boolean isRunnable = true;

    RecordChat recordChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_chat);

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
        //on action up stop recording stop animimation, hide and reset countdown
        micBtn = (ImageButton)findViewById(R.id.micBtn);
        micBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Animation a;
                if(event.getAction() == MotionEvent.ACTION_DOWN) {

                    record_timer.setVisibility(View.VISIBLE);
                    a = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
                    mic_anim_circle.startAnimation(a);
                    isRunnable = true;

                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    // recordChat.stopRecording();
                    isRunnable = false;
                    record_timer.setVisibility(View.INVISIBLE);
                    mic_anim_circle.clearAnimation();
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

    @Override
    public void run() {

        while (isRunnable) {
            //recordChat = new RecordChat();
            currentTime = System.currentTimeMillis();
            //currentTimePlus5 = currentTime + 5000;
            record_timer.setText(String.valueOf(currentTime));

        }
    }// run()


}// EOF