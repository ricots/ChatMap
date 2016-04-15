package im.brianoneill.chatmap.controller;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import im.brianoneill.chatmap.R;

/**
 * Created by brianoneill on 15/04/16.
 */
public class UserIconFragment extends Fragment {

//    fragment to be used when the user takes their photo for the userID set up

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("ONCREATEVIEW", "CALLED");
        View userIconFragView = inflater.inflate(R.layout.drap__drop_user_ic, container, false);
        Log.e("INFLATER", "CALLED");
        return userIconFragView;
    }
}
