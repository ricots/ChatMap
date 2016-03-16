package im.brianoneill.chatmap.controller.map_creation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import im.brianoneill.chatmap.R;
import im.brianoneill.chatmap.controller.map_list.MapList;

public class MapInvite extends AppCompatActivity {

    Button messageDoneBtn;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_invite);

        //set up button
        setUpMessageScreenButtons();

    }//onCreate(Bundle savedInstanceState)


    private void setUpMessageScreenButtons(){

        messageDoneBtn = (Button)findViewById(R.id.messageDoneBtn);
        messageDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), MapList.class);
                startActivity(intent);
            }
        });
    }//setUpMessageScreenButtons()
}//EOF
