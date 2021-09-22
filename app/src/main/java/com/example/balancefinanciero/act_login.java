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

public class act_login extends AppCompatActivity {

    EditText txtUsuario, txtContraseña;
    Button btnIngresar, btnRegistrate;

    Cliente cliente;
    RegistroCliente registroCliente = new RegistroCliente();

    ArrayList<Cliente> listaClientes;

    String mensaje="";
    int posicion=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_login);

        txtUsuario = findViewById(R.id.txtUsuarioLogin);
        txtContraseña = findViewById(R.id.txtContrasenaLogin);

        btnIngresar = findViewById(R.id.btnIngresar);
        btnRegistrate = findViewById(R.id.btnRegistrate);

        listaClientes = getIntent().getParcelableArrayListExtra("listaClientes");

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtUsuario.getText().toString().isEmpty() || txtContraseña.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Ingrese todos los datos", Toast.LENGTH_SHORT).show();
                }//Fin if
                else{
                    posicion = registroCliente.buscarPosicion(txtUsuario.getText().toString());
                    mensaje = registroCliente.getInformacionCliente(posicion);
                    Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG).show();
                    //lanzar a la pagina principal
                    Intent intent = new Intent(act_login.this, act_principal.class);
                    startActivity(intent);
                }//Fin else
            }//Fin onClick
        });//Fin btnIngresar

        btnRegistrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act_login.this, act_registro.class);


                startActivity(intent);
            }//Fin Onclick
        });//Fin btnRegistrate

    }//Fin onCreate

}//Fin clase