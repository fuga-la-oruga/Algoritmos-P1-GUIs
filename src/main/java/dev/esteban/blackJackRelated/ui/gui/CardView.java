package dev.esteban.blackJackRelated.ui.gui;

import dev.esteban.solitarieRelated.deckOfCards.CartaInglesa;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CardView extends StackPane
{
    private static final double WIDTH = 85;
    private static final double HEIGHT = 125;

    private final CartaInglesa card;
    private final Rectangle background;
    private final Label label;

    public CardView(CartaInglesa card)
    {
        this.card = card;

        background = new Rectangle(WIDTH, HEIGHT);
        background.setArcWidth(12);
        background.setArcHeight(12);
        background.setStroke(Color.BLACK);

        label = new Label();
        label.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        setAlignment(Pos.CENTER);
        getChildren().addAll(background, label);

        refresh();
    }

    public void refresh()
    {
        if (!card.isFaceup())
        {
            background.setFill(Color.DARKBLUE);
            label.setText("");
        }
        else
        {
            background.setFill(Color.WHITE);
            label.setText(card.toString());
            label.setTextFill(card.getColor().equals("rojo") ? Color.RED : Color.BLACK);
        }
    }
}