package com.servidor.Controller;

import com.servidor.Model.ComentarioDTO;
import com.servidor.Model.ProductoDTO; 
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ProductoController {

    @FXML
    private ImageView imageView;
    @FXML
    private Label nombreLabel;
    @FXML
    private Label idLabel;
    @FXML
    private Label precioLabel;
    @FXML
    private Label fechaLabel;
    @FXML
    private Label categoriaLabel;
    @FXML
    private VBox comentariosVBox;

    private ProductoDTO producto;
    private String vendedorId; // Variable para almacenar el ID del vendedor


    public void initializeWithProduct(ProductoDTO producto, String vendedorId) {
        this.producto = producto;
        this.vendedorId = vendedorId; 
        // Cargar la imagen del producto
        Image productImage = new Image(getClass().getResourceAsStream(producto.getImagenRuta()));
        imageView.setImage(productImage);
        
        // Establecer los valores en las etiquetas
        nombreLabel.setText(producto.getNombre());
        idLabel.setText(producto.getId());
        precioLabel.setText(String.valueOf(producto.getPrecio()));
        fechaLabel.setText(producto.getFechaPublicacion().toString());
        categoriaLabel.setText(producto.getCategoria().toString());

        // Cargar los comentarios
        for (ComentarioDTO comentario : producto.getComentarios()) {
            Label comentarioLabel = new Label(comentario.getTexto());
            comentariosVBox.getChildren().add(comentarioLabel);
        }
    }

    @FXML
    public void initialize() {
        // Este método se puede dejar vacío o usar para inicializaciones generales
    }

    @FXML
    private void handleVolverButton() {
        try {
            // Cargar la vista Inicial.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/servidor/Inicial.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) imageView.getScene().getWindow(); // Obtiene la ventana actual
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Manejar excepciones de carga de vista
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Carga de Vista");
            alert.setHeaderText(null);
            alert.setContentText("No se pudo cargar la vista inicial.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleComprarButton() {
        try {
            // Conectar al servidor y enviar la solicitud de compra
            Socket socket = new Socket("localhost", 12345); // Cambia la dirección y el puerto según tu servidor
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Enviar el ID del producto para la compra
            out.println("comprar:" + producto.getId());

            // Esperar respuesta del servidor
            String response = in.readLine();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Compra");
            alert.setHeaderText(null);
            alert.setContentText(response); // Mostrar mensaje de éxito o error
            alert.showAndWait();

            // Cerrar la conexión
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Conexión");
            alert.setHeaderText(null);
            alert.setContentText("No se pudo conectar al servidor.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleLikeButton() {
        try {
            // Conectar al servidor y enviar la solicitud de like
            Socket socket = new Socket("localhost", 12345); // Cambia la dirección y el puerto según tu servidor
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Enviar el ID del vendedor y el ID del producto para el like
            out.println("like:" + vendedorId + ":" + producto.getId());

            // Esperar respuesta del servidor
            String response = in.readLine();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Like");
            alert.setHeaderText(null);
            alert.setContentText(response); // Mostrar mensaje de éxito o error
            alert.showAndWait();

            // Cerrar la conexión
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Conexión");
            alert.setHeaderText(null);
            alert.setContentText("No se pudo conectar al servidor.");
            alert.showAndWait();
        }
    }

        @FXML
    private void handleVerLikesButton() {
        try {
            // Conectar al servidor y enviar la solicitud para leer likes
            Socket socket = new Socket("localhost", 12345); // Cambia la dirección y el puerto según tu servidor
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Enviar el ID del vendedor para leer los likes
            out.println("leerLikes:" + vendedorId);

            // Esperar respuesta del servidor
            String response = in.readLine();
            // Aquí se asume que el servidor devuelve una lista de vendedores en formato de texto
            List<String> vendedores = parseVendedores(response);

            // Cargar la nueva vista para mostrar la lista de vendedores
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/servidor/Vendedores.fxml"));
            VendedoresController vendedoresController = new VendedoresController(vendedores);
            loader.setController(vendedoresController);
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Lista de Vendedores");
            stage.show();

            // Cerrar la conexión
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de Conexión");
            alert.setHeaderText(null);
            alert.setContentText("No se pudo conectar al servidor.");
            alert.showAndWait();
        }
    }

    private List<String> parseVendedores(String response) {

        return List.of(response.split(",")); 
    }
}