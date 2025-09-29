package com.example.gestortareas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "gestorTareas.db";
    private static final int DATABASE_VERSION = 1;

    // Tabla usuario
    private static final String TABLE_USERS = "usuarios";
    private static final String COL_USER_ID = "id";
    private static final String COL_USER_NAME = "usuario";
    private static final String COL_USER_PASS = "password";

    // Tabla tarea
    private static final String TABLE_TASKS = "tareas";
    private static final String COL_TASK_ID = "id";
    private static final String COL_TASK_TITLE = "titulo";
    private static final String COL_TASK_DESC = "descripcion";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabla usuarios
        db.execSQL("CREATE TABLE " + TABLE_USERS +
                " (" + COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER_NAME + " TEXT, " +
                COL_USER_PASS + " TEXT)");

        // Usuario inicial
        db.execSQL("INSERT INTO " + TABLE_USERS + " (" + COL_USER_NAME + ", " + COL_USER_PASS + ") VALUES ('admin', '1234')");

        // Tabla tareas
        db.execSQL("CREATE TABLE " + TABLE_TASKS +
                " (" + COL_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TASK_TITLE + " TEXT, " +
                COL_TASK_DESC + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    // Usuarios
    public boolean validarUsuario(String usuario, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS +
                        " WHERE " + COL_USER_NAME + "=? AND " + COL_USER_PASS + "=?",
                new String[]{usuario, password});
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    public boolean insertarUsuario(String usuario, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USER_NAME, usuario);
        values.put(COL_USER_PASS, password);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    // Tareas
    public boolean insertarTarea(String titulo, String descripcion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TASK_TITLE, titulo);
        values.put(COL_TASK_DESC, descripcion);
        long result = db.insert(TABLE_TASKS, null, values);
        return result != -1;
    }

    public Cursor obtenerTareas() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TASKS, null);
    }

    public boolean actualizarTarea(int id, String titulo, String descripcion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TASK_TITLE, titulo);
        values.put(COL_TASK_DESC, descripcion);
        int result = db.update(TABLE_TASKS, values, COL_TASK_ID + "=?",
                new String[]{String.valueOf(id)});
        return result > 0;
    }

    public boolean eliminarTarea(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_TASKS, COL_TASK_ID + "=?",
                new String[]{String.valueOf(id)});
        return result > 0;
    }
}
