package com.servidor.Controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatController {

    @FXML
    private TextField messageTextField; // Campo de texto para escribir el mensaje

    @FXML
    private Button sendMessageButton; // Botón para enviar el mensaje

    @FXML
    private VBox chatContainer; // Contenedor para los mensajes

    @FXML
    private ImageView logoImage; // Imagen del logo

    @FXML
    private ComboBox<String> userComboBox; // ComboBox para seleccionar usuario

    private Socket socket; // Socket para la conexión con el servidor
    private PrintWriter out; // Para enviar mensajes al servidor
    private BufferedReader in; // Para recibir mensajes del servidor

    private String serverAddress = "localhost"; // Dirección del servidor
    private int serverPort = 12345; // Puerto del servidor
    private String username; // Nombre del vendedor

    @FXML
    public void initialize() {
        // Cargar el logo
        loadLogoImage();

        // Inicializar el ComboBox con los usuarios
        userComboBox.getItems().addAll("Vendedor1", "Vendedor2"); // Agregar más vendedores según sea necesario
        userComboBox.setValue("Vendedor1"); // Valor por defecto

        // Iniciar la conexión al servidor
        connectToServer();

        // Configurar el nombre del vendedor
        username = "Vendedor1"; // Cambia esto según el vendedor
    }

    private void connectToServer() {
        try {
            socket = new Socket(serverAddress, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    
            // Crear un nuevo hilo para recibir mensajes del servidor
            new Thread(() -> {
                try {
                    String receivedMessage; // Declarar la variable aquí
                    while ((receivedMessage = in.readLine()) != null) {
                        // Actualizar el área de chat en el hilo de la interfaz de usuario
                        final String messageToDisplay = receivedMessage; 
                        Platform.runLater(() -> displayMessage(messageToDisplay));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
    
        } catch (IOException e) {
            e.printStackTrace();
            displayMessage("No se pudo conectar al servidor.");
        }
    }

    @FXML
    public void sendMessage() {
        String message = messageTextField.getText(); // Obtener el mensaje del campo de texto
        if (!message.isEmpty()) {
            String selectedUser  = userComboBox.getValue(); // Obtener el usuario seleccionado
            // Enviar el mensaje al servidor con el nombre del vendedor y el usuario seleccionado
            out.println(username + " a " + selectedUser  + ": " + message);
            displayMessage("Yo a " + selectedUser  + ": " + message); // Mostrar el mensaje en el área de texto
            messageTextField.clear(); // Limpiar el campo de texto
        }
    }

    private void displayMessage(String message) {
        // Crear un HBox para el mensaje
        HBox messageBox = new HBox();
        Text messageText = new Text(message);
        messageText.setStyle("-fx-background-color: lightgray; -fx-padding: 10; -fx-background-radius: 10;");

        // Determinar la alineación del mensaje
        if (message.startsWith(username)) {
            messageText.setStyle(messageText.getStyle() + "-fx-alignment: center-right;"); // Mensaje del usuario a la derecha
            messageBox.getChildren().add(messageText);
        } else {
            messageText.setStyle(messageText.getStyle() + "-fx-alignment: center-left;"); // Mensaje de otro usuario a la izquierda
            messageBox.getChildren().add(messageText);
        }

        chatContainer.getChildren().add(messageBox); // Agregar el mensaje al contenedor
    }

    private void loadLogoImage() {
        try {
            // Cargar la imagen del logo desde el recurso
            Image logoImg = new Image(getClass().getResourceAsStream("/com/servidor/images/logo.png"));
            logoImage.setImage(logoImg);
        } catch (Exception e) {
            e.printStackTrace();
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