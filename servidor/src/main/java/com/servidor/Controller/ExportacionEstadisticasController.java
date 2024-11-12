package com.servidor.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class ExportacionEstadisticasController {

    @FXML
    private Button rutaButton;
    @FXML
    private Button exportarButton;

    private String rutaSeleccionada;

    @FXML
    public void initialize() {
        rutaButton.setOnAction(event -> elegirRuta());
        exportarButton.setOnAction(event -> exportarArchivo());
    }

    private void elegirRuta() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog((Stage) rutaButton.getScene().getWindow());

        if (selectedDirectory != null) {
            rutaSeleccionada = selectedDirectory.getAbsolutePath();
            rutaButton.setText("Ruta seleccionada: " + rutaSeleccionada);
        } else {
            rutaButton.setText("Elija la ruta para generar el archivo");
        }
    }

    private void exportarArchivo() {
        if (rutaSeleccionada != null && !rutaSeleccionada.isEmpty()) {
            // Aquí puedes agregar la lógica para exportar el archivo
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Exportación");
            alert.setHeaderText(null);
            alert.setContentText("Archivo exportado a: " + rutaSeleccionada);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione una ruta antes de exportar.");
            alert.showAndWait();
        }
    }
}