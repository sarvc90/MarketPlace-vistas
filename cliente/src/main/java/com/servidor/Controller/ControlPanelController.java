package com.servidor.Controller;

import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;

public class ControlPanelController {

    @FXML
    private ImageView logoImage;

    @FXML
    public void initialize() {
        try {

            // Cargar la imagen del logo
            Image logoImg = new Image(getClass().getResourceAsStream("/com/servidor/images/logo.png"));
            logoImage.setImage(logoImg);
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir la traza completa de la excepción
        }
    }

    @FXML
    private void showDateSelectionDialog() {
        // Crear un nuevo diálogo
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Seleccionar Fechas");

        // Crear DatePicker para la fecha inicial
        DatePicker startDatePicker = new DatePicker();
        Label startDateLabel = new Label("Fecha inicial:");
        
        // Crear DatePicker para la fecha final
        DatePicker endDatePicker = new DatePicker();
        Label endDateLabel = new Label("Fecha final:");

        // Crear un contenedor para los DatePicker
        VBox vbox = new VBox(10, startDateLabel, startDatePicker, endDateLabel, endDatePicker);
        dialog.getDialogPane().setContent(vbox);

        // Añadir botones de confirmación y cancelación
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Mostrar el diálogo y esperar la respuesta
        dialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                // Obtener las fechas seleccionadas
                LocalDate startDate = startDatePicker.getValue();
                LocalDate endDate = endDatePicker.getValue();
                
                // Validar que ambas fechas han sido seleccionadas
                if (startDate != null && endDate != null) {
                    // Aquí puedes implementar la lógica para filtrar con las fechas seleccionadas
                    System.out.println("Fecha inicial: " + startDate);
                    System.out.println("Fecha final: " + endDate);
                } else {
                    // Manejar el caso donde no se seleccionó alguna fecha
                    System.out.println("Por favor, selecciona ambas fechas.");
                }
            }
        });
    }
}