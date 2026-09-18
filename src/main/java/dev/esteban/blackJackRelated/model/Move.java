package dev.esteban.blackJackRelated.model;

import dev.esteban.solitarieRelated.deckOfCards.CartaInglesa;

public class Move
{
    public static final String HIT = "HIT";
    public static final String STAND = "STAND";

    private int player_i;
    private String type;
    private CartaInglesa playerCard;
    private int previousPlayerIndex;
    private Stack<CartaInglesa> dealerCards;

    public Move
    (
        int playerIndex,
        String type,
        CartaInglesa playerCard,
        int previousPlayerIndex
    )
    {
        this.player_i = playerIndex;
        this.type = type;
        this.playerCard = playerCard;
        this.previousPlayerIndex = previousPlayerIndex;
        dealerCards = new Stack<>(21);
    }

    public int getPlayer_i()
    {
        return player_i;
    }

    public String getType()
    {
        return type;
    }

    public CartaInglesa getPlayerCard()
    {
        return playerCard;
    }

    public int getPreviousPlayerIndex()
    {
        return previousPlayerIndex;
    }

    public Stack<CartaInglesa> getDealerCards()
    {
        return dealerCards;
    }
}