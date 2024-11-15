package com.servidor.Model;

import java.time.LocalDateTime;
import java.util.List;

public class ProductoDTO {
    private String id;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaPublicacion;
    private String imagenRuta;
    private int precio;
    private int meGustas;
    private List<ComentarioDTO> comentarios; // Suponiendo que también tienes un DTO para Comentario
    private String estado; // Podrías usar un enum o String dependiendo de tu necesidad
    private String categoria; // Podrías usar un enum o String dependiendo de tu necesidad

    // Constructor
    public ProductoDTO() {}

    public ProductoDTO(String id, String nombre, String descripcion, LocalDateTime fechaPublicacion,
                       String imagenRuta, int precio, int meGustas, List<ComentarioDTO> comentarios,
                       String estado, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaPublicacion = fechaPublicacion;
        this.imagenRuta = imagenRuta;
        this.precio = precio;
        this.meGustas = meGustas;
        this.comentarios = comentarios;
        this.estado = estado;
        this.categoria = categoria;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getImagenRuta() {
        return imagenRuta;
    }

    public void setImagenRuta(String imagenRuta) {
        this.imagenRuta = imagenRuta;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getMeGustas() {
        return meGustas;
    }

    public void setMeGustas(int meGustas) {
        this.meGustas = meGustas;
    }

    public List<ComentarioDTO> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioDTO> comentarios) {
        this.comentarios = comentarios;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}