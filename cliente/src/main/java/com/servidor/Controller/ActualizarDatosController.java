package com.servidor.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ActualizarDatosController {

    @FXML
    private TextField firstNameField; // Campo para nombres
    @FXML
    private TextField lastNameField; // Campo para apellidos
    @FXML
    private TextField cedulaField; // Campo para cédula
    @FXML
    private PasswordField passwordField; // Campo para contraseña
    @FXML
    private TextField addressField; // Campo para dirección
    @FXML
    private ImageView userImage; // Imagen del usuario

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String userId;

    // Método para inicializar el perfil con el ID del usuario
    public void initializeProfile(String userId) {
        this.userId = userId;
        connectToServer(); // Conectar al servidor
        loadUserData(); // Cargar los datos del usuario
    }

    private void connectToServer() {
        try {
            socket = new Socket("localhost", 12345); 
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al conectar con el servidor.");
        }
    }

    private void loadUserData() {
        try {
            // Enviar solicitud al servidor para obtener los datos del usuario
            out.println("GET_VENDEDOR " + userId); // Se envía el ID del vendedor

            // Leer la respuesta del servidor
            String response = in.readLine(); 
            if (response != null && !response.isEmpty()) {
                // Procesar la respuesta y llenar los campos
                String[] userData = response.split(",");

                firstNameField.setText(userData[0]); // Nombres
                lastNameField.setText(userData[1]); // Apellidos
                cedulaField.setText(userData[2]); // Cédula
                passwordField.setText(userData[3]); // Contraseña
                addressField.setText(userData[4]); // Dirección

                // Cargar la imagen del usuario
                Image userImg = new Image(getClass().getResourceAsStream("/com/servidor/images/user(2).png"));
                userImage.setImage(userImg);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar los datos del usuario.");
        }
    }

    @FXML
    public void handleActualizarButton() {
        // Lógica para actualizar los datos del usuario
        String nombres = firstNameField.getText();
        String apellidos = lastNameField.getText();
        String cedula = cedulaField.getText();
        String contrasena = passwordField.getText();
        String direccion = addressField.getText();

        // Enviar los datos actualizados al servidor
        try {
            out.println("UPDATE_VENDEDOR " + userId + "," + nombres + "," + apellidos + "," + cedula + "," + contrasena + "," + direccion);
            String response = in.readLine(); // Leer la respuesta del servidor
            System.out.println("Respuesta del servidor: " + response);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al enviar los datos actualizados al servidor.");
        }
    }

@FXML
public void handleBackButton() {
    try {
        // Cargar la vista de perfil
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/servidor/views/perfil.fxml"));
        Parent root = loader.load();

        // Obtener el controlador de la vista de perfil
        PerfilController perfilController = loader.getController();
        perfilController.initializeProfile(userId); // Pasar el ID del usuario para inicializar el perfil

        // Crear una nueva escena y mostrarla
        Stage stage = (Stage) firstNameField.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Error al abrir la vista de perfil.");
    }
}

    // Método para cerrar la conexión al salir
    public void closeConnection() {
        try {
            if (out != null) out.close();
            if (in != null) in.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}