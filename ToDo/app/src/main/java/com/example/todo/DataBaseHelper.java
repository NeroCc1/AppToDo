package com.example.todo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "toDo.db";
    private static final int DATABASE_VERSION = 1;

    // Tabla Usuario
    private static final String CREATE_TABLE_USUARIO = "CREATE TABLE Usuario ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "nombreUsuario TEXT,"
            + "contraseña TEXT)";

    // Tabla Recordatorio
    private static final String CREATE_TABLE_RECORDATORIO = "CREATE TABLE Recordatorio ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "idUsuario INTEGER,"
            + "titulo TEXT,"
            + "descripcion TEXT,"
            + "fecha TEXT,"
            + "FOREIGN KEY(idUsuario) REFERENCES Usuario(id))";

    // Tabla Tarea
    private static final String CREATE_TABLE_TAREA = "CREATE TABLE Tarea ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "idUsuario INTEGER,"
            + "titulo TEXT,"
            + "descripcion TEXT,"
            + "fecha TEXT,"
            + "hora TEXT,"
            + "ee TEXT,"
            + "FOREIGN KEY(idUsuario) REFERENCES Usuario(id))";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USUARIO);
        db.execSQL(CREATE_TABLE_RECORDATORIO);
        db.execSQL(CREATE_TABLE_TAREA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
