package com.example.balancefinanciero.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.balancefinanciero.R;
import com.example.balancefinanciero.modelo.AdaptadorCuentas;
import com.example.balancefinanciero.modelo.AdaptadorMonedero;
import com.example.balancefinanciero.modelo.Cuenta;
import com.example.balancefinanciero.modelo.Monedero;
import com.example.balancefinanciero.modelo.Movimiento;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MonederoFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    TextView txt_totalMonedero;
    ImageButton btn_RegistrarMonedero;
    RecyclerView recyclerMonedero;

    ArrayList<Monedero> listMonedero;
    DatePicker datePicker;
    TimePicker timePicker;

    boolean esIngreso;
    double balanceActual;

    //variables de firebase
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    public MonederoFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_monedero,container,false);
        inicializarDatabase();

        txt_totalMonedero = (TextView) vista.findViewById(R.id.txt_totalMonedero);

        listMonedero = new ArrayList<>();
        recyclerMonedero = (RecyclerView) vista.findViewById(R.id.recyclerMonedero);
        recyclerMonedero.setLayoutManager(new LinearLayoutManager(getContext()));
        actualizarPantalla();

        btn_RegistrarMonedero = (ImageButton) vista.findViewById(R.id.btn_registrarMonedero);
        btn_RegistrarMonedero.setOnClickListener((View)->{showDialog();});


        return vista;
    }

    private void inicializarDatabase() {
        FirebaseApp.initializeApp(getContext());
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
    }//Fin de inicializarDatabase

    private void showDialog() {
        final Dialog dialog = new Dialog(getContext());//se declara
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.monedero_dialog);

        this.datePicker=dialog.findViewById(R.id.datePicker1);


        this.timePicker=dialog.findViewById(R.id.timePicker1);







        //Initializing the views of the dialog.
        final EditText detalle = dialog.findViewById(R.id.et_detalleCuenta);
        final EditText monto = dialog.findViewById(R.id.et_montoId);//se iguala
        final Button ingreso= dialog.findViewById(R.id.btn_Ingreso);
        final Button  gasto= dialog.findViewById(R.id.btn_gasto);

        Button guardar = dialog.findViewById(R.id.btn_guardarCuenta);
        Button cancelar = dialog.findViewById(R.id.btn_cancelarAC);
        esIngreso=true;


        ingreso.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           esIngreso=true;
                                           gasto.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blanco));
                                           gasto.setTextColor(ContextCompat.getColor(getContext(), R.color.silver));
                                           ingreso.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.yellowgreen));
                                           ingreso.setTextColor(ContextCompat.getColor(getContext(), R.color.blanco));

                                       }
                                   }

        );
        gasto.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         esIngreso=false;
                                         gasto.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red));
                                         gasto.setTextColor(ContextCompat.getColor(getContext(), R.color.blanco));
                                         ingreso.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blanco));
                                         ingreso.setTextColor(ContextCompat.getColor(getContext(), R.color.silver));

                                     }
                                 }

        );




        guardar.setOnClickListener(new View.OnClickListener() {//se le da funcionabilidad al boton
            @Override
            public void onClick(View v) {

                String detalleTransacion=detalle.getText().toString();
                double montoTransaccion;

                try {
                    montoTransaccion = Double.parseDouble(monto.getText().toString());//se obtiene el monton para igualarlo a una variable
                }catch (Exception e){
                    montoTransaccion=0;//se iguala a 0 en caso de que el monto sea null o algo diferente a lo esperado
                }

                //se corrigen valores negativos
                if (montoTransaccion<0){
                    montoTransaccion=montoTransaccion*-1;
                }



                if(!detalleTransacion.isEmpty()&&montoTransaccion != 0) {

                    Calendar calendar = Calendar.getInstance();
                    //int year, int month, int date, int hourOfDay, int minute
                    calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getHour(), timePicker.getMinute());
                    Date diaRegistro = calendar.getTime();


                    Monedero nuevoMonedero = new Monedero(montoTransaccion, detalleTransacion, firebaseAuth.getCurrentUser().getUid(),  diaRegistro, esIngreso);
                    actualizarRegistro(nuevoMonedero);
                    dialog.dismiss();
                }else{
                    Toast.makeText(getContext(),"Faltan datos", Toast.LENGTH_LONG).show();
                }

            }//Fin del onClick
        });//fin del set onclick listener
        cancelar.setOnClickListener(new View.OnClickListener() {//funcion del boton de cancelar
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }//fin del onClick
        });//fin del set onclick listener

        dialog.show();
    }//Fin del dialog

    private void actualizarRegistro(Monedero nuevoMonedero) {
        databaseReference.child("Monedero").child(nuevoMonedero.getFechaString()).setValue(nuevoMonedero);
        actualizarPantalla();
    }


    private void actualizarPantalla(){
        databaseReference.child("Monedero").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listMonedero.clear();
                for(DataSnapshot objSnapshot: snapshot.getChildren()){
                    Monedero monederoActual=objSnapshot.getValue(Monedero.class);
                    if(monederoActual.getIdUsuario().equals( firebaseAuth.getCurrentUser().getUid())) {
                        listMonedero.add(monederoActual);
                    }
                }
                //Toast.makeText(getContext(), "Cuentas encontradas "+contador, Toast.LENGTH_SHORT).show();
                AdaptadorMonedero adaptador = new AdaptadorMonedero(listMonedero);
                recyclerMonedero.setAdapter(adaptador);
                actualizarBalance();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage()+error.getDetails(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Actualiza el monto actual de la cuenta
    private void actualizarBalance(){

        databaseReference.child("Monedero").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                balanceActual=0;
                for(DataSnapshot objSnapshot: snapshot.getChildren()){
                    Monedero monederoActual=objSnapshot.getValue(Monedero.class);
                    if(monederoActual.getIdUsuario().equals( firebaseAuth.getCurrentUser().getUid())) {
                       if(monederoActual.isIngreso()){
                           balanceActual+=monederoActual.getMonto();
                       }else{
                           balanceActual-=monederoActual.getMonto();
                       }
                    }
                }
                txt_totalMonedero.setText(String.valueOf(balanceActual));
                if(balanceActual<0){
                    txt_totalMonedero.setTextColor(Color.parseColor("#DB1319"));
                }else{
                    txt_totalMonedero.setTextColor(Color.parseColor("#218F3E"));
                }//Fin del else
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage()+error.getDetails(), Toast.LENGTH_SHORT).show();
            }
        });



    }//Fin metodo



    public double getTotalDineroMonedero() {
        return this.balanceActual;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}// fin de la clase