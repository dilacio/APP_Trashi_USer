package com.learn2crack.swipeview.Negocio;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.learn2crack.swipeview.Datos.DataDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TransNegocioInsertMaterialAlPunto extends AsyncTask<String,  Void, String> {

    private String material;
    private Context ct;
    private String punto;

    public TransNegocioInsertMaterialAlPunto(String material,  Context ct, String idPunto) {
        this.material = material;
        this.ct = ct;
        this.punto = idPunto;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            String response = "";

            ResultSet r = st.executeQuery("select ID from Material where upper(descripcion) = '"+material.toUpperCase()+"'");
            String idMaterial = "";
            if(r.first())
            {
                idMaterial = String.valueOf(r.getInt("id"));
            }

            ResultSet rs = st.executeQuery("select distinct ID_Punto from Punto_Materiales as pm where ID_Punto = "+punto+" and ID_Material = "+idMaterial);
            if(rs.first())
            {
                int Resultado = st.executeUpdate("UPDATE Punto_Materiales set Baja = null where ID_Punto = "+ punto +" and ID_Material = "+ idMaterial );
                if(Resultado > 0 && Resultado > 0)
                {
                    response = material + " agregado";
                }
                else {
                    response =  "Error al agregar " + material;
                }
            }
            else
            {
                ResultSet re = st.executeQuery("select ID from Material where upper(descripcion) = '"+material+"'");
                if(re.first())
                {
                    idMaterial = String.valueOf(re.getInt("id"));
                }

                int resInsert = st.executeUpdate("insert into Punto_Materiales (ID_Punto,ID_Material,Baja) values ("+ Integer.parseInt(punto) +"," + Integer.parseInt(idMaterial)+", null)");
                if(resInsert == 1){
                    return material + " agregado";
                } else {
                    return "Error al agregar " + material;
                }
            }
            rs.close();
            con.close();
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage().toString();
        }
    }

    @Override
    protected void onPostExecute(String response) {
        Toast.makeText(ct, response, Toast.LENGTH_SHORT).show();
    }
}
