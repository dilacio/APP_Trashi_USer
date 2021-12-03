package com.learn2crack.swipeview.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.learn2crack.swipeview.Modelo.Material;
import com.learn2crack.swipeview.R;

import java.util.List;

public class ViewListMaterialesAdapter extends ArrayAdapter<Material> {

    public ViewListMaterialesAdapter(Context context, List<Material> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.list_template2, null);

        TextView id = (TextView) item.findViewById(R.id.id);
        TextView material = (TextView) item.findViewById(R.id.Material);
        TextView informacion = (TextView) item.findViewById(R.id.Informacion);
        TextView imagen = (TextView) item.findViewById(R.id._imagen);

        id.setText("ID: "+ getItem(position).getId()+"");
        material.setText("Direccion: "+ getItem(position).getNombre()+"");
        informacion.setText("Material: "+ getItem(position).getInformacion()+"");
        imagen.setText("Imagen: " + getItem(position).getImagen());

        return item;
    }
}
