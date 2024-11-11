module com.mycompany.finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.logging;
    opens com.mycompany.finalproject to javafx.fxml;
    exports com.mycompany.finalproject;
}
