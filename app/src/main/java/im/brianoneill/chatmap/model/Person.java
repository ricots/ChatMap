package im.brianoneill.chatmap.model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by brianoneill on 14/03/16.
 */
public class Person {

    private String username;
    private BitmapDrawable userIcon;
    private String email;
    private String password;


    //constructors
    public Person(String username, BitmapDrawable userIcon){
        this.username = username;
        this.userIcon = userIcon;
    }

    public Person(String email, String password){
        this.email = email;
        this.password = password;
    }


    //setters and getters for username and icon
    //TODO: find out how to deal with password
    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public BitmapDrawable getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(BitmapDrawable userIcon) {
        this.userIcon = userIcon;
    }




}
