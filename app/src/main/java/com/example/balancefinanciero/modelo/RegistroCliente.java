package com.example.balancefinanciero.modelo;

import java.util.ArrayList;

public class RegistroCliente {

    ArrayList <Cliente> listaClientes;

    public RegistroCliente() {
        listaClientes = new ArrayList<Cliente>();
    }//Fin constructor

    public int buscarPosicion(String usuario){
        if(usuario!=null){
            for (int i = 0; i < listaClientes.size() ; i++) {
                if (listaClientes.get(i).getUsuario().equalsIgnoreCase(usuario)){
                    return i;
                }//fin if
            }//fin for
        }//fin if
        return -1;
    }//Fin buscarPosicion

    public String agregarCliente(Cliente cliente){
        if(cliente!=null){
            if(buscarPosicion(cliente.getUsuario())==-1){
                listaClientes.add(cliente);
                return "Agregado correctamente";
            }//fin if
            else{
                return "Ya existe";
            }//fin else
        }//Fin if
        return "Error al agregar";
    }//Fin agregarCliente

    public String getInformacionCliente(int posicion){
        if(posicion!=-1){
            return listaClientes.get(posicion).toString();
        }//Fin if
        else{
            return "No existe";
        }//Fin else
    }//Fin metodo getInformacion

    public Cliente devolverUsuario(int posicion){
        if(posicion!=-1){
            return listaClientes.get(posicion);
        }//Fin if
        else{
            return null;
        }//Fin else
    }//fin metodo devolver

    public ArrayList<Cliente> devolverLista(){
        return listaClientes;
    }//Fin metodo devolverlista

    public int buscarUsuario(ArrayList<Cliente> listaClientes, String usuario){
        if (usuario!=null){
            for (int i = 0; i<listaClientes.size(); i++){
                if (listaClientes.get(i).getUsuario().equalsIgnoreCase(usuario)){
                    return i;
                }//Fin if
            }//Fin for
        }//Fin if
        return -1;
    }//Fin metodo

    public boolean verificarContrasena(ArrayList<Cliente> listaClientes, int posicion, String contrasena){
        if(posicion!=-1 && contrasena!=null){
            listaClientes.get(posicion).getContrasena().equalsIgnoreCase(contrasena);
            return true;
        }//Fin if
        return false;
    }//Fin metodo

}//Fin clase RegistroCliente
