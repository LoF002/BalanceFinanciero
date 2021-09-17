package com.example.balancefinanciero.Modelo;

import java.util.ArrayList;

public class RegistroCliente {

    ArrayList <Cliente> listaClientes;

    public RegistroCliente() {
        this.listaClientes = new ArrayList<Cliente>();
    }//Fin constructor

    public int buscarPosicion(String usuario){
        if(usuario!=null){
            for (int i = 0; i < listaClientes.size() ; i++) {
                if (listaClientes.get(i).getUsuario().equalsIgnoreCase(usuario)){
                    return i;
                }//fin for
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

    public String getInformacionUsuario(int posicion){
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

}//Fin clase RegistroCliente
