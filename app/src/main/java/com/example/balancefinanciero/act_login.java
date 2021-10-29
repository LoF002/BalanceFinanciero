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

    //Referencias a la clase cliente y registro cliente (Obsoletas por firebase)
    RegistroCliente registroCliente = new RegistroCliente();
    ArrayList<Cliente> listaClientes;

    //Variables globales
    String usuario="", contrasena="";
    int posicion=0;
    boolean login;

    //Referencias a Firebase
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
                                //Comparara el usuario ingresado con el de la base de datos y si es valido realiza el intent
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){//se comprueba si el usuario es correcto
                                        user=firebaseAuth.getCurrentUser();
                                        Toast.makeText(act_login.this, "Bienvenido: "+user.getDisplayName(), Toast.LENGTH_SHORT).show();//se le indica al usuario que se pudo hacer login
                                     Intent intent = new Intent(act_login.this, act_loading_screen.class);
                                        startActivity(intent);//se redirige a la pantalla de carga para luego ir a la pantalla principal
                                        limpiar();
                                    }else{

                                        try {
                                            throw task.getException();
                                        }//Fin del try
                                        catch(Exception e) {
                                            Toast.makeText(act_login.this, e.getMessage(), Toast.LENGTH_LONG).show();//se imprime el error
                                        }//fin del cath
                                    }//Fin del else

                                }//Fin del override

                            }

                    );
                }//fin del if

            }//Fin onClick
        });//Fin btnIngresar

        //Realiza el cambio de activity al clicar el boton
        btnRegistrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act_login.this, act_registro.class);//se envia a la pantalla de registro
                startActivity(intent);
            }//Fin Onclick
        });//Fin btnRegistrate
    }//fin de btnClickListener

    //Validacion de espacios
    private boolean validar() {
        //se obtienen los datos de los inputs
        String correo=txtCorreo.getText().toString();
        String password=txtContrasenia.getText().toString();
        if(correo.isEmpty()){//se valida si estan vacios o contienen datos
            txtCorreo.setError("Requerido");
            return false;
        }else if(password.isEmpty()){
            txtContrasenia.setError("Requerido");
            return false;
        }else{
            return true;
        }//fin del else
    }//Fin metodo validar

    //Limpia los espacios de texto
    public void limpiar(){
        txtCorreo.setText("");
        txtContrasenia.setText("");
    }//Fin limpiar

    //Inicializa la BD diciendole cual usar y preparando el Autenticator
    private void inicializarDB() {
        FirebaseApp.initializeApp(this);
        firebaseAuth=FirebaseAuth.getInstance();//se instacia la base de datos
        databaseReference= FirebaseDatabase.getInstance().getReference();
    }//Fin metodo inicializarDB

}//Fin clase