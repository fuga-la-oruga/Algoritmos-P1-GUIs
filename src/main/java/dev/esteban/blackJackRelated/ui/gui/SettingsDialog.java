package dev.esteban.blackJackRelated.ui.gui;

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

import java.util.function.IntConsumer;

public class SettingsDialog
{
    public static void show(Window owner, int currentPlayers, IntConsumer onChangePlayers, Runnable onRestart, Runnable onExit)
    {
        Stage stage = new Stage();
        stage.initOwner(owner);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setTitle("Ajustes");

        Label titleLabel = new Label("Ajustes");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label playersLabel = new Label("Jugadores");
        HBox playersRow = new HBox(10);
        playersRow.setAlignment(Pos.CENTER);

        for (int i = 1; i <= 4; i ++)
        {
            int playerCount = i;
            Button playerButton = new Button(String.valueOf(i));

            if (i == currentPlayers)
            {
                playerButton.setStyle("-fx-base: lightgray;");
            }

            playerButton.setOnAction(e ->
            {
                stage.close();

                if (playerCount != currentPlayers)
                {
                    javafx.application.Platform.runLater(() ->
                            onChangePlayers.accept(playerCount));
                }
            });

            playersRow.getChildren().add(playerButton);
        }

        Button restartButton = new Button("Reiniciar");

        restartButton.setOnAction(e ->
        {
            stage.close();

            javafx.application.Platform.runLater(onRestart);
        });

        Button exitButton = new Button("Salir");

        exitButton.setOnAction(e ->
        {
            stage.close();

            javafx.application.Platform.runLater(onExit);
        });

        VBox root = new VBox(15, titleLabel, playersLabel, playersRow, restartButton, exitButton);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        stage.setScene(new Scene(root, 350, 400));
        stage.showAndWait();
    }
}