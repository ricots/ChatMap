package im.brianoneill.chatmap.controller;

/**
 * Brian ONeill 15023745 16 March 2016
 * Higher Diploma in Science in Computing - Mobile
 * firebase backend: https://inchatmap.firebaseio.com/
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;


import im.brianoneill.chatmap.R;

import im.brianoneill.chatmap.controller.map_list.MapList;
import im.brianoneill.chatmap.utils.Constants;


public class MainActivity extends AppCompatActivity {

    Button loginBtn, signUp;
    EditText userEmail, userPassword;
    Firebase firebase;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        //get reference to firebase to create new user accounts
         firebase = new Firebase(Constants.FIREBASE_URL);

        loginBtn = (Button)findViewById(R.id.loginBtn);
        signUp = (Button)findViewById(R.id.createAccBtn);

        userEmail = (EditText) findViewById(R.id.emailEditText);
        userPassword = (EditText)findViewById(R.id.passwordEditText);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e_target = getEmailEditText();
                String p_target = getPasswordEditText();
                //validate email
                if (e_target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(e_target).matches()){
                    showFireBaseProgressDialog(getResources().getString(R.string.creating_user_account));
                    createNewFirebaseUserAccount(e_target, p_target);
                Intent intent = new Intent(getApplicationContext(), IdCreatorActivity.class);
                startActivity(intent);
                }else{
                    userEmail.setError(getResources().getString(R.string.invalid_email));
                }
            }//onClick(View v)
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e_target = getEmailEditText();
                String p_target = getPasswordEditText();

//                showFireBaseProgressDialog(getResources().getString(R.string.authenticating));
//                loginUserToFirebase(e_target, p_target);
                Intent intent = new Intent(getApplicationContext(), MapList.class);
                startActivity(intent);

            }//onClick(View v)
        });
    }


    private void createNewFirebaseUserAccount(String email, String password){
        firebase.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>(){

            @Override
            public void onSuccess(Map<String, Object> stringObjectMap) {
                Log.e("SUCCESS", "CREATED");
                progressDialog.dismiss();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                if (firebaseError.getCode() == FirebaseError.EMAIL_TAKEN) {
                    //if the email is already taken display an error to the user
                    userEmail.setError(getString(R.string.email_taken));
                    progressDialog.dismiss();
                } else {
                    showGenericFirebaseErrorMessage();
                }

            }
        });
    }

    private void loginUserToFirebase(String email, String password){
        firebase.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData){
                  progressDialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), MapList.class);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                if(firebaseError.getCode() == FirebaseError.USER_DOES_NOT_EXIST){
                    userEmail.setError(getString(R.string.user_does_not_exist));
                }
                if(firebaseError.getCode() == FirebaseError.INVALID_PASSWORD){
                    userPassword.setError(getString(R.string.invalid_password));
                }
                progressDialog.dismiss();
            }
        });
    }

    private String getEmailEditText(){
        String emailEditText = userEmail.getText().toString();
        return emailEditText;
    }

    private String getPasswordEditText(){
        String passwordEditText = userPassword.getText().toString();
        return passwordEditText;
    }


    private void showFireBaseProgressDialog(String userMessage){
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle(getResources().getString(R.string.contacting_firebase));
        progressDialog.setMessage(userMessage);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void showGenericFirebaseErrorMessage(){
        Toast.makeText(getApplicationContext(), "something went wrong try again later", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
