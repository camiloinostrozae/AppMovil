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

public class ListarCampanasActivity extends AppCompatActivity implements CampanaContract.viewCampanas/**,
        TextToSpeech.OnInitListener*/{

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private CampanaContract.presenter presenter;
    private ActionBar actionBar;
/**
    private TextToSpeech tts;
    private TextView etx;
    private TextView etx2;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_campanas);
        //tts = new TextToSpeech(this, this);
       // etx2 = findViewById(R.id.tipo_campana);
       //etx = findViewById(R.id.recycler_campana);
        inicializarRecyclerView();
        initProgressBar();
        presenter = new getCampanasPresenterImpl(this, new getCampanasInteractorImpl());
        presenter.requestDataFromServer();
        //Para ocultar el menú de la barra
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);


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
        CampanaAdapter adapter = new CampanaAdapter(campanaArrayList,recyclerCampanaClickListener,this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Toast.makeText(ListarCampanasActivity.this,"Error: " + throwable.getMessage(),Toast.LENGTH_LONG).show();

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


    /**@Override
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
        tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);
    }*/



}
