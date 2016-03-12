package im.brianoneill.chatmap.controller.map_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import im.brianoneill.chatmap.R;
import im.brianoneill.chatmap.model.ChatMap;

/**
 * Created by brianoneill on 11/03/16.
 */
public class MapListAdapter extends ArrayAdapter {


    private final Context context;
    private final ArrayList<ChatMap> mapList;

    public MapListAdapter(Context context, ArrayList<ChatMap> mapList){
        super(context, -1, mapList);
        this.context = context;
        this.mapList = mapList;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mapListView = inflater.inflate(R.layout.map_list_row, parent, false);

        TextView mapListNameTextView = (TextView)mapListView.findViewById(R.id.mapListNameTextView);
        TextView mapListTimeRemaining = (TextView)mapListNameTextView.findViewById(R.id.mapListTimeRemainingTextView);


        //TODO: this data will be populated by a returned Firebase object
        //TODO: format time
        String mapName = mapList.get(position).getMapName();
        String timeRemaining = mapList.get(position).getTimeToLive();

        mapListNameTextView.setText("@" + mapName);
        mapListTimeRemaining.setText(timeRemaining);



        return mapListView;
    }
}
