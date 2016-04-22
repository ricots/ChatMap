package im.brianoneill.chatmap.controller.map_list;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import im.brianoneill.chatmap.R;
import im.brianoneill.chatmap.controller.map_chat.MapChatActivity;
import im.brianoneill.chatmap.controller.map_creation.MapCreatorActivity;

public class MapList extends AppCompatActivity implements MapListFragment.MapListFragmentInterface {

    MapListFragment mapListFragment;
    FragmentManager fragmentManager;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_list);

        //custom method to set up reference to MapListFragment and a fragment manager
        initializeFragmentManager();


    }//onCreate()

    //add the mapListFragment to the chatMapListFrame
    private void initializeFragmentManager(){
        mapListFragment = new MapListFragment();
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().add(R.id.chatMapListFrame, mapListFragment).commit();
    }

    //implementation of interface method to start map creator activity
    @Override
    public void startMapCreatorActivity() {
        intent = new Intent(getApplicationContext(), MapCreatorActivity.class);
        startActivity(intent);
    }

    //responds to list item clicks and starts the appropriate Chat Map
    @Override
    public void startChatMapActivity(String mapName) {
        intent = new Intent(getApplicationContext(), MapChatActivity.class);
        intent.putExtra("MAP_NAME", mapName);
        startActivity(intent);
    }
}
