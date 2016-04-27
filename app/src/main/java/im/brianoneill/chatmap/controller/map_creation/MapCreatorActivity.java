package im.brianoneill.chatmap.controller.map_creation;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import im.brianoneill.chatmap.R;
import im.brianoneill.chatmap.controller.map_list.MapList;

public class MapCreatorActivity extends AppCompatActivity {

    static final int SET_LOCATION_REQUEST = 1;

    ImageButton backToMapListBtn, setMapLocationBtn, setDateTimeBtn, addContactsBtn;
    Button mapCreatorDoneBtn;
    Intent intent;
    DialogFragment datePickerFragment;
    FragmentManager fragmentManager;

    TextView setLocationTextView, setDateTimeTextView, addContactsTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_creator);

        initializeMapCreatorButtons();
        initializeButtonListeners();

        fragmentManager = getFragmentManager();

    }//onCreate(Bundle savedInstanceState)

    //set up button references and listeners
    private void initializeMapCreatorButtons(){

        backToMapListBtn = (ImageButton)findViewById(R.id.backToMapListBtn);
        setMapLocationBtn = (ImageButton)findViewById(R.id.setMapLocationBtn);
        setDateTimeBtn = (ImageButton)findViewById(R.id.setDateTimeBtn);
        addContactsBtn = (ImageButton)findViewById(R.id.addContactsBtn);
        mapCreatorDoneBtn = (Button)findViewById(R.id.mapCreatorDoneBtn);

        //get reference to textviews to set colour on activity result
        setLocationTextView = (TextView)findViewById(R.id.setLocationTextView);
        setDateTimeTextView = (TextView)findViewById(R.id.setDateTimeTextView);
        addContactsTextView = (TextView)findViewById(R.id.addContactsTextView);

    }//initializeMapCreatorButtons()

    //setOnClickListeners for each of the buttons
    private void initializeButtonListeners(){

        backToMapListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), MapList.class);
                startActivity(intent);
            }
        });

        setMapLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), MapLocationActivity.class);
                startActivityForResult(intent, SET_LOCATION_REQUEST);
            }
        });

        setDateTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pop up dialog fragment
                showDatePickerDialog(v);
            }
        });

        addContactsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), AddContactsActivity.class);
                startActivity(intent);
            }
        });


        mapCreatorDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: activate only when map set up complete
            }
        });

    }//initializeButtonListeners()

    //When the user clicks setDateTimeBtn, the system calls the following method:
    public void showDatePickerDialog(View v) {
        datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(fragmentManager, "datePicker");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to

        Log.e("TRIGGER", "ACTIVITY RESULT");
        if (requestCode == SET_LOCATION_REQUEST) {
            // Make sure the request was successful
            Log.e("TRIGGER", "SET LOCATION");

                if(data != null){
                    //has location been set?
                    Log.e("TRIGGER", "HAS DATA");
                    boolean has_location = data.getBooleanExtra("HAS_LOCATION", true);
                    if(has_location){
                        Log.e("TRIGGER", "HAS LOCATION");
                        setLocationTextView.setTextColor(getResources().getColor(R.color.chatMapRed));
                    }
                }

        }
    }//onActivityResult()

}//EOF
