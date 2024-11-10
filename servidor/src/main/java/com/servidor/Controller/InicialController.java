package com.servidor.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.collections.FXCollections;

import java.io.InputStream;

public class InicialController {

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private ImageView logoImage; // Añadir el ImageView para el logo

    @FXML
    private ComboBox<String> categoryComboBox; // ComboBox para categorías

    @FXML
    private Button settingsButton;

    @FXML
    private Button notificationsButton; // Botón de notificaciones

    @FXML
    private Button chatButton; // Nuevo botón de chat

    @FXML
    private Button userButton; 

    @FXML
    public void initialize() {
        try {
            // Cargar la imagen del logo
            InputStream logoStream = getClass().getResourceAsStream("/com/servidor/images/logo.png");
            if (logoStream == null) {
                System.out.println("No se pudo encontrar la imagen del logo");
            } else {
                Image logoImg = new Image(logoStream);
                logoImage.setImage(logoImg); // Establecer la imagen del logo en el ImageView
            }

            // Cargar la imagen de búsqueda
            InputStream searchStream = getClass().getResourceAsStream("/com/servidor/images/search.png");
            if (searchStream == null) {
                System.out.println("No se pudo encontrar la imagen de búsqueda");
            } else {
                Image searchImg = new Image(searchStream);
                ImageView searchImageView = new ImageView(searchImg);
                searchImageView.setFitWidth(30);
                searchImageView.setFitHeight(30);
                searchImageView.setPreserveRatio(true);
                searchButton.setGraphic(searchImageView); // Establecer la imagen como gráfico del botón
            }

            // Cargar la imagen de ajustes
            InputStream settingsStream = getClass().getResourceAsStream("/com/servidor/images/config.png");
            if (settingsStream == null) {
                System.out.println("No se pudo encontrar la imagen de ajustes");
            } else {
                Image settingsImg = new Image(settingsStream);
                ImageView settingsImageView = new ImageView(settingsImg);
                settingsImageView.setFitWidth(30);
                settingsImageView.setFitHeight(30);
                settingsImageView.setPreserveRatio(true);
                settingsButton.setGraphic(settingsImageView); // Asignar la imagen al botón de ajustes
            }

            // Cargar la imagen de notificaciones
            InputStream notificationsStream = getClass().getResourceAsStream("/com/servidor/images/notifications.png");
            if (notificationsStream == null) {
                System.out.println("No se pudo encontrar la imagen de notificaciones");
            } else {
                Image notificationsImg = new Image(notificationsStream);
                ImageView notificationsImageView = new ImageView(notificationsImg);
                notificationsImageView.setFitWidth(30);
                notificationsImageView.setFitHeight(30);
                notificationsImageView.setPreserveRatio(true);
                notificationsButton.setGraphic(notificationsImageView); // Asignar la imagen al botón de notificaciones
            }

            // Cargar la imagen de chat
            InputStream chatStream = getClass().getResourceAsStream("/com/servidor/images/chat.png");
            if (chatStream == null) {
                System.out.println("No se pudo encontrar la imagen de chat");
            } else {
                Image chatImg = new Image(chatStream);
                ImageView chatImageView = new ImageView(chatImg);
                chatImageView.setFitWidth(30);
                chatImageView.setFitHeight(30);
                chatImageView.setPreserveRatio(true);
                chatButton.setGraphic(chatImageView); // Asignar la imagen al botón de chat
            }

            // Cargar la imagen de usuario
            InputStream userStream = getClass().getResourceAsStream("/com/servidor/images/user(1).png");
            if (userStream == null) {
                System.out.println("No se pudo encontrar la imagen de usuario");
            } else {
                Image userImg = new Image(userStream);
                ImageView userImageView = new ImageView(userImg);
                userImageView.setFitWidth(30);
                userImageView.setFitHeight(30);
                userImageView.setPreserveRatio(true);
                userButton.setGraphic(userImageView ); // Asignar la imagen al botón de usuario
            }

            // Agregar elementos al ComboBox
            categoryComboBox.setItems(FXCollections.observableArrayList(
                "VEHICULOS", 
                "TECNOLOGIA", 
                "HOGAR", 
                "DEPORTES", 
                "BELLEZA", 
                "JUGUETES", 
                "SALUD", 
                "ROPA"
            ));
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir la traza completa de la excepción
        }
    }

    @FXML
    private void handleSearch() {
        // Manejo de la acción del botón de búsqueda
        String searchText = searchField.getText();
        String selectedCategory = categoryComboBox.getValue(); // Obtener la categoría seleccionada
        if (selectedCategory == null) {
            System.out.println("Por favor, selecciona una categoría.");
            return;
        }
        System.out.println("Buscando: " + searchText + " en la categoría: " + selectedCategory);
        // Aquí puedes agregar la lógica para manejar la búsqueda
    }

    @FXML
    private void handleSettings() {
        // Lógica para manejar la acción del botón de ajustes
        System.out.println("Botón de ajustes presionado");
    }

    @FXML
    private void handleNotifications() {
        // Lógica para manejar la acción del botón de notificaciones
        System.out.println("Botón de notificaciones presionado");
    }

    @FXML
    private void handleChat() {
        // Lógica para manejar la acción del botón de chat
        System.out.println("Botón de chat presionado");
    }

    @FXML
    private void handleUser () {
        // Lógica para manejar la acción del botón de usuario
        System.out.println("Botón de usuario presionado");
    }
}