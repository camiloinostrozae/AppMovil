package com.app.pdi.aplicacionmovilpdi.model.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.app.pdi.aplicacionmovilpdi.model.Object.InicioSesion;
import com.app.pdi.aplicacionmovilpdi.model.Object.Persona;

public class SharedPreferencesSesion {

    public static final String PREFERENCES_NAME = "APP_MOVIL_PDI";
    public static final String PREFERENCES_USER_NAME = "PREFERENCES_USER_NAME";
    public static final String PREFERENCES_USER_APELLIDO = "PREFERENCES_USER_APELLIDO";
    public static final String PREFERENCES_USER_AUTHKEY = "PREFERENCES_USER_AUTHKEY";
    public static final String PREFERENCES_USER_EMAIL = "PREFERENCES_USER_EMAIL";

    private final SharedPreferences preferences;
    private boolean userLoggedIn = false;
    private static SharedPreferencesSesion INSTANCE;

    public static SharedPreferencesSesion get(Context context){

        if(INSTANCE == null){
            INSTANCE  = new SharedPreferencesSesion(context);
        }
        return INSTANCE;

    }

    private SharedPreferencesSesion(Context context){
        preferences = context.getApplicationContext().getSharedPreferences(PREFERENCES_NAME,Context.MODE_PRIVATE);
        userLoggedIn = !TextUtils.isEmpty(preferences.getString(PREFERENCES_USER_AUTHKEY,null));
    }

    public boolean isLogged(){
        return userLoggedIn;
    }

    public void guardarPersona(InicioSesion sesion){
        if(sesion !=null){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(PREFERENCES_USER_NAME,sesion.getNombre());
            editor.putString(PREFERENCES_USER_APELLIDO,sesion.getApellido());
            editor.putString(PREFERENCES_USER_AUTHKEY,sesion.getAuthKey());
            editor.putString(PREFERENCES_USER_EMAIL,sesion.getEmail());
            editor.apply();

            userLoggedIn = true;
        }
    }

    public void cerrarSesion(){
        userLoggedIn = false;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFERENCES_USER_NAME,null);
        editor.putString(PREFERENCES_USER_APELLIDO,null);
        editor.putString(PREFERENCES_USER_AUTHKEY,null);
        editor.putString(PREFERENCES_USER_EMAIL,null);
        editor.apply();
    }
}
