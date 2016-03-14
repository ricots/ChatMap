package im.brianoneill.chatmap.controller.map_creation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import im.brianoneill.chatmap.R;
import im.brianoneill.chatmap.model.Person;

/**
 * Created by brianoneill on 14/03/16.
 */
public class AddContactsAdapter extends ArrayAdapter {

    private final Context context;
    private final ArrayList<Person> persons;

    public AddContactsAdapter(Context context, ArrayList<Person> persons){
        super(context, -1, persons);
        this.context = context;
        this.persons = persons;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView ==  null){
            convertView = inflater.inflate(R.layout.contacts_list_row, parent, false);

            ImageView userIconContactsImageView = (ImageView)convertView.findViewById(R.id.userIconContactsImageView);
            TextView contactUsernameTextView = (TextView)convertView.findViewById(R.id.contactUsernameTextView);

            BitmapDrawable userIcon = persons.get(position).getUserIcon();
            String  contactUsername = persons.get(position).getUsername();

            userIconContactsImageView.setImageResource(userIcon);






        return  convertView;
    }
}
