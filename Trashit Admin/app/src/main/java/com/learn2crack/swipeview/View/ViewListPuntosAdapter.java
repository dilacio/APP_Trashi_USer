package com.learn2crack.swipeview.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.learn2crack.swipeview.Modelo.Puntos;
import com.learn2crack.swipeview.R;

import java.util.List;

public class ViewListPuntosAdapter extends ArrayAdapter<Puntos> {
    public ViewListPuntosAdapter(Context context, List<Puntos> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.list_template, null);

        TextView id = (TextView) item.findViewById(R.id.id);
        TextView direccion = (TextView) item.findViewById(R.id.direccion);
        TextView material = (TextView) item.findViewById(R.id.material);
        TextView barrio = (TextView) item.findViewById(R.id.barrio);
        TextView latitud = (TextView) item.findViewById(R.id.latitud);
        TextView longitud = (TextView) item.findViewById(R.id.longitud);

        id.setText("ID: "+ getItem(position).getId()+"");
        direccion.setText("Direccion: "+ getItem(position).getDireccion()+"");
        material.setText("Material: "+ getItem(position).getMaterial()+"");
        barrio.setText("Barrio: " + getItem(position).getBarrio());
        latitud.setText("Latitud: " + getItem(position).getLatitud());
        longitud.setText("Longitud: " + getItem(position).getLongitud());
        return item;
    }
}
