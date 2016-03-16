package im.brianoneill.chatmap.controller.map_creation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import im.brianoneill.chatmap.R;

public class MapLocationActivity extends AppCompatActivity {

    Intent intent;
    ImageButton backToMapCreatorBtn;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_location);

        // custom method to set up buttons with listeners
        initializeLocationScreenButtons();

    }//onCreate(Bundle savedInstanceState)


    private void initializeLocationScreenButtons(){

        backToMapCreatorBtn = (ImageButton)findViewById(R.id.backToMapCreatorBtn);
        done = (Button)findViewById(R.id.setLocationDoneBtn);

        backToMapCreatorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), MapCreatorActivity.class);
                startActivity(intent);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), MapCreatorActivity.class);
                startActivity(intent);
            }
        });

    }//initializeLocationScreenButtons()

}//EOF
