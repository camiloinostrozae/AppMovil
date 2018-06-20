package com.app.pdi.aplicacionmovilpdi.presenter.adapter;


import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.pdi.aplicacionmovilpdi.R;
import com.app.pdi.aplicacionmovilpdi.model.Object.Tramite;
import com.app.pdi.aplicacionmovilpdi.model.interactor.interfaces.RecyclerTramiteClickListener;
import com.app.pdi.aplicacionmovilpdi.view.ContenidoTramiteSeleccionado;


import java.util.ArrayList;

import java.util.Locale;

public class TramiteAdapter extends RecyclerView.Adapter<TramiteAdapter.TramiteViewHolder>{

    private ArrayList<Tramite> lista;
    private RecyclerTramiteClickListener recyclerTramiteClickListener;
    Context context;
    TextToSpeech textoSpeech;
    public TramiteAdapter(ArrayList<Tramite> lista, RecyclerTramiteClickListener recyclerTramiteClickListener, Context context){
        this.lista = lista;
        this.recyclerTramiteClickListener = recyclerTramiteClickListener;
        this.context = context;
    }


    @Override
    public TramiteViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.contenido_tramites, viewGroup, false);
        return new TramiteViewHolder(view);
    }


    @Override
    public void onBindViewHolder(TramiteViewHolder holder, final int position) {
        //Se establece lo que se mostrará en el ReccyclerView
        holder.titulo_tramite.setText("Título: " + lista.get(position).getTitulo());
        holder.tipo_tramite.setText("Tipo: " + lista.get(position).getTipo());
        textoSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textoSpeech.setLanguage(Locale.getDefault());
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String texto = "Título, " + lista.get(position).getTitulo()+" Tipo, " + lista.get(position).getTipo();
                textoSpeech.speak(texto, TextToSpeech.QUEUE_FLUSH,null);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent intent =  new Intent(context, ContenidoTramiteSeleccionado.class);
                //Se pasa a través de la activite el contenido y titulo del tramite que se verá solo en otra activity
                intent.putExtra("titulo_tramite",lista.get(position).getTitulo());
                intent.putExtra("contenido_tramite", lista.get(position).getContenido());
                intent.putExtra("id_tramite",lista.get(position).getIdTramite());
                context.startActivity(intent);
                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    class TramiteViewHolder extends RecyclerView.ViewHolder{
        TextView titulo_tramite, tipo_tramite;

        public TramiteViewHolder(View itemView) {
            super(itemView);
            titulo_tramite = itemView.findViewById(R.id.titulo_tramite);
            tipo_tramite = itemView.findViewById(R.id.tipo_tramite);
        }
    }

}
