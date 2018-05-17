package com.app.pdi.aplicacionmovilpdi.presenter.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.pdi.aplicacionmovilpdi.R;
import com.app.pdi.aplicacionmovilpdi.model.Object.Campana;
import com.app.pdi.aplicacionmovilpdi.model.interactor.interfaces.RecyclerCampanaClickListener;
import com.app.pdi.aplicacionmovilpdi.view.ContenidoCampanaSeleccionada;

import java.util.ArrayList;
import java.util.List;

public class CampanaAdapter extends RecyclerView.Adapter<CampanaAdapter.CampanaViewHolder>{

    private List<Campana> lista;
    private RecyclerCampanaClickListener recyclerCampanaClickListener;
    Context context;
    public CampanaAdapter(List<Campana> lista, RecyclerCampanaClickListener recyclerCampanaClickListener, Context context){
        this.lista = lista;
        this.recyclerCampanaClickListener = recyclerCampanaClickListener;
        this.context = context;
    }

    @Override
    public CampanaViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.contenido_campanas, viewGroup, false);
        return new CampanaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CampanaViewHolder holder, final int position) {
        //Se establece lo que se mostrará en el ReccyclerView
        holder.titulo_campana.setText("Titulo: " + lista.get(position).getTitulo());
        holder.tipo_campana.setText("Tipo: " + lista.get(position).getTipo());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(context, ContenidoCampanaSeleccionada.class);
                //Se pasa a través de la activite el contenido y titulo de la campana que se verá solo en otra activity
                intent.putExtra("titulo_campana",lista.get(position).getTitulo());
                intent.putExtra("contenido_campana", lista.get(position).getContenido());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }


    class CampanaViewHolder extends RecyclerView.ViewHolder{
        TextView titulo_campana, tipo_campana;

        public CampanaViewHolder(View itemView) {
            super(itemView);
            titulo_campana = itemView.findViewById(R.id.titulo_campana);
            tipo_campana = itemView.findViewById(R.id.tipo_campana);

        }
    }

}
