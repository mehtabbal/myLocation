package com.example.mylocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationProvider;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button getLocation;
    TextView tv, tv1, tv2;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLocation = findViewById(R.id.button);
        tv = findViewById(R.id.textView);
        tv1 = findViewById(R.id.longi);
        tv2 = findViewById(R.id.country);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check permission
                if(ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    //if permission granted
                    findLocation();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }
            }
            private void findLocation(){
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        //initialize location
                        Location location = task.getResult();
                        if(location != null) {
                            // initialize geocoder
                            Geocoder geocoder = new Geocoder(MainActivity.this,
                                    Locale.getDefault());
                            try {
                                List<Address> adresses = geocoder.getFromLocation(
                                        location.getLatitude(), location.getLongitude(), 1);
                                tv.setText(Html.fromHtml("<font color ='#6200EE'><b>Latitude:</b><br></font>"
                                + adresses.get(0).getLatitude()));
                                tv1.setText(Html.fromHtml("<font color ='#6200EE'><b>Longitude:</b><br></font>"
                                        + adresses.get(0).getLongitude()));
                                tv2.setText(Html.fromHtml("<font color ='#6200EE'><b>Country:</b><br></font>"
                                        + adresses.get(0).getLocality()));

                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                });
            }
        });
    }
}
