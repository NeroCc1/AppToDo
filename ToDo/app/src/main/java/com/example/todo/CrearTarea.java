package com.example.todo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CrearTarea extends AppCompatActivity {
    private String user;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        user = intent.getStringExtra("nombreUsuario");
        userId = intent.getIntExtra("idUsuario", -1);
    }

    public void crearT(View view) {
        EditText eTitulo = findViewById(R.id.eTituloT);
        EditText eFecha = findViewById(R.id.eFechaT);
        EditText eHora = findViewById(R.id.eHoraT);
        EditText eEE = findViewById(R.id.eEE);


        String titulo = eTitulo.getText().toString();
        String fecha = eFecha.getText().toString();
        String hora = eHora.getText().toString();
        String ee = eEE.getText().toString();

        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("idUsuario", userId);
        values.put("titulo", titulo);
        values.put("fecha", fecha);
        values.put("hora", hora);
        values.put("ee", ee);

        long newRowId = db.insert("Tarea", null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "Tarea creada correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al crear la tarea", Toast.LENGTH_SHORT).show();
        }

        db.close();

        Intent intent = new Intent(this, Tareas.class);
        intent.putExtra("nombreUsuario", user);
        intent.putExtra("idUsuario", userId);
        startActivity(intent);
    }

}