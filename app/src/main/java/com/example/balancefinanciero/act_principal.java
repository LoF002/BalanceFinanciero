package com.example.balancefinanciero;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.balancefinanciero.fragments.CuentasFragment;
import com.example.balancefinanciero.fragments.FechasFragment;
import com.example.balancefinanciero.fragments.MonederoFragment;
import com.example.balancefinanciero.fragments.RegistroFragment;
import com.example.balancefinanciero.fragments.ReportesFragment;
import com.example.balancefinanciero.modelo.Cuenta;
import com.example.balancefinanciero.modelo.Movimiento;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class act_principal extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Creacion de fragmentos para el bottomNavigationMenu
    CuentasFragment cuentasFragment = new CuentasFragment();
    FechasFragment fechasFragment = new FechasFragment();
    RegistroFragment registroFragment = new RegistroFragment();
    ReportesFragment reportesFragment = new ReportesFragment();
    MonederoFragment monederoFragment = new MonederoFragment();

    //Creacion de instancia de bottomNavigationMenu
    BottomNavigationView navigation;

    //firebase
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_principal);

        inicializarDatabase();

        //Asignacion de valor al bottomNavigationMenu
        navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(registroFragment);


        pruebaDatos();

    }//Fin onCreate

    private void inicializarDatabase() {
        FirebaseApp.initializeApp(this);
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
    }//Fin de inicializarDatabase

    public void pruebaDatos(){//Prueba a registrar un movimineto en la BD segun el usuario que haya iniciado sesion
        user=firebaseAuth.getCurrentUser();
        Cuenta nuevaCuenta=new Cuenta(UUID.randomUUID().toString(),user.getUid(),"BCR",true, 500000);
        Movimiento nuevoMov=new Movimiento("10-15-2021","Ropa", 20000, false);
        ArrayList<Movimiento> listaPrueba=new ArrayList<>();
        listaPrueba.add(nuevoMov);
        nuevaCuenta.setListaMovientos(listaPrueba);
        databaseReference.child("Cuenta").child(nuevaCuenta.getIdCuenta()).setValue(nuevaCuenta)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(act_principal.this, "Cuenta agregada a: "+user.getDisplayName(), Toast.LENGTH_SHORT).show();

                        }else{
                            try {
                                throw task.getException();
                            } catch(Exception e) {
                                Toast.makeText(act_principal.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }//Fin del catch
                        }//fin del else
                    }//Fin del onComplete
                }

        );
    }// fin de pruebaDatos

    //Metodo mOnNavigationItemSelectedListener el cual se encarga de cambiar la vista dependiendo de lo seleccionado
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.cuentasFragment:
                    loadFragment(cuentasFragment);
                    return true;
                case R.id.fechasFragment:
                    loadFragment(fechasFragment);
                    return true;
                case R.id.registroFragment:
                    loadFragment(registroFragment);
                    return true;
                case R.id.reportesFragment:
                    loadFragment(reportesFragment);
                    return true;
                case R.id.monederoFragment:
                    loadFragment(monederoFragment);
                    return true;
            }
            return false;
        }
    };

    public void loadFragment(androidx.fragment.app.Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }//Fin metodo loadFragment

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}