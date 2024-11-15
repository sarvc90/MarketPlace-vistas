package com.servidor.Model;

import java.time.LocalDateTime;

public class ComentarioDTO {
    private String id;
    private String autorId; // Solo almacenamos el ID del autor en el DTO
    private LocalDateTime fechaPublicacion;
    private String texto;

    // Constructor
    public ComentarioDTO() {}

    public ComentarioDTO(String id, String autorId, LocalDateTime fechaPublicacion, String texto) {
        this.id = id;
        this.autorId = autorId;
        this.fechaPublicacion = fechaPublicacion;
        this.texto = texto;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAutorId() {
        return autorId;
    }

    public void setAutorId(String autorId) {
        this.autorId = autorId;
    }

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}