package com.app.pdi.aplicacionmovilpdi.model.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class Localizacion extends Service implements LocationListener {

    private Context context;
    private boolean isGpsEnable = false;
    private boolean isNetworkEnable = false;
    boolean canGetLocation = false;
    private double latitud;
    private double longitud;
    Location location;
    LocationManager locationManager;

    public Localizacion() {
        super();
        this.context = this.getApplicationContext();
    }

    public Localizacion(Context context) {
        super();
        this.context = context;
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) this.context.getSystemService(LOCATION_SERVICE);
            isGpsEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!isGpsEnable && !isNetworkEnable) {
                Log.e("Dato","No esta na activado el GPS");
            }else{
                this.canGetLocation = true;

                if (isNetworkEnable) {
                    //check the network permission
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                    }
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000 * 60 * 1, 20, this);

                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (location != null) {
                            latitud = location.getLatitude();
                            longitud = location.getLongitude();
                        }
                    }
                }


                if(isGpsEnable){

                    if(location==null){
                        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions((Activity) context, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                        }

                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000 * 60 * 1, 20, this);
                        if(locationManager!=null){
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if (location != null) {
                                latitud = location.getLatitude();
                                longitud = location.getLongitude();
                            }
                        }
                    }

                }
            }

        } catch (Exception e) {

        }

       return location;

    }


    public double getLatitud(){
        if(location != null){
            latitud = location.getLatitude();
        }
        return latitud;
    }

    /**
     * Function to get longitude
     * */

    public double getLongitud(){
        if(location != null){
            longitud = location.getLongitude();
        }
        return longitud;
    }


    public boolean canGetLocation() {
        return this.canGetLocation;
    }


    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        getLatitud();
        getLongitud();

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
