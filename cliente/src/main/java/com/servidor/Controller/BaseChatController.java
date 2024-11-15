package com.servidor.Controller;

import java.io.IOException;
import java.io.InputStream;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BaseChatController {

    @FXML
    private ImageView logoImage;

    @FXML
    public void initialize() {
        try {
            // Cargar la imagen del logo
            InputStream logoStream = getClass().getResourceAsStream("/com/servidor/images/logo.png");
            if (logoStream == null) {
                System.out.println("No se pudo encontrar la imagen del logo");
            } else {
                Image logoImg = new Image(logoStream);
                logoImage.setImage(logoImg); // Establecer la imagen del logo en el ImageView
            }
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir la traza completa de la excepción
        }
    }


}