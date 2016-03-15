package im.brianoneill.chatmap.controller.map_chat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import im.brianoneill.chatmap.R;

/**
 * Created by brianoneill on 15/03/16.
 * based on tutorial by Greenhouse Channel https://www.youtube.com/watch?v=9WvATKrzqQM
 */
public class ChatMapUserIconListHolder extends RecyclerView.ViewHolder {

    // holds references to all individual listView items and in it's inner views
    ImageView userIcon;

    public ChatMapUserIconListHolder(View itemView) {
        super(itemView);
        userIcon = (ImageView) itemView.findViewById(R.id.chatMapUserIconImageView);
    }
}
