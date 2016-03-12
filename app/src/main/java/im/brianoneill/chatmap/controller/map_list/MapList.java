package im.brianoneill.chatmap.controller.map_list;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import im.brianoneill.chatmap.R;

public class MapList extends AppCompatActivity {

    MapListFragment mapListFragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_list);

        //custom method to set up reference to MapListFragment and a fragment manager
        initializeFragmentManager();


    }//onCreate()

    private void initializeFragmentManager(){
        mapListFragment = new MapListFragment();
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().add(R.id.chatMapListFrame, mapListFragment).commit();
    }
}
