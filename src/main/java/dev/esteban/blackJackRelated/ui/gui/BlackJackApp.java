package dev.esteban.blackJackRelated.ui.gui;

import dev.esteban.blackJackRelated.model.BlackJackGame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BlackJackApp extends Application
{
    @Override
    public void start(Stage stage)
    {
        BlackJackGame game = new BlackJackGame();
        GameView view = new GameView();
        new BlackJackController(game, view, stage);

        Scene scene = new Scene(view, 900, 600);
        stage.setTitle("Black Jack");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}