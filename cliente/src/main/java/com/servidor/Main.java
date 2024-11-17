package com.servidor;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Cargar el archivo FXML
            Parent root = FXMLLoader.load(getClass().getResource("LogIn.xml"));

            // Crear la escena y establecerla en el escenario
            Scene scene = new Scene(root, 800, 600); // Puedes ajustar el tamaño aquí
            primaryStage.setTitle("Log In");
            primaryStage.setScene(scene);
            primaryStage.setResizable(true); // Para que se pueda redimensionar
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

