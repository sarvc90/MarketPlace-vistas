package com.servidor.Controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

    @FXML
    private void handleLogInButton() {
        System.out.println("Iniciando Sesion");
    }

    @FXML
    private void handleRegisterButton() {
        try {
            // Cargar la vista LogIn.xml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/servidor/Registro.xml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) stackPane.getScene().getWindow(); 
            stage.setScene(scene); 
            stage.show(); 
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
}