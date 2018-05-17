package com.app.pdi.aplicacionmovilpdi.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.pdi.aplicacionmovilpdi.R;
import com.app.pdi.aplicacionmovilpdi.model.Object.InicioSesion;
import com.app.pdi.aplicacionmovilpdi.model.utils.SharedPreferencesSesion;

public class PrincipalActivity extends AppCompatActivity {

    private ActionBar actionBar;
    InicioSesion sesion;
    private Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //Para que la barra de herramientas no muestre el titulo de la app
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        sesion = new InicioSesion();

    }

    public void ListarCampanas(View view){
        Intent intent = new Intent(this,ListarCampanasActivity.class);
        startActivity(intent);
    }

    public void Prueba(View view){
        String apellido = SharedPreferencesSesion.get(this).getPreferencesUserApellido();
        Toast.makeText(PrincipalActivity.this,"jola " + apellido,Toast.LENGTH_SHORT).show();
    }
}
