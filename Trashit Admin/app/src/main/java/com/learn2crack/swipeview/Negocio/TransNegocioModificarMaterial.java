package com.learn2crack.swipeview.Negocio;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.learn2crack.swipeview.Datos.DataDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Locale;

public class TransNegocioModificarMaterial extends AsyncTask<String,  Void, String> {

    private Context ct;
    private String informacion;
    private String imagen;
    private String materialName;
    private String id;

    public TransNegocioModificarMaterial(Context ct, String informacion, String imagen, String materialName, String id) {
        this.ct = ct;
        this.informacion = informacion;
        this.imagen = imagen;
        this.materialName = materialName;
        this.id = id;
    }

    @Override
    protected String doInBackground(String... strings) {

        int idAux = 0;
        String descripcionAux = "";

        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();

            ResultSet check = st.executeQuery("Select ID, Descripcion from Material where Descripcion = '" + this.materialName + "' order by Descripcion");

            if(check.next()){
                idAux = check.getInt("ID");
                descripcionAux = check.getString("Descripcion").toLowerCase(Locale.ROOT);
            }

            if(
                    idAux != Integer.parseInt(this.id) &&
                            descripcionAux.equals(this.materialName.toLowerCase(Locale.ROOT))
            )
                return "Ya existe un registro con ese nombre";

            int rs1 = st.executeUpdate(
                    "UPDATE Material set Descripcion = '" + this.materialName + "', Informacion = '" + this.informacion + "', Imagen = '" + this.imagen + "' where ID  = " + this.id
            );
            if(rs1 > 0)
            {
                con.close();
                return "Se ha actualizado correctamente el registro";
            }
            else {
                con.close();
                return "Ha ocurrido un error al actualizar el registro";
            }

        } catch (Exception e){
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(ct, s, Toast.LENGTH_SHORT).show();
    }
}
