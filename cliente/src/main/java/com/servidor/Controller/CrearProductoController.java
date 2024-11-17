package com.servidor.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.servidor.Model.ProductoDTO;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ComboBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CrearProductoController {

    @FXML
    private Button logoButton;
    @FXML
    private Button rutaButton;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField descripcionField;
    @FXML
    private TextField PrecioField;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private ImageView logoImage;
    @FXML
    private VBox CreacionBox;

    private String rutaSeleccionada;
    private String userId; 

    // Método de inicialización que se llama después de cargar el FXML
    @FXML void initialize(String userId) {
        InputStream logoStream = getClass().getResourceAsStream("/com/servidor/images/logo.png");
        if (logoStream == null) {
            System.out.println("No se pudo encontrar la imagen de búsqueda");
        } else {
            Image logoImg = new Image(logoStream);
            ImageView logoImageView = new ImageView(logoImg);
            logoImageView.setFitWidth(30);
            logoImageView.setFitHeight(30);
            logoImageView.setPreserveRatio(true);
            logoButton.setGraphic(logoImageView); // Establecer la imagen como gráfico del botón
        }
        // Inicializa el ComboBox con las categorías
        categoryComboBox.setItems(FXCollections.observableArrayList(
                "VEHICULOS",
                "TECNOLOGIA",
                "HOGAR",
                "DEPORTES",
                "BELLEZA",
                "JUGUETES",
                "SALUD",
                "ROPA"));
        this.userId = userId;
    }

    // Método que se llama cuando se presiona el botón del logo
    @FXML
    private void handleLogo() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/servidor/Inicial.xml"));
            Parent root = loader.load();
            Stage stage = (Stage) CreacionBox.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Inicio");
            stage.setWidth(800);
            stage.setHeight(600);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para manejar la selección de imagen
    @FXML
    private void handleSelectImageButton() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos JPG (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog((Stage) rutaButton.getScene().getWindow());

        if (selectedFile != null) {
            rutaSeleccionada = selectedFile.getAbsolutePath();
            rutaButton.setText("Ruta seleccionada: " + rutaSeleccionada);
        } else {
            rutaButton.setText("Elija un archivo JPG");
        }
    }

    // Método para manejar la creación de un producto
    @FXML
    public void handlePublicarProducto() {
        String nombre = nombreField.getText();
        String descripcion = descripcionField.getText();
        String precioText = PrecioField.getText();
        String categoria = categoryComboBox.getValue();

        // Validar los campos
        if (nombre.isEmpty() || descripcion.isEmpty() || precioText.isEmpty() || categoria == null) {
            showAlert("Error", "Todos los campos deben ser llenados.");
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(precioText);
        } catch (NumberFormatException e) {
            showAlert("Error", "El precio debe ser un número válido.");
            return;
        }

        // Crear el objeto de producto DTO
        ProductoDTO productoDTO = new ProductoDTO(
                null, // id
                nombre, // nombre
                descripcion, // descripcion
                LocalDateTime.now(), // fechaPublicacion (o un valor válido)
                rutaSeleccionada, // imagenRuta
                (int) precio, // precio (asegúrate de que sea un int)
                0, // meGustas
                new ArrayList<>(), // comentarios
                "desconocido", // estado
                categoria // categoria
        );

        // Enviar el producto al servidor (suponiendo que tienes un método para esto)
        boolean exito = enviarProductoAlServidor(productoDTO);

        // Mostrar alerta según el resultado
        if (exito) {
            showAlert("Éxito", "Producto creado exitosamente.");
        } else {
            showAlert("Error", "No se pudo crear el producto.");
        }
    }

    // Método para mostrar alertas
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

private boolean enviarProductoAlServidor(ProductoDTO productoDTO) {
    try (Socket socket = new Socket("localhost", 12345); // Cambia la dirección y el puerto según tu servidor
         PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

        // Crear una cadena delimitada con los datos del producto
        String productoData = String.join(",",
                productoDTO.getNombre(),
                productoDTO.getDescripcion(),
                productoDTO.getFechaPublicacion().toString(), // Asegúrate de que el formato sea adecuado
                productoDTO.getImagenRuta(),
                String.valueOf(productoDTO.getPrecio()),
                String.valueOf(productoDTO.getMeGustas()),
                productoDTO.getEstado(),
                productoDTO.getCategoria());

        // Enviar el producto al servidor
        out.println(productoData);

        // Esperar la respuesta del servidor
        String respuesta = in.readLine();

        // Procesar la respuesta
        return "Éxito".equalsIgnoreCase(respuesta); // Retorna true si la respuesta es "Éxito"

    } catch (IOException e) {
        e.printStackTrace();
        return false; // Retorna false en caso de error
    }
}
}