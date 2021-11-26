package com.example.balancefinanciero.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.balancefinanciero.act_principal;
import com.example.balancefinanciero.modelo.AdaptadorCuentas;
import com.example.balancefinanciero.modelo.Cuenta;
import com.example.balancefinanciero.R;
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
import java.util.UUID;

public class CuentasFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    Button btnCamara;

    ImageView fotoPerfil;

    ArrayList<Cuenta> listaCuentas;

    RecyclerView recyclerCuentas;

    ImageButton btn_registrarCuenta;

    double dineroCuentas = 0;

    //firebase
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;


    public CuentasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_cuentas, container, false);
        inicializarDatabase();

        //codigo para camara y foto perfil
        //btnCamara = vista.findViewById(R.id.btn_cambiarFoto);
        //fotoPerfil = vista.findViewById(R.id.imgProfile);


        //codigo para usar recycler personalizado
        listaCuentas=new ArrayList<>();
        recyclerCuentas= (RecyclerView) vista.findViewById(R.id.reclyclerCuentas);
        recyclerCuentas.setLayoutManager(new LinearLayoutManager(getContext()));
        filtrarCuentas();
        //fin codigo recycler

        //Codigo para actuallizar cuentas
        btn_registrarCuenta= (ImageButton) vista.findViewById(R.id.btn_registrarCuenta);
        btn_registrarCuenta.setOnClickListener((View)->{showDialog();});

        return vista;
    }//Fin onCreateView

    private void inicializarDatabase() {
        FirebaseApp.initializeApp(getContext());
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
    }//Fin de inicializarDatabase

/*
    private void CapturarFoto() {
        //verifica los permisos y los solicitan si no estan activados
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);
        }


        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //lanzarCamara();
            }
        });
    }//fin del metodo
/*
 */
    /*
    private void lanzarCamara() {
        Intent intentC=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intentC.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intentC, 100);
        }
    }

    protected void onActivityResult(int requestCode, int codeResult,@Nullable Intent data) {
        super.onActivityResult(requestCode, codeResult, data);

        if (requestCode == 100 && codeResult == RESULT_OK) {
            Toast.makeText(getContext(), "entro", Toast.LENGTH_SHORT).show();
            ;
            Bitmap imgBitMap = (Bitmap) data.getExtras().get("data");


            fotoPerfil.setImageBitmap(imgBitMap);

        }
    }

     */

    private void showDialog() {

        final Dialog dialog = new Dialog(getContext());
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.add_account_dialog);

        //Initializing the views of the dialog.
        final EditText entidad = dialog.findViewById(R.id.et_entidadCuenta);
        final EditText monto = dialog.findViewById(R.id.et_saldoInicialCuenta);
        final EditText detalle = dialog.findViewById(R.id.et_detalleCuenta);
        final Spinner spinnerCuenta= dialog.findViewById(R.id.spinner_moneda);


        Button guardar = dialog.findViewById(R.id.btn_guardarCuenta);
        Button cancelar = dialog.findViewById(R.id.btn_cancelarAC);

        //obtener una instancia del tiempo "ahora"
        Calendar calendario= Calendar.getInstance();
        // obtener el formato deseado
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        //convertir
        String dia = dateFormat.format(calendario.getTime());

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String entidadCuenta = entidad.getText().toString();
                String detalleCuenta = detalle.getText().toString();

                double saldoIinicial;
                try {
                    saldoIinicial = Double.parseDouble(monto.getText().toString());
                    dineroCuentas = saldoIinicial;
                }catch (Exception e){
                    saldoIinicial=0;
                }

                if(!entidadCuenta.isEmpty() && !detalleCuenta.isEmpty()&&saldoIinicial>0) {
                    Cuenta nuevaCuenta = new Cuenta(UUID.randomUUID().toString(), firebaseAuth.getCurrentUser().getUid(), entidadCuenta, detalleCuenta, true, saldoIinicial);
                    addCuentas(nuevaCuenta);
                    dialog.dismiss();
                }else {
                    Toast.makeText(getContext(), "Faltan datos", Toast.LENGTH_LONG).show();
                }
            }//Fin del onClick
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();
    }//Fin metodo showDialog

    public void filtrarCuentas(){

        databaseReference.child("Cuenta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaCuentas.clear();
                int contador=0;
                for(DataSnapshot objSnapshot: snapshot.getChildren()){
                    Cuenta cuentaActual=objSnapshot.getValue(Cuenta.class);
                    if(cuentaActual.getIdUsuario().equals( firebaseAuth.getCurrentUser().getUid())) {
                        listaCuentas.add(cuentaActual);
                        dineroCuentas += cuentaActual.getMonto();
                        contador++;
                    }

                }
                Toast.makeText(getContext(), "Cuentas encontradas "+contador, Toast.LENGTH_SHORT).show();
                AdaptadorCuentas adapter=new AdaptadorCuentas(listaCuentas);
                recyclerCuentas.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addCuentas(Cuenta cuenta){


        databaseReference.child("Cuenta").child(cuenta.getIdCuenta()).setValue(cuenta)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task) {
                                               if (task.isSuccessful()) {
                                                   Toast.makeText(getContext(), "Cuenta agregada a: "+firebaseAuth.getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();

                                               }else{
                                                   try {
                                                       throw task.getException();
                                                   } catch(Exception e) {
                                                       Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                                   }//Fin del catch
                                               }//fin del else
                                           }//Fin del onComplete
                                       }

                );
        filtrarCuentas();

    }//Fin metodo para agregar cuentas en un array

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public int getListaCuentasSize(){//tamaño de la lista de cuentas
        int size;
        if (listaCuentas == null){
            size = 0;
        }else {
            size = listaCuentas.size();
        }
        return size;
    }//fin del metodo

    public double getDineroCuentas() {
        return dineroCuentas;
    }//fin del metodo
}//fin de la clase