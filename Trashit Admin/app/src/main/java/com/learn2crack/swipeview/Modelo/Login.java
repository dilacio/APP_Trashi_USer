package com.learn2crack.swipeview.Modelo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.learn2crack.swipeview.Negocio.TransNegocioValidarCredenciales;
import com.learn2crack.swipeview.R;

public class Login extends AppCompatActivity {
    EditText usuario, contrasenia;
    Button ingresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ingresar = (Button) findViewById(R.id.Ingresar);
        usuario = (EditText) findViewById(R.id.User);
        contrasenia = (EditText) findViewById(R.id.Password);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(usuario.getText().toString().equals("")|| contrasenia.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Complete todos los campos",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ValidarCredenciales(usuario.getText().toString(),contrasenia.getText().toString());
                }
            }
        });
    }
    public void ValidarCredenciales(String usuario, String contrasenia) {
        TransNegocioValidarCredenciales task = new TransNegocioValidarCredenciales(getApplicationContext(),usuario,contrasenia);
        task.execute();
    }
}