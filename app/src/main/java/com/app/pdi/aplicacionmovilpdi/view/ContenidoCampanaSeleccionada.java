package com.app.pdi.aplicacionmovilpdi.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import android.util.Log;


import java.util.Locale;

import com.app.pdi.aplicacionmovilpdi.R;

public class ContenidoCampanaSeleccionada extends AppCompatActivity  implements

        TextToSpeech.OnInitListener{

         TextToSpeech tts;
         TextView etx;

        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campana_seleccionada);

        tts = new TextToSpeech(this, this);
        etx = findViewById(R.id.contenido_campana_seleccionada);

        getIntentFromRecycler();

    }


    @Override
    public void onInit(int status) {

        if(status==TextToSpeech.SUCCESS){
            int result = tts.setLanguage(Locale.getDefault());
            if(result==TextToSpeech.LANG_NOT_SUPPORTED || result==TextToSpeech.LANG_MISSING_DATA){
                Log.e("TTS","Este lenguaje no es soportado");
            }else{
                speakOut();
            }
        }else{
            Log.e("TTS","Inicializacion del lenguaje es fallida");
        }
    }

    private void speakOut() {

        String text = etx.getText().toString();
        tts.setSpeechRate((float) 0.9);//define la velocidad del relato x defecto 1.0
        //tts.setPitch((float) 1.1);//define el tomo del relato x defecto 1.0
        tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);


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
