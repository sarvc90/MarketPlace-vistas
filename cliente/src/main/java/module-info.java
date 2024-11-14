module com.servidor {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.servidor.Controller to javafx.fxml;
    exports com.servidor.Controller;

    opens com.servidor to javafx.graphics;
    exports com.servidor;
}
