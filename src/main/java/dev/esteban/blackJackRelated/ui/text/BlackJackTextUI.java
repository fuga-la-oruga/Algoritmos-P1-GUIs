package dev.esteban.blackJackRelated.ui.text;

import dev.esteban.blackJackRelated.model.BlackJackGame;
import dev.esteban.blackJackRelated.model.GameResult;
import dev.esteban.blackJackRelated.model.Hand;
import dev.esteban.solitarieRelated.deckOfCards.CartaInglesa;

import java.util.Scanner;

public class BlackJackTextUI
{
    private final BlackJackGame game;
    private final Scanner sc;

    public BlackJackTextUI()
    {
        game = new BlackJackGame();
        sc = new Scanner(System.in);
    }

    public void play()
    {
        System.out.println("-----| BLACK JACK |-----");
        boolean keepPlaying = true;
        while (keepPlaying)
        {
            int numberOfPlayers = askNumberOfPlayers();
            game.startGame(numberOfPlayers);
            playRound();
            System.out.println("Jugar otra vez? (S/N)");
            String resp = sc.nextLine().trim().toUpperCase();
            keepPlaying = resp.equals("S");
        }
        System.out.println("Gracias por jugar.");
    }

    private int askNumberOfPlayers()
    {
        int numberOfPlayers = 0;
        while (numberOfPlayers < 1 || numberOfPlayers > 4)
        {
            System.out.println("Cuantos jugadores? (1-4)");
            String input = sc.nextLine().trim();
            try
            {
                numberOfPlayers = Integer.parseInt(input);
            }
            catch (NumberFormatException e)
            {
                numberOfPlayers = 0;
            }
            if (numberOfPlayers < 1 || numberOfPlayers > 4)
            {
                System.out.println("Cantidad invalida. Ingresa un numero entre 1 y 4.");
            }
        }
        return numberOfPlayers;
    }

    private void playRound()
    {
        while (!game.allPlayersFinished())
        {
            playTurnCycle();
            if (!game.allPlayersFinished())
            {
                game.dealerAutoPlay();
            }
        }
        game.finishDealer();
        game.revealDealerHand();
        showFinalResults();
    }

    private void playTurnCycle()
    {
        for (int i = 0; i < game.getNumberOfPlayers(); i++)
        {
            if (game.isPlayerFinished(i))
            {
                continue;
            }
            playPlayerTurn(i);
        }
    }

    private void playPlayerTurn(int playerIndex)
    {
        Hand hand = game.getPlayerHand(playerIndex);
        System.out.println("Dealer: " + dealerHiddenView());
        System.out.println("Jugador " + (playerIndex + 1) + ": " + hand + "  (Total: " + hand.getTotalValue() + ")");
        boolean validInput = false;
        while (!validInput)
        {
            System.out.println("Pedir (H) o Plantarse (S)?");
            String op = sc.nextLine().trim().toUpperCase();
            if (op.equals("H"))
            {
                game.playerHit(playerIndex);
                if (game.getPlayerHand(playerIndex).isBusted())
                {
                    System.out.println("El Jugador " + (playerIndex + 1) + " Se paso.");
                }
                validInput = true;
            }
            else if (op.equals("S"))
            {
                game.playerStand(playerIndex);
                validInput = true;
            }
            else
            {
                System.out.println("Opcion invalida. Intenta otra vez.");
            }
        }
    }

    private String dealerHiddenView()
    {
        Hand dealerHand = game.getDealerHand();
        StringBuilder sb = new StringBuilder();
        for (CartaInglesa card : dealerHand.getCards())
        {
            sb.append(card.isFaceup() ? card.toString() : "?").append(" ");
        }
        return sb.toString().trim();
    }

    private void showFinalResults()
    {
        Hand dealerHand = game.getDealerHand();
        System.out.println();
        System.out.println("-----| RESULTADOS |-----");
        System.out.println("Dealer: " + dealerHand + "  (Total: " + dealerHand.getTotalValue() + ")");
        for (int i = 0; i < game.getNumberOfPlayers(); i++)
        {
            Hand playerHand = game.getPlayerHand(i);
            GameResult result = game.determineResult(i);
            System.out.println("Player " + (i + 1) + ": " + playerHand + "  (Total: " + playerHand.getTotalValue() + ")  -> " + resultText(result));
        }
    }

    private String resultText(GameResult result)
    {
        return switch (result)
        {
            case PLAYER_WINS -> "Gana";
            case DEALER_WINS -> "Pierde";
            case TIE -> "Empate";
        };
    }
}