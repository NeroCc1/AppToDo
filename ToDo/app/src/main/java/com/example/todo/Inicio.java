package com.example.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Inicio extends AppCompatActivity {
    private String user;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        user = intent.getStringExtra("nombreUsuario");
        userId = intent.getIntExtra("idUsuario", 0);

        TextView bienvenida = findViewById(R.id.welcome);
        bienvenida.setText("¡Bienvenido " + user + "!");
    }

    public void verRecordatorios(View view) {
        Intent intent = new Intent(this, Recordatorios.class);
        intent.putExtra("nombreUsuario", user);
        intent.putExtra("idUsuario", userId);
        startActivity(intent);
    }

    public void verTareas(View view) {
        Intent intent = new Intent(this, Tareas.class);
        intent.putExtra("nombreUsuario", user);
        intent.putExtra("idUsuario", userId);
        startActivity(intent);
    }


}
