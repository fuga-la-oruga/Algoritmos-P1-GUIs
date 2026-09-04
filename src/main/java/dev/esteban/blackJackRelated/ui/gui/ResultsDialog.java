package dev.esteban.blackJackRelated.ui.gui;

import dev.esteban.blackJackRelated.model.BlackJackGame;
import dev.esteban.blackJackRelated.model.GameResult;
import dev.esteban.blackJackRelated.model.Hand;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ResultsDialog
{
    public static void show(Window owner, BlackJackGame game, Runnable onPlayAgain, Runnable onExit)
    {
        Stage stage = new Stage();
        stage.initOwner(owner);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Resultados");

        Label titleLabel = new Label("Resultados");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Hand dealerHand = game.getDealerHand();
        Label dealerLabel = new Label("Dealer: " + dealerHand.getTotalValue());

        VBox playersBox = new VBox(8);
        for (int i = 0; i < game.getNumberOfPlayers(); i++)
        {
            Hand playerHand = game.getPlayerHand(i);
            GameResult result = game.determineResult(i);
            Label playerLabel = new Label("Jugador " + (i + 1) + ": " + playerHand.getTotalValue() + "    (" + resultText(result) + ")");
            playersBox.getChildren().add(playerLabel);
        }

        Button playAgainButton = new Button("Otra partida");
        playAgainButton.setOnAction(e ->
        {
            stage.close();
            onPlayAgain.run();
        });

        Button exitButton = new Button("Salir");
        exitButton.setOnAction(e ->
        {
            stage.close();
            onExit.run();
        });

        HBox buttonsRow = new HBox(10, playAgainButton, exitButton);
        buttonsRow.setAlignment(Pos.CENTER);

        VBox root = new VBox(15, titleLabel, dealerLabel, playersBox, buttonsRow);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(root, 400, 400));
        stage.showAndWait();
    }

    private static String resultText(GameResult result)
    {
        return switch (result)
        {
            case PLAYER_WINS -> "Gana";
            case DEALER_WINS -> "Pierde";
            case TIE -> "Empate";
        };
    }
}