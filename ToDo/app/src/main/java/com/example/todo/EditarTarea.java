package com.example.todo;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditarTarea extends AppCompatActivity {

    private String titulo;
    private int idUsuario;
    private String descripcion;
    private String fecha;
    private String hora;
    private String ee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tarea);
        getSupportActionBar().hide();

        // Obtener los extras del Intent
        Intent intent = getIntent();
        titulo = intent.getStringExtra("titulo");
        idUsuario = intent.getIntExtra("idUsuario", 0);
        descripcion = intent.getStringExtra("descripcion");
        fecha = intent.getStringExtra("fecha");
        hora = intent.getStringExtra("hora");
        ee = intent.getStringExtra("ee");

        // Mostrar los datos en los elementos de la interfaz de usuario
        EditText editTextTitulo = findViewById(R.id.eTituloTT);
        EditText editTextDescripcion = findViewById(R.id.eDescripcionTT);
        EditText editTextFecha = findViewById(R.id.eFechaTT);
        EditText editTextHora = findViewById(R.id.eHoraTT);
        EditText editTextEe = findViewById(R.id.eEeTT);

        editTextTitulo.setText(titulo);
        editTextDescripcion.setText(descripcion);
        editTextFecha.setText(fecha);
        editTextHora.setText(hora);
        editTextEe.setText(ee);
    }

    public void guardarDatos(View view) {
        EditText editTextTitulo = findViewById(R.id.eTituloTT);
        EditText editTextDescripcion = findViewById(R.id.eDescripcionTT);
        EditText editTextFecha = findViewById(R.id.eFechaTT);
        EditText editTextHora = findViewById(R.id.eHoraTT);
        EditText editTextEe = findViewById(R.id.eEeTT);

        String nuevoTitulo = editTextTitulo.getText().toString();
        String nuevaDescripcion = editTextDescripcion.getText().toString();
        String nuevaFecha = editTextFecha.getText().toString();
        String nuevaHora = editTextHora.getText().toString();
        String nuevaEe = editTextEe.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Actualizar Tarea");
        builder.setMessage("¿Estás seguro de que deseas actualizar esta tarea?");
        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                actualizarTarea(nuevoTitulo, nuevaDescripcion, nuevaFecha, nuevaHora, nuevaEe);
            }
        });
        builder.setNegativeButton("Cancelar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void actualizarTarea(String nuevoTitulo, String nuevaDescripcion, String nuevaFecha, String nuevaHora, String nuevaEe) {
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("titulo", nuevoTitulo);
        values.put("descripcion", nuevaDescripcion);
        values.put("fecha", nuevaFecha);
        values.put("hora", nuevaHora);
        values.put("ee", nuevaEe);

        String whereClause = "titulo = ? AND idUsuario = ?";
        String[] whereArgs = {titulo, String.valueOf(idUsuario)};
        int rowsUpdated = db.update("Tarea", values, whereClause, whereArgs);

        db.close();

        if (rowsUpdated > 0) {
            Toast.makeText(this, "Tarea actualizada", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al actualizar la tarea", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, Tareas.class);
        intent.putExtra("idUsuario", idUsuario);
        startActivity(intent);
    }
}
