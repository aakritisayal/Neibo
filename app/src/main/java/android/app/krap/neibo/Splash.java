package android.app.krap.neibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

/**
 * Created by Android on 10/21/2016.
 */
public class Splash extends Activity implements LocationListener {

    protected static final int REQUEST_CAMERA = 1;

    SharedPreferences sp;
    SharedPreferences.Editor edt;
    String logged_in;
    LocationManager locationManager;

    public static final int REQUEST_LOCATION = 1;
    String TAG = "lOCATION SERVICE";
    GPSTracker gpsTracker;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    SharedPreferences shared;
    SharedPreferences.Editor editor;

    String latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        sp = getSharedPreferences("shared_value", Context.MODE_MULTI_PROCESS);
        edt = sp.edit();

        shared = getSharedPreferences("shared_location", Context.MODE_MULTI_PROCESS);
        editor = shared.edit();

        logged_in = sp.getString("logged_in", "");

        gpsTracker = new GPSTracker(Splash.this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    }


    @Override
    protected void onResume() {
        super.onResume();

        latitude = shared.getString("latitude", "");
        longitude = shared.getString("longitude", "");

        if (latitude.equals("") || longitude.equals("") || latitude.equals("0.0") || longitude.equals("0.0")) {

            getLatLog();
        } else {
            Thread th = new Thread(r);
            th.start();
        }
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {

            try {
                Thread.sleep(3000);

                if (logged_in.equals("logged_in")) {
                    Intent it = new Intent(Splash.this, Homepage.class);
                    startActivity(it);
                    finish();
                } else {
                    Intent it = new Intent(Splash.this, MainActivity.class);
                    startActivity(it);
                    finish();
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
// Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:

                        double latitude = gpsTracker.getLatitude();
                        double longitude = gpsTracker.getLongitude();

//                        strLatitude = String.valueOf(latitude);
//                        strLongitude = String.valueOf(longitude);


                        editor.putString("latitude", String.valueOf(latitude));
                        editor.putString("longitude", String.valueOf(longitude));
                        editor.commit();

                        Intent myIntent = new Intent(this, Splash.class);
                        myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(myIntent);
                        finish();


                        //  createLocationRequest();

                        //    mLocationClient.requestLocationUpdates(mLocationRequest, this);


//                        double location = getLocation();
//                        if (location == 0.0) {
//                            getLocation();
//                        } else {
//                            Thread thread = new Thread(r);
//                            thread.start();
//                        }

                        break;
                    case Activity.RESULT_CANCELED:
                        settingsRequest();//keep asking if imp or do whatever
                        break;
                }
                break;
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();

        double longitude = location.getLongitude();


        editor.putString("latitude", String.valueOf(latitude));
        editor.putString("longitude", String.valueOf(longitude));
        editor.commit();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    public void getLocationUpdates() {
        if (gpsTracker.isGPSEnabled) {

            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();


            editor.putString("latitude", String.valueOf(latitude));
            editor.putString("longitude", String.valueOf(longitude));
            editor.commit();


            Thread thread = new Thread(r);
            thread.start();


        } else {

            Common_methods.settingsRequest(Splash.this);


        }
    }

    public void settingsRequest() {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(Splash.this).addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.e(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.e(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(Splash.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.e(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.e(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
// Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

                    getLocationUpdates();

                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    //getLocationUpdates();

                }
                break;
        }
    }


    public void getLatLog() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);

                getLocationUpdates();
            } else {
                ActivityCompat.requestPermissions(Splash.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

                ActivityCompat.requestPermissions(Splash.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            }
        } else {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);

            getLocationUpdates();
        }

    }
}
