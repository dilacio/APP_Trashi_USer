package com.learn2crack.swipeview.Modelo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.learn2crack.swipeview.R;
import com.learn2crack.swipeview.View.MainActivity;
import com.learn2crack.swipeview.View.MainActivity2;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    public void Puntos (View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void Materiales (View view)
    {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);

    }
}