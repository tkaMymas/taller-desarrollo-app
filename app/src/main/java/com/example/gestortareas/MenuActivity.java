package com.example.gestortareas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btnTareas = findViewById(R.id.btnTareas);
        btnTareas.setOnClickListener(v -> {
            startActivity(new Intent(MenuActivity.this, TareasActivity.class));
        });
    }
}
