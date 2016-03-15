package im.brianoneill.chatmap.controller.map_chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import im.brianoneill.chatmap.R;

public class MapChatActivity extends AppCompatActivity {

    TextView chatMapNameTextView;
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_chat);

        //custom method sets the map name
        //TODO: get date from Firebase and set appropriate textView
        setMapName();

    }

    // get the map name from the intent extra and set it to the textView
    private void setMapName(){
        chatMapNameTextView = (TextView)findViewById(R.id.chatMapNameTextView);
        extras = getIntent().getExtras();
        if(extras == null){
            return;
        }
        String mapName = extras.getString("MAP_NAME");
        chatMapNameTextView.setText(mapName);
    }
}
