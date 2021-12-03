package com.learn2crack.swipeview.View;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.learn2crack.swipeview.R;
import com.learn2crack.swipeview.Negocio.TransNegocioBuscarMaterial;
import com.learn2crack.swipeview.Negocio.TransNegocioModificarMaterial;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;


public class ViewModificacionMateriales extends Fragment {

    public static final String TITLE = "MODIFICACION";
    public EditText id;
    public EditText informacion;
    public EditText imagen;
    public EditText materialName;
    public Button buscar;
    public Button eliminar;
    public Button modificar;

    protected ArrayAdapter<CharSequence> adapter;
    public static ViewModificacionMateriales newInstance() {

        return new ViewModificacionMateriales();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        View v = inflater.inflate(R.layout.fragment_view_modificacion_materiales,container,false);
        id = (EditText) v.findViewById(R.id.Id);
        informacion = (EditText) v.findViewById(R.id.Informacion);
        imagen = (EditText) v.findViewById(R.id._imagen);
        buscar = (Button) v.findViewById(R.id.Buscar);
        materialName = (EditText) v.findViewById(R.id.MaterialName);
        modificar = (Button) v.findViewById(R.id.Modificar);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        informacion.addTextChangedListener(textWatcher);
        imagen.addTextChangedListener(textWatcher);

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewModificacionMateriales.this.buscar();
            }
        });

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(informacion.getText().toString().equals("") ||
                        imagen.getText().toString().equals("") ||
                        materialName.getText().toString().equals("") )
                {
                    Toast.makeText(getContext(),
                            "Todos los campos son obligatorios.", Toast.LENGTH_SHORT).show();
                }else if(!URLUtil.isValidUrl(imagen.getText().toString())) {
                    Toast.makeText(getContext(), "La url de imagen ingresada no es válida",Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        URLConnection connection = new URL(imagen.getText().toString()).openConnection();
                        String contentType = connection.getHeaderField("Content-Type");
                        if(contentType == null){
                            contentType = "";
                        }
                        boolean image = contentType.startsWith("image/");
                        if(!image){
                            Toast.makeText(getContext(), "La url ingresada no pertenece a una imagen",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ViewModificacionMateriales.this.modificar();
                }
            }
        });

        return v;
    }

    public void buscar()
    {
        TransNegocioBuscarMaterial task = new TransNegocioBuscarMaterial(this.id, this.informacion, this.imagen, this.materialName,getContext());
        task.execute();
    }

    public void modificar()
    {
        TransNegocioModificarMaterial task = new TransNegocioModificarMaterial(
                getContext(),
                informacion.getText().toString(),
                imagen.getText().toString(),
                materialName.getText().toString(),
                id.getText().toString()
        );
        task.execute();
    }
}