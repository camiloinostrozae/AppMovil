package com.app.pdi.aplicacionmovilpdi.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.pdi.aplicacionmovilpdi.model.Object.Region;

import org.w3c.dom.Text;

import java.util.List;

public class RegionAdapter extends ArrayAdapter<Region> {
    private Context miContext;
    private List<Region> misRegiones;

    public RegionAdapter(@NonNull Context context,@NonNull List<Region> opciones) {
        super(context, 0, opciones);
        miContext = context;
        misRegiones = opciones;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view;
        Region region = misRegiones.get(position);
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(miContext);
            view  = inflater.inflate(android.R.layout.simple_list_item_1,parent,false);
        }else{
            view = convertView;
        }
        TextView textView = (TextView)view.findViewById(android.R.id.text1);
        textView.setText(region.getNombre());
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        return getView(position,convertView,parent);
    }


}
