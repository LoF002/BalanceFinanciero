package com.example.balancefinanciero.modelo;

public class Movimiento {

    //Clase del objeto movimiento  para registrar todos los movimientos del usuario en la pantalla principal

    private String fecha;
    private String detalle;
    private double monto;
    private boolean ingreso;

    public Movimiento(String fecha, String detalle, double monto, boolean ingreso) {
        this.fecha = fecha;
        this.detalle = detalle;
        this.monto = monto;
        this.ingreso = ingreso;
    }


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
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
}
