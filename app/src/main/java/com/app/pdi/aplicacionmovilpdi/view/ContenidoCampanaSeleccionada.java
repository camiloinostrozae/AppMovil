package com.app.pdi.aplicacionmovilpdi.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.app.pdi.aplicacionmovilpdi.R;

public class ContenidoCampanaSeleccionada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campana_seleccionada);

        getIntentFromRecycler();

    }

    private void getIntentFromRecycler(){
        if(getIntent().hasExtra("contenido_campana")){
            String contenido = getIntent().getStringExtra("contenido_campana");
            setContenido(contenido);
        }
    }

    private void setContenido(String contenidoIntent){
        TextView contenido = findViewById(R.id.contenido_campana_seleccionada);
        contenido.setText(contenidoIntent);
    }
}
