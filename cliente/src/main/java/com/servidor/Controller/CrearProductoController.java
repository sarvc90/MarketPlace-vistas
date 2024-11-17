package com.servidor.Controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
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

    // Método de inicialización que se llama después de cargar el FXML
    @FXML
    private void initialize() {

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

            // Establecer el tamaño de la ventana
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

        // Establecer el filtro para archivos JPG
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos JPG (*.jpg)", "*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);

        // Mostrar el diálogo para seleccionar un archivo
        File selectedFile = fileChooser.showOpenDialog((Stage) rutaButton.getScene().getWindow());

        if (selectedFile != null) {
            // Obtener la ruta absoluta del archivo seleccionado
            rutaSeleccionada = selectedFile.getAbsolutePath();
            rutaButton.setText("Ruta seleccionada: " + rutaSeleccionada);
        } else {
            rutaButton.setText("Elija un archivo JPG");
        }
    }

}