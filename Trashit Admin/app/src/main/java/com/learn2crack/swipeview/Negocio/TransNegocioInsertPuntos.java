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

public class TransNegocioInsertPuntos extends AsyncTask<String, Void, String> {

    private com.learn2crack.swipeview.Modelo.Puntos Puntos;
    private Context context;
    Set<String> listaDeMateriales;
    Set<Integer> listaDeIdMateriales = new HashSet<>();

    public TransNegocioInsertPuntos(Context ct, Puntos Products1, Set<String> listaDeMateriales) {
        Puntos = Products1;
        context = ct;
        this.listaDeMateriales = listaDeMateriales;
    }

    @Override
    protected String doInBackground(String... strings) {
        int UltimoRegistro = 0;
        int validarRegistroExistente = 0;
        String response = "";

        try {
            String materialContatenado = "";
            int count = 0;
            int listaLength = listaDeMateriales.size();
            for (String material : listaDeMateriales) {
                count++;
                if (listaLength != count) {
                    materialContatenado += material + " / ";
                } else {
                    materialContatenado += material;
                }
            }
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            for (String material : listaDeMateriales) {
                ResultSet rs1 = st.executeQuery("SELECT id FROM Material WHERE descripcion='" + material + "'");
                if (rs1.first()) {
                    listaDeIdMateriales.add(rs1.getInt("id"));
                }
            }
            ResultSet validacion = st.executeQuery("SELECT id FROM Puntos_Reciclado WHERE geometrycoordinates_x =" + Puntos.getLongitud() + " AND geometrycoordinates_y = " + Puntos.getLatitud());
            if (validacion.first()) {
                validarRegistroExistente = validacion.getInt("id");
            }
            if (validarRegistroExistente == 0) {
                ResultSet rs2 = st.executeQuery("SELECT id FROM Barrio WHERE descripcion='" + Puntos.getBarrio() + "'");
                if (rs2.first()) {
                    Puntos.setIdBarrio(rs2.getInt("id"));
                }
                int Resultado1 = st.executeUpdate("INSERT INTO Puntos_Reciclado (type,Direccion,Barrio,Materiales,Mas_Info,geometrytype,geometrycoordinates_x,geometrycoordinates_y,comuna)" + "VALUES ( 'Feature'," + "'" + Puntos.getDireccion() + "'" + "," + Puntos.getIdBarrio() + "," + "'" + materialContatenado + "'" + ", 'Los materiales deben estar limpios y secos', 'Point'," + Puntos.getLongitud() + "," + Puntos.getLatitud() + ",1)");
                ResultSet rs3 = st.executeQuery("SELECT id FROM Puntos_Reciclado order by id desc limit 1");
                if (rs3.first()) {
                    UltimoRegistro = rs3.getInt("id");
                }
                for (Integer Id : listaDeIdMateriales) {
                    int Resultado2 = st.executeUpdate("INSERT INTO Punto_Materiales (ID_Punto,ID_Material)" + "VALUES (" + UltimoRegistro + "," + Id + ")");
                    if (Resultado2 > 0 && Resultado1 > 0) {
                        response = "Punto de reciclaje agregado correctamente";
                    } else {
                        response = "No se ha podido agregar el punto de reciclaje";
                    }
                }
            } else {
                response = "Ya existe un punto con esas coordenadas";
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            response = "No se ha podido agregar el punto de reciclaje";
        }
        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
    }
}
