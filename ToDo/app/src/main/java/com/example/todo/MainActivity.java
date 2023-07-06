package com.example.todo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        TextView textView = findViewById(R.id.txtRegistro);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Registro.class);
                startActivity(intent);
            }
        });
    }

    public void ingresar(View view) {
        EditText eUsuario = findViewById(R.id.txtUsuario);
        EditText eContraseña = findViewById(R.id.txtContraseña);

        String usuario = eUsuario.getText().toString();
        String contraseña = eContraseña.getText().toString();

        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {"id", "nombreUsuario"};
        String selection = "nombreUsuario = ? AND contraseña = ?";
        String[] selectionArgs = {usuario, contraseña};

        Cursor cursor = db.query("Usuario", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            // El usuario existe en la base de datos
            // Obtener el ID del usuario desde el cursor
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));

            // Redirige a otra Activity
            Intent intent = new Intent(this, Inicio.class);
            intent.putExtra("nombreUsuario", usuario);
            intent.putExtra("idUsuario", userId);
            startActivity(intent);
            // Aquí puedes realizar acciones adicionales si es necesario
        } else {
            // El usuario no existe en la base de datos
            Toast.makeText(this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();
    }
}
