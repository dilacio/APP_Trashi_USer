package com.learn2crack.swipeview.View;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.learn2crack.swipeview.Modelo.Puntos;
import com.learn2crack.swipeview.R;
import com.learn2crack.swipeview.Negocio.TransNegocioDropDown;
import com.learn2crack.swipeview.Negocio.TransNegocioInsertPuntos;

import java.util.HashSet;
import java.util.Set;

public class ViewAltaPuntos extends Fragment {

    public static final String TITLE = "ALTA";
    EditText latitud, longitud, direccion;
    Button agregar;
    Spinner material, barrio;
    com.learn2crack.swipeview.Modelo.Puntos Puntos;
    TableLayout tabla;
    TableRow registro;
    Button boton ;
    SharedPreferences pref;
    TransNegocioDropDown transNegocioDropDown;
    protected ArrayAdapter<CharSequence> adapter;
    public static ViewAltaPuntos newInstance() {

        return new ViewAltaPuntos();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);
        latitud = (EditText) v.findViewById(R.id.Latitud);
        longitud = (EditText) v.findViewById(R.id.Longitud);
        direccion = (EditText) v.findViewById(R.id.Direccion);
        agregar = (Button) v.findViewById(R.id.Modificar);
        barrio = (Spinner) v.findViewById(R.id.Barrio);
        tabla = (TableLayout) v.findViewById(R.id.tablaMateriales);
        registro = (TableRow) v.findViewById(R.id.registroTabla);
        GetBarriosMateriales();
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pref = getContext().getSharedPreferences("elegido",0);
                Set<String> listaDeMateriales = pref.getStringSet("listaDeMateriales",null);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.commit();
                if(latitud.getText().toString().equals("")|| longitud.getText().toString().equals("")||direccion.getText().toString().equals("") || listaDeMateriales == null)
                {
                    Toast.makeText(getContext(), "Complete todos los campos",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(latitud.getText().toString().contains("-") && latitud.getText().toString().contains(".")
                    && longitud.getText().toString().contains("-") && longitud.getText().toString().contains("."))
                    {
                        int contadorGuionesLatitud = 0;
                        int contadorPuntosLatitud = 0;
                        int contadorGuionesLongitud = 0;
                        int contadorPuntosLongitud = 0;
                        for(int x=0; x<latitud.getText().toString().length();x++)
                        {
                            if(latitud.getText().toString().charAt(x) == '-')
                            {
                                contadorGuionesLatitud++;
                            }
                            if(latitud.getText().toString().charAt(x) == '.')
                            {
                                contadorPuntosLatitud++;
                            }
                        }
                        for(int x=0; x<longitud.getText().toString().length();x++)
                        {
                            if(longitud.getText().toString().charAt(x) == '-')
                            {
                                contadorGuionesLongitud++;
                            }
                            if(longitud.getText().toString().charAt(x) == '.')
                            {
                                contadorPuntosLongitud++;
                            }
                        }
                        if(contadorGuionesLongitud == 1 && contadorPuntosLongitud == 1
                                && contadorGuionesLatitud == 1 && contadorPuntosLatitud == 1) {
                            if(longitud.getText().toString().charAt(0) == '-'
                                    && latitud.getText().toString().charAt(0) == '-') {
                                if (Character.isDigit(longitud.getText().toString().charAt(1))
                                        && Character.isDigit(latitud.getText().toString().charAt(1))
                                        && Character.isDigit(latitud.getText().toString().charAt(2)) &&
                                            Character.isDigit(longitud.getText().toString().charAt(2))) {
                                    if (longitud.getText().toString().charAt(3) == '.'
                                            && latitud.getText().toString().charAt(3) == '.')
                                    {
                                        if (longitud.getText().toString().length() > 5 == true && latitud.getText().toString().length() > 5 == true) {
                                            Puntos = new Puntos();
                                            Puntos.setLatitud(Double.parseDouble(latitud.getText().toString()));
                                            Puntos.setLongitud(Double.parseDouble(longitud.getText().toString()));
                                            Puntos.setDireccion(direccion.getText().toString());
                                            Puntos.setBarrio(barrio.getSelectedItem().toString());
                                            Insert(listaDeMateriales);
                                            Set<String> listaDeMateriales2 = new HashSet<>();
                                            transNegocioDropDown.setListaDeMateriales2(listaDeMateriales2);
                                        } else {
                                            Toast.makeText(getContext(), "La latitud y la longitud deben tener mayor longitud de caracteres", Toast.LENGTH_SHORT).show();
                                        }
                                }
                                    else
                                    {
                                        Toast.makeText(getContext(), "Los caracteres siguientes al signo negativo y los dos numericos debe ser un punto", Toast.LENGTH_SHORT).show();

                                    }
                                }
                                else
                                {
                                    Toast.makeText(getContext(), "Despues del signo negativo, los dos caracteres siguientes deben ser numericos", Toast.LENGTH_SHORT).show();
                                }
                            }
                else
                {
                    Toast.makeText(getContext(), "La latitud y la longitud deben comenzar con un signo negativo",Toast.LENGTH_SHORT).show();
                }
            }
                        else
            {
                Toast.makeText(getContext(), "La latitud y la longitud deben contener un solo punto y un solo signo negativo",Toast.LENGTH_SHORT).show();
            }
        }
                    else
        {
            Toast.makeText(getContext(), "La latitud y la longitud deben ser decimal y comenzar con un signo negativo",Toast.LENGTH_SHORT).show();
        }
                }
            }
        });
        return v;
    }
    public void Insert(Set<String> listaDeMateriales) {
        TransNegocioInsertPuntos task = new TransNegocioInsertPuntos(getContext(), Puntos,listaDeMateriales);
        task.execute();
    }
    public void GetBarriosMateriales() {
        transNegocioDropDown = new TransNegocioDropDown(barrio,getContext(),boton,registro,tabla);
        transNegocioDropDown.execute();
    }

}