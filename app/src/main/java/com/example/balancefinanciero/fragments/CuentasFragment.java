package com.example.balancefinanciero.fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
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
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.balancefinanciero.modelo.AdaptadorCuentas;
import com.example.balancefinanciero.modelo.Cuenta;
import com.example.balancefinanciero.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CuentasFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    Button btnCamara;

    ImageView fotoPerfil;

    ArrayList<Cuenta> listaCuentas;

    RecyclerView recyclerCuentas;

    ImageButton btn_registrarCuenta;

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

        //codigo para camara y foto perfil
        btnCamara = vista.findViewById(R.id.btn_cambiarFoto);
        fotoPerfil = vista.findViewById(R.id.imgProfile);
        CapturarFoto();

        //codigo para usar recycler personalizado
        listaCuentas=new ArrayList<>();
        recyclerCuentas= (RecyclerView) vista.findViewById(R.id.recyclerCuentas);
        recyclerCuentas.setLayoutManager(new LinearLayoutManager(getContext()));
        AdaptadorCuentas adapter=new AdaptadorCuentas(listaCuentas);
        recyclerCuentas.setAdapter(adapter);
        //fin codigo recycler

        //Codigo para actuallizar cuentas
        btn_registrarCuenta= (ImageButton) vista.findViewById(R.id.btn_registrarCuenta);
        btn_registrarCuenta.setOnClickListener((View)->{showDialog();});

        return vista;
    }//Fin onCreateView

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
    }

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
        //final EditText entidad = dialog.findViewById(R.id.et_entidadAC);
        //final EditText monto = dialog.findViewById(R.id.et_saldoInicialAC);
        //final RadioButton debito= dialog.findViewById(R.id.btn_rDebito);
       // final RadioButton  credito= dialog.findViewById(R.id.btn_rCredito);
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
                /*String name = nameEt.getText().toString();
                String age = ageEt.getText().toString();
                Boolean hasAccepted = termsCb.isChecked();
                populateInfoTv(name,age,hasAccepted);*/
               // String entidadCuenta = entidad.getText().toString();
                double saldoCuenta = 0;
                String tipoCuenta = "";

               /* try {
                    saldoCuenta = Double.parseDouble(monto.getText().toString());
                }catch (Exception e){
                    saldoCuenta=0;
                }
                //se corrigen valores negativos
                if (saldoCuenta<0){
                    saldoCuenta=saldoCuenta*-1;
                }
                if(debito.isChecked()){
                    tipoCuenta="Débito";
                }
                if(credito.isChecked()){
                    tipoCuenta="Crédito";
                }

                if((debito.isChecked()||credito.isChecked())&&!entidadCuenta.isEmpty()&&saldoCuenta!=0) {
                    Cuenta nuevaCuenta = new Cuenta("", "", entidadCuenta, tipoCuenta, true, saldoCuenta);
                    llenarCuentas(nuevaCuenta);
                    dialog.dismiss();
                }else{
                    Toast.makeText(getContext(),"Faltan datos", Toast.LENGTH_LONG).show();
                }*/
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

    private void llenarCuentas(Cuenta cuenta){
        listaCuentas.add(cuenta);
    }//Fin metodo para agrgar cuentas en un array

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}