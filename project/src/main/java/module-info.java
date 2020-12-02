module du05.HOV0026 {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    opens boulder_dash to javafx.fxml;
    exports boulder_dash;
}