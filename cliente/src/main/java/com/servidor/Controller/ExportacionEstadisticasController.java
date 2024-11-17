package com.servidor.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ExportacionEstadisticasController {

    @FXML
    private Button rutaButton;
    @FXML
    private Button exportarButton;

    private String rutaSeleccionada;
    private String userId; // ID del usuario para enviar al servidor

    @FXML
    public void initialize(String userId) {
        rutaButton.setOnAction(event -> elegirRuta());
        exportarButton.setOnAction(event -> exportarArchivo());
        this.userId = userId; 
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
        if (rutaSeleccionada != null && !rutaSeleccionada.isEmpty() && userId != null) {
            try {
                // Conectar al servidor
                Socket socket = new Socket("localhost", 12345); // Cambia la dirección y el puerto según tu servidor
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Enviar el ID del usuario para solicitar la exportación
                out.println("EXPORTAR_ESTADISTICAS " + userId);

                // Leer la respuesta del servidor (suponiendo que el servidor devuelve el contenido del archivo)
                StringBuilder contenidoArchivo = new StringBuilder();
                String linea;
                while ((linea = in.readLine()) != null) {
                    contenidoArchivo.append(linea).append("\n");
                }

                // Guardar el contenido en un archivo de texto en la ruta seleccionada
                File archivo = new File(rutaSeleccionada + File.separator + "estadisticas.txt");
                try (FileWriter fileWriter = new FileWriter(archivo)) {
                    fileWriter.write(contenidoArchivo.toString());
                }

                // Mostrar mensaje de éxito
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exportación");
                alert.setHeaderText(null);
                alert.setContentText("Archivo exportado a: " + archivo.getAbsolutePath());
                alert.showAndWait();

                // Cerrar la conexión
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error al exportar el archivo: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione una ruta y asegúrese de que el ID del usuario esté establecido antes de exportar.");
            alert.showAndWait();
        }
    }
}