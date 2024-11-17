package com.servidor.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    private TextField nameField;

    @FXML
    private PasswordField passwordField;

    private static String HOST = "localhost";
    private static final int PUERTO = 12345;

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
        String username = nameField.getText().trim(); // Usar trim para eliminar espacios en blanco
        String password = passwordField.getText();

        // Validar que los campos no estén vacíos
        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, complete todos los campos.");
            alert.showAndWait();
            return; // Salir del método si hay campos vacíos
        }

        try (Socket socket = new Socket(HOST, PUERTO);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Enviar el comando de inicio de sesión junto con el nombre de usuario y la
            // contraseña
            out.println("LOGIN " + username + " " + password);

            // Leer la respuesta del servidor
            String response = in.readLine();

            // Verificar si la respuesta es nula
            if (response == null || response.isEmpty()) {
                // Mostrar un alert de error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error de Inicio de Sesión");
                alert.setHeaderText(null);
                alert.setContentText("Usuario o contraseña incorrectos.");
                alert.showAndWait();
            } else {
                // Aquí puedes manejar la respuesta válida (por ejemplo, iniciar sesión)
                System.out.println("Inicio de sesión exitoso. ID: " + response);

                // Cargar la nueva vista
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/servidor/Inicial.fxml")); // Cambia
                                                                                                              // la ruta
                                                                                                              // al
                                                                                                              // archivo
                                                                                                              // FXML
                    Scene scene = new Scene(loader.load());
                    Stage stage = (Stage) stackPane.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();

                    // Pasar el ID al nuevo controlador
                    InicialController inicialController = loader.getController();
                    inicialController.initializeWithUser(response); // Método para inicializar con el ID

                } catch (IOException e) {
                    e.printStackTrace();
                    // Manejar excepciones de carga de vista
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error de Carga de Vista");
                    alert.setHeaderText(null);
                    alert.setContentText("No se pudo cargar la vista inicial.");
                    alert.showAndWait();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            // Manejar excepciones de conexión
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Conexión");
            alert.setHeaderText(null);
            alert.setContentText("No se pudo conectar al servidor.");
            alert.showAndWait();
        }
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