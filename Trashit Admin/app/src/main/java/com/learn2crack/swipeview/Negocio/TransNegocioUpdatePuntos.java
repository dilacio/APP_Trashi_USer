package com.learn2crack.swipeview.Negocio;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.learn2crack.swipeview.Datos.DataDB;
import com.learn2crack.swipeview.Modelo.Puntos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

public class TransNegocioUpdatePuntos extends AsyncTask<String, Void, String> {

    private com.learn2crack.swipeview.Modelo.Puntos Puntos;
    private Context context;
    Set<String> listaDeMateriales = new HashSet<>();

    public TransNegocioUpdatePuntos(Context ct, Puntos Puntos1) {
        Puntos = Puntos1;
        context = ct;
    }

    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        int validarRegistroExistente = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            ResultSet flag = st.executeQuery("SELECT * FROM Puntos_Reciclado WHERE id=" + Puntos.getId() );

            if (flag.next()) {
                ResultSet rs1 = st.executeQuery("SELECT Material.Descripcion FROM Punto_Materiales as pm inner join Material on Material.ID = pm.ID_Material where Baja is null AND id_punto =" + Puntos.getId() + "  order by Material.Descripcion");
                while (rs1.next()) {
                    {
                        listaDeMateriales.add((rs1.getString("Material.Descripcion")));
                    }
                }
                ResultSet validacion = st.executeQuery("SELECT id FROM Puntos_Reciclado WHERE geometrycoordinates_x =" + Puntos.getLongitud() + " AND geometrycoordinates_y = " + Puntos.getLatitud());
                if (validacion.first()) {
                    validarRegistroExistente = validacion.getInt("id");
                }
                if (validarRegistroExistente == Puntos.getId()) {
                    ResultSet rs2 = st.executeQuery("SELECT id FROM Barrio WHERE descripcion='" + Puntos.getBarrio() + "'");
                    if (rs2.first()) {
                        Puntos.setIdBarrio(rs2.getInt("id"));
                    }
                    String MaterialConcatenado = "";
                    int count = 0;
                    int listaLength = listaDeMateriales.size();
                    for (String Materiales : listaDeMateriales) {
                        count++;
                        if (listaLength != count) {
                            MaterialConcatenado += Materiales + " / ";
                        } else {
                            MaterialConcatenado += Materiales;
                        }
                    }
                    int Resultado1 = st.executeUpdate("UPDATE Puntos_Reciclado set Direccion= '" + Puntos.getDireccion() + "'" + ", Materiales = '" + MaterialConcatenado + "'" + ",  Barrio=" + Puntos.getIdBarrio() + ",geometrycoordinates_x=" + Puntos.getLongitud() + ",geometrycoordinates_y = " + Puntos.getLatitud() + " WHERE id=" + Puntos.getId());
                    if (Resultado1 > 0) {
                        response = "Se ha actualizado el punto de reciclaje";
                    } else return "No se ha podido actualizar el punto de reciclaje";
                    con.close();
                } else {
                    response = "Ya existe un punto con esas coordenadas";
                }
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = "No se ha podido actualizar el el punto de reciclaje";
        }
        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
    }
}
