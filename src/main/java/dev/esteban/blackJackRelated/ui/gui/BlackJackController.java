package dev.esteban.blackJackRelated.ui.gui;

import dev.esteban.blackJackRelated.model.BlackJackGame;
import dev.esteban.blackJackRelated.model.Move;
import dev.esteban.blackJackRelated.model.Stack;
import dev.esteban.solitarieRelated.deckOfCards.CartaInglesa;
import javafx.application.Platform;
import javafx.stage.Stage;

public class BlackJackController
{
    private final BlackJackGame game;
    private final GameView view;
    private final Stage stage;
    private int currentPlayerIndex;
    private final Stack<Move> undoStack = new Stack<>(100);

    public BlackJackController(BlackJackGame game, GameView view, Stage stage)
    {
        this.game = game;
        this.view = view;
        this.stage = stage;

        view.getSettingsButton().setOnAction(e -> openSettings());
        view.getHitButton().setOnAction(e -> hit());
        view.getStandButton().setOnAction(e -> stand());
        view.getUndoButton().setOnAction(e -> undo());

        view.getHandButton().setOnAction(e -> HandDialog.show
        (
            stage,
            "Cartas del jugador " + (currentPlayerIndex + 1),
            game.getPlayerHand(currentPlayerIndex),
            false
        ));

        view.getDealerSlot().getCardsButton().setOnAction(e -> HandDialog.show
        (
            stage,
            "Cartas del Dealer",
            game.getDealerHand(),
            true
        ));

        stage.setOnCloseRequest(e ->
        {
            e.consume();
            confirmExit();
        });

        startNewRound(1);
    }

    private void startNewRound(int numberOfPlayers)
    {
        clearUndoStack();
        game.startGame(numberOfPlayers);
        currentPlayerIndex = findNextUnfinishedPlayer(0);
        view.showPlayers(numberOfPlayers);
        refreshView();
    }

    private void hit()
    {
        int previousPlayerIndex = currentPlayerIndex;
        game.clearDealerCardsThisTurn();
        game.playerHit(currentPlayerIndex);
        advanceTurn();

        Move movement = new Move
        (
            previousPlayerIndex,
            Move.HIT,
            game.getPlayerHand(previousPlayerIndex).getLastCard(),
            previousPlayerIndex
        );

        Stack<CartaInglesa> dealerCards = game.getDealerCardsThisTurn();

        while (!dealerCards.isEmpty())
        {
            movement.getDealerCards().push(dealerCards.pop());
        }

        undoStack.push(movement);
    }

    private void stand()
    {
        int previousPlayerIndex = currentPlayerIndex;

        game.playerStand(currentPlayerIndex);
        advanceTurn();

        Move movement = new Move
        (
            previousPlayerIndex,
            Move.STAND,
            null,
            previousPlayerIndex
        );

        undoStack.push(movement);
    }

    private void advanceTurn()
    {
        int next = findNextUnfinishedPlayer(currentPlayerIndex + 1);

        if (next != -1)
        {
            currentPlayerIndex = next;
            refreshView();
            return;
        }

        if (!game.allPlayersFinished())
        {
            dealerPlayOneStep();
            currentPlayerIndex = findNextUnfinishedPlayer(0);
            refreshView();
        }
        else
        {
            currentPlayerIndex = -1;
            refreshView();
            finishRound();
        }
    }

    private void dealerPlayOneStep()
    {
        boolean tookCard = game.dealerAutoPlay();
        refreshView();

        if (tookCard)
        {
            DialogUtils.showNotice(stage, "El Dealer ha recogido una carta");
        }
        else
        {
            DialogUtils.showNotice(stage, "El Dealer se ha plantado");
        }
    }

    private void finishRound()
    {
        if (game.shouldDealerPlay())
        {
            while (!game.isDealerFinished())
            {
                dealerPlayOneStep();
            }
        }

        game.revealDealerHand();
        refreshView();

        ResultsDialog.show
        (
            stage,
            game,
            () -> startNewRound(game.getNumberOfPlayers()),
            () -> confirmExit()
        );
    }

    private int findNextUnfinishedPlayer(int fromIndex)
    {
        for (int i = fromIndex; i < game.getNumberOfPlayers(); i ++)
        {
            if (!game.isPlayerFinished(i))
            {
                return i;
            }
        }
        return -1;
    }

    private void openSettings()
    {
        SettingsDialog.show
        (
            stage,
            game.getNumberOfPlayers(),
            this::confirmChangePlayers,
            this::confirmRestart,
            this::confirmExit
        );
    }

    private void confirmChangePlayers(int numberOfPlayers)
    {
        boolean confirmed = DialogUtils.showConfirmation
        (
            stage,
            "Se perdera la partida actual. Deseas iniciar una partida nueva con " + numberOfPlayers + " jugador(es)?"
        );

        if (confirmed)
        {
            startNewRound(numberOfPlayers);
        }
    }

    private void confirmRestart()
    {
        boolean confirmed = DialogUtils.showConfirmation(stage, "Deseas reiniciar la partida actual?");

        if (confirmed)
        {
            startNewRound(game.getNumberOfPlayers());
        }
    }

    private void confirmExit()
    {
        boolean confirmed = DialogUtils.showConfirmation(stage, "Deseas salir del juego?");

        if (confirmed)
        {
            Platform.exit();
        }
    }

    private void refreshView()
    {
        view.getDealerSlot().update(game.getDealerHand());

        for (int i = 0; i < game.getNumberOfPlayers(); i ++)
        {
            boolean active = (i == currentPlayerIndex);
            String status = "";

            if (game.getPlayerHand(i).isBusted())
            {
                status = "Pasado";
            }
            else if (game.isPlayerFinished(i))
            {
                status = "Plantado";
            }

            view.getPlayerSlot(i).update(game.getPlayerHand(i), i + 1, active, status);
        }

        boolean roundActive = currentPlayerIndex != -1;
        view.getHitButton().setDisable(!roundActive);
        view.getStandButton().setDisable(!roundActive);
        view.getHandButton().setDisable(!roundActive);
    }

    private void clearUndoStack()
    {
        while (!undoStack.isEmpty())
        {
            undoStack.pop();
        }
    }

    private void undo()
    {
        if (undoStack.isEmpty())
        {
            return;
        }

        Move movement = undoStack.pop();

        game.undoDealerMove(movement.getDealerCards());

        if (movement.getType().equals(Move.HIT))
        {
            game.undoHit(movement.getPlayer_i());
        }
        else if (movement.getType().equals(Move.STAND))
        {
            game.undoStand(movement.getPlayer_i());
        }

        currentPlayerIndex = movement.getPreviousPlayerIndex();

        refreshView();
    }
}