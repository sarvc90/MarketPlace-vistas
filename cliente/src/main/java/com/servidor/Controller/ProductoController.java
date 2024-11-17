package com.servidor.Controller;

import com.servidor.Model.ComentarioDTO;
import com.servidor.Model.ProductoDTO; 
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
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

    private ProductoDTO producto;

    public ProductoController(ProductoDTO producto) {
        this.producto = producto;
    }

    public void initializeWithProduct(ProductoDTO producto) {
        this.producto = producto;

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
}