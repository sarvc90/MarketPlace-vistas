package com.servidor.Controller;

import com.servidor.models.Producto; // Asegúrate de que la ruta es correcta
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

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

    private Producto producto;

    public ProductoController(Producto producto) {
        this.producto = producto;
    }

    @FXML
    public void initialize() {
        // Cargar la imagen del producto
        Image productImage = new Image(producto.getImagenRuta());
        imageView.setImage(productImage);
        
        // Establecer los valores en las etiquetas
        nombreLabel.setText(producto.getNombre());
        idLabel.setText(producto.getId());
        precioLabel.setText(String.valueOf(product o.getPrecio()));
        fechaLabel.setText(producto.getFechaPublicacion().toString());
        categoriaLabel.setText(producto.getCategoria().toString());

        // Cargar los comentarios
        for (Comentario comentario : producto.getComentarios()) {
            Label comentarioLabel = new Label(comentario.getTexto());
            comentariosVBox.getChildren().add(comentarioLabel);
        }
    }
}