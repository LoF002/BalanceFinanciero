package com.example.balancefinanciero.Modelo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.balancefinanciero.R;

import java.util.ArrayList;

public class AdaptadorMovimientos extends RecyclerView.Adapter<AdaptadorMovimientos.ViewHolderMovimientos> {
    ArrayList<Movimiento> listaMomivientos;

    public AdaptadorMovimientos(ArrayList<Movimiento> listaMomivientos) {
        this.listaMomivientos = listaMomivientos;
    }

    @NonNull
    @Override
    public ViewHolderMovimientos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_mov,null,false);
        return new ViewHolderMovimientos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMovimientos holder, int position) {
        holder.descripcion.setText(listaMomivientos.get(position).getDetalle());
        holder.monto.setText(String.valueOf(listaMomivientos.get(position).getMonto()));
        holder.fecha.setText(listaMomivientos.get(position).getFecha());

    }

    @Override
    public int getItemCount() {
        return listaMomivientos.size();
    }

    public class ViewHolderMovimientos extends RecyclerView.ViewHolder {
        TextView descripcion, monto, fecha;
        ImageView icono;
        public ViewHolderMovimientos(@NonNull View itemView) {
            super(itemView);
            descripcion= (TextView) itemView.findViewById(R.id.txt_descripcionId);
            monto= (TextView) itemView.findViewById(R.id.txt_montoId);
            fecha= (TextView) itemView.findViewById(R.id.txt_fechaId);
            icono= (ImageView) itemView.findViewById(R.id.img_imagenId);

        }
    }
}
