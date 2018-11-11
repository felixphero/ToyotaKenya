package com.parse.motors;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
// Google Maps Dependencies
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
// Parse Dependencies
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
// Java dependencies
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    private GoogleMap mMap;
    //saveCurrentUserLocation();
    private ParseGeoPoint getCurrentUserLocation(){

        // finding currentUser
        ParseUser currentUser = ParseUser.getCurrentUser();

        if (currentUser == null) {
            // if it's not possible to find the user, do something like returning to login activity
        }
        // otherwise, return the current user location
        return currentUser.getParseGeoPoint("Location");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

    }

    private void saveCurrentUserLocation() {
        // requesting permission to get user's location
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else {
            // getting last know user's location
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            // checking if the location is null
            if(location != null){
                // if it isn't, save it to Back4App Dashboard
                ParseGeoPoint currentUserLocation = new ParseGeoPoint(location.getLatitude(), location.getLongitude());

                ParseUser currentUser = ParseUser.getCurrentUser();

                if (currentUser != null) {
                    currentUser.put("Location", currentUserLocation);
                    currentUser.saveInBackground();
                } else {
                    // do something like coming back to the login activity
                }
            }
            else {
                // if it is null, do something like displaying error and coming back to the menu activity
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST_LOCATION:
                saveCurrentUserLocation();
                break;
        }
    }
    private void showWarehouseInMap(final GoogleMap googleMap){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("warehouses");
        query.whereExists("location");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override  public void done(List<ParseObject> stores, ParseException e) {
                if (e == null) {
                    for(int i = 0; i < stores.size(); i++) {
                        LatLng storeLocation = new LatLng(stores.get(i).getParseGeoPoint("location").getLatitude(), stores.get(i).getParseGeoPoint("location").getLongitude());
                        googleMap.addMarker(new MarkerOptions().position(storeLocation).title(stores.get(i).getString("region")).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    }
                } else {
                    // handle the error
                    Log.d("store", "Error: " + e.getMessage());
                }
            }
        });
        ParseQuery.clearAllCachedResults();
    }
    private void alertDisplayer(String title,String message){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        android.app.AlertDialog ok = builder.create();
        ok.show();
    }
    private void showClosestWareHouse(final GoogleMap googleMap){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("warehouses");
        query.whereNear("location", getCurrentUserLocation());
        // setting the limit of near stores to 1, you'll have in the nearStores list only one object: the closest store from the current user
        query.setLimit(1);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override  public void done(List<ParseObject> nearStores, ParseException e) {
                if (e == null) {
                    ParseObject closestStore = nearStores.get(0);
                    // showing current user location, using the method implemented in Step 5
                    showCurrentUserInMap(mMap);
                    // finding and displaying the distance between the current user and the closest store to him, using method implemented in Step 4
                    double distance = getCurrentUserLocation().distanceInKilometersTo(closestStore.getParseGeoPoint("location"));
                    alertDisplayer(getString(R.string.wefoundLocation_map)+" ", getString(R.string.map2location)+" " + closestStore.getString("region") + getString(R.string.map_uko) +" "+ Math.round (distance * 100.0) / 100.0  + getString(R.string.map_diatance));
                    // creating a marker in the map showing the closest store to the current user
                    LatLng closestStoreLocation = new LatLng(closestStore.getParseGeoPoint("location").getLatitude(), closestStore.getParseGeoPoint("location").getLongitude());
                    googleMap.addMarker(new MarkerOptions().position(closestStoreLocation).title(closestStore.getString("region")).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                    // zoom the map to the closestStoreLocation
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(closestStoreLocation, 8));
                } else {
                    Log.d("store", "Error: " + e.getMessage());
                }
            }
        });
        ParseQuery.clearAllCachedResults();

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
        saveCurrentUserLocation();
        showWarehouseInMap(mMap);
        showCurrentUserInMap(mMap);
        showClosestWareHouse(mMap);
        //showClosestWarehouse(mMap);
        // Add a marker in Sydney and move the camera
       // LatLng sydney = new LatLng(-34, 151);
       // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    private void showCurrentUserInMap(final GoogleMap googleMap){

        // calling retrieve user's location method of Step 4
        ParseGeoPoint currentUserLocation = getCurrentUserLocation();

        // creating a marker in the map showing the current user location
        LatLng currentUser = new LatLng(currentUserLocation.getLatitude(), currentUserLocation.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(currentUser).title(ParseUser.getCurrentUser().getUsername()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        // zoom the map to the currentUserLocation
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentUser, 8));
    }
}
