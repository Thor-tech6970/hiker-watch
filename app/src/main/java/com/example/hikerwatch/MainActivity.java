package com.example.hikerwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager ;
    LocationListener locationListener;
    Location lastKnownLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                //Log.i("Location"  , location.toString());
                updateLocation(location);



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
        };



        if(ContextCompat.checkSelfPermission(this , Manifest.permission.ACCESS_FINE_LOCATION) !=PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this , new String[] {Manifest.permission.ACCESS_FINE_LOCATION} ,1);


        }

        else {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER , 0 , 0 ,locationListener);

            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (lastKnownLocation != null) {

                updateLocation(lastKnownLocation);


            }

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            if(ContextCompat.checkSelfPermission(this , Manifest.permission.ACCESS_FINE_LOCATION) ==PackageManager.PERMISSION_GRANTED){

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER , 0 , 0 , locationListener);


            }
    }}

    public  void updateLocation(Location location){

        Log.i("Location " , location.toString());

        Geocoder geocoder = new Geocoder(getApplicationContext() , Locale.getDefault());

        try {
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude() , location.getLongitude() ,1);

           // Log.i("List address" , addressList.get(0).toString());

            if(addressList.get(0).toString() != null && addressList.size()>0){


                String address = "";

                if(addressList.get(0).getThoroughfare() != null){

                    address += addressList.get(0).getThoroughfare() + " , " + " ";


                }

                if (addressList.get(0).getSubAdminArea() != null){

                    address += addressList.get(0).getSubAdminArea() + " , " + " ";
                }

                if(addressList.get(0).getAdminArea() != null){

                    address += addressList.get(0).getAdminArea() + " , " + " ";
                }

                TextView textView5 = (TextView) findViewById(R.id.textView5);

                textView5.setText("Address: " +address);


               }

        }

        catch (Exception e){

            e.printStackTrace();
        }

        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        TextView textView4 = (TextView) findViewById(R.id.textView4);


        textView2.setText(" Lattitude: " +Double.toString(location.getLatitude()));

        textView3.setText(" Longitude: " +Double.toString(location.getLongitude()));

        textView4.setText("Altitude: "    +Double.toString(location.getAltitude()));






    }
}
