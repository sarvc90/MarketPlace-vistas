package com.servidor.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class AdministradorController {

    @FXML
    private ImageView userImage; 
    @FXML
    private ListView<String> productosListView; // Lista para productos
    @FXML
    private ListView<String> vendedoresListView; // Lista para vendedores

    private String userId; // ID del usuario

    @FXML
    public void initialize() {
        // Cargar la imagen del usuario
        Image userImg = new Image(getClass().getResourceAsStream("/com/servidor/images/user(2).png"));
        userImage.setImage(userImg);
        
        // Cargar listas de productos y vendedores
        cargarProductos();
        cargarVendedores();
    }

    private void cargarProductos() {
        try {
            // Conectar al servidor para obtener la lista de productos
            Socket socket = new Socket("localhost", 12345); // Cambia la dirección y el puerto según tu servidor
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Solicitar la lista de productos
            out.println("OBTENER_PRODUCTOS");
            String linea;
            while ((linea = in.readLine()) != null) {
                productosListView.getItems().add(linea); // Agregar cada producto a la lista
            }

            // Cerrar la conexión
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error al cargar la lista de productos: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private void cargarVendedores() {
        try {
            // Conectar al servidor para obtener la lista de vendedores
            Socket socket = new Socket("localhost", 12345); // Cambia la dirección y el puerto según tu servidor
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Solicitar la lista de vendedores
            out.println("OBTENER_VENDEDORES");
            String linea;
            while ((linea = in.readLine()) != null) {
                vendedoresListView.getItems().add(linea); // Agregar cada vendedor a la lista
            }

            // Cerrar la conexión
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error al cargar la lista de vendedores: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void handlePanelControl() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/servidor/views/ControlPanel.fxml"));
            Parent root = loader.load();

            // Crear una nueva escena y mostrarla
            Stage stage = new Stage();
            stage.setTitle("Panel de Control");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al abrir la vista del panel de control.");
        }
    }

    @FXML
    public void handleExportarEstadisticas() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/servidor/views/ExportacionEstadisticas.fxml"));
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

    @FXML
    public void handleEliminarProducto() {
        String productoSeleccionado = productosListView.getSelectionModel().getSelectedItem();
        if (productoSeleccionado != null) {
            try {
                // Conectar al servidor para eliminar el producto
                Socket socket = new Socket("localhost", 12345);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Enviar solicitud de eliminación
                out.println("ELIMINAR_PRODUCTO:" + productoSeleccionado);
                String respuesta = in.readLine(); // Esperar respuesta del servidor

                // Mostrar mensaje de resultado
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Resultado de Eliminación");
                alert.setHeaderText(null);
                alert.setContentText(respuesta);
                alert.showAndWait();

                // Recargar la lista de productos
                productosListView.getItems().clear();
                cargarProductos();

                // Cerrar la conexión
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error al eliminar el producto: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione un producto para eliminar.");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleEliminarVendedor() {
        String vendedorSeleccionado = vendedoresListView.getSelectionModel().getSelectedItem();
        if (vendedorSeleccionado != null) {
            try {
                // Conectar al servidor para eliminar el vendedor
                Socket socket = new Socket("localhost", 12345);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Enviar solicitud de eliminación
                out.println("ELIMINAR_VENDEDOR:" + vendedorSeleccionado);
                String respuesta = in.readLine(); // Esperar respuesta del servidor

                // Mostrar mensaje de resultado
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Resultado de Eliminación");
                alert.setHeaderText(null);
                alert.setContentText(respuesta);
                alert.showAndWait();

                // Recargar la lista de vendedores
                vendedoresListView.getItems().clear();
                cargarVendedores();

                // Cerrar la conexión
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error al eliminar el vendedor: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione un vendedor para eliminar.");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleCrearVendedor() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/servidor/views/Registro.fxml"));
            Parent root = loader.load();

            // Crear una nueva escena y mostrarla
            Stage stage = new Stage();
            stage.setTitle("Registro de Vendedor");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al abrir la vista de registro de vendedor.");
        }
    }
}