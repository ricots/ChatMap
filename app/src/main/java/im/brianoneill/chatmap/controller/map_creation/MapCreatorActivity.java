package im.brianoneill.chatmap.controller.map_creation;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;

import im.brianoneill.chatmap.R;
import im.brianoneill.chatmap.controller.map_list.MapList;

public class MapCreatorActivity extends AppCompatActivity {

    ImageButton backToMapListBtn, setMapLocationBtn, setDateTimeBtn, addContactsBtn;
    Button mapCreatorDoneBtn;
    Intent intent;
    DialogFragment datePickerFragment;
    FragmentManager fragmentManager;


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
                startActivity(intent);
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


}
