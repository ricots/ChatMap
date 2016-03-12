package im.brianoneill.chatmap.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import im.brianoneill.chatmap.R;
import im.brianoneill.chatmap.controller.map_list.MapList;

public class IdCreatorActivity extends AppCompatActivity {

    Button userIdDoneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_creator);

        userIdDoneBtn = (Button)findViewById(R.id.userIdDoneBtn);

        userIdDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapList.class);
                startActivity(intent);
            }
        });
    }
}
