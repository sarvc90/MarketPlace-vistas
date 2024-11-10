module com.servidor {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.servidor.Controller to javafx.fxml;
    exports com.servidor.Controller;

    opens com.servidor to javafx.graphics;
    exports com.servidor;
}
