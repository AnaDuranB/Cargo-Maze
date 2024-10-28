package com.cargomaze.cargo_maze;

import com.cargomaze.cargo_maze.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameSessionTest {
    private GameSession gameSession;
    private Player player1,player2, player3, player4;
    private Board board;

    @BeforeEach
    public void setUp() {
        gameSession = new GameSession("session1");
        board = gameSession.getBoard();
        player1 = new Player("p1","Player 1", gameSession);
        player2 = new Player("p2","Player 2", gameSession);
        player3 = new Player("p3","Player 3", gameSession);
        player4 = new Player("p4","Player 4", gameSession);

        gameSession.addPlayer(player1);
        gameSession.addPlayer(player2);
        gameSession.addPlayer(player3);
        gameSession.addPlayer(player4);

        player1.setReady(true);
        player2.setReady(true);
        player3.setReady(true);
        player4.setReady(true);
        gameSession.startGame();
    }

    @Test
    public void testAddPlayer() {
        Player newPlayer = new Player("5", "Player5", gameSession);
        assertFalse(gameSession.addPlayer(newPlayer)); // falso, ya hay 4 jugadores
    }

    @Test
    public void testStartGame() {
        gameSession.getPlayers().forEach(player -> player.setReady(true));
        gameSession.startGame();

        assertEquals(GameStatus.IN_PROGRESS, gameSession.getStatus());
    }

   
}
