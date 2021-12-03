package com.learn2crack.swipeview.View;

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
import com.learn2crack.swipeview.Negocio.TransNegocioDeletePuntos;
import com.learn2crack.swipeview.Negocio.TransNegocioDropDown2;
import com.learn2crack.swipeview.Negocio.TransNegocioSearchPuntos;
import com.learn2crack.swipeview.Negocio.TransNegocioUpdatePuntos;

public class ViewModificacionPuntos extends Fragment {

    public static final String TITLE = "MODIFICACION";
    EditText latitud, longitud, direccion, id;
    Button modificar, buscar, eliminar;
    Spinner material, barrio;
    com.learn2crack.swipeview.Modelo.Puntos Puntos;
    TableLayout tabla;
    TableRow registro;
    Button boton ;

    protected ArrayAdapter<CharSequence> adapter;
    public static ViewModificacionPuntos newInstance() {

        return new ViewModificacionPuntos();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile,container,false);
        modificar = (Button) v.findViewById(R.id.Modificar);
        buscar = (Button) v.findViewById(R.id.Buscar);
        eliminar = (Button) v.findViewById(R.id.Eliminar);
        latitud = (EditText) v.findViewById(R.id.Latitud);
        longitud = (EditText) v.findViewById(R.id.Longitud);
        direccion = (EditText) v.findViewById(R.id.Direccion);
        id = (EditText) v.findViewById(R.id.Id);
        barrio = (Spinner) v.findViewById(R.id.Barrio);
        tabla = (TableLayout) v.findViewById(R.id.tablaMateriales);
        registro = (TableRow) v.findViewById(R.id.registroTabla);
        GetBarriosMateriales();

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.getText().toString().equals(""))
                {
                    Toast.makeText(getContext(), "Para buscar es obligatorio ingresar un ID",Toast.LENGTH_SHORT).show();
                }
                else {
                    cargaDatosDespuesModificacion();
                }
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id.getText().toString().equals(""))
                {
                    Toast.makeText(getContext(), "Para eliminar es obligatorio ingresar un ID",Toast.LENGTH_SHORT).show();
                }
                else {
                    String IdRegistro = id.getText().toString();
                    DeletePuntos(IdRegistro);
                }
            }
        });

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Id = id.getText().toString();
                String Latitud = latitud.getText().toString();
                String Longitud = longitud.getText().toString();
                String Direccion = direccion.getText().toString();
                String Barrio = barrio.getSelectedItem().toString();
                if(Id.equals("")||Latitud.equals("")||Longitud.equals("")||Direccion.equals(""))
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
                            && latitud.getText().toString().charAt(0) == '-')
                            {
                                if (Character.isDigit(longitud.getText().toString().charAt(1))
                                        && Character.isDigit(latitud.getText().toString().charAt(1))
                                        && Character.isDigit(latitud.getText().toString().charAt(2)) &&
                                        Character.isDigit(longitud.getText().toString().charAt(2))) {
                                    if (longitud.getText().toString().charAt(3) == '.'
                                            && latitud.getText().toString().charAt(3) == '.')
                                    {
                                if(longitud.getText().toString().length()>5 == true && latitud.getText().toString().length()>5 == true) {
                                    Puntos = new Puntos();
                                    Puntos.setId(Integer.parseInt(id.getText().toString()));
                                    Puntos.setLatitud(Double.parseDouble(latitud.getText().toString()));
                                    Puntos.setLongitud(Double.parseDouble(longitud.getText().toString()));
                                    Puntos.setDireccion(direccion.getText().toString());
                                    Puntos.setBarrio(barrio.getSelectedItem().toString());
                                    UpdatePuntos();
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
    public void GetBarriosMateriales() {
        TransNegocioDropDown2 task = new TransNegocioDropDown2(barrio,material,getContext());
        task.execute();
    }
    public void GetBarriosMateriales(String idABuscar) {
        TransNegocioDropDown2 task = new TransNegocioDropDown2(barrio,getContext(),idABuscar,boton,registro,tabla);
        task.execute();
    }
    public void SearchPuntos(String IdRegistro) {
        TransNegocioSearchPuntos task = new TransNegocioSearchPuntos(getContext(),IdRegistro,id, latitud, longitud, direccion, barrio, material);

        task.execute();
    }
    public void DeletePuntos(String IdRegistro) {
        TransNegocioDeletePuntos task = new TransNegocioDeletePuntos(getContext(), IdRegistro);
        task.execute();
    }
    public void UpdatePuntos() {
        TransNegocioUpdatePuntos task = new TransNegocioUpdatePuntos(getContext(),Puntos);
        task.execute();
    }

    public void cargaDatosDespuesModificacion() {
        String IdRegistro = id.getText().toString();
        GetBarriosMateriales(IdRegistro);
        SearchPuntos(IdRegistro);

    }
}