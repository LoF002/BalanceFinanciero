package com.example.balancefinanciero;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class act_registro extends AppCompatActivity {

    EditText txtNombre, txtApellidos, txtUsuarioRegistro, txtContrasenaRegistro;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_registro);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtUsuarioRegistro = findViewById(R.id.txtUsuarioRegistro);
        txtContrasenaRegistro = findViewById(R.id.txtContrasenaRegistro);

        btnRegistrar = findViewById(R.id.btnRegistrar);

    }//Fin onCreate
}//Fin clase