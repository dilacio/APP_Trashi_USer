package com.learn2crack.swipeview.Negocio;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.learn2crack.swipeview.Datos.DataDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class TransNegocioEliminarMaterialDePunto extends AsyncTask<String,  Void, String> {

    public Integer idPunto;
    public Context ct;
    String materialABorrar;

    public TransNegocioEliminarMaterialDePunto(Context ct, String material, int Punto) {
    this.idPunto = Punto;
    this.ct = ct;
    this.materialABorrar = material;
    }

    @Override
    protected String doInBackground(String... strings) {

        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();

            int rs1 = st.executeUpdate("update Punto_Materiales  p inner join Material m on p.ID_Material = m.ID  set Baja = curdate()   where p.ID_Punto = "+idPunto+" and upper(m.Descripcion) = '"+materialABorrar.toUpperCase()+"'");
            if(rs1 > 0)
            {
                return materialABorrar + " eliminado";
            }
            con.close();
            return "Ha ocurrido un error al eliminar " + materialABorrar;
        }catch(Exception e){
            return e.getMessage();
        }
    }

    protected void onPostExecute(String response) {
        Toast.makeText(ct, response, Toast.LENGTH_SHORT).show();
    }
}
