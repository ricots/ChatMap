package im.brianoneill.chatmap.controller.map_list;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import im.brianoneill.chatmap.R;
import im.brianoneill.chatmap.model.ChatMap;

/**
 * Created by brianoneill on 12/03/16.
 */
public class MapListFragment extends Fragment {

    ListView mapListView;
    MapListAdapter mapListAdapter;
    ArrayList<ChatMap> fakeList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View chatMapListFragView = inflater.inflate(R.layout.chat_map_list_frag, container, false);

        fakeList = new ArrayList<>();
        //TODO: populate from Firebase
        fakeList.add(new ChatMap("cafe klatch", "2 : 5: 55 : 23"));
        fakeList.add(new ChatMap("cafe klatch", "2 : 5: 55 : 23"));
        fakeList.add(new ChatMap("cafe klatch", "2 : 5: 55 : 23"));
        fakeList.add(new ChatMap("cafe klatch", "2 : 5: 55 : 23"));
        fakeList.add(new ChatMap("cafe klatch", "2 : 5: 55 : 23"));

        mapListView = (ListView)chatMapListFragView.findViewById(R.id.mapListView);
        mapListAdapter = new MapListAdapter(getActivity(),fakeList);

        return chatMapListFragView;
    }
}
