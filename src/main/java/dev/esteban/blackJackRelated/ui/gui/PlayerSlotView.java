package dev.esteban.blackJackRelated.ui.gui;

import dev.esteban.blackJackRelated.model.Hand;
import dev.esteban.blackJackRelated.model.Stack;
import dev.esteban.solitarieRelated.deckOfCards.CartaInglesa;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class PlayerSlotView extends VBox
{
    private final Label statusLabel = new Label();
    private final Label nameLabel = new Label();
    private final StackPane cardSlot = new StackPane();

    public PlayerSlotView()
    {
        setAlignment(Pos.CENTER);
        setSpacing(5);
        statusLabel.setStyle("-fx-text-fill: gray;");
        getChildren().addAll(statusLabel, nameLabel, cardSlot);
    }

    public void update(Hand hand, int playerNumber, boolean active, String statusText)
    {
        statusLabel.setText(statusText);
        nameLabel.setText("Jugador " + playerNumber + " (x" + hand.getNumberOfCards() + ")");
        if (active)
        {
            nameLabel.setStyle("-fx-font-weight: bold; -fx-font-style: italic;");
        }
        else
        {
            nameLabel.setStyle("-fx-text-fill: gray;");
        }

        cardSlot.getChildren().clear();
        Stack<CartaInglesa> cards = hand.getCards();
        if (!cards.isEmpty())
        {
            CartaInglesa lastCard = cards.peek();
            cardSlot.getChildren().add(new CardView(lastCard));
        }
    }
}