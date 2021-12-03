package com.learn2crack.swipeview.Negocio;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.learn2crack.swipeview.Datos.DataDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class TransNegocioDropDown extends AsyncTask<String,  Void, String> {

    private static String result2;
    private static ArrayList<String> listBarrios = new ArrayList<String>();
    private static ArrayList<String> listMateriales = new ArrayList<String>();
    Set<String> listaDeMateriales2 = new HashSet<>();
    private Spinner lvBarrios;
    private Context context;
    ArrayAdapter<String> comboAdapterBarrio;
    Button boton ;
    TableLayout tabla;
    TableRow registro;
    SharedPreferences pref;


    public void setListaDeMateriales2(Set<String> listaDeMateriales2) {
        this.listaDeMateriales2 = listaDeMateriales2;
    }
    public TransNegocioDropDown(Spinner lv1, Context ct,Button btn, TableRow tr, TableLayout tl)
    {
        lvBarrios = lv1;
        context = ct;
        boton = btn;
        tabla = tl;
        registro = tr;
    }

    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        listMateriales.clear();
        listBarrios.clear();
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select descripcion from Barrio order by Descripcion");
            while(rs.next()) {
                String item = rs.getString("descripcion");
                listBarrios.add(item);
            }
            rs = st.executeQuery("select descripcion from Material order by Descripcion");
            while(rs.next()) {
                String item = rs.getString("descripcion");
                listMateriales.add(item);
            }
            response = "Conexion exitosa";
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
        comboAdapterBarrio = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, listBarrios);
        lvBarrios.setAdapter(comboAdapterBarrio);
        Integer contador = 0;
        if( !listMateriales.isEmpty()){
            tabla.removeAllViewsInLayout();
            tabla.removeAllViews();
            for (final   String material: listMateriales) {
                GradientDrawable gd = new GradientDrawable();
                gd.setColor(Color.parseColor("#00796b"));
                gd.setCornerRadius(70);
                gd.setStroke(1, 0xFF000000);
                boton = new Button(context);
                boton.setId(contador++);
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
                        Toast.makeText(context, material + " agregado",Toast.LENGTH_SHORT).show();
                        listaDeMateriales2.add(material);
                        pref = context.getSharedPreferences("elegido",0);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putStringSet("listaDeMateriales",listaDeMateriales2);
                        editor.commit();
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
