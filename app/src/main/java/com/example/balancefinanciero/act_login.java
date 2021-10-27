package com.example.balancefinanciero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class act_login extends AppCompatActivity {

    EditText txtCorreo, txtContrasenia;
    ImageButton btnIngresar, btnRegistrate;

    //firebase
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_login);
        inicializarDB();
        txtCorreo = findViewById(R.id.txtUsuarioLogin);
        txtContrasenia = findViewById(R.id.txtContrasenaLogin);

        btnIngresar = findViewById(R.id.btnIngresar);
        btnRegistrate = findViewById(R.id.btnRegistrate);

        btnClickListener();




    }//Fin onCreate

    private void btnClickListener() {
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validar()){
                    firebaseAuth.signInWithEmailAndPassword(txtCorreo.getText().toString(),txtContrasenia.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override

                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        user=firebaseAuth.getCurrentUser();
                                        Toast.makeText(act_login.this, "Bienvenido: "+user.getDisplayName(), Toast.LENGTH_SHORT).show();
                                     Intent intent = new Intent(act_login.this, act_loading_screen.class);
                                        startActivity(intent);
                                        limpiar();
                                    }else{

                                        try {
                                            throw task.getException();
                                        }
                                        catch(Exception e) {
                                            Toast.makeText(act_login.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }

                                }

                            }

                    );
                }

            }//Fin onClick
        });//Fin btnIngresar

        btnRegistrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act_login.this, act_registro.class);
                startActivity(intent);
            }//Fin Onclick
        });//Fin btnRegistrate
    }//Fin metodo

    private boolean validar() {
        String correo=txtCorreo.getText().toString();
        String password=txtContrasenia.getText().toString();
        if(correo.isEmpty()){
            txtCorreo.setError("Requerido");
            return false;
        }else if(password.isEmpty()){
            txtContrasenia.setError("Requerido");
            return false;
        }else{
            return true;
        }
    }//Fin metodo validar

    public void limpiar(){
        txtCorreo.setText("");
        txtContrasenia.setText("");
    }//Fin limpiar

    private void inicializarDB() {
        FirebaseApp.initializeApp(this);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
    }//Fin metodo inicializarDB

}//Fin clase