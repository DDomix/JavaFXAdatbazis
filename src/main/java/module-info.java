module hu.petrik.javafxadatbazis {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports hu.petrik.javafxadatbazis;
    opens hu.petrik.javafxadatbazis to javafx.fxml;
}