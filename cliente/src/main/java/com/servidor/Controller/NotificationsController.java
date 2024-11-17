package com.servidor.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;

public class NotificationsController {

    @FXML
    private ListView<String> solicitudesListView; // ListView para solicitudes
    @FXML
    private ListView<String> mensajesListView; // ListView para mensajes

    public void setData(List<String> solicitudes, List<String> mensajes) {
        // Agregar datos a las ListViews
        solicitudesListView.getItems().addAll(solicitudes);
        mensajesListView.getItems().addAll(mensajes);
    }
}