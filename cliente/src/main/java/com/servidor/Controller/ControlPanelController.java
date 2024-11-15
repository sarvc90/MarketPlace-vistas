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
        loadLogoImage();
    }

    private void loadLogoImage() {
        try {
            Image logoImg = new Image(getClass().getResourceAsStream("/com/servidor/images/logo.png"));
            logoImage.setImage(logoImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showDateSelectionDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Seleccionar Fechas");

        DatePicker startDatePicker = new DatePicker();
        Label startDateLabel = new Label("Fecha inicial:");

        DatePicker endDatePicker = new DatePicker();
        Label endDateLabel = new Label("Fecha final:");

        VBox vbox = new VBox(10, startDateLabel, startDatePicker, endDateLabel, endDatePicker);
        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                LocalDate startDate = startDatePicker.getValue();
                LocalDate endDate = endDatePicker.getValue();

                if (startDate != null && endDate != null) {
                    System.out.println("Fecha inicial: " + startDate);
                    System.out.println("Fecha final: " + endDate);
                } else {
                    System.out.println("Por favor, selecciona ambas fechas.");
                }
            }
        });
    }

    @FXML
    private void handleMessagesBetweenVendors() {
        System.out.println("Acción: Mensajes enviados entre 2 vendedores");
        // Implementa la lógica necesaria aquí
    }

    @FXML
    private void handleProductsPublishedInDateRange() {
        System.out.println("Acción: Cantidad de productos publicados dentro de un rango de fecha");
        // Implementa la lógica necesaria aquí
    }

    @FXML
    private void handleProductsPublishedByVendor() {
        System.out.println("Acción: Cantidad de productos publicados por vendedor");
        // Implementa la lógica necesaria aquí
    }

    @FXML
    private void handleContactsByVendor() {
        System.out.println("Acción: Cantidad de contactos por vendedor");
        // Implementa la lógica necesaria aquí
    }

    @FXML
    private void handleTop10PopularProducts() {
        System.out.println("Acción: Top 10 de productos más populares");
        // Implementa la lógica necesaria aquí
    }
}