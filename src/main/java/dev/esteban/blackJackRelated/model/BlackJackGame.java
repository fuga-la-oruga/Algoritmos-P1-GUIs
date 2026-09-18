package dev.esteban.blackJackRelated.model;

import dev.esteban.solitarieRelated.deckOfCards.CartaInglesa;
import dev.esteban.solitarieRelated.deckOfCards.Mazo;

import java.util.ArrayList;
import java.util.List;

public class BlackJackGame
{
    private Mazo deck;
    private List<Hand> playerHands;
    private List<Boolean> playerStood;
    private Hand dealerHand;
    private boolean dealerStood;
    private int numberOfPlayers;

    public void startGame(int numberOfPlayers)
    {
        this.numberOfPlayers = numberOfPlayers;
        deck = new Mazo();
        playerHands = new ArrayList<>();
        playerStood = new ArrayList<>();
        dealerHand = new Hand();
        dealerStood = false;

        for (int i = 0; i < numberOfPlayers; i ++)
        {
            playerHands.add(new Hand());
            playerStood.add(false);
        }

        for (int i = 0; i < numberOfPlayers; i ++)
        {
            dealCard(playerHands.get(i), true);
        }
        dealCard(dealerHand, false);
        for (int i = 0; i < numberOfPlayers; i ++)
        {
            dealCard(playerHands.get(i), true);
        }
        dealCard(dealerHand, true);
    }

    public void playerHit(int playerIndex)
    {
        if (isPlayerFinished(playerIndex))
        {
            return;
        }
        dealCard(playerHands.get(playerIndex), true);
    }

    public void playerStand(int playerIndex)
    {
        playerStood.set(playerIndex, true);
    }

    public boolean isPlayerFinished(int playerIndex)
    {
        return playerStood.get(playerIndex) || playerHands.get(playerIndex).isBusted();
    }

    public boolean allPlayersFinished()
    {
        for (int i = 0; i < numberOfPlayers; i ++)
        {
            if (!isPlayerFinished(i))
            {
                return false;
            }
        }
        return true;
    }

    public boolean dealerAutoPlay()
    {
        if (isDealerFinished())
        {
            return false;
        }
        if (dealerHand.getTotalValue() <= 16)
        {
            dealCard(dealerHand, true);
            return true;
        }
        dealerStood = true;
        return false;
    }

    public boolean isDealerFinished()
    {
        return dealerStood || dealerHand.isBusted();
    }

    public void finishDealer()
    {
        while (!isDealerFinished())
        {
            dealerAutoPlay();
        }
    }

    public void revealDealerHand()
    {
        if (!dealerHand.getCards().isEmpty())
        {
            dealerHand.getFirstCard().makeFaceUp();
        }
    }

    public GameResult determineResult(int playerIndex)
    {
        Hand playerHand = playerHands.get(playerIndex);

        if (playerHand.isBusted())
        {
            return GameResult.DEALER_WINS;
        }

        if (dealerHand.isBusted())
        {
            return GameResult.PLAYER_WINS;
        }

        int playerTotal = playerHand.getTotalValue();
        int dealerTotal = dealerHand.getTotalValue();

        if (playerTotal > dealerTotal)
        {
            return GameResult.PLAYER_WINS;
        }

        if (dealerTotal > playerTotal)
        {
            return GameResult.DEALER_WINS;
        }

        return GameResult.TIE;
    }

    private void dealCard(Hand hand, boolean visible)
    {
        CartaInglesa card = deck.obtenerUnaCarta();

        if (visible)
        {
            card.makeFaceUp();
        }

        hand.addCard(card);
    }

    public boolean shouldDealerPlay()
    {
        for (int i = 0; i < numberOfPlayers; i ++)
        {
            if (!playerHands.get(i).isBusted())
            {
                return true;
            }
        }
        return false;
    }

    public int getNumberOfPlayers()
    {
        return numberOfPlayers;
    }

    public Hand getPlayerHand(int playerIndex)
    {
        return playerHands.get(playerIndex);
    }

    public List<Hand> getPlayerHands()
    {
        return playerHands;
    }

    public Hand getDealerHand()
    {
        return dealerHand;
    }
}