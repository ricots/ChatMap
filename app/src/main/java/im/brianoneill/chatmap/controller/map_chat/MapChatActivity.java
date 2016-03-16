package im.brianoneill.chatmap.controller.map_chat;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import im.brianoneill.chatmap.R;
import im.brianoneill.chatmap.controller.map_list.MapList;

public class MapChatActivity extends AppCompatActivity {

    TextView chatMapNameTextView;
    Bundle extras;
    RecyclerView recyclerView;
    ImageButton backToMapListBtn;
    Intent intent;
    FragmentManager fragmentManager;
    ChatFragment chatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_chat);

        //custom method to initialize buttons
        initializeButtons();

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
}
