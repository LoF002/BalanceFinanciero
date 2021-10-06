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

    String mensaje="", usuario="", contrasena="";
    int posicion=0;
    boolean login;

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
                    if (listaClientes != null){
                        usuario = txtUsuario.getText().toString();
                        contrasena = txtContraseña.getText().toString();
                        posicion = registroCliente.buscarUsuario(listaClientes, usuario);

                        login = registroCliente.verificarContrasena(listaClientes, posicion, contrasena);

                        if (login!=false){
                            Toast.makeText(getApplicationContext(),"Bienvenido", Toast.LENGTH_LONG).show();
                            //lanzar a la pagina principal
                            Intent intent = new Intent(act_login.this, act_loading_screen.class);
                            startActivity(intent);
                            limpiar();
                        }//Fin if
                        else {
                            Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrecta", Toast.LENGTH_LONG).show();
                            limpiar();
                        }//Fin
                    }//Fin
                    else {
                        Toast.makeText(getApplicationContext(),"Usuario sin registrar", Toast.LENGTH_LONG).show();
                        limpiar();
                    }//

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

    public void limpiar(){
        txtUsuario.setText("");
        txtContraseña.setText("");
    }//Fin limpiar

}//Fin clase