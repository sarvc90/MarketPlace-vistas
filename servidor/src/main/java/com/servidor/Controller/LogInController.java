package com.servidor.Controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class LogInController {

    @FXML
    private StackPane stackPane;

    @FXML
    private VBox loginVBox;

    @FXML
    private ImageView userImage;

    @FXML 
    private ImageView logoImage;

    @FXML
    public void initialize() {
        try {
            // Cargar la imagen del usuario
            Image userImg = new Image(getClass().getResourceAsStream("/com/servidor/images/user.png"));
            userImage.setImage(userImg);
    
            // Cargar la imagen del logo
            Image logoImg = new Image(getClass().getResourceAsStream("/com/servidor/images/logo.png"));
            logoImage.setImage(logoImg);
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir la traza completa de la excepción
        }
    }
}