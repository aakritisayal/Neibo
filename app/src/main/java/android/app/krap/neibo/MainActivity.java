package android.app.krap.neibo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Locale;

public class MainActivity extends Activity {

LinearLayout linearThai,linearEng;

    GPSTracker gpsTracker;
    SharedPreferences shared;
    SharedPreferences.Editor editor;
    String latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shared = getSharedPreferences("shared_location", Context.MODE_MULTI_PROCESS);
        editor = shared.edit();

        gpsTracker = new GPSTracker(MainActivity.this);

        linearEng =(LinearLayout) findViewById(R.id.linearEng);
        linearThai =(LinearLayout) findViewById(R.id.linearThai);

        linearThai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("th");
            }
        });

        linearEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLocale("en");
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();

        latitude = shared.getString("latitude", "");
        longitude = shared.getString("longitude", "");

        if (latitude.equals("") || longitude.equals("") || latitude.equals("0.0") || longitude.equals("0.0")) {

            getLocationUpdates();
        } else {

            editor.putString("latitude", String.valueOf(latitude));
            editor.putString("longitude", String.valueOf(longitude));
            editor.commit();
        }
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, Suggestion_login.class);
        refresh.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(refresh);
        finish();
    }
    public void getLocationUpdates() {
        if (gpsTracker.isGPSEnabled) {

            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();


            editor.putString("latitude", String.valueOf(latitude));
            editor.putString("longitude", String.valueOf(longitude));
            editor.commit();



        } else {

            Common_methods.settingsRequest(MainActivity.this);


        }
    }

}
