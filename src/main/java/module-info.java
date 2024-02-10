module com.example.programming2_c1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires mysql.connector.j;  
    requires java.desktop;
    requires itextpdf;

    opens com.example.programming2_c1 to javafx.fxml;
    exports com.example.programming2_c1;
    exports com.example.programming2_c1.JetClasses;
    opens com.example.programming2_c1.JetClasses to javafx.fxml;
    exports com.example.programming2_c1.ClientControllers;
    opens com.example.programming2_c1.ClientControllers to javafx.fxml;
    exports com.example.programming2_c1.UserClasses;
    opens com.example.programming2_c1.UserClasses to javafx.fxml;
    exports com.example.programming2_c1.AdminControllers;
    opens com.example.programming2_c1.AdminControllers to javafx.fxml;

}