package com.example.balancefinanciero.modelo;


//Clase objeto monedero  para registrar montos en efectivo

import java.text.SimpleDateFormat;
import java.util.Date;

public class Monedero {
    private double monto;
    private String detalle,idUsuario;
    private Date fecha;
    private boolean ingreso;

    public Monedero(double monto, String detalle, String idUsiario, Date fecha, boolean ingreso) {
        this.monto = monto;
        this.detalle = detalle;
        this.idUsuario=idUsiario;
        this.fecha = fecha;
        this.ingreso=ingreso;
    }

    public Monedero(){
        this.monto = 0;
        this.detalle = "";
        this.idUsuario="";
        this.fecha = null;
        this.ingreso=true;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFechaString(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        String dia = dateFormat.format(this.fecha);
        return dia;
    }

    public boolean isIngreso() {
        return ingreso;
    }

    public void setIngreso(boolean ingreso) {
        this.ingreso = ingreso;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
