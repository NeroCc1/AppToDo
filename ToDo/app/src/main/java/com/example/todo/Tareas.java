package com.example.todo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Tareas extends AppCompatActivity {
    private ListView listTar;
    private ArrayAdapter<String> adapter;
    private Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);
        getSupportActionBar().hide();

        listTar = findViewById(R.id.listT);

        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Intent intent = getIntent();
        userId = intent.getIntExtra("idUsuario", 0);

        String query = "SELECT titulo FROM Tarea WHERE idUsuario = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        ArrayList<String> tareasList = new ArrayList<>();

        while (cursor.moveToNext()) {
            String titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"));
            tareasList.add(titulo);

            Log.d("Tarea", "Título: " + titulo);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tareasList);
        listTar.setAdapter(adapter);

        cursor.close();
        db.close();

        listTar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tituloSeleccionado = tareasList.get(position);

                Tarea tarea = obtenerTarea(tituloSeleccionado);

                Intent intent = new Intent(Tareas.this, TareaView.class);
                intent.putExtra("idUsuario", userId);
                intent.putExtra("titulo", tarea.getTitulo());
                intent.putExtra("descripcion", tarea.getDescripcion()); // Agregar descripción como extra
                intent.putExtra("fecha", tarea.getFecha());
                intent.putExtra("hora", tarea.getHora());
                intent.putExtra("ee", tarea.getEe());
                startActivity(intent);
            }
        });
    }

    private Tarea obtenerTarea(String titulo) {
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {"idUsuario", "fecha", "hora", "ee"};
        String selection = "titulo = ?";
        String[] selectionArgs = {titulo};

        Cursor cursor = db.query("Tarea", projection, selection, selectionArgs, null, null, null);
        Tarea tarea = null;

        if (cursor != null && cursor.moveToFirst()) {
            int idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow("idUsuario"));
            String fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"));
            String hora = cursor.getString(cursor.getColumnIndexOrThrow("hora"));
            String ee = cursor.getString(cursor.getColumnIndexOrThrow("ee"));

            tarea = new Tarea();
            tarea.setTitulo(titulo);
            tarea.setIdUsuario(idUsuario);
            tarea.setFecha(fecha);
            tarea.setHora(hora);
            tarea.setEe(ee);
        }

        cursor.close();
        db.close();

        return tarea;
    }

    public void crearT(View view) {
        Intent intent = new Intent(this, CrearTarea.class);
        intent.putExtra("idUsuario", userId);
        startActivity(intent);
    }
}
