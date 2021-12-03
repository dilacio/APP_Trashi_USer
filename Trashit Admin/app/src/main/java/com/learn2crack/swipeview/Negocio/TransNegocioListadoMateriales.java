package com.learn2crack.swipeview.Negocio;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.learn2crack.swipeview.Datos.DataDB;
import com.learn2crack.swipeview.Modelo.Material;
import com.learn2crack.swipeview.View.ViewListMaterialesAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class TransNegocioListadoMateriales extends AsyncTask<String,  Void, String>  {

    ListView list;
    Context ct;
    private String buscar = "";
    private ArrayList<Material> listadoMateriales = new ArrayList<>();

    public TransNegocioListadoMateriales(ListView list, Context ct) {
        this.list = list;
        this.ct = ct;
    }
    public TransNegocioListadoMateriales(ListView lv, Context ct, String textoBuscar)
    {
        list = lv;
        this.ct = ct;
        buscar = textoBuscar;
    }

    @Override
    protected String doInBackground(String... strings) {
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            ResultSet rs1 = null;
            if (buscar.toString().length() >  0){
                rs1 = st.executeQuery("select * from Material where cast(id as char) like '%"+ buscar +"%'|| Descripcion like '%"+buscar+"%' || Informacion like '%"+buscar+"%' || Imagen like '%"+buscar+"%' order by ID");
            }
            else
            {
                rs1 = st.executeQuery("select * from Material order by ID ");
            }
            listadoMateriales.clear();
            while(rs1.next()){
                Material material = new Material(
                        rs1.getString("ID"),
                        rs1.getString("Descripcion"),
                        rs1.getString("Informacion"),
                        rs1.getString("Imagen")
                );
                listadoMateriales.add(material);
            }
            rs1.close();
            con.close();
            return "";
        }catch(Exception e){
            Toast.makeText(ct, e.getMessage(), Toast.LENGTH_SHORT).show();
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        if(listadoMateriales.isEmpty())
        {
            Toast.makeText(ct, "No se han encontrado resultados", Toast.LENGTH_SHORT).show();
            ViewListMaterialesAdapter adapter = new ViewListMaterialesAdapter(list.getContext(), listadoMateriales);
            list.setAdapter(adapter);
        }
        else {
            ViewListMaterialesAdapter adapter = new ViewListMaterialesAdapter(list.getContext(), listadoMateriales);
            list.setAdapter(adapter);
        }
    }
}
