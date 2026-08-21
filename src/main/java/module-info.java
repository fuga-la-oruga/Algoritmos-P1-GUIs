module dev.esteban.main {
    requires javafx.controls;
    requires javafx.fxml;


    opens dev.esteban.main to javafx.fxml;
    exports dev.esteban.main;
}