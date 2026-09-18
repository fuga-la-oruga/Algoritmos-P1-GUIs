package dev.esteban.blackJackRelated.ui.gui;

import dev.esteban.blackJackRelated.model.Hand;
import dev.esteban.solitarieRelated.deckOfCards.CartaInglesa;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class HandDialog
{
    public static void show(Window owner, String title, Hand hand, boolean dealer)
    {
        Stage stage = new Stage();
        stage.initOwner(owner);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle(title);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        HBox cardsRow = new HBox(15);
        cardsRow.setAlignment(Pos.CENTER);
        boolean allFaceUp = true;

        String totalText = dealer ? "Total: ?" : "Total: " + hand.getTotalValue();
        Label totalLabel = new Label(totalText);

        VBox root = new VBox(20, titleLabel, cardsRow, totalLabel);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(root, 550, 300));
        stage.showAndWait();
    }
}