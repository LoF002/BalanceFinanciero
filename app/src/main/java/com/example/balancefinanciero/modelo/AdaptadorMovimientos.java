package com.example.balancefinanciero.modelo;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balancefinanciero.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdaptadorMovimientos extends RecyclerView.Adapter<AdaptadorMovimientos.ViewHolderMovimientos> {

    ArrayList<Movimiento> listaMomivientos;

    public AdaptadorMovimientos(ArrayList<Movimiento> listaMomivientos) {
        this.listaMomivientos = listaMomivientos;
    }//Fin del AdaptadorMovimientos

    //Asigna como se va a ver la lista(De acuerdo al lyt item_list_mov)
    @NonNull
    @Override
    public ViewHolderMovimientos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_mov,null,false);
        return new ViewHolderMovimientos(vista);
    }//Fin del onCreate

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMovimientos holder, int position) {
        holder.setIsRecyclable(false);

        holder.descripcion.setText(listaMomivientos.get(position).getDetalle());
        if(listaMomivientos.get(position).isIngreso()) {
            holder.monto.setText(String.valueOf(listaMomivientos.get(position).getMonto()));
        }else{
            holder.monto.setText(String.valueOf(listaMomivientos.get(position).getMonto()*-1));
        }
        holder.fecha.setText(listaMomivientos.get(position).getFechaString());

        //Asigna un color al monto dependiendo de su valor
        if(!listaMomivientos.get(position).isIngreso()){
            holder.monto.setTextColor(Color.parseColor("#DB1319"));
        }else{
            holder.monto.setTextColor(Color.parseColor("#218F3E"));
        }//Fin del else

    }//Fin de onBindViewHolder

    //Cuenta los items dentro de la lista
    @Override
    public int getItemCount() {
        return listaMomivientos.size();
    }


    public class ViewHolderMovimientos extends RecyclerView.ViewHolder {
        TextView descripcion, monto, fecha;
        ImageView icono;
        //Convierte la informacion a Strings para insertarlos en la vista
        public ViewHolderMovimientos(@NonNull View itemView) {
            super(itemView);
            descripcion= (TextView) itemView.findViewById(R.id.txt_descripcionId);
            monto= (TextView) itemView.findViewById(R.id.txt_montoId);
            fecha= (TextView) itemView.findViewById(R.id.txt_fechaId);


        }//Fin de ViewHolderMovimientos
    }//Fin de ViewHolderMovimientos
}//Fin de clase
