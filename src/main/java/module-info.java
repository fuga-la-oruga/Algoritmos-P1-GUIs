module dev.esteban.solitarieRelated.main {
    requires javafx.controls;
    requires javafx.fxml;


    opens dev.esteban.solitarieRelated.main to javafx.fxml;
    exports dev.esteban.solitarieRelated.main;
}