package com.learn2crack.swipeview.Negocio;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import com.learn2crack.swipeview.Datos.DataDB;
import com.learn2crack.swipeview.Modelo.Puntos;
import com.learn2crack.swipeview.View.ViewListPuntosAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class TransNegocioList extends AsyncTask<String,  Void, String> {
    private static String result2;
    private static ArrayList<Puntos> list = new ArrayList<Puntos>();
    private ListView lvArticulos;
    private Context context;
    private String buscar = "";


    public TransNegocioList(ListView lv, Context ct)
    {
        lvArticulos = lv;
        context = ct;
    }
    public TransNegocioList(ListView lv, Context ct,String textoBuscar)
    {
        lvArticulos = lv;
        context = ct;
        buscar = textoBuscar;
    }

    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        Puntos Puntos;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            ResultSet rs;
            if (buscar.toString().length() >  0){
                rs = st.executeQuery("select Puntos_Reciclado.id,direccion,Barrio.descripcion,materiales,geometrycoordinates_y,geometrycoordinates_x from Puntos_Reciclado inner join Barrio on Puntos_Reciclado.barrio = Barrio.id where Fecha_baja is null AND (cast(Puntos_Reciclado.id as char) like '%"+buscar +"%'|| direccion like '%"+buscar+"%' || Barrio.descripcion like '%"+buscar+"%' || materiales like '%"+buscar+"%' || geometrycoordinates_x like '%"+buscar+"%' || geometrycoordinates_y like '%"+buscar +"%' ) order by Puntos_Reciclado.id asc");
            }
            else
            {
                rs = st.executeQuery("select Puntos_Reciclado.id,direccion,Barrio.descripcion,materiales,geometrycoordinates_y,geometrycoordinates_x from Puntos_Reciclado inner join Barrio on Puntos_Reciclado.barrio = Barrio.id where Fecha_baja is null order by Puntos_Reciclado.id asc");
            }

            list.clear();
            while(rs.next()) {
                Puntos = new Puntos();
                Puntos.setId(rs.getInt("id"));
                Puntos.setDireccion(rs.getString("direccion"));
                Puntos.setBarrio(rs.getString("barrio.descripcion"));
                Puntos.setMaterial(rs.getString("materiales"));
                Puntos.setLatitud(rs.getDouble("geometrycoordinates_y"));
                Puntos.setLongitud(rs.getDouble("geometrycoordinates_x"));

                list.add(Puntos);
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
        if(list.isEmpty())
        {
            Toast.makeText(context, "No se han encontrado resultados", Toast.LENGTH_SHORT).show();
            ViewListPuntosAdapter adapter = new ViewListPuntosAdapter(context, list);
            lvArticulos.setAdapter(adapter);
        }
        else
        {
            ViewListPuntosAdapter adapter = new ViewListPuntosAdapter(context, list);
            lvArticulos.setAdapter(adapter);
        }

    }
}
