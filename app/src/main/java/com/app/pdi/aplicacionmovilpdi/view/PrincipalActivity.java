package com.app.pdi.aplicacionmovilpdi.view;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import android.widget.Button;
import android.widget.Toast;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;


import com.app.pdi.aplicacionmovilpdi.R;
import com.app.pdi.aplicacionmovilpdi.model.Object.InicioSesion;
import com.app.pdi.aplicacionmovilpdi.model.utils.SharedPreferencesSesion;

public class PrincipalActivity extends AppCompatActivity implements

        TextToSpeech.OnInitListener{

    TextToSpeech tts;
    TextView etx;
    TextView grabar;
    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1;
    private ActionBar actionBar;
    InicioSesion sesion;
    private Button boton;
    public static int MILISEGUNDOS_ESPERA = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primero);
        tts = new TextToSpeech(this, this);
        grabar = findViewById(R.id.txtGrabarVoz);
        //Para que la barra de herramientas no muestre el titulo de la app
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        sesion = new InicioSesion();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RECOGNIZE_SPEECH_ACTIVITY:

                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> speech = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String strSpeech2Text = speech.get(0);

                    grabar.setText(strSpeech2Text);

                    if(grabar.getText().equals("campañas") || grabar.getText().equals("campaña")){
                        Intent intent = new Intent(this,ListarCampanasActivity.class);
                        startActivity(intent);
                    }else{
                        speakOutReintento();
                    }

                }else{
                    if(data ==null){
                        speakOutFallo();
                    }
                }


                break;
            default:

                break;
        }
    }

    public void ListarCampanas(View view){
        Intent intent = new Intent(this,ListarCampanasActivity.class);
        startActivity(intent);
    }


    public void onClickImgBtnHablar(View v) {

        speakOut2();
        esperar(MILISEGUNDOS_ESPERA);
    }

    public void esperar(int milisegundos) {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                escuchar();
            }
        }, milisegundos);
    }

    public void escuchar(){
        Intent intentActionRecognizeSpeech = new Intent(
                RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // Configura el Lenguaje (Español-México)
        intentActionRecognizeSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-MX");
        try {
            startActivityForResult(intentActionRecognizeSpeech,
                    RECOGNIZE_SPEECH_ACTIVITY);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "Tú dispositivo no soporta el reconocimiento por voz",
                    Toast.LENGTH_SHORT).show();
        }
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

        String text = "Bienvenido, toque la pantalla para continuar";
        tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);
    }

    private void speakOut2() {

        String text = "Si quiere ver las campañas diga, Campañas";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void speakOutFallo() {

        String text = "No entendí, por favor intente nuevamente";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void speakOutReintento() {

        String text = "Intente nuevamente";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void Prueba(View view){
        String apellido = SharedPreferencesSesion.get(this).getPreferencesUserApellido();
        Toast.makeText(PrincipalActivity.this,"jola " + apellido,Toast.LENGTH_SHORT).show();
      }
}
