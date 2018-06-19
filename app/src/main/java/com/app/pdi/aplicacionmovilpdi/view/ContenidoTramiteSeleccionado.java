package com.app.pdi.aplicacionmovilpdi.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import android.util.Log;


import java.util.Locale;

import com.app.pdi.aplicacionmovilpdi.R;
import com.app.pdi.aplicacionmovilpdi.model.Object.ResponseInteraccion;
import com.app.pdi.aplicacionmovilpdi.model.interactor.RestInteraccionCampana;
import com.app.pdi.aplicacionmovilpdi.model.interactor.RestInteraccionTramite;
import com.app.pdi.aplicacionmovilpdi.model.utils.SharedPreferencesSesion;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContenidoTramiteSeleccionado extends AppCompatActivity  implements TextToSpeech.OnInitListener{

    TextToSpeech tts;
    TextToSpeech textoSpeech;
    TextView etx;
    ImageButton botonDetener;
    String titulo;
    String contenido;
    public static int MILISEGUNDOS_ESPERA = 15000;
    private ActionBar actionBar;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tramite_seleccionado);

        //Para que l a barra de herramientas no muestre el titulo de la app
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        registrarInteraccion();
        tts = new TextToSpeech(this, this);
        etx = findViewById(R.id.contenido_tramite_seleccionado);
        botonDetener = findViewById(R.id.btnStop);
        textoSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textoSpeech.setLanguage(Locale.getDefault());
                }
            }
        });
        botonDetener.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View view){
                if (tts != null) {
                    tts.stop();
                    tts.shutdown();
                    textoSpeech.speak("Relato Detenido",TextToSpeech.QUEUE_FLUSH,null);
                }
                //botonDetener.setEnabled(false);
            }

        });
        botonDetener.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick (View view){
                onBackPressed();
                textoSpeech.speak(" Volviendo Atrás, lista de trámites",TextToSpeech.QUEUE_FLUSH,null);
                return false;
            }

        });

        getIntentFromRecycler();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();  // Invoca al método
    }


    @Override
    public void onInit(int status) {

        if(status==TextToSpeech.SUCCESS){
            int result = tts.setLanguage(Locale.getDefault());
            if(result==TextToSpeech.LANG_NOT_SUPPORTED || result==TextToSpeech.LANG_MISSING_DATA){
                Log.e("TTS","Este lenguaje no es soportado");
            }else{
                speakOut2(titulo);
                esperar(MILISEGUNDOS_ESPERA);
            }
        }else{
            Log.e("TTS","Inicializacion del lenguaje es fallida");
        }
    }

    private void speakOut() {

        String text = etx.getText().toString();
        tts.setSpeechRate((float) 0.9);//define la velocidad del relato x defecto 1.0
        //tts.setPitch((float) 1.1);//define el tono del relato x defecto 1.0
        tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);
    }

    private void speakOut2(String titulo) {
        String text = "Para detener el relato presione una vez la pantalla, " +
                "para volver a la lista de trámites mantenga presionada la pantalla. Contenido del Trámite: "+titulo;
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
        //Obtengo los Intent que vienen de TramiteAdaptaer, especificamente, los que vienen de  itemView.setOnClickListener
        if(getIntent().hasExtra("contenido_tramite") && getIntent().hasExtra("titulo_tramite")){
            contenido = getIntent().getStringExtra("contenido_tramite");
            titulo = getIntent().getStringExtra("titulo_tramite");
            setTitulo(titulo);
            setContenido(contenido);
        }
    }

    private void setTitulo(String tituloIntent){
        TextView titulo = findViewById(R.id.titulo_tramite_seleccionado);
        titulo.setText(tituloIntent);

    }

    private void setContenido(String contenidoIntent){
        TextView contenido = findViewById(R.id.contenido_tramite_seleccionado);
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


    public void registrarInteraccion(){
        String auth_key;
        int tramite_id_tramite=0;
        //Obtengo la auth_key del usuario guardada en las preferencias compartidas
        auth_key = SharedPreferencesSesion.get(this).getPreferencesUserAuthkey();
        if(getIntent().hasExtra("id_tramite")){
            tramite_id_tramite = getIntent().getIntExtra("id_tramite",0);
        }
        //Mando a llamar el cliente REST
        RestInteraccionTramite.registrarInteraccionTramite().registrarInteraccionTramite(auth_key,tramite_id_tramite)
                .enqueue(new Callback<ResponseInteraccion>() {
                    @Override
                    public void onResponse(Call<ResponseInteraccion> call, Response<ResponseInteraccion> response) {
                        if(response.isSuccessful()){
                            Log.e("dato","SI LOS REGISTRE el acceso al tramite!!!!");
                        }else{
                            Log.e("dato","No lo pude nah registrar al tramite");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseInteraccion> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }


}
