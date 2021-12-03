package com.learn2crack.swipeview.Negocio;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.learn2crack.swipeview.Datos.DataDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TransNegocioInsertMaterial extends AsyncTask<String,  Void, String> {

    private String material;
    private String informacion;
    private String imagen;
    private Context ct;

    public TransNegocioInsertMaterial(String material, String informacion, String imagen, Context ct) {
        this.material = material;
        this.informacion = informacion;
        this.imagen = imagen;
        this.ct = ct;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();

            ResultSet rs1 = st.executeQuery("select * from Material where Descripcion = '" + this.material + "';");
            if(rs1.first())
            {
                return "Ya existe un material con ese nombre.";
            }
            rs1.close();

            int r = st.executeUpdate(
                    "INSERT INTO Material (Descripcion, Informacion, Imagen)" +
                            "VALUES ('" + this.material + "', '" + this.informacion + "', '" + this.imagen + "' );");
            con.close();
            if(r == 1){
                return "El registro ha sido grabado exitosamente.";
            } else {
                return "Ha ocurrido un error al guardar el registro.";
            }
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
