package com.example.balancefinanciero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class act_registro extends AppCompatActivity {

    //variables firebase
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    //Variables de interfaz grafica
    EditText txtNombre, txtApellidos, txtCorreoRegistro, txtContrasenaRegistro;
    ImageButton btnRegistrar;

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

        //Llama al onclick listener encargado de registrar un cliente
        registro();

    }//Fin onCreate

    //Inicia e instancia la base de datos
    private void inicializarDB() {
        FirebaseApp.initializeApp(this);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference();
    }//Fin metodo


    //Registra el usuario
    public void registro(){
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Llama al metodo validar
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
                                                        }//Fin if
                                                    }//Fin onComplete
                                                });//Fin listener


                                    //Levanta la aplicación en caso de error
                                    }else{

                                        try {
                                            throw task.getException();
                                        }
                                        catch(Exception e) {
                                            Toast.makeText(act_registro.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }//Fin del else

                                }//Fin de onComplete

                            }//Fin de addOnComplete Listener
                    );//Cierre de addOnCompleteListener

                }//Fin de if para validar

            }//Fin onClick
        });//Fin btnRegistrar

    }//Fin de metodo Registro

    //Verifica la información
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

    }//Fin de metodo validar

    //Limpia los espacios de texto
    public void limpiar(){
        txtNombre.setText("");
        txtApellidos.setText("");
        txtCorreoRegistro.setText("");
        txtContrasenaRegistro.setText("");
    }//Fin limpiar

}//Fin clase