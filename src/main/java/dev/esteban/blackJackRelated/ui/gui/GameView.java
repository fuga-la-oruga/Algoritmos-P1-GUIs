package dev.esteban.blackJackRelated.ui.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class GameView extends BorderPane
{
    private final Button settingsButton = new Button("Ajustes");
    private final DealerSlotView dealerSlot = new DealerSlotView();
    private final HBox playersRow = new HBox(40);
    private final ArrayList<PlayerSlotView> playerSlots = new ArrayList<>();

    private final Button handButton = new Button("Cartas");
    private final Button hitButton = new Button("Pedir");
    private final Button standButton = new Button("Plantarse");
    private final Button undoButton = new Button("Deshacer");

    public GameView()
    {
        setPadding(new Insets(20));

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(settingsButton, spacer, undoButton);

        setTop(topBar);

        for (int i = 0; i < 4; i++)
        {
            playerSlots.add(new PlayerSlotView());
        }
        playersRow.setAlignment(Pos.CENTER);
        playersRow.getChildren().addAll(playerSlots);

        VBox centerBox = new VBox(60, dealerSlot, playersRow);
        centerBox.setAlignment(Pos.CENTER);
        setCenter(centerBox);

        HBox actionsRow = new HBox(10, handButton, hitButton, standButton);
        actionsRow.setAlignment(Pos.CENTER);
        actionsRow.setPadding(new Insets(20, 0, 0, 0));
        setBottom(actionsRow);
    }

    public void showPlayers(int numberOfPlayers)
    {
        playersRow.getChildren().clear();
        for (int i = 0; i < numberOfPlayers; i++)
        {
            playersRow.getChildren().add(playerSlots.get(i));
        }
    }

    public DealerSlotView getDealerSlot()
    {
        return dealerSlot;
    }

    public PlayerSlotView getPlayerSlot(int index)
    {
        return playerSlots.get(index);
    }

    public Button getSettingsButton()
    {
        return settingsButton;
    }

    public Button getHandButton()
    {
        return handButton;
    }

    public Button getHitButton()
    {
        return hitButton;
    }

    public Button getStandButton()
    {
        return standButton;
    }

    public Button getUndoButton()
    {
        return undoButton;
    }
}