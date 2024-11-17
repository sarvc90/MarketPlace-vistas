package com.servidor.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class BaseChatController {

    @FXML
    private ListView<String> vendedoresListView; // Lista de vendedores
    @FXML
    private TextArea chatArea; // Área de texto para mostrar el chat
    @FXML
    private TextArea messageField; // Campo de texto para escribir mensajes

    private String vendedorId; // ID del vendedor actual
    private Socket socket; // Socket para la conexión con el servidor
    private PrintWriter out; // Para enviar mensajes al servidor
    private BufferedReader in; // Para recibir mensajes del servidor

    public void initialize(String vendedorId) {
        this.vendedorId = vendedorId;
        connectToServer();
        loadVendedores();
    }

    private void connectToServer() {
        try {
            socket = new Socket("localhost", 12345); // Cambiar según sea necesario
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al conectar con el servidor.");
        }
    }

    private void loadVendedores() {
        // Aquí se debe implementar la lógica para solicitar la lista de vendedores al servidor
        // y agregarla a la ListView. Por ejemplo:
        List<String> vendedores = obtenerVendedoresDesdeServidor();
        vendedoresListView.getItems().addAll(vendedores);
    }
private List<String> obtenerVendedoresDesdeServidor() {
    List<String> vendedores = new ArrayList<>();
    try {
        // Enviar solicitud al servidor para obtener la lista de vendedores
        out.println("GET_VENDEDORES " + vendedorId); // Se envía el ID del vendedor actual

        // Leer la respuesta del servidor
        String response;
        while ((response = in.readLine()) != null) {
            // Suponiendo que el servidor envía la lista de vendedores en líneas separadas
            if (response.equals("END")) {
                break; // Termina la lectura al recibir "END"
            }
            vendedores.add(response); // Agregar el vendedor a la lista
        }
    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Error al obtener la lista de vendedores desde el servidor.");
    }
    return vendedores;
}
    @FXML
    private void handleVendedorSelection(MouseEvent event) {
        String selectedVendedor = vendedoresListView.getSelectionModel().getSelectedItem();
        if (selectedVendedor != null) {
            chatArea.appendText("Iniciando chat con: " + selectedVendedor + "\n");
            // Aquí puedes agregar lógica para cargar mensajes anteriores o inicializar el chat
        }
    }

    @FXML
    private void handleSendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            String selectedVendedor = vendedoresListView.getSelectionModel().getSelectedItem();
            if (selectedVendedor != null) {
                out.println(vendedorId + " a " + selectedVendedor + ": " + message);
                chatArea.appendText("Yo a " + selectedVendedor + ": " + message + "\n");
                messageField.clear();
            } else {
                chatArea.appendText("Por favor, selecciona un vendedor para chatear.\n");
            }
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