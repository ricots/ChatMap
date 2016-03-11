package im.brianoneill.chatmap.controller.map_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import im.brianoneill.chatmap.R;

/**
 * Created by brianoneill on 11/03/16.
 */
public class MapListAdapter extends ArrayAdapter {


    private final Context context;
    private final ArrayList<HashMap<String, String>> mapList;

    public MapListAdapter(Context context, ArrayList<HashMap<String, String>> mapList){
        super(context, -1, mapList);
        this.context = context;
        this.mapList = mapList;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mapListView = inflater.inflate(R.layout.map_list_row, parent, false);


        return mapListView;
    }
}
