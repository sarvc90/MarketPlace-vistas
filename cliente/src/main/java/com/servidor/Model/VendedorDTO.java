package com.servidor.Model;

import java.io.Serializable;
import java.util.List;

public class VendedorDTO implements Serializable{
    private String id;
    private String nombre;
    private String apellido;
    private String cedula;
    private String direccion;
    private String contraseña;
    private List<String> publicacionesIds; // Solo almacenamos los IDs de las publicaciones
    private List<String> redDeContactosIds; // Solo almacenamos los IDs de los contactos
    private List<Integer> calificaciones;
    private int contadorCalificaciones;
    private double promedioCalificaciones;

    // Constructor
    public VendedorDTO() {}

    public VendedorDTO(String id, String nombre, String apellido, String cedula, String direccion,String contraseña,
                       List<String> publicacionesIds, List<String> redDeContactosIds,
                       List<Integer> calificaciones, int contadorCalificaciones, double promedioCalificaciones) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.direccion = direccion;
        this.contraseña = contraseña;
        this.publicacionesIds = publicacionesIds;
        this.redDeContactosIds = redDeContactosIds;
        this.calificaciones = calificaciones;
        this.contadorCalificaciones = contadorCalificaciones;
        this.promedioCalificaciones = promedioCalificaciones;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getContraseña(){
        return contraseña;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<String> getPublicacionesIds() {
        return publicacionesIds;
    }

    public void setPublicacionesIds(List<String> publicacionesIds) {
        this.publicacionesIds = publicacionesIds;
    }

    public List<String> getRedDeContactosIds() {
        return redDeContactosIds;
    }

    public void setRedDeContactosIds(List<String> redDeContactosIds) {
        this.redDeContactosIds = redDeContactosIds;
    }

    public List<Integer> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<Integer> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public int getContadorCalificaciones() {
        return contadorCalificaciones;
    }

    public void setContadorCalificaciones(int contadorCalificaciones) {
        this.contadorCalificaciones = contadorCalificaciones;
    }

    public double getPromedioCalificaciones() {
        return promedioCalificaciones;
    }

    public void setPromedioCalificaciones(double promedioCalificaciones) {
        this.promedioCalificaciones = promedioCalificaciones;
    }
}