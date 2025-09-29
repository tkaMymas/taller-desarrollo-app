package com.example.gestortareas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TareasActivity extends AppCompatActivity {
    DBHelper dbHelper;
    ListView listView;
    ArrayList<String> listaTareas;
    ArrayList<Integer> listaIds;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);

        dbHelper = new DBHelper(this);
        listView = findViewById(R.id.listViewTareas);
        listaTareas = new ArrayList<>();
        listaIds = new ArrayList<>();

        cargarTareas();

        // Click corto = Editar
        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            int tareaId = listaIds.get(position);
            mostrarDialogoEditar(tareaId);
        });

        // Click largo = Eliminar
        listView.setOnItemLongClickListener((adapterView, view, position, id) -> {
            int tareaId = listaIds.get(position);
            dbHelper.eliminarTarea(tareaId);
            Toast.makeText(this, "Tarea eliminada", Toast.LENGTH_SHORT).show();
            cargarTareas();
            return true;
        });

        // Botón agregar tarea
        findViewById(R.id.btnAgregarTarea).setOnClickListener(v -> mostrarDialogoAgregar());
    }

    private void cargarTareas() {
        listaTareas.clear();
        listaIds.clear();
        Cursor cursor = dbHelper.obtenerTareas();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String titulo = cursor.getString(1);
                String descripcion = cursor.getString(2);
                listaIds.add(id);
                listaTareas.add(titulo + " - " + descripcion);
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaTareas);
        listView.setAdapter(adapter);
    }

    private void mostrarDialogoAgregar() {
        View view = getLayoutInflater().inflate(R.layout.item_tarea, null);
        EditText etTitulo = view.findViewById(R.id.etTitulo);
        EditText etDescripcion = view.findViewById(R.id.etDescripcion);

        new AlertDialog.Builder(this)
                .setTitle("Nueva tarea")
                .setView(view)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    dbHelper.insertarTarea(etTitulo.getText().toString(), etDescripcion.getText().toString());
                    cargarTareas();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void mostrarDialogoEditar(int id) {
        View view = getLayoutInflater().inflate(R.layout.item_tarea, null);
        EditText etTitulo = view.findViewById(R.id.etTitulo);
        EditText etDescripcion = view.findViewById(R.id.etDescripcion);

        new AlertDialog.Builder(this)
                .setTitle("Editar tarea")
                .setView(view)
                .setPositiveButton("Actualizar", (dialog, which) -> {
                    dbHelper.actualizarTarea(id, etTitulo.getText().toString(), etDescripcion.getText().toString());
                    cargarTareas();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
