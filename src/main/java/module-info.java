module com.example.jdbcfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.desktop;

    exports com.jdbcfx;
    opens com.jdbcfx to javafx.fxml;
    exports com.jdbcfx.data;
    opens com.jdbcfx.data to javafx.fxml;
    exports com.jdbcfx.entity;
    opens com.jdbcfx.entity to javafx.fxml;
    exports com.jdbcfx.config;
    opens com.jdbcfx.config to javafx.fxml;
}