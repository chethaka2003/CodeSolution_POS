module com.codesolution.cs_pos_v1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires animatefx;
    requires org.controlsfx.controls;

    opens com.codesolution.cs_pos_v1 to javafx.fxml;
    exports com.codesolution.cs_pos_v1;

    opens Controllers to javafx.fxml;

    opens Services to javafx.fxml;
    opens DataBase to javafx.fxml;
}