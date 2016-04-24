package im.brianoneill.chatmap.controller;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import im.brianoneill.chatmap.R;
import im.brianoneill.chatmap.controller.map_list.MapList;

public class IdCreatorActivity extends AppCompatActivity {

    //my application class references
    Button userIdDoneBtn;
    ImageButton cameraBtn;
    EditText usernameEditText;


    //android class references
    Toast usernameToast;
    Intent cameraIntent;

    //this.variables
    private String username;
    private String toastText = "Please enter a username first";
    private final String CHAT_MAP_USER_PIC = "Chat_Map_User_Pic";

    private static final int CAPTURE_IMAGE_REQUEST_CODE = 100;
    private String timeStamp = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create a dynamic layout using custom method
        setContentView(R.layout.activity_id_creator);

        cameraBtn = (ImageButton)findViewById(R.id.cameraBtn);
        userIdDoneBtn = (Button)findViewById(R.id.userIdDoneBtn);
        usernameEditText = (EditText)findViewById(R.id.usernameEditText);

        //set first fragment
        CheeseIconFragment cheeseIconFragment = new CheeseIconFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction  = manager.beginTransaction();
        transaction.add(R.id.fragSwapper, cheeseIconFragment).commit();


        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if the user has not entered a username then don't allow camera launch
                username = usernameEditText.getText().toString().trim();
                if(username == null || username.equals("")){
                    //tell the user to input their username
                    requestUserNameInputToast();
                }else{
                    //TODO save the username locally and to firebase
                    //launch camera
                    //Code given by Dr. Sahni in CameraExample
                    cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), CHAT_MAP_USER_PIC);
                        if (! mediaStorageDir.exists()) {
                            mediaStorageDir.mkdirs();
                        }
                        timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                        File mPhotoFile = new File(mediaStorageDir.getPath() + File.separator + username + timeStamp + ".jpg");
                        Uri mPhotoFileUri=Uri.fromFile(mPhotoFile);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,mPhotoFileUri );
                        startActivityForResult(cameraIntent, CAPTURE_IMAGE_REQUEST_CODE);
                    }
                }//if(username == null || username.equals("")) else

            }//onClick(View v)
        });//cameraBtn.setOnClickListener(new View.OnClickListener()

        //start the Map List if user has entered a username
        userIdDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username != null){
                    Intent intent = new Intent(getApplicationContext(), MapList.class);
                    startActivity(intent);
                }else{
                    //don't allow start of next activity until user has entered a username
                    requestUserNameInputToast();
                }
            }
        });//userIdDoneBtn.setOnClickListener(new View.OnClickListener()

        usernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });




    }//onCreate(Bundle savedInstanceState)


    /* helper methods
-------------------------------------------------------------------------------------*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), CHAT_MAP_USER_PIC);
        if (! mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }
        String mDisplayFolder = "Pictures" + File.separator + CHAT_MAP_USER_PIC + File.separator + username + timeStamp + ".jpg";
        File mPhotoFile = new File(mediaStorageDir.getPath() + File.separator + username + timeStamp + ".jpg");
        Uri mPhotoFileUri=Uri.fromFile(mPhotoFile);
        if (requestCode == CAPTURE_IMAGE_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mPhotoFileUri);
                    //bitmap = crupAndScale(bitmap, 300); // if you mind scaling
                    //pofileImageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if(bitmap != null){
                    Log.e("BITMAP", "EXISTS");
                }else{
                    Log.e("BITMAP", "DOESNOTEXIST");
                    // Log.e("FILEPATH", mPhotoFileUri.toString());
                }

                //if an image is returned pass it to DragDropUserIconview and set as Bitmap
                DragDropUserIconView dragDropUserIconView = new DragDropUserIconView(getApplicationContext());
                dragDropUserIconView.setUserIconImage(bitmap);

                //swap the fragments to show the result to the user
                UserIconFragment userIconFragment = new UserIconFragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction  = manager.beginTransaction();
                transaction.replace(R.id.fragSwapper, userIconFragment);
                transaction.commit();


                Toast.makeText(this, "Full filename:\n"+mPhotoFile.toString(), Toast.LENGTH_LONG).show();
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(mPhotoFileUri);
                this.sendBroadcast(mediaScanIntent);
            } else if (resultCode == RESULT_CANCELED) {
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }//onActivityResult(int requestCode, int resultCode, Intent data)

    //this code from stack overflow to hide keyboard on touch of the background
    //http://stackoverflow.com/questions/4165414/how-to-hide-soft-keyboard-on-android-after-clicking-outside-edittext
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private void requestUserNameInputToast(){
        usernameToast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG);
        usernameToast.setGravity(Gravity.TOP, 0, 100);
        usernameToast.show();
    }





}//EOF

