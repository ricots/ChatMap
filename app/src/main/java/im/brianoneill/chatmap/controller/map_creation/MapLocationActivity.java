package im.brianoneill.chatmap.controller.map_creation;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import im.brianoneill.chatmap.R;
import im.brianoneill.chatmap.model.RealmMap;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MapLocationActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    Intent intent;
    ImageButton backToMapCreatorBtn;
    ImageButton mapPinButton;
    Button done;
    EditText locationSearchEditText;
    EditText setMapNameEditText;

    GoogleApiClient googleApiClient;
    protected Location lastLocation;
    private double lastLatitude;
    private double lastLongitude;
    private String mapName;

    private String locationSearch;
    private double searchLatitude;
    private double searchLongitude;
    private final float ZOOM_LEVEL = 17;

    static final int SET_LOCATION_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        buildGoogleApiClient();

        // custom method to set up buttons with listeners
        initializeLocationScreenButtons(findViewById(R.id.map_location_layout));
    }


    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lastLatitude, lastLongitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Last Known Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }


    private void initializeLocationScreenButtons(View view) {

        backToMapCreatorBtn = (ImageButton) findViewById(R.id.backToMapCreatorBtn);
        done = (Button) findViewById(R.id.setLocationDoneBtn);
        mapPinButton = (ImageButton) findViewById(R.id.mapPinBtn);
        locationSearchEditText = (EditText)findViewById(R.id.locationSearchEditText);
        setMapNameEditText = (EditText)findViewById(R.id.setMapNameEditText);
        setMapNameEditText.setFocusable(true);
        setMapNameEditText.setFocusableInTouchMode(true);


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
                //move screen up


                //set the map name
                mapName = setMapNameEditText.getText().toString();
                //confirm
                confirmMapName(mapName);


            }
        });

        mapPinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //attempt location search for the user
                try {
                    geolocate(v);
                    if(searchLatitude != 0 && searchLongitude != 0){
                        moveToSearchLocation(searchLatitude, searchLongitude, ZOOM_LEVEL);
                    }else{
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.unable_to_find_location), Toast.LENGTH_LONG).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(v);
                    return false;
                }

            });
        }

    }//initializeLocationScreenButtons()

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (lastLocation != null) {
            // set latitude and longitude
            lastLatitude = lastLocation.getLatitude();
            lastLongitude = lastLocation.getLongitude();
        } else {
            Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    //hide keyboard when user perfoms search
    private void hideSoftKeyboard(View view){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //attempt location search based on user input
    private void geolocate(View view) throws IOException {
        hideSoftKeyboard(view);

        //get the searched location
        locationSearch = locationSearchEditText.getText().toString();
        if(locationSearch == null){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_location_detected), Toast.LENGTH_LONG).show();
            return;
        }else if (locationSearch.equals("")){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_location_detected), Toast.LENGTH_LONG).show();
            return;
        }
        Geocoder geocoder  = new Geocoder(this);
        //limit to 1 address for now - will return a fuller list when I have more time to implement
        List<Address> addressList = geocoder.getFromLocationName(locationSearch, 1);
        Address address = addressList.get(0);

        //get the latitude and longitude of the returned address
        searchLatitude = address.getLatitude();
        searchLongitude = address.getLongitude();

        //test get lang and long
        //Toast.makeText(this, String.valueOf(searchLatitude) + " " + String.valueOf(searchLongitude), Toast.LENGTH_LONG).show();

    }

    private void moveToSearchLocation(double latitude, double longitude, float zoom){
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(latLng).title("Add a Map Name"));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        mMap.moveCamera(cameraUpdate);

    }

    //show alert dialog confirming map name for user
    private void confirmMapName(final String mapName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Confirm Map Name: " + mapName)
                .setPositiveButton(R.string.confirm_map_name, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // database
                        RealmMap realmMap = new RealmMap();
                        realmMap.setRealmMapName(mapName);
                        realmMap.setRealmLatitude(searchLatitude);
                        realmMap.setRealmLongitude(searchLongitude);
                        // Create a RealmConfiguration which is to locate Realm file in package's "files" directory.
                        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getApplicationContext()).build();
                        // Get a Realm instance for this thread
                        Realm realm = Realm.getInstance(realmConfig);
                        // commit to database
                        realm.beginTransaction();
                        realm.copyToRealm(realmMap);
                        realm.commitTransaction();

                        //go back to Map Creator Activity and set text to red color
                        intent = new Intent();
                        intent.putExtra("HAS_LOCATION", true);
                        setResult(SET_LOCATION_REQUEST, intent);
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        builder.create();
        builder.show();
    }

}//EOF
