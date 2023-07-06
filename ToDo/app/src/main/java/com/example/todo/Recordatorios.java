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

public class Recordatorios extends AppCompatActivity {
    private ListView listRecord;
    private ArrayAdapter<String> adapter;
    private Integer userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordatorios);
        getSupportActionBar().hide();

        listRecord = findViewById(R.id.listRecordatorios);

        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Intent intent = getIntent();
        userId = intent.getIntExtra("idUsuario", 0);

        String query = "SELECT * FROM Recordatorio WHERE idUsuario = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        ArrayList<String> recordatoriosList = new ArrayList<>();

        while (cursor.moveToNext()) {
            String titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"));
            recordatoriosList.add(titulo);

            Log.d("Recordatorio", "Título: " + titulo);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recordatoriosList);
        listRecord.setAdapter(adapter);

        cursor.close();
        db.close();


        listRecord.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String tituloSeleccionado = recordatoriosList.get(position);

                Recordatorio recordatorio = obtenerRecordatorio(tituloSeleccionado);


                Intent intent = new Intent(Recordatorios.this, RecordatorioView.class);
                intent.putExtra("idUsuario", userId);
                intent.putExtra("titulo", recordatorio.getTitulo());
                intent.putExtra("descripcion", recordatorio.getDescripcion());
                intent.putExtra("fecha", recordatorio.getFecha());
                startActivity(intent);
            }
        });
    }

    private Recordatorio obtenerRecordatorio(String titulo) {
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {"idUsuario", "descripcion", "fecha"};
        String selection = "titulo = ?";
        String[] selectionArgs = {titulo};

        Cursor cursor = db.query("Recordatorio", projection, selection, selectionArgs, null, null, null);
        Recordatorio recordatorio = null;

        if (cursor != null && cursor.moveToFirst()) {
            int idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow("idUsuario"));
            String descripcion = cursor.getString(cursor.getColumnIndexOrThrow("descripcion"));
            String fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"));

            recordatorio = new Recordatorio();
            recordatorio.setTitulo(titulo);
            recordatorio.setIdUsuario(idUsuario);
            recordatorio.setDescripcion(descripcion);
            recordatorio.setFecha(fecha);
        }

        cursor.close();
        db.close();

        return recordatorio;
    }


    public void crearRecordatorio(View view) {
        Intent intent = new Intent(this, CrearRecordatorio.class);
        intent.putExtra("idUsuario", userId);
        startActivity(intent);
    }
}

