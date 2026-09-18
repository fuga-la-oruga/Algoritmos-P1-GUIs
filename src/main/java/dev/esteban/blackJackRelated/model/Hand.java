package dev.esteban.blackJackRelated.model;

import dev.esteban.solitarieRelated.deckOfCards.CartaInglesa;

public class Hand
{
    private final Stack<CartaInglesa> cards = new Stack<>(21);
    private int totalValue = 0;
    private int aces = 0;
    private CartaInglesa firstCard;

    public void addCard(CartaInglesa card)
    {
        cards.push(card);
        int cardValue = getCardValue(card);
        totalValue += cardValue;

        if(cardValue == 11)
        {
            aces ++;
        }

        while (totalValue > 21 && aces > 0)
        {
            totalValue -= 10;
            aces--;
        }

        if(cards.getSize() == 1)
        {
            firstCard = card;
        }
    }

    public Stack<CartaInglesa> getCards()
    {
        return cards;
    }

    public int getTotalValue()
    {
        return totalValue;
    }

    public boolean isBusted()
    {
        return getTotalValue() > 21;
    }

    public boolean isBlackJack()
    {
        return cards.getSize() == 2 && getTotalValue() == 21;
    }

    private int getCardValue(CartaInglesa card)
    {
        int value = card.getValor();

        if (value == 14)
        {
            return 11;
        }

        if (value >= 11 && value <= 13)
        {
            return 10;
        }

        return value;
    }

    public int getNumberOfCards()
    {
        return cards.getSize();
    }

    public CartaInglesa getLastCard()
    {
        return cards.peek();
    }

    public CartaInglesa getFirstCard()
    {
        return firstCard;
    }
}