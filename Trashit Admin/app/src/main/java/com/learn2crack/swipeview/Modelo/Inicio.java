package com.learn2crack.swipeview.Modelo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.learn2crack.swipeview.R;

public class Inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
    }
    public void Redireccion (View view)
    {
        Intent intent = new Intent(this,  Login.class);
        startActivity(intent);

    }
}