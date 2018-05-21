package com.app.pdi.aplicacionmovilpdi.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import android.util.Log;


import java.util.Locale;

import com.app.pdi.aplicacionmovilpdi.R;

public class ContenidoCampanaSeleccionada extends AppCompatActivity  implements

        TextToSpeech.OnInitListener{

         TextToSpeech tts;
         TextView etx;
         ImageButton botonDetener;
         public static int MILISEGUNDOS_ESPERA = 5000;

        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campana_seleccionada);

        tts = new TextToSpeech(this, this);
        etx = findViewById(R.id.contenido_campana_seleccionada);
        botonDetener = findViewById(R.id.btnStop);
            botonDetener.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick (View view){
                        if (tts != null) {
                            tts.stop();
                            tts.shutdown();
                        }
                    botonDetener.setEnabled(false);
                    }

            });

        getIntentFromRecycler();

    }


    @Override
    public void onInit(int status) {

        if(status==TextToSpeech.SUCCESS){
            int result = tts.setLanguage(Locale.getDefault());
            if(result==TextToSpeech.LANG_NOT_SUPPORTED || result==TextToSpeech.LANG_MISSING_DATA){
                Log.e("TTS","Este lenguaje no es soportado");
            }else{
                speakOut2();
                esperar(MILISEGUNDOS_ESPERA);
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

    private void speakOut2() {
        String text = "Información de la campaña, para detener el relato presione la pantalla";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void esperar(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                speakOut();
            }
        }, milisegundos);
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



    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }


}
