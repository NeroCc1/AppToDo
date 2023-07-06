package com.example.todo;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;



public class RecordatorioView extends AppCompatActivity {

    private String titulo;
    private int idUsuario;
    private String descripcion;
    private String fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordatorio_view);
        getSupportActionBar().hide();

        // Obtener los extras del Intent
        Intent intent = getIntent();
        titulo = intent.getStringExtra("titulo");
        idUsuario = intent.getIntExtra("idUsuario", 0);
        descripcion = intent.getStringExtra("descripcion");
        fecha = intent.getStringExtra("fecha");

        // Mostrar los datos en los elementos de la interfaz de usuario
        TextView txtTitulo = findViewById(R.id.tituloTextView);
        TextView txtDescripcion = findViewById(R.id.descripcionTextView);
        TextView txtFecha = findViewById(R.id.fechaTextView);

        txtTitulo.setText(titulo);
        txtDescripcion.setText(descripcion);
        txtFecha.setText(fecha);
    }

    public void eliminarRecordatorio(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar Recordatorio");
        builder.setMessage("¿Estás seguro de que deseas eliminar este recordatorio?");
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                eliminarRecordatorio();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void eliminarRecordatorio() {
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        String whereClause = "titulo = ? AND idUsuario = ?";
        String[] whereArgs = {titulo, String.valueOf(idUsuario)};
        int rowsDeleted = db.delete("Recordatorio", whereClause, whereArgs);

        db.close();

        if (rowsDeleted > 0) {
            Toast.makeText(this, "Recordatorio eliminado", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al eliminar el recordatorio", Toast.LENGTH_SHORT).show();
        }
    }

    public void editarRecord(View view) {
        Intent intent = new Intent(this, EditarRecordatorio.class);
        intent.putExtra("titulo", titulo);
        intent.putExtra("idUsuario", idUsuario);
        intent.putExtra("descripcion", descripcion);
        intent.putExtra("fecha", fecha);
        startActivity(intent);
    }


}
