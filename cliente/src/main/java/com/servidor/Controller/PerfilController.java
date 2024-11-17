package com.servidor.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PerfilController {

    @FXML
    private ImageView userImage; // Asegúrate de que este ID coincida con el de tu FXML
    @FXML
    private Text nombresText;
    @FXML
    private Text apellidosText;
    @FXML
    private Text cedulaText;
    @FXML
    private Text contrasenaText;
    @FXML
    private Text direccionText;
    @FXML
    private Text reputacionText;

    private String userId;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    // Método para inicializar el perfil con el ID del usuario
    public void initializeProfile(String userId) {
        this.userId = userId;
        connectToServer(); // Conectar al servidor
        loadUserProfile(userId); // Cargar el perfil del usuario
    }

    private void connectToServer() {
        try {
            socket = new Socket("localhost", 12345); // Cambia la dirección y el puerto según sea necesario
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al conectar con el servidor.");
        }
    }

    private void loadUserProfile(String userId) {
        try {
            // Enviar solicitud al servidor para obtener el vendedor DTO
            out.println("GET_VENDEDOR " + userId); // Se envía el ID del vendedor

            // Leer la respuesta del servidor
            String response = in.readLine(); // Suponiendo que el servidor devuelve una línea con el DTO
            if (response != null && !response.isEmpty()) {
                // Aquí debes procesar la respuesta y llenar los campos
                String[] userData = response.split(","); // Suponiendo que los datos están separados por comas

                // Asignar los valores a los campos de texto
                nombresText.setText(userData[0]); // Nombres
                apellidosText.setText(userData[1]); // Apellidos
                cedulaText.setText(userData[2]); // Cédula
                contrasenaText.setText(userData[3]); // Contraseña
                direccionText.setText(userData[4]); // Dirección
                reputacionText.setText(userData[5]); // Reputación

                // Cargar la imagen del usuario
                Image userImg = new Image(getClass().getResourceAsStream("/com/servidor/images/user(2).png"));
                userImage.setImage(userImg);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar el perfil del usuario.");
        }
    }

    @FXML
    public void handleActualizarDatos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/servidor/views/actualizarDatos.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la nueva vista
            ActualizarDatosController actualizarDatosController = loader.getController();
            actualizarDatosController.initializeProfile(userId); // Pasar el ID del usuario

            // Crear una nueva escena y mostrarla
            Stage stage = (Stage) userImage.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al abrir la vista de actualización de datos.");
        }
    }

    @FXML
    public void handlePublicarProducto() {
        // Cargar la vista de publicar producto
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/servidor/publicarProducto.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar la vista de publicar producto.");
            return; // Salir del método si hay un error
        }

        // Obtener el controlador de la nueva vista de publicar producto
        CrearProductoController publicarProductoController = loader.getController();
        publicarProductoController.initialize(userId); // Inicializar con el ID del vendedor

        // Crear una nueva escena y ventana
        Stage stage = new Stage();
        stage.setTitle("Publicar Producto");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void handleExportarEstadisticas() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/servidor/views/ExportacionEstadisticas.fxml"));
            Parent root = loader.load();
    
            // Obtener el controlador de la nueva vista
            ExportacionEstadisticasController exportacionEstadisticasController = loader.getController();
            exportacionEstadisticasController.initialize(userId); // Pasar el ID del usuario
    
            // Crear una nueva escena y mostrarla
            Stage stage = new Stage();
            stage.setTitle("Exportación de Estadísticas");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al abrir la vista de exportación de estadísticas.");
        }
    }

    // Método para cerrar la conexión al salir
    public void closeConnection() {
        try {
            if (out != null)
                out.close();
            if (in != null)
                in.close();
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}