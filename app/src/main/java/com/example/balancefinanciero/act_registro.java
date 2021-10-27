package com.example.balancefinanciero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.balancefinanciero.Modelo.Cliente;
import com.example.balancefinanciero.Modelo.RegistroCliente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class act_registro extends AppCompatActivity {
    //variables firebase

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    EditText txtNombre, txtApellidos, txtCorreoRegistro, txtContrasenaRegistro;
    ImageButton btnRegistrar;

    Cliente cliente;
    RegistroCliente registroCliente= new RegistroCliente();
    ArrayList<Cliente> listaClientes;

    String mensaje="";
    int posicion=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_registro);
        inicializarDB();
        txtNombre = findViewById(R.id.txtNombre);
        txtApellidos = findViewById(R.id.txtApellidos);
        txtCorreoRegistro = findViewById(R.id.txtUsuarioRegistro);
        txtContrasenaRegistro = findViewById(R.id.txtContrasenaRegistro);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        registro();//llama al onclick listener
        listaClientes = getIntent().getParcelableArrayListExtra("listaClientes");

    }//Fin onCreate
    private void inicializarDB() {
        FirebaseApp.initializeApp(this);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference();
    }


    public void registro(){
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validar()){
                    firebaseAuth.createUserWithEmailAndPassword(txtCorreoRegistro.getText().toString(),txtContrasenaRegistro.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                                @Override

                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        String nombreCompleto=txtNombre.getText().toString()+" "+txtApellidos.getText().toString();
                                        Toast.makeText(act_registro.this, "Agregado correctamente", Toast.LENGTH_SHORT).show();
                                        UserProfileChangeRequest profileUpdates= new UserProfileChangeRequest.Builder().setDisplayName(nombreCompleto) .build();
                                        user=firebaseAuth.getCurrentUser();
                                        user.updateProfile(profileUpdates)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(act_registro.this, "nombre: "+user.getDisplayName(), Toast.LENGTH_SHORT).show();
                                                            limpiar();
                                                            Intent intent = new Intent(act_registro.this, act_login.class);
                                                            startActivity(intent);
                                                        }
                                                    }
                                                });



                                    }else{

                                        try {
                                            throw task.getException();
                                        }
                                        catch(Exception e) {
                                            Toast.makeText(act_registro.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }

                                }

                            }
                    );
                }

            }//Fin onClick
        });//Fin btnRegistrar
    }
    private boolean validar() {
        String nombre=txtNombre.getText().toString();
        String apellidos=txtApellidos.getText().toString();
        String correo=txtCorreoRegistro.getText().toString();
        String password=txtContrasenaRegistro.getText().toString();
        if(nombre.isEmpty()){
            txtNombre.setError("Requerido");
            return false;
        }else if(apellidos.isEmpty()){
            txtApellidos.setError("Requerido");
            return false;
        }else if(correo.isEmpty()){
            txtCorreoRegistro.setError("Requerido");
            return false;
        }else if(password.isEmpty()){
            txtContrasenaRegistro.setError("Requerido");
            return false;
        }else{
            return true;
        }

    }


    public void limpiar(){
        txtNombre.setText("");
        txtApellidos.setText("");
        txtCorreoRegistro.setText("");
        txtContrasenaRegistro.setText("");
    }//Fin limpiar

}//Fin clase