package com.servidor.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.servidor.Model.ProductoDTO;

import java.io.InputStream;

public class InicialController {

    @FXML
    private Button searchButton;

    @FXML
    private TextField searchField;

    @FXML
    private ImageView logoImage;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private Button settingsButton;

    @FXML
    private Button notificationsButton;

    @FXML
    private Button chatButton;

    @FXML
    private Button userButton;

    @FXML
    private VBox productList;

    private static final String HOST = "localhost";
    private static final int PUERTO = 12345;

    private String userId; // Atributo para almacenar el ID del usuario

    @FXML
    public void initialize() {
        // Inicialización de la interfaz de usuario
        loadLogo();
        loadButtonImages();
        loadCategories();
    }

    // Método para inicializar con el ID del usuario
    public void initializeWithUser(String userId) {
        this.userId = userId;
        loadLogo();
        loadButtonImages();
        loadCategories();
        loadUserData();
    }

    private void loadLogo() {
        try {
            InputStream logoStream = getClass().getResourceAsStream("/com/servidor/images/logo.png");
            if (logoStream == null) {
                System.out.println("No se pudo encontrar la imagen del logo");
            } else {
                Image logoImg = new Image(logoStream);
                logoImage.setImage(logoImg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para mostrar productos en el ScrollPane
    private void displayProducts(List<ProductoDTO> productos) {
        productList.getChildren().clear(); // Limpiar la lista antes de agregar nuevos productos

        for (ProductoDTO producto : productos) {
            VBox productBox = new VBox(); // Crear un VBox para cada producto
            productBox.setSpacing(5);

            // Cargar la imagen del producto
            ImageView productImage = new ImageView(new Image(getClass().getResourceAsStream(producto.getImagenRuta())));
            productImage.setFitWidth(100); // Ajustar el tamaño de la imagen
            productImage.setFitHeight(100);
            productImage.setPreserveRatio(true);

            // Crear un botón para abrir la vista del producto
            Button productButton = new Button(producto.getNombre() + " - $" + producto.getPrecio());
            productButton.setGraphic(productImage);
            productButton.setOnAction(e -> openProductView(producto)); // Al hacer clic, abrir la vista del producto

            productBox.getChildren().add(productButton); // Agregar el botón al VBox
            productList.getChildren().add(productBox); // Agregar el VBox a la lista de productos
        }
    }

    private void openProductView(ProductoDTO producto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/servidor/Producto.xml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) searchButton.getScene().getWindow(); // Obtener la ventana actual
            stage.setScene(scene);

            // Aquí puedes pasar el producto al nuevo controlador
            ProductoController productoController = loader.getController();
            productoController.initializeWithProduct(producto); // Método para inicializar con el producto

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("No se pudo cargar la vista del producto.");
        }
    }

    // Método para recargar los productos
    @FXML
    private void handleReloadProducts() {
        loadUserData(); // Recargar datos del usuario
    }

    private void loadButtonImages() {
        loadButtonImage(searchButton, "/com/servidor/images/search.png");
        loadButtonImage(settingsButton, "/com/servidor/images/config.png");
        loadButtonImage(notificationsButton, "/com/servidor/images/notifications.png");
        loadButtonImage(chatButton, "/com/servidor/images/chat.png");
        loadButtonImage(userButton, "/com/servidor/images/user(1).png");
    }

    private void loadCategories() {
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

    private void loadButtonImage(Button button, String imagePath) {
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream == null) {
            System.out.println("No se pudo encontrar la imagen en: " + imagePath);
        } else {
            Image buttonImage = new Image(imageStream);
            ImageView imageView = new ImageView(buttonImage);
            imageView.setFitWidth(30);
            imageView.setFitHeight(30);
            imageView.setPreserveRatio(true);
            button.setGraphic(imageView);
        }
    }

    private void loadUserData() {
        System.out.println("Cargando datos para el usuario con ID: " + userId);

        try (Socket socket = new Socket(HOST, PUERTO);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Enviar el ID del usuario al servidor
            out.println("GET_PRODUCTS " + userId);

            // Leer la respuesta del servidor
            String response;
            List<ProductoDTO> productos = new ArrayList<>();

            while ((response = in.readLine()) != null) {
                // Suponiendo que cada línea es un producto con campos separados por comas
                String[] fields = response.split(","); // Cambia el delimitador según sea necesario
                if (fields.length >= 5) { // Asegúrate de que hay suficientes campos
                    ProductoDTO producto = new ProductoDTO();
                    producto.setId(fields[0]);
                    producto.setNombre(fields[1]);
                    producto.setDescripcion(fields[2]);
                    producto.setPrecio(Integer.parseInt(fields[3]));
                    producto.setImagenRuta(fields[4]);
                    // Agrega otros campos según sea necesario
                    productos.add(producto);
                }
            }

            // Mostrar los productos
            displayProducts(productos);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("No se pudo conectar al servidor para cargar los datos del usuario.");
        }
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText();
        String selectedCategory = categoryComboBox.getValue(); // Obtener la categoría seleccionada

        if (selectedCategory == null) {
            System.out.println("Por favor, selecciona una categoría.");
            return;
        }

        System.out.println("Buscando: " + searchText + " en la categoría: " + selectedCategory);

        // Enviar búsqueda al servidor
        sendSearchToServer(searchText, selectedCategory);
    }

    private void sendSearchToServer(String searchText, String selectedCategory) {
        try (Socket socket = new Socket(HOST, PUERTO);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Enviar el comando de búsqueda al servidor
            out.println("SEARCH " + selectedCategory + " " + searchText);

            // Leer la respuesta del servidor
            String response;
            while ((response = in.readLine()) != null) {
                // Aquí puedes procesar la respuesta y mostrar los resultados
                System.out.println("Resultado recibido: " + response);
                // Agregar lógica para mostrar los resultados en la interfaz
            }

        } catch (IOException e) {
            e.printStackTrace();
            // Manejar excepciones de conexión
            System.out.println("No se pudo conectar al servidor para realizar la búsqueda.");
        }
    }

    @FXML
    private void handleSettings() {
        try {
            // Cargar la vista ControlPanel.xml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/servidor/ControlPanel.xml"));
            Scene scene = new Scene(loader.load());
            
            // Obtener la ventana actual y cambiar la escena
            Stage stage = (Stage) settingsButton.getScene().getWindow(); // Asegúrate de que settingsButton es el botón que llama a este método
            stage.setScene(scene); // Cambia la escena a la vista de ControlPanel
            stage.show(); // Muestra la nueva escena
        } catch (IOException e) {
            e.printStackTrace(); // Imprimir la traza completa de la excepción
            System.out.println("No se pudo cargar la vista de ControlPanel.");
        }
    }

@FXML
private void handleNotifications() {
    try (Socket socket = new Socket(HOST, PUERTO);
         PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

        // Enviar el ID al servidor
        out.println("GET_NOTIFICATIONS " + userId);

        // Leer la respuesta del servidor
        String response = in.readLine(); // Suponiendo que el servidor devuelve las solicitudes y mensajes en un formato específico
        String[] data = response.split(";"); // Suponiendo que las listas están separadas por un punto y coma

        // Parsear las solicitudes y mensajes
        List<String> solicitudes = Arrays.asList(data[0].split(",")); // Solicitudes
        List<String> mensajes = Arrays.asList(data[1].split(",")); // Mensajes

        // Abrir una nueva ventana para mostrar las notificaciones
        openNotificationsWindow(solicitudes, mensajes);

    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Error al conectar con el servidor.");
    }
}

private void openNotificationsWindow(List<String> solicitudes, List<String> mensajes) {
    try {
        // Cargar la vista de notificaciones
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/servidor/NotificationsView.xml"));
        Parent root = loader.load();

        // Obtener el controlador de la nueva vista
        NotificationsController notificationsController = loader.getController();
        notificationsController.setData(solicitudes, mensajes); // Pasar las listas al controlador

        // Crear una nueva escena y ventana
        Stage stage = new Stage();
        stage.setTitle("Notificaciones");
        stage.setScene(new Scene(root));
        stage.show();

    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("No se pudo cargar la vista de notificaciones.");
    }
}

    @FXML
    private void handleChat() {
        System.out.println("Botón de chat presionado");
    }

    @FXML
    private void handleUser() {
        System.out.println("Botón de usuario presionado");
    }
}