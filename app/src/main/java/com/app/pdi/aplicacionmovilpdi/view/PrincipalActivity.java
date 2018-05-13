package com.app.pdi.aplicacionmovilpdi.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.app.pdi.aplicacionmovilpdi.R;

public class PrincipalActivity extends AppCompatActivity {

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //Para que la barra de herramientas no muestre el titulo de la app
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
    }

    public void ListarCampanas(View view){
        Intent intent = new Intent(this,ListarCampanasActivity.class);
        startActivity(intent);
    }
}
