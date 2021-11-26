package com.example.balancefinanciero.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Movimiento {

    //Clase del objeto movimiento  para registrar todos los movimientos del usuario en la pantalla principal

    private Date fecha;
    private String detalle,idCuenta;
    private double monto;
    private boolean ingreso;

    public Movimiento(Date fecha, String detalle, double monto, boolean ingreso, String  idCuenta) {
        this.fecha = fecha;
        this.detalle = detalle;
        this.monto = monto;
        this.ingreso = ingreso;
        this.idCuenta= idCuenta;
    }


    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public boolean isIngreso() {
        return ingreso;
    }

    public void setIngreso(boolean ingreso) {
        this.ingreso = ingreso;
    }

    public String getFechaString(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        String dia = dateFormat.format(this.fecha);
        return dia;
    }

    public String getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(String idCuenta) {
        this.idCuenta = idCuenta;
    }
}
