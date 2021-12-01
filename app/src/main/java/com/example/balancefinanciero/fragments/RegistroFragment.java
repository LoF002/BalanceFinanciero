package com.example.balancefinanciero.fragments;

import android.app.Dialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.balancefinanciero.modelo.AdaptadorCuentas;
import com.example.balancefinanciero.modelo.AdaptadorMovimientos;
import com.example.balancefinanciero.modelo.Cuenta;
import com.example.balancefinanciero.modelo.Movimiento;
import com.example.balancefinanciero.R;
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


public class RegistroFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    //declaracion de variables
    Spinner spinnerDias, spinnerMeses;
    TextView ingresosTotales, gastosTotales;

    ArrayList<Movimiento> listaMovimientos;
    ArrayList<Cuenta> listaTempCuentas;

    AdaptadorMovimientos adapter;

    int numeroCuenta;
    boolean esIngreso=true;

    RecyclerView recyclerMomivientos;

    ImageButton btn_registrarMovimiento;

    DatePicker datePicker;
    TimePicker timePicker;


    //variables de firebase
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;




    public RegistroFragment(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public String getIngresosTotales() {
        return ingresosTotales.getText().toString();
    }

    public String getGastosTotales() {
        return gastosTotales.getText().toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_registro, container, false);

        inicializarDatabase();
        listaTempCuentas=new ArrayList<>();
        getCuentas();

        //Spinner dias

        spinnerDias = vista.findViewById(R.id.spinnerDias);
        ArrayAdapter<CharSequence> adapterSpinnerDias = ArrayAdapter.createFromResource(getContext(),
                R.array.dias, android.R.layout.simple_spinner_item);//se asigna el adapter correspondiente  al spinner
        adapterSpinnerDias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//se asigna el layout que se usara
        spinnerDias.setAdapter(adapterSpinnerDias);
        spinnerDias.setOnItemSelectedListener(this);
        //Fin Spinner dias

        ingresosTotales = (TextView) vista.findViewById(R.id.txt_totalIngresos);
        gastosTotales = (TextView) vista.findViewById(R.id.txt_totalGastos);


        //Spinner meses
        spinnerMeses = vista.findViewById(R.id.spinnerMeses);
        ArrayAdapter<CharSequence> adapterSpinnerMeses = ArrayAdapter.createFromResource(getContext(),
                R.array.meses, android.R.layout.simple_spinner_item);//se asigna el adapter correspondiente  al spinner
        adapterSpinnerMeses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//se asigna el layout que se usara
        spinnerMeses.setAdapter(adapterSpinnerMeses);
        spinnerMeses.setOnItemSelectedListener(this);
        //Fin Spinner meses

        //codigo para usar recycler personalizado
        listaMovimientos=new ArrayList<>();//declaracion
        recyclerMomivientos = (RecyclerView) vista.findViewById(R.id.recyclerMovimientos);
        recyclerMomivientos.setLayoutManager(new LinearLayoutManager(getContext()));//se asigna el layout
        //adapter=new AdaptadorMovimientos(listaMovimientos);// se inicializa el adapter a usar
        //recyclerMomivientos.setAdapter(adapter);//se asigna el adapter
        //fin codigo recycler

        btn_registrarMovimiento = vista.findViewById(R.id.btn_registrarMovimiento);
        btn_registrarMovimiento.setOnClickListener((View)->{showDialog();});//se asing el metodo a usar del boton

        filtrarMovimientos();


        return vista;//se devuelve la vista a usar
    }//fin del oncreateView


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
        dialog.setContentView(R.layout.transaction_dialog);

        this.datePicker=dialog.findViewById(R.id.datePicker1);


        this.timePicker=dialog.findViewById(R.id.timePicker1);


        Calendar calendario= Calendar.getInstance();// se declara la variable del calendario


        //this.timePicker.setHour(calendario.getTime().getHours());
        // this.timePicker.setMinute(calendario.getTime().getMinutes());



        //Initializing the views of the dialog.
        final EditText detalle = dialog.findViewById(R.id.et_detalleCuenta);
        final EditText monto = dialog.findViewById(R.id.et_montoId);//se iguala
        final Button ingreso= dialog.findViewById(R.id.btn_Ingreso);
        final Button  gasto= dialog.findViewById(R.id.btn_gasto);
        final Spinner listaCuentas=dialog.findViewById(R.id.spinner_Cuentas);
        Button guardar = dialog.findViewById(R.id.btn_guardarCuenta);
        Button cancelar = dialog.findViewById(R.id.btn_cancelarAC);
        actualizarSpinnerCuenta(listaCuentas);
        listaCuentas.setOnItemSelectedListener(this);
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

        //obtener una instancia del tiempo "ahora"
        //Calendar calendario= Calendar.getInstance();// se declara la variable del calendario
        //TimeZone miZonaHorario=new SimpleTimeZone(-6,"CR");
        //calendario.setTimeZone(miZonaHorario);
        // obtener el formato deseado
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        //convertir
        String dia = dateFormat.format(calendario.getTime());


        guardar.setOnClickListener(new View.OnClickListener() {//se le da funcionabilidad al boton
            @Override
            public void onClick(View v) {
                /*String name = nameEt.getText().toString();
                String age = ageEt.getText().toString();
                Boolean hasAccepted = termsCb.isChecked();
                populateInfoTv(name,age,hasAccepted);*/
                String detalleTransacion=detalle.getText().toString();
                double montoTransaccion;

                try {
                    montoTransaccion = Double.parseDouble(monto.getText().toString());//se obtiene el monton para igualarlo a una variable
                }catch (Exception e){
                    montoTransaccion=0;//se iguala a 0 en caso de que el monto sea null o algo diferente a lo esperado
                }
                boolean valorIngreso=false;

                //se corrigen valores negativos
                if (montoTransaccion<0){
                    montoTransaccion=montoTransaccion*-1;
                }

                /*
                if(ingreso.isChecked()){
                    valorIngreso=true;
                }

                //convierte en negativo el valor si se trata de un gasto
                if(gasto.isChecked()){
                    valorIngreso=false;
                    montoTransaccion=montoTransaccion*-1;
                }

                if((ingreso.isChecked()||gasto.isChecked())&&!detalleTransacion.isEmpty()&&montoTransaccion!=0) {
                    Movimiento nuevoMovimiento = new Movimiento(dia, detalleTransacion, montoTransaccion, valorIngreso);
                    llenarMovientos(nuevoMovimiento);
                    actualizarBalance(montoTransaccion);
                    dialog.dismiss();
                }else{
                    Toast.makeText(getContext(),"Faltan datos", Toast.LENGTH_LONG).show();
                }*/

                valorIngreso=true;
                if( valorIngreso && !detalleTransacion.isEmpty()&&montoTransaccion != 0) {

                    Calendar calendar = Calendar.getInstance();
                    //int year, int month, int date, int hourOfDay, int minute
                    calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getHour(), timePicker.getMinute());
                    Date diaRegistro = calendar.getTime();


                    Movimiento nuevoMovimiento = new Movimiento(diaRegistro, detalleTransacion, montoTransaccion, esIngreso, getCuentaActual().getIdCuenta());
                    actulizarRegistro(nuevoMovimiento);
                    actualizarBalance(montoTransaccion);
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

    private void actualizarPantalla() {
        if(recyclerMomivientos.getAdapter()==null) {
            adapter = new AdaptadorMovimientos(listaMovimientos);//se declara y se inicializa el adapter a usar
            recyclerMomivientos.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    private void getCuentas(){
        databaseReference.child("Cuenta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaTempCuentas.clear();

                for(DataSnapshot objSnapshot: snapshot.getChildren()){
                    Cuenta cuentaActual=objSnapshot.getValue(Cuenta.class);
                    if(cuentaActual.getIdUsuario().equals( firebaseAuth.getCurrentUser().getUid())) {
                        listaTempCuentas.add(cuentaActual);
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private Cuenta getCuentaActual(){
        getCuentas();
         return listaTempCuentas.get(this.numeroCuenta);
    }

    private void actulizarRegistro(Movimiento mov){
        ArrayList<Movimiento> listaM;
        if(getCuentaActual().getListaMovientos()!=null){
            listaM=getCuentaActual().getListaMovientos();
        }else{
            listaM =new ArrayList<>();
        }

        listaM.add(mov);
        databaseReference.child("Cuenta").child(getCuentaActual().getIdCuenta()).child("listaMovientos").setValue(listaM);
        filtrarMovimientos();
    }


    private String[] getNombreCuentas(ArrayList<Cuenta> listaCuentas){

        String[] nombreCuentas= new String[listaCuentas.size()];

        for(int i=0; i<listaCuentas.size();i++){
            nombreCuentas[i]=listaCuentas.get(i).getNombre();
        }

        return nombreCuentas;
    }
    private void actualizarSpinnerCuenta(Spinner spin){

        String[] nombreCuentas=getNombreCuentas(this.listaTempCuentas);
        ArrayAdapter ad = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, nombreCuentas);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spin.setAdapter(ad);
    }

    //Actualiza el monto actual de la cuenta
    private void actualizarBalance(double monto){
        double ingresosActuales = Double.parseDouble(ingresosTotales.getText().toString());
        double gastosActuales = Double.parseDouble(gastosTotales.getText().toString());

        if(monto<0){
            gastosTotales.setText(String.valueOf(gastosActuales+monto));
        }//Fin else
        else{
            ingresosTotales.setText(String.valueOf(ingresosActuales+monto));
        }//Fin else
    }//Fin metodo

    private void filtrarMovimientos(){
        listaMovimientos.clear();

        databaseReference.child("Cuenta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot objSnapshot: snapshot.getChildren()){
                    Cuenta cuentaActual=objSnapshot.getValue(Cuenta.class);
                    if(cuentaActual.getIdUsuario().equals( firebaseAuth.getCurrentUser().getUid())) {
                        if(cuentaActual.getListaMovientos()!=null){
                            for (Movimiento movimiento : cuentaActual.getListaMovientos())
                            {
                                listaMovimientos.add(movimiento);
                            }
                        }//comprueba que la cuenta tenga movimientos
                    }//filtra las cuentas del usuario actual
                    actualizarPantalla();
                }//fin for DataSnapshot


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.numeroCuenta=position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public int getListaMovimientosSize(){//tamaño de la lista de movimientos
        int size = listaMovimientos.size();
        return size;
    }//fin del metodo
}//fin de la clase