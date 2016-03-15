package im.brianoneill.chatmap.controller.map_chat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import im.brianoneill.chatmap.R;
import im.brianoneill.chatmap.model.Person;

/**
 * Created by brianoneill on 15/03/16.
 */
public class ChatFragmentListAdapter extends ArrayAdapter {

    Context context;
    ArrayList<Person> persons;

    public ChatFragmentListAdapter(Context context, ArrayList<Person> persons){
        super(context, -1, persons);
        this.context = context;
        this.persons = persons;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            convertView = inflater.inflate(R.layout.chat_fragment_row, parent, false);

            ImageView chatFragUserIconForListView = (ImageView)convertView.findViewById(R.id.chatFragUserIconForListView);
            ImageView soundWave = (ImageView)convertView.findViewById(R.id.soundWave);


            //get user icon and set to the imageView
            Bitmap userIcon = persons.get(position).getUserIcon();
            Drawable userIconDrawable = new BitmapDrawable(context.getResources(), userIcon);
            chatFragUserIconForListView.setImageDrawable(userIconDrawable);

            //temp solution: get sound wave image TODO: replace with animation
            Bitmap soundWaveBitmap = persons.get(position).getSoundWave();
            Drawable soundWaveDrawable = new BitmapDrawable(context.getResources(), soundWaveBitmap);
            soundWave.setImageDrawable(soundWaveDrawable);


        }
        return convertView;
    }
}
