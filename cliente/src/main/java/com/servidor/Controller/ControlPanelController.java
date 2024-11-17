package com.servidor.Controller;

import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

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
                    handleProductsPublishedInDateRange(startDate, endDate);
                } else {
                    System.out.println("Por favor, selecciona ambas fechas.");
                }
            }
        });
    }

    @FXML
    private void handleMessagesBetweenVendors() {
        // Aquí asumimos que se seleccionan dos vendedores de alguna manera, por ejemplo, mediante un ListView
        String vendedor1 = "vendedor1"; // Reemplazar con el vendedor seleccionado
        String vendedor2 = "vendedor2"; // Reemplazar con el vendedor seleccionado

        try {
            Socket socket = new Socket("localhost", 12345);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Enviar la solicitud de mensajes entre vendedores
            out.println("MENSAJES_ENTRE_VENDEDORES:" + vendedor1 + "," + vendedor2);
            String respuesta = in.readLine(); // Esperar respuesta del servidor

            // Mostrar mensaje de respuesta
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mensajes entre Vendedores");
            alert.setHeaderText(null);
            alert.setContentText(respuesta);
            alert.showAndWait();

            // Cerrar la conexión
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error al obtener los mensajes entre vendedores: " + e.getMessage());
        }
    }

    @FXML
    private void handleProductsPublishedInDateRange(LocalDate startDate, LocalDate endDate) {
        try {
            Socket socket = new Socket("localhost", 12345);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Enviar la solicitud de productos publicados en el rango de fechas
            out.println("PRODUCTOS_PUBLICADOS_RANGO:" + startDate + "," + endDate);
            String respuesta = in.readLine(); // Esperar respuesta del servidor

            // Mostrar los productos en un Alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Productos Publicados");
            alert.setHeaderText(null);
            alert.setContentText("Productos publicados en el rango de fechas:\n" + respuesta);
            alert.showAndWait();

            // Cerrar la conexión
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error al obtener productos publicados: " + e.getMessage());
        }
    }

    @FXML
    private void handleProductsPublishedByVendor() {
        String vendorId = "vendorId"; // Reemplazar con el ID del vendedor seleccionado

        try {
            Socket socket = new Socket("localhost", 12345);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Enviar la solicitud de productos publicados por el vendedor
            out.println("PRODUCTOS_PUBLICADOS_POR_VENDEDOR:" + vendorId);
            String respuesta = in.readLine(); // Esperar respuesta del servidor

            // Mostrar los productos en un Alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Productos Publicados por Vendedor");
            alert.setHeaderText(null);
            alert.setContentText("Productos publicados por el vendedor:\n" + respuesta);
            alert.showAndWait();

            // Cerrar la conexión
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error al obtener productos publicados por el vendedor: " + e.getMessage());
        }
    }

    @FXML
    private void handleContactsByVendor() {
        String vendorId = "vendorId"; // Reemplazar con el ID del vendedor seleccionado

        try {
            Socket socket = new Socket("localhost", 12345);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Enviar la solicitud de contactos por el vendedor
            out.println("CONTACTOS_POR_VENDEDOR:" + vendorId);
            String respuesta = in.readLine(); // Esperar respuesta del servidor

            // Mostrar los contactos en un Alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Contactos por Vendedor");
            alert.setHeaderText(null);
            alert.setContentText("Contactos del vendedor:\n" + respuesta);
            alert.showAndWait();

            // Cerrar la conexión
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error al obtener contactos por el vendedor: " + e.getMessage());
        }
    }

    @FXML
    private void handleTop10PopularProducts() {
        try {
            Socket socket = new Socket("localhost", 12345);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Enviar la solicitud de los 10 productos más populares
            out.println("TOP_10_PRODUCTOS_POPULARES");
            String respuesta = in.readLine(); // Esperar respuesta del servidor

            // Mostrar los productos en un Alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Top 10 Productos Más Populares");
            alert.setHeaderText(null);
            alert.setContentText("Los 10 productos más populares son:\n" + respuesta);
            alert.showAndWait();

            // Cerrar la conexión
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error al obtener los productos más populares: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}