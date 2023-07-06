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

public class EditarRecordatorio extends AppCompatActivity {

    private String titulo;
    private int idUsuario;
    private String descripcion;
    private String fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_recordatorio);
        getSupportActionBar().hide();


        Intent intent = getIntent();
        titulo = intent.getStringExtra("titulo");
        idUsuario = intent.getIntExtra("idUsuario", 0);
        descripcion = intent.getStringExtra("descripcion");
        fecha = intent.getStringExtra("fecha");


        EditText editTextTitulo = findViewById(R.id.eTitulo);
        EditText editTextDescripcion = findViewById(R.id.eDesc);
        EditText editTextFecha = findViewById(R.id.eFecha);


        editTextTitulo.setText(titulo);
        editTextDescripcion.setText(descripcion);
        editTextFecha.setText(fecha);
    }


    public void guardarDatos(View view) {

        EditText editTextTitulo = findViewById(R.id.eTitulo);
        EditText editTextDescripcion = findViewById(R.id.eDesc);
        EditText editTextFecha = findViewById(R.id.eFecha);

        String nuevoTitulo = editTextTitulo.getText().toString();
        String nuevaDescripcion = editTextDescripcion.getText().toString();
        String nuevaFecha = editTextFecha.getText().toString();


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Actualizar Recordatorio");
        builder.setMessage("¿Estás seguro de que deseas actualizar este recordatorio?");
        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                actualizarRecordatorio(nuevoTitulo, nuevaDescripcion, nuevaFecha);
            }
        });
        builder.setNegativeButton("Cancelar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void actualizarRecordatorio(String nuevoTitulo, String nuevaDescripcion, String nuevaFecha) {
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("titulo", nuevoTitulo);
        values.put("descripcion", nuevaDescripcion);
        values.put("fecha", nuevaFecha);

        String whereClause = "titulo = ? AND idUsuario = ?";
        String[] whereArgs = {titulo, String.valueOf(idUsuario)};
        int rowsUpdated = db.update("Recordatorio", values, whereClause, whereArgs);

        db.close();

        if (rowsUpdated > 0) {
            Toast.makeText(this, "Recordatorio actualizado", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al actualizar el recordatorio", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(this, Recordatorios.class);
        intent.putExtra("nombreUsuario", idUsuario);
        startActivity(intent);
    }
}
