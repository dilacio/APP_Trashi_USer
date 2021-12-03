package com.learn2crack.swipeview.Negocio;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import com.learn2crack.swipeview.Datos.DataDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TransNegocioBuscarMaterial extends AsyncTask<String,  Void, String> {

    public EditText id;
    public EditText informacion;
    public EditText imagen;
    public EditText materialName;
    public Context ct;

    private String _informacion = "";
    private String _descripcion = "";
    private String _imagen = "";


    public TransNegocioBuscarMaterial(EditText id,EditText informacion, EditText imagen, EditText materialName, Context ct) {
        this.id = id;
        this.informacion = informacion;
        this.imagen = imagen;
        this.materialName = materialName;
        this.ct = ct;
    }

    @Override
    protected String doInBackground(String... strings) {

        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();

            ResultSet rs1 = st.executeQuery("select Informacion, Imagen, Descripcion from Material where id = '" + this.id.getText().toString() + "' order by Descripcion");
            if(rs1.next())
            {
                _informacion = rs1.getString("Informacion");
                _imagen = rs1.getString("Imagen");
                _descripcion = rs1.getString("Descripcion");
            } else {
                con.close();
                return "No se han encontrado resultados.";
            }
            con.close();
            rs1.close();
            return "";
        }catch(Exception e){
            return e.getMessage();
        }
    }

    protected void onPostExecute(String response) {

        if(response.length() > 0)
            Toast.makeText(ct, response, Toast.LENGTH_SHORT).show();

        informacion.setText(_informacion);
        imagen.setText(_imagen);
        materialName.setText(_descripcion);
    }
}
