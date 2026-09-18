package dev.esteban.blackJackRelated.ui.gui;

import dev.esteban.blackJackRelated.model.Hand;
import dev.esteban.blackJackRelated.model.Stack;
import dev.esteban.solitarieRelated.deckOfCards.CartaInglesa;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.util.List;

public class DealerSlotView extends VBox
{
    private final Label nameLabel = new Label();
    private final StackPane cardSlot = new StackPane();
    private final Button cardsButton = new Button("Cartas");

    public DealerSlotView()
    {
        setAlignment(Pos.CENTER);
        setSpacing(5);

        Region leftSpacer = new Region();
        leftSpacer.prefWidthProperty().bind(cardsButton.widthProperty());

        HBox cardRow = new HBox(10, leftSpacer, cardSlot, cardsButton);
        cardRow.setAlignment(Pos.CENTER);

        getChildren().addAll(cardRow, nameLabel);
    }

    public void update(Hand hand)
    {
        nameLabel.setText("Dealer (x" + hand.getNumberOfCards() + ")");
        cardSlot.getChildren().clear();
        Stack<CartaInglesa> cards = hand.getCards();
        if (!cards.isEmpty())
        {
            CartaInglesa lastCard = cards.peek();
            cardSlot.getChildren().add(new CardView(lastCard));
        }
    }

    public Button getCardsButton()
    {
        return cardsButton;
    }
}