package com.learn2crack.swipeview.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.learn2crack.swipeview.R;
import com.learn2crack.swipeview.Negocio.TransNegocioListadoMateriales;

public class ViewListadoMateriales extends Fragment {
        public static final String TITLE = "LISTADO";
        ListView list;
        EditText textoBuscar;
        Button botonBuscar;
        public static ViewListadoMateriales newInstance() {

            return new ViewListadoMateriales();
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_view_listado_materiales,container,false);
            list = (ListView)v.findViewById(R.id.list);
            textoBuscar = (EditText) v.findViewById(R.id.textoABuscar) ;
            botonBuscar = (Button) v.findViewById(R.id.botonTextoBuscar) ;
            GetList();
            botonBuscar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buscarTexto(textoBuscar.getText().toString());
                }

            });
            return v;
        }
        public void GetList() {
            TransNegocioListadoMateriales task = new TransNegocioListadoMateriales(list,getContext());
            task.execute();
        }
    public void buscarTexto (String texto) {
        TransNegocioListadoMateriales task = new TransNegocioListadoMateriales(list,getContext(),texto);
        task.execute();
    }
}