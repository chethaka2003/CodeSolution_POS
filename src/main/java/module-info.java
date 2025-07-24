module com.codesolution.cs_pos_v1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.codesolution.cs_pos_v1 to javafx.fxml;
    exports com.codesolution.cs_pos_v1;
}