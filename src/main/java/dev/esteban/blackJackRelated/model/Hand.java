package dev.esteban.blackJackRelated.model;

import dev.esteban.solitarieRelated.deckOfCards.CartaInglesa;
import java.util.ArrayList;

public class Hand
{
    private final ArrayList<CartaInglesa> cards = new ArrayList<>();

    public void addCard(CartaInglesa card)
    {
        cards.add(card);
    }

    public ArrayList<CartaInglesa> getCards()
    {
        return cards;
    }

    public int getTotalValue()
    {
        int total = 0;
        int aces = 0;
        for (CartaInglesa card : cards)
        {
            total += getCardValue(card);
            if (card.getValor() == 14)
            {
                aces ++;
            }
        }
        while (total > 21 && aces > 0)
        {
            total -= 10;
            aces --;
        }
        return total;
    }

    public boolean isBusted()
    {
        return getTotalValue() > 21;
    }

    public boolean isBlackJack()
    {
        return cards.size() == 2 && getTotalValue() == 21;
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

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (CartaInglesa card : cards)
        {
            sb.append(card.toString()).append(" ");
        }
        return sb.toString().trim();
    }
}