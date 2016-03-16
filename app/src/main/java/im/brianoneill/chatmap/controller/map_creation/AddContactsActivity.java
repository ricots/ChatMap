package im.brianoneill.chatmap.controller.map_creation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import im.brianoneill.chatmap.R;
import im.brianoneill.chatmap.model.Person;

public class AddContactsActivity extends AppCompatActivity {

    ImageButton backToMapCreatorBtn;
    ImageButton msgBtn;
    Intent intent;
    ListView listView;
    AddContactsAdapter addContactsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);

        //custom method to set up buttons
        initializeContactScreenButtons();
        listView = (ListView)findViewById(R.id.addContactsListView);

        //tmp code to demo a list of people in the listView
        ArrayList<Person> persons = new ArrayList<>();
        persons.add(new Person("John", this));
        persons.add(new Person("Joe", this));
        persons.add(new Person("Connor", this));

        addContactsAdapter = new AddContactsAdapter(this, persons);
        listView.setAdapter(addContactsAdapter);



    }//onCreate(Bundle savedInstanceState)

    private void initializeContactScreenButtons(){

        backToMapCreatorBtn = (ImageButton)findViewById(R.id.backToMapCreatorBtn);
        msgBtn = (ImageButton)findViewById(R.id.msgBtn);

        backToMapCreatorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), MapCreatorActivity.class);
                startActivity(intent);
            }
        });

        msgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), MapInvite.class);
                startActivity(intent);
            }
        });
    }//initializeContactScreenButtons()

}//EOF
