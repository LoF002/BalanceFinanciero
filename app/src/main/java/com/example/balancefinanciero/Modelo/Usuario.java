package com.example.balancefinanciero.Modelo;

public class Usuario {

    private String usuario, contrasena, nombres, apellidos;

    public Usuario(String usuario, String contrasena, String nombres, String apellidos) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public Usuario() {
        this.usuario = "";
        this.contrasena = "";
        this.nombres = "";
        this.apellidos = "";
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "usuario='" + usuario + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                '}';
    }

}//Fin clase Usuario
