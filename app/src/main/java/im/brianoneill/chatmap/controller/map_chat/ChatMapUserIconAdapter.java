package im.brianoneill.chatmap.controller.map_chat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import im.brianoneill.chatmap.R;
import im.brianoneill.chatmap.model.Person;

/**
 * Created by brianoneill on 15/03/16.
 */
public class ChatMapUserIconAdapter extends RecyclerView.Adapter<ChatMapUserIconListHolder> {

    private final ArrayList<Person> persons;
    Context context;

    public ChatMapUserIconAdapter(Context context){
        this.context = context;
        persons = new ArrayList<>();
    }

    //artificially add user icon
    //TODO: dynamically load icon from firebase object
    public void addUserIcon(Context context){
        persons.add(new Person("bob", context));
        notifyItemInserted(persons.size() -1);
    }

    //for later use
    public void removeUserIcon(String name){
        int position = persons.indexOf(name);
        persons.remove(position);
        notifyItemRemoved(position);
    }

    //responsible for inflating a particular layout
    @Override
    public ChatMapUserIconListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_user_icon, parent, false);

        return new ChatMapUserIconListHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatMapUserIconListHolder holder, int position) {
        Bitmap userIcon = persons.get(position).getUserIcon();
        Drawable d = new BitmapDrawable(context.getResources(), userIcon);
        holder.userIcon.setImageDrawable(d);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

}
