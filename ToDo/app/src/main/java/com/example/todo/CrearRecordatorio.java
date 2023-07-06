package com.example.todo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CrearRecordatorio extends AppCompatActivity {
    private String user;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_recordatorio);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        user = intent.getStringExtra("nombreUsuario");
        userId = intent.getIntExtra("idUsuario", -1);


    }

    public void crearR(View view) {
        EditText eTitulo = findViewById(R.id.txtTituloR);
        EditText eDesc = findViewById(R.id.txtDescR);
        EditText eFecha = findViewById(R.id.txtFechaR);

        String titulo = eTitulo.getText().toString();
        String desc = eDesc.getText().toString();
        String fecha = eFecha.getText().toString();

        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("idUsuario", userId);
        values.put("titulo", titulo);
        values.put("descripcion", desc);
        values.put("fecha", fecha);

        long newRowId = db.insert("Recordatorio", null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "Recordatorio creado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al crear el recordatorio", Toast.LENGTH_SHORT).show();
        }

        db.close();

        Intent intent = new Intent(this, Recordatorios.class);
        intent.putExtra("nombreUsuario", user);
        intent.putExtra("idUsuario", userId);
        startActivity(intent);
    }
}
