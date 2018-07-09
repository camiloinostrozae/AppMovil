package com.app.pdi.aplicacionmovilpdi.view;

import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;


import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.app.pdi.aplicacionmovilpdi.R;
import com.app.pdi.aplicacionmovilpdi.model.Object.Campana;
import com.app.pdi.aplicacionmovilpdi.model.interactor.interfaces.CampanaContract;
import com.app.pdi.aplicacionmovilpdi.model.interactor.interfaces.RecyclerCampanaClickListener;
import com.app.pdi.aplicacionmovilpdi.model.interactor.getCampanasInteractorImpl;
import com.app.pdi.aplicacionmovilpdi.presenter.adapter.CampanaAdapter;
import com.app.pdi.aplicacionmovilpdi.presenter.getCampanasPresenterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

public class ListarCampanasActivity extends AppCompatActivity implements CampanaContract.viewCampanas,
        TextToSpeech.OnInitListener{

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private CampanaContract.presenter presenter;
    private ActionBar actionBar;

    private TextToSpeech tts;
    private TextToSpeech textoSpeech;
    private Button volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_campanas);
        tts = new TextToSpeech(this, this);
        volver = findViewById(R.id.volver);

        textoSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textoSpeech.setLanguage(Locale.getDefault());
                }
            }
        });
        volver.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick (View view){
                if (tts != null) {
                    tts.stop();
                    tts.shutdown();
                }
                Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                v.vibrate(200);
                textoSpeech.speak("Botón Volver",TextToSpeech.QUEUE_FLUSH,null);
            }

        });
        volver.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick (View view){
                if (tts != null) {
                    tts.stop();
                    tts.shutdown();
                }
                onBackPressed();
                textoSpeech.speak(" Volviendo Atrás",TextToSpeech.QUEUE_FLUSH,null);
                return false;
            }

        });

        inicializarRecyclerView();
        initProgressBar();
        presenter = new getCampanasPresenterImpl(this, new getCampanasInteractorImpl());
        presenter.requestDataFromServer();
        //Para ocultar el menú de la barra
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();  // Invoca al método
    }


    private void inicializarRecyclerView(){
        recyclerView = findViewById(R.id.recycler_campana);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListarCampanasActivity.this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initProgressBar(){
        progressBar = new ProgressBar(this,null,android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setGravity(Gravity.CENTER);
        relativeLayout.addView(progressBar);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        progressBar.setVisibility(View.INVISIBLE);

        this.addContentView(relativeLayout,params);
    }

    private RecyclerCampanaClickListener recyclerCampanaClickListener = new RecyclerCampanaClickListener() {
        @Override
        public void onItemClick(Campana campana) {
            Toast.makeText(ListarCampanasActivity.this,"Titulo: " + campana.getTitulo(),Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setDataToRecyclerView(List<Campana> campanaArrayList) {
        CampanaAdapter adapter = new CampanaAdapter((ArrayList<Campana>) campanaArrayList,recyclerCampanaClickListener,this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Toast.makeText(ListarCampanasActivity.this,"Error: " + throwable.getMessage(),Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onDestroy(){
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_app,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.menu_refresh){
            presenter.onRefreshButtonClick();
        }

        return  super.onOptionsItemSelected(item);
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

    private void speakOut(){

        String texto = "A continuación se presentan una lista de botones." +
                "Cada botón representa una campaña." +
                "Presione una vez el botón para saber su información." +
                "Mantenga presionado el botón para saber su contenido";
         tts.speak(texto,TextToSpeech.QUEUE_FLUSH,null);

    }


}
