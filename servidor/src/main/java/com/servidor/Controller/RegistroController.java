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

public class RegistroController {

    @FXML
    private StackPane stackPane;

    @FXML
    private VBox registroVBox;

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
    private void handleBackButton() {
        try {
            // Cargar la vista LogIn.xml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/servidor/LogIn.xml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) stackPane.getScene().getWindow(); // Obtiene la ventana actual
            stage.setScene(scene); // Cambia la escena a la vista de inicio de sesión
            stage.show(); // Muestra la nueva escena
        } catch (IOException e) {
            e.printStackTrace(); // Imprimir la traza completa de la excepción
        }
    }

    @FXML
    private void handleRegisterButton(){
        System.out.println("Intentando registro");
    }
}