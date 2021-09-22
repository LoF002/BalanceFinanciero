package com.example.balancefinanciero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.balancefinanciero.Modelo.Cliente;
import com.example.balancefinanciero.Modelo.RegistroCliente;

import java.util.ArrayList;

public class act_registro extends AppCompatActivity {

    EditText txtNombre, txtApellidos, txtUsuarioRegistro, txtContrasenaRegistro;
    Button btnRegistrar;

    Cliente cliente;
    RegistroCliente registroCliente= new RegistroCliente();
    ArrayList<Cliente> listaClientes;

    String mensaje="";
    int posicion=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_registro);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtUsuarioRegistro = findViewById(R.id.txtUsuarioRegistro);
        txtContrasenaRegistro = findViewById(R.id.txtContrasenaRegistro);

        btnRegistrar = findViewById(R.id.btnRegistrar);

        listaClientes = getIntent().getParcelableArrayListExtra("listaClientes");

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtNombre.getText().toString().isEmpty() || txtApellidos.getText().toString().isEmpty() || txtUsuarioRegistro.getText().toString().isEmpty() || txtContrasenaRegistro.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Ingrese todos los datos", Toast.LENGTH_SHORT).show();
                }//Fin if
                else{
                    cliente = new Cliente(txtUsuarioRegistro.getText().toString(), txtContrasenaRegistro.getText().toString(), txtNombre.getText().toString(), txtApellidos.getText().toString());
                    mensaje = registroCliente.agregarCliente(cliente);//string
                    //posicion = registroCliente.buscarPosicion(txtUsuarioRegistro.getText().toString());

                    Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(), registroCliente.getInformacionCliente(posicion), Toast.LENGTH_LONG).show();

                    limpiar();

                    Intent intent = new Intent(act_registro.this, act_login.class);
                    intent.putParcelableArrayListExtra("listaClientes", registroCliente.devolverLista());
                    startActivity(intent);
                }//Fin else
            }//Fin onClick
        });//Fin btnRegistrar

    }//Fin onCreate

    public void limpiar(){
        txtNombre.setText("");
        txtApellidos.setText("");
        txtUsuarioRegistro.setText("");
        txtContrasenaRegistro.setText("");
    }//Fin limpiar

}//Fin clase