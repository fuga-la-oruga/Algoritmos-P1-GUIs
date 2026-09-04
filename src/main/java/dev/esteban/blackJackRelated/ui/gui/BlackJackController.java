package dev.esteban.blackJackRelated.ui.gui;

import dev.esteban.blackJackRelated.model.BlackJackGame;
import javafx.application.Platform;
import javafx.stage.Stage;

public class BlackJackController
{
    private final BlackJackGame game;
    private final GameView view;
    private final Stage stage;
    private int currentPlayerIndex;

    public BlackJackController(BlackJackGame game, GameView view, Stage stage)
    {
        this.game = game;
        this.view = view;
        this.stage = stage;

        view.getSettingsButton().setOnAction(e -> openSettings());
        view.getHitButton().setOnAction(e -> hit());
        view.getStandButton().setOnAction(e -> stand());
        view.getHandButton().setOnAction(e -> HandDialog.show(
                stage,
                "Cartas del jugador " + (currentPlayerIndex + 1),
                game.getPlayerHand(currentPlayerIndex)));
        view.getDealerSlot().getCardsButton().setOnAction(e -> HandDialog.show(
                stage,
                "Cartas del Dealer",
                game.getDealerHand()));

        stage.setOnCloseRequest(e ->
        {
            e.consume();
            confirmExit();
        });

        startNewRound(1);
    }

    private void startNewRound(int numberOfPlayers)
    {
        game.startGame(numberOfPlayers);
        currentPlayerIndex = findNextUnfinishedPlayer(0);
        view.showPlayers(numberOfPlayers);
        refreshView();
    }

    private void hit()
    {
        game.playerHit(currentPlayerIndex);
        advanceTurn();
    }

    private void stand()
    {
        game.playerStand(currentPlayerIndex);
        advanceTurn();
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
        while (!game.isDealerFinished())
        {
            dealerPlayOneStep();
        }
        game.revealDealerHand();
        refreshView();
        ResultsDialog.show(stage, game, () -> startNewRound(game.getNumberOfPlayers()), this::confirmExit);
    }

    private int findNextUnfinishedPlayer(int fromIndex)
    {
        for (int i = fromIndex; i < game.getNumberOfPlayers(); i++)
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
        SettingsDialog.show(
                stage,
                game.getNumberOfPlayers(),
                this::confirmChangePlayers,
                this::confirmRestart,
                this::confirmExit);
    }

    private void confirmChangePlayers(int numberOfPlayers)
    {
        boolean confirmed = DialogUtils.showConfirmation(
                stage,
                "Se perdera la partida actual. Deseas iniciar una partida nueva con " + numberOfPlayers + " jugador(es)?");
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
        for (int i = 0; i < game.getNumberOfPlayers(); i++)
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
}