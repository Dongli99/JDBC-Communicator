module com.example.jdbcfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.desktop;

    exports com.jdbcfx;
    opens com.jdbcfx to javafx.fxml;
}