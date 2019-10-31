package com.codeshot.skillupfb.MAPs;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codeshot.skillupfb.AddNote.AddNoteActivity;
import com.codeshot.skillupfb.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
        , GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    //MAP
    private GoogleMap mMap;
    private LocationManager locationManager;
    private Location currentLocation,lastKnowLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //VIEW
        Button btnSent;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btnSent=findViewById(R.id.btnSentCurrentLocation);
        btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MapsActivity.this, AddNoteActivity.class);
                if (currentLocation==null)return;
                intent.putExtra("currentLatLong",currentLocation.getLatitude()+","+currentLocation.getLongitude());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED&&ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            //ask for Permissions
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 5, this);
            Location lastknowLocation=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            LatLng latLng = new LatLng(lastknowLocation.getLatitude(), lastknowLocation.getLongitude());
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng).title("My").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission( Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5, 5, this);
                }
            }

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Snackbar.make(findViewById(R.id.map),connectionResult.getErrorMessage(),Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        this.currentLocation=location;
        if (lastKnowLocation != null && lastKnowLocation.distanceTo(location) == 0) {
            return;
        }
//                Log.i("MyLOcationnnnnnnnn is ",location.toString());
        // Add a marker in Sydney and move the camera
        mMap.clear();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        mMap.addMarker(new MarkerOptions().position(latLng).title("You").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

//                currentRef.child("personalData").child("latitude").setValue(location.getLatitude());
//                currentRef.child("personalData").child("longitude").setValue(location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        lastKnowLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getBaseContext(),"Please Enable Your Gps Service",Toast.LENGTH_LONG).show();
    }
}
