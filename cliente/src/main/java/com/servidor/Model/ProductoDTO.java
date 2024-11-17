package com.servidor.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductoDTO implements Serializable{
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
        this.id = id; // Asignar ID directamente, asumiendo que puede ser nulo
        this.nombre = nombre != null ? nombre : ""; // Asignar un valor vacío si es nulo
        this.descripcion = descripcion != null ? descripcion : ""; // Asignar un valor vacío si es nulo
        this.fechaPublicacion = fechaPublicacion != null ? fechaPublicacion : LocalDateTime.now(); // Asignar la fecha actual si es nula
        this.imagenRuta = imagenRuta != null ? imagenRuta : ""; // Asignar un valor vacío si es nulo
        this.precio = precio; // Asignar directamente, asumiendo que puede ser 0
        this.meGustas = meGustas; // Asignar directamente, asumiendo que puede ser 0
        this.comentarios = comentarios != null ? comentarios : new ArrayList<>(); // Inicializar como lista vacía si es nula
        this.estado = estado != null ? estado : "desconocido"; // Asignar un estado predeterminado si es nulo
        this.categoria = categoria != null ? categoria : "sin categoría"; // Asignar una categoría predeterminada si es nula
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