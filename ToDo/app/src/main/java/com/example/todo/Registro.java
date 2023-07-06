package com.example.todo;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().hide();



    }

    public void login(View view) {
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        EditText eUsuario = findViewById(R.id.nombreUsr);
        EditText eContra = findViewById(R.id.password);
        EditText eContraCon = findViewById(R.id.confirmacion);


        String usuario = eUsuario.getText().toString();
        String passw = eContra.getText().toString();
        String confirmacion = eContraCon.getText().toString();

        ContentValues values = new ContentValues();
        values.put("nombreUsuario", usuario);
        values.put("contraseña", passw);

        if( passw.equals(confirmacion)){
            long newRowId = db.insert("Usuario", null, values);
            if (newRowId != -1 ) {
                Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                // Realiza acciones adicionales si es necesario
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

}