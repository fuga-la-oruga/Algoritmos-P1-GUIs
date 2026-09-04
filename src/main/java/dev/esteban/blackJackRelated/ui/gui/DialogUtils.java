package dev.esteban.blackJackRelated.ui.gui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

import java.util.Optional;

public class DialogUtils
{
    public static void showNotice(Window owner, String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(owner);
        alert.setTitle("Aviso");
        alert.setHeaderText("Aviso");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static boolean showConfirmation(Window owner, String message)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(owner);
        alert.setTitle("Confirmar");
        alert.setHeaderText("Confirmación");
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && result.get() == ButtonType.OK;
    }
}