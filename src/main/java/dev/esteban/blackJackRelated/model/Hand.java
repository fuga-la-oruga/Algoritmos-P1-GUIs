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

        if (value == 13)
        {
            return 11;
        }

        if (value >= 10 && value <= 12)
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

    public CartaInglesa removeLastCard()
    {
        if (cards.isEmpty())
        {
            return null;
        }

        Stack<CartaInglesa> auxiliary = new Stack<>(21);
        CartaInglesa removedCard = cards.pop();

        totalValue = 0;
        aces = 0;

        while (!cards.isEmpty())
        {
            CartaInglesa card = cards.pop();
            auxiliary.push(card);
        }

        while (!auxiliary.isEmpty())
        {
            CartaInglesa card = auxiliary.pop();
            cards.push(card);

            int cardValue = getCardValue(card);
            totalValue += cardValue;

            if (cardValue == 11)
            {
                aces ++;
            }

            while (totalValue > 21 && aces > 0)
            {
                totalValue -= 10;
                aces --;
            }
        }

        return removedCard;
    }

    public Stack<CartaInglesa> getLastCards(int amount)
    {
        Stack<CartaInglesa> result = new Stack<>(21);
        Stack<CartaInglesa> auxiliary = new Stack<>(21);

        for (int i = 0; i < amount; i++)
        {
            if (cards.isEmpty())
            {
                break;
            }

            CartaInglesa card = cards.pop();
            auxiliary.push(card);
            result.push(card);
        }

        while (!auxiliary.isEmpty())
        {
            cards.push(auxiliary.pop());
        }

        return result;
    }
}