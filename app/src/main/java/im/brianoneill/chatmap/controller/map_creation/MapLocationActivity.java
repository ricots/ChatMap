package im.brianoneill.chatmap.controller.map_creation;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import im.brianoneill.chatmap.R;

public class MapLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Intent intent;
    ImageButton backToMapCreatorBtn;
    ImageButton mapPinButton;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // custom method to set up buttons with listeners
        initializeLocationScreenButtons();
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
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }


    private void initializeLocationScreenButtons(){

        backToMapCreatorBtn = (ImageButton)findViewById(R.id.backToMapCreatorBtn);
        done = (Button)findViewById(R.id.setLocationDoneBtn);
        mapPinButton = (ImageButton)findViewById(R.id.mapPinBtn);

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

        mapPinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send map and data to firebase
            }
        });

    }//initializeLocationScreenButtons()


    //add fake map and data to firebase

}
