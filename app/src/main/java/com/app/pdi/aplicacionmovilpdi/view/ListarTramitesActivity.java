package com.app.pdi.aplicacionmovilpdi.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;


import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import com.app.pdi.aplicacionmovilpdi.R;
import com.app.pdi.aplicacionmovilpdi.model.Object.Tramite;
import com.app.pdi.aplicacionmovilpdi.model.interactor.getTramitesInteractorImpl;
import com.app.pdi.aplicacionmovilpdi.model.interactor.interfaces.RecyclerTramiteClickListener;
import com.app.pdi.aplicacionmovilpdi.model.interactor.interfaces.TramiteContract;
import com.app.pdi.aplicacionmovilpdi.presenter.adapter.TramiteAdapter;
import com.app.pdi.aplicacionmovilpdi.presenter.getTramitesPresenterImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

public class ListarTramitesActivity extends AppCompatActivity implements TramiteContract.viewTramites,
        TextToSpeech.OnInitListener{

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private TramiteContract.presenter presenter;
    private ActionBar actionBar;

    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_tramites);
        tts = new TextToSpeech(this, this);
        inicializarRecyclerView();
        initProgressBar();
        presenter = new getTramitesPresenterImpl(this, new getTramitesInteractorImpl());
        presenter.requestDataFromServer();
        //Para ocultar el menú de la barra
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);


    }


    private void inicializarRecyclerView(){
        recyclerView = findViewById(R.id.recycler_tramite);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListarTramitesActivity.this);
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

    private RecyclerTramiteClickListener recyclerTramiteClickListener = new RecyclerTramiteClickListener() {
        @Override
        public void onItemClick(Tramite tramite) {
            Toast.makeText(ListarTramitesActivity.this,"Titulo: " + tramite.getTitulo(),Toast.LENGTH_LONG).show();
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
    public void setDataToRecyclerView(List<Tramite> tramiteArrayList) {
        TramiteAdapter adapter = new TramiteAdapter((ArrayList<Tramite>) tramiteArrayList,recyclerTramiteClickListener,this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Toast.makeText(ListarTramitesActivity.this,"Error: " + throwable.getMessage(),Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onDestroy(){
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

        String texto = "Lista de trámites disponibles, si desea saber el título y el tipo del trámite " +
                "presione una vez sobre el elemento. si desea saber su contenido mantenga presionado el elemento";
        tts.speak(texto,TextToSpeech.QUEUE_FLUSH,null);

    }


}
