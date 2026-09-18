module dev.esteban.solitarieRelated.main
{
    requires javafx.controls;
    requires javafx.fxml;

    opens dev.esteban.solitarieRelated.main to javafx.fxml;

    opens dev.esteban.blackJackRelated.ui.gui to javafx.graphics;
    exports dev.esteban.blackJackRelated.ui.gui;
}