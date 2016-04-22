package im.brianoneill.chatmap.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import im.brianoneill.chatmap.R;


/**
 * Created by brianoneill on 14/03/16.
 */
public class Person{


    private String username;
    private Bitmap userIcon;
    private Bitmap soundWave;
    private String encodedAudio;

    Context context;

    //Required Firebase empty Constructor
    public Person(){

    }


    //internal constructor for the application
    //TODO: add Bitmap userIcon to the constructor
    public Person(String username, Context context) {
        this.username = username;
        this.context = context;
        //code to show how a bitmap might be used
        //TODO: remove BitmapFactories and use constructor
        this.userIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.tmp_headshot);
        this.soundWave = BitmapFactory.decodeResource(context.getResources(), R.drawable.soundwave);

    }

    //firebase constructor
    //TODO: add usericon
    public Person(String username, String encodedAudio) {
        this.username = username;
        this.encodedAudio = encodedAudio;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public Bitmap getUserIcon() {
        return userIcon;
    }

    public String getEncodedAudio() {
        return encodedAudio;
    }

    //replace default user icon if user has used camera
    public void setUserIcon(Bitmap userIcon) {
        this.userIcon = userIcon;
    }

    public Bitmap getSoundWave(){
        return soundWave;
    }


}
