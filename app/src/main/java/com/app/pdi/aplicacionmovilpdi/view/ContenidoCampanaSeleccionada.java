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
        //Obtengo los Intent que vienen de CampanaAdaptaer, especificamente, los que vienen de  itemView.setOnClickListener
        if(getIntent().hasExtra("contenido_campana") && getIntent().hasExtra("titulo_campana")){
            String contenido = getIntent().getStringExtra("contenido_campana");
            String titulo = getIntent().getStringExtra("titulo_campana");
            setTitulo(titulo);
            setContenido(contenido);
        }
    }

    private void setTitulo(String tituloIntent){
        TextView titulo = findViewById(R.id.titulo_campana_seleccionada);
        titulo.setText(tituloIntent);

    }

    private void setContenido(String contenidoIntent){
        TextView contenido = findViewById(R.id.contenido_campana_seleccionada);
        contenido.setText(contenidoIntent);
    }


}
