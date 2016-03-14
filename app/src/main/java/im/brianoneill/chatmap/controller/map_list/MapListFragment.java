package im.brianoneill.chatmap.controller.map_list;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import im.brianoneill.chatmap.R;
import im.brianoneill.chatmap.model.ChatMap;

/**
 * Created by brianoneill on 12/03/16.
 */
public class MapListFragment extends Fragment implements View.OnClickListener {

    ListView mapListView;
    MapListAdapter mapListAdapter;
    ArrayList<ChatMap> fakeList;
    MapListFragmentInterface mapListFragmentInterface;
    ImageButton createChatMapButton;
    View chatMapListFragView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        chatMapListFragView = inflater.inflate(R.layout.chat_map_list_frag, container, false);

        fakeList = new ArrayList<>();
        //TODO: populate from Firebase
        fakeList.add(new ChatMap("cafe klatch", "2 : 5: 55 : 23"));
        fakeList.add(new ChatMap("cafe klatch", "2 : 5: 55 : 23"));
        fakeList.add(new ChatMap("cafe klatch", "2 : 5: 55 : 23"));
        fakeList.add(new ChatMap("cafe klatch", "2 : 5: 55 : 23"));
        fakeList.add(new ChatMap("cafe klatch", "2 : 5: 55 : 23"));

        mapListView = (ListView)chatMapListFragView.findViewById(R.id.mapListView);
        mapListAdapter = new MapListAdapter(getActivity(),fakeList);
        mapListView.setAdapter(mapListAdapter);

        return chatMapListFragView;
    }

    //when the createChatMapButton is clicked startMapCreatorActivity() will respond through
    //the interface starting the MapCreator Activity
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createChatMapButton = (ImageButton)chatMapListFragView.findViewById(R.id.createChatMapBtn);
        createChatMapButton.setOnClickListener(this);
        mapListFragmentInterface = (MapListFragmentInterface)getActivity();
    }


    @Override
    public void onClick(View v) {
        mapListFragmentInterface.startMapCreatorActivity();
    }

    public interface MapListFragmentInterface{
        void startMapCreatorActivity();
    }
}
