package com.example.balancefinanciero.modelo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.balancefinanciero.R;

import java.util.List;
import java.util.Map;
import java.util.Timer;

public class AdaptadorFechas extends BaseExpandableListAdapter {

    private Context context;
    private Map<String, List<String>> fechasAPI;
    private List<String> fechasLista;

    public AdaptadorFechas(Context context, List<String> fechasLista, Map<String, List<String>> fechasAPI) {
        this.context = context;
        this.fechasAPI = fechasAPI;
        this.fechasLista = fechasLista;
    }

    @Override
    public int getGroupCount() {
        return fechasAPI.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return fechasAPI.get(fechasLista.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return fechasAPI.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return fechasAPI.get(fechasLista.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String actividad = fechasLista.get(groupPosition);
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fechas_item, null);
        }//Fin if
        TextView item = convertView.findViewById(R.id.fecha);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(actividad);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String model = fechasAPI.get(fechasLista.get(groupPosition)).get(childPosition);
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fecha_items, null);
        }//Fin if
        TextView item = convertView.findViewById(R.id.actividad);
        ImageView delete = convertView.findViewById(R.id.delete);
        item.setText(model);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Realmente desea eliminar?");
                builder.setCancelable(true);
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<String> child = fechasAPI.get(fechasLista.get(groupPosition));
                        child.remove(childPosition);
                        notifyDataSetChanged();
                    }//Fin onClick
                });//Fin setPositiveButton
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }//Fin onClick
                });//Fin setNegativeButton
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }//Fin onClick
        });//Fin setOnClickListener
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}//Fin clase AdaptadorFechas
