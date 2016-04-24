package im.brianoneill.chatmap.controller;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import im.brianoneill.chatmap.R;

/**
 * Created by brianoneill on 15/04/16.
 */
public class CheeseIconFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View cheeseIconFragView = inflater.inflate(R.layout.cheese_image, container, false);
        return cheeseIconFragView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}