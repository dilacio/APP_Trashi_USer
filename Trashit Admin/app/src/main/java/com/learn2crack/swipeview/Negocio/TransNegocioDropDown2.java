package com.learn2crack.swipeview.Negocio;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.learn2crack.swipeview.Datos.DataDB;
import com.learn2crack.swipeview.Modelo.Puntos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class TransNegocioDropDown2 extends AsyncTask<String,  Void, String> {
    private static String result2;
    private static ArrayList<String> listBarrios = new ArrayList<String>();
    private static ArrayList<String> listMateriales = new ArrayList<String>();
    private Spinner lvBarrios;
    private Spinner lvMateriales;
    private Context context;
    ArrayAdapter<String> comboAdapterBarrio;
    private String idPunto = "0";
    Puntos puntos;
    Set<String> listaDeMateriales = new HashSet<>();
    Button boton ;
    TableLayout tabla;
    TableRow registro;

    public TransNegocioDropDown2(Spinner lv1, Spinner lv2, Context ct)
    {
        lvBarrios = lv1;
        lvMateriales = lv2;
        context = ct;
    }
    public TransNegocioDropDown2(Spinner lv1, Context ct, String idABuscar, Button btn, TableRow tr, TableLayout tl)
    {
        context = ct;
        idPunto = idABuscar;
        boton = btn;
        tabla = tl;
        registro = tr;
        lvBarrios = lv1;
    }

    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            ResultSet rs;
            listMateriales.clear();
            listaDeMateriales.clear();
            listBarrios.clear();
                rs = st.executeQuery("select b.descripcion from Barrio as b order by b.Descripcion");
                while(rs.next()) {
                    String item = rs.getString("b.descripcion");
                    listBarrios.add(item);
                }

            if(idPunto != "0"){
                rs = st.executeQuery("select m.descripcion from Material as m join Punto_Materiales as pm on m.ID = pm.ID_Material where Baja is null and ID_Punto = "+idPunto + " order by m.Descripcion");
            }
            else
            {
                rs = st.executeQuery("select descripcion from Material order by Descripcion");
            }

            while(rs.next()) {
                String item = rs.getString("descripcion");
                if(idPunto != "0") {
                    listaDeMateriales.add(item);
                }
            }
            if(idPunto != "0"){
                rs = st.executeQuery("select m.descripcion from Material as m where ID not in (select distinct ID_Material from Punto_Materiales where ID_Punto = "+idPunto+" and Baja is null) order by m.Descripcion");
                while(rs.next()) {
                    String item = rs.getString("descripcion");
                    listMateriales.add(item);
                }}
            response = "Conexion exitosa";
            con.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            result2 = "Conexion no exitosa";
        }
        return response;
    }
    @Override
    protected void onPostExecute(String response) {
        TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 10, 20, 10);
        comboAdapterBarrio = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, listBarrios);
        lvBarrios.setAdapter(comboAdapterBarrio);
        Integer contador = 0;
        Integer contador2 = 0;
        if( !listMateriales.isEmpty()){
            tabla.removeAllViewsInLayout();
            tabla.removeAllViews();
            for (final   String material: listMateriales) {
                GradientDrawable gd = new GradientDrawable();
                gd.setColor(Color.parseColor("#00796b"));
                gd.setCornerRadius(70);
                gd.setStroke(1, 0xFF000000);
                boton = new Button(context);
                boton.setId(contador2++);
                String textoBoton = "Agregar " + material.toString();
                boton.setTag(material);
                boton.setHeight(15);
                boton.setWidth(900);
                boton.setTextColor(Color.parseColor("#ffffff"));
                boton.setBackground(gd);
                boton.setText(textoBoton);
                boton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TransNegocioInsertMaterialAlPunto task = new TransNegocioInsertMaterialAlPunto(material,context,idPunto);
                        task.execute();
                    }
                });

                registro.setLayoutParams(params);
                registro = new TableRow(context);
                registro.addView(boton);
                tabla.addView(registro);
            }

        }
        if( !listaDeMateriales.isEmpty()){
            for (final   String material: listaDeMateriales) {
                GradientDrawable gd = new GradientDrawable();
                gd.setColor(Color.parseColor("#d32f2f"));
                gd.setCornerRadius(70);
                gd.setStroke(1, 0xFF000000);
                boton = new Button(context);
                boton.setId(contador++);
                String textoBoton = "Eliminar " + material.toString();
                boton.setTag(material);
                boton.setHeight(15);
                boton.setWidth(500);
                boton.setTextColor(Color.parseColor("#ffffff"));
                boton.setBackground(gd);
                boton.setText(textoBoton);
                boton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TransNegocioEliminarMaterialDePunto task = new TransNegocioEliminarMaterialDePunto(context,material,Integer.parseInt(idPunto));
                        task.execute();
                    }
                });

                registro.setLayoutParams(params);
                registro = new TableRow(context);
                registro.addView(boton);
                tabla.addView(registro);
            }

        }
        }
    }

