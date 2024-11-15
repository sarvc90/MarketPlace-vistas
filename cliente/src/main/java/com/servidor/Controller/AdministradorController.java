package com.servidor.Controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AdministradorController {

    @FXML
    private ImageView userImage; // Asegúrate de que este ID coincida con el de tu FXML

    @FXML
    public void initialize() {
        // Cargar la imagen desde el directorio de recursos
        Image userImg = new Image(getClass().getResourceAsStream("/com/servidor/images/user(2).png"));
        userImage.setImage(userImg);
    }

    @FXML
    public void handleActualizarDatos() {
        // Lógica para actualizar datos
    }

    @FXML
    public void handlePanelControl() {
        // Lógica para actualizar datos
    }

    @FXML
    public void handleExportarEstadisticas() {
        // Lógica para actualizar datos
    }

    @FXML
    public void handleEliminarProducto() {
        // Lógica para actualizar datos
    }


    @FXML
    public void handleCrearVendedor() {
        // Lógica para actualizar datos
    }

    @FXML
    public void handleEliminarVendedor() {
        // Lógica para actualizar datos
    }

    @FXML
    public void handleCrearProducto() {
        // Lógica para actualizar datos
    }

    
}