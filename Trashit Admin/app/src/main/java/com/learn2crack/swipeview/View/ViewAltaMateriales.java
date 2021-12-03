package com.learn2crack.swipeview.View;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.learn2crack.swipeview.R;
import com.learn2crack.swipeview.Negocio.TransNegocioInsertMaterial;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class ViewAltaMateriales extends Fragment {

    public static final String TITLE = "ALTA";
    EditText material, informacion, imagen;
    Button agregar;

    public static ViewAltaMateriales newInstance() {

        return new ViewAltaMateriales();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View v = inflater.inflate(R.layout.fragment_view_alta_materiales, container, false);
        material = (EditText) v.findViewById(R.id.Material);
        informacion = (EditText) v.findViewById(R.id.Informacion);
        imagen = (EditText) v.findViewById(R.id._imagen);
        agregar = (Button) v.findViewById(R.id.Modificar);
        agregar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(
                        material.getText().toString().equals("") ||
                                informacion.getText().toString().equals("") ||
                                imagen.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Complete todos los campos",Toast.LENGTH_SHORT).show();
                } else if(!URLUtil.isValidUrl(imagen.getText().toString())) {
                    Toast.makeText(getContext(), "La url de imagen ingresada no es válida",Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        URLConnection connection = new URL(imagen.getText().toString()).openConnection();
                        String contentType = connection.getHeaderField("Content-Type");
                        Log.i( "tipo de contenido: ", contentType);
                        boolean image = contentType.startsWith("image/");
                        if(!image){
                            Toast.makeText(getContext(), "La url ingresada no pertenece a una imagen",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ViewAltaMateriales.this.crearMaterial();
                }
            }
        });
        return v;
    }

    public void crearMaterial()
    {
        TransNegocioInsertMaterial task = new TransNegocioInsertMaterial(
                material.getText().toString(),
                informacion.getText().toString(),
                imagen.getText().toString(),
                this.getContext());
        task.execute();
    }
}