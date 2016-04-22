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
    private String encodedAudio;
    private String email;
    private String password;


    Context context;

    private Bitmap soundWave;

    //Required Firebase empty Constructor
    public Person(){

    }

    //internal construtor for sign up
    public Person(String email, String password){
        this.email = email;
        this.password = password;
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

//    public Person(String username, )

//    public Person(String email, String password){
//        this.email = email;
//        this.password = password;
//    }


    //setters and getters for username and icon
    //TODO: find out how to deal with password
    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public Bitmap getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(Bitmap userIcon) {
        this.userIcon = userIcon;
    }

    public Bitmap getSoundWave(){
        return soundWave;
    }


}
