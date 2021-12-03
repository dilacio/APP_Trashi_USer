package com.learn2crack.swipeview.Negocio;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.learn2crack.swipeview.Datos.DataDB;
import com.learn2crack.swipeview.Modelo.Home;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TransNegocioValidarCredenciales extends AsyncTask<String,  Void, String> {
    private Context context;
    private String User, Password;

    public TransNegocioValidarCredenciales(Context ct, String User, String Contrasenia)
    {
        context = ct;
        this.User = User;
        this.Password = Contrasenia;
    }

    @Override
    protected String doInBackground(String... strings) {
        String response = "";
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Connection con = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT Nombre_Usuario FROM Usuarios WHERE Nombre_Usuario =" + "'" + User + "'" + " AND Contrasenia =" + "'" + Password + "'"  + " AND Estado = 1");
            if(rs.first())
            {
                String usuario = rs.getString("Nombre_Usuario");
            }
            else
            {
                response = "Usuario o contraseña incorrecta";
            }
            con.close();
        }
        catch(Exception e) {
            e.printStackTrace();
            response = "Usuario o contraseña incorrecta";
        }

        return response;
    }
    @Override
    protected void onPostExecute(String response) {
        if(response != "Usuario o contraseña incorrecta")
        {
            Intent intent = new Intent(context, Home.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(intent);
        }
        else {
            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
        }
    }
}

