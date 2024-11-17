package com.servidor.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.util.List;

public class VendedoresController {

    @FXML
    private ListView<String> vendedoresListView;

    public VendedoresController(List<String> vendedores) {
        // Inicializar la lista de vendedores
        this.vendedoresListView.getItems().addAll(vendedores);
    }

    @FXML
    private void handleCerrarButton() {
        // Cerrar la ventana de vendedores
        Stage stage = (Stage) vendedoresListView.getScene().getWindow();
        stage.close();
    }
}