package com.example.balancefinanciero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

public class act_login extends AppCompatActivity {

    EditText txtCorreo, txtContrasenia;
    ImageButton btnIngresar, btnRegistrate;
    Button btn_biometricLogin;
    TextView txt_msgHuellaDisponibilidad;

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

        btn_biometricLogin = findViewById(R.id.btn_biometricLogin);
        txt_msgHuellaDisponibilidad = findViewById(R.id.txt_msgHuellaDisponibilidad);


        btnClickListener();

        //creacion del BiometricManager
        BiometricManager biometricManager = BiometricManager.from(this);

        //Switch para realizar acciones dependiendo de los resultados de la biometria
        switch (biometricManager.canAuthenticate()){
            case BiometricManager.BIOMETRIC_SUCCESS:
                txt_msgHuellaDisponibilidad.setText("Inicio mediante huella digital/biometria disponible");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE: //Esta es para los dispositivos sin lector de huella
                txt_msgHuellaDisponibilidad.setText("No disponible en este dispositivo");
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                txt_msgHuellaDisponibilidad.setText("No disponible en este momento");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                txt_msgHuellaDisponibilidad.setText("No existe registro de una huella");
                break;
        }//Fin switch

        Executor executor = ContextCompat.getMainExecutor(getApplicationContext());
        BiometricPrompt biometricPrompt = new BiometricPrompt(act_login.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override //se llama cuando hay un error con la autenticacion de credenciales
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }//Fin onAuthenticationError

            @Override //se llama cuando se ingresa correctamente
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(act_login.this, "Bienvenido: "+user.getDisplayName(), Toast.LENGTH_SHORT).show();
            }//Fin onAuthenticationSucceeded

            @Override //se llama cuando falla la autenticacion
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }//Fin onAuthenticationFailed
        });//Fin AuthenticationCallback

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Ingreso")
                .setDescription("")
                .setNegativeButtonText("Cancel")
                .build();

        btn_biometricLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }//Fin onClick
        });//Fin setOnClickListener


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
                                    if (task.isSuccessful()){
                                        user=firebaseAuth.getCurrentUser();
                                        Toast.makeText(act_login.this, "Bienvenido: "+user.getDisplayName(), Toast.LENGTH_SHORT).show();
                                     Intent intent = new Intent(act_login.this, act_loading_screen.class);
                                        startActivity(intent);
                                        limpiar();
                                    }else{

                                        try {
                                            throw task.getException();
                                        }//Fin del try
                                        catch(Exception e) {
                                            Toast.makeText(act_login.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(act_login.this, act_registro.class);
                startActivity(intent);
            }//Fin Onclick
        });//Fin btnRegistrate
    }//fin de btnClickListener

    //Validacion de espacios
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
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
    }//Fin metodo inicializarDB

}//Fin clase