package com.learn2crack.swipeview.Negocio;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.learn2crack.swipeview.Datos.DataDB;
import com.learn2crack.swipeview.Modelo.Puntos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class TransNegocioDeletePuntos extends AsyncTask<String,  Void, String> {
    private Puntos puntos;
    private Context context;
    private String IdRegistro;

    public TransNegocioDeletePuntos(Context ct, String Id)
    {
        IdRegistro = Id;
        context = ct;
    }

    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            int Resultado1 = st.executeUpdate("UPDATE Puntos_Reciclado set Fecha_Baja = curdate() WHERE id=" + IdRegistro);
            int Resultado2 = st.executeUpdate("UPDATE Punto_Materiales set Baja = curdate() WHERE ID_Punto="+  IdRegistro);
            if(Resultado1 > 0 && Resultado2 > 0)
            {
                response = "Se ha eliminado el punto de reciclaje";
            }
            else
            {
                response = "No se ha podido eliminar el punto de reciclaje";
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            response = "No se ha podido eliminar el punto de reciclaje";
        }
        return response;
    }
    @Override
    protected void onPostExecute(String response) {
        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
    }
}