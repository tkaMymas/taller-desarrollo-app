package com.example.gestortareas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText etUsuario, etPassword;
    Button btnLogin, btnRegistrar;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        dbHelper = new DBHelper(this);

        // Botón de login
        btnLogin.setOnClickListener(v -> {
            String user = etUsuario.getText().toString();
            String pass = etPassword.getText().toString();

            if (dbHelper.validarUsuario(user, pass)) {
                Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón de registrar
        btnRegistrar.setOnClickListener(v -> {
            String user = etUsuario.getText().toString();
            String pass = etPassword.getText().toString();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                if (dbHelper.insertarUsuario(user, pass)) {
                    Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}