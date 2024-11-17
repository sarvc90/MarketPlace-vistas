package com.servidor.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import com.servidor.Model.VendedorDTO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
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
    private TextField nombreField; // Campo para el nombre
    @FXML
    private TextField apellidoField; // Campo para el apellido
    @FXML
    private TextField cedulaField; // Campo para la cédula
    @FXML
    private TextField direccionField; // Campo para la dirección

    private static final String HOST = "localhost";
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
    private void handleRegisterButton() {
        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        String cedula = cedulaField.getText();
        String direccion = direccionField.getText();

        // Crear un objeto VendedorDTO
        VendedorDTO vendedor = new VendedorDTO();
        vendedor.setNombre(nombre);
        vendedor.setApellido(apellido);
        vendedor.setCedula(cedula);
        vendedor.setDireccion(direccion);
        // Puedes agregar valores por defecto para las listas si es necesario
        vendedor.setPublicacionesIds(new ArrayList<>());
        vendedor.setRedDeContactosIds(new ArrayList<>());
        vendedor.setCalificaciones(new ArrayList<>());
        vendedor.setContadorCalificaciones(0);
        vendedor.setPromedioCalificaciones(0.0);

        // Enviar el vendedor al servidor
        try (Socket socket = new Socket(HOST, PUERTO);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Enviar el comando de registro
            out.println("REGISTER " + vendedor.getNombre() + "," + vendedor.getApellido() + "," +
                    vendedor.getCedula() + "," + vendedor.getDireccion());

            // Leer la respuesta del servidor
            String response = in.readLine();
            showAlert(response); // Mostrar alerta con la respuesta del servidor

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error al conectar con el servidor.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registro");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}