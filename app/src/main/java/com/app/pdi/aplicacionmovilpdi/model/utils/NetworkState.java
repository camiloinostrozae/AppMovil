package com.app.pdi.aplicacionmovilpdi.model.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

public class NetworkState {

    private static NetworkState state;

    public static NetworkState getInstance(){
        if(state == null){
            state = new NetworkState();
        }
        return state;
    }

    public boolean estadoConexion(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnected();


    }
}
