package com.cargomaze.cargo_maze.ModelTests;

import com.cargomaze.cargo_maze.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class GameSessionTest {
    private GameSession gameSession;
    private Player player1,player2, player3, player4;

    @BeforeEach
    public void setUp() {
        gameSession = new GameSession("session1");
        player1 = new Player("p1","Player 1");
        player2 = new Player("p2","Player 2");
        player3 = new Player("p3","Player 3");
        player4 = new Player("p4","Player 4");

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
        Player newPlayer = new Player("5", "Player5");
        assertFalse(gameSession.addPlayer(newPlayer)); // falso, ya hay 4 jugadores
    }

    @Test
    public void testStartGame() {
        int WIDTH = 15;
        int HEIGHT = 10;
        gameSession.getPlayers().forEach(player -> player.setReady(true));
        gameSession.startGame();
        assertEquals(GameStatus.IN_PROGRESS, gameSession.getStatus());
        
        //Se verifica que al iniciar el juego se verique que se le asignen las posiciones iniciales a lo jugadores
        assertEquals(gameSession.getPlayers().get(0).getPosition(), new Position(1, 1));
        assertEquals(gameSession.getPlayers().get(1).getPosition(), new Position(1, HEIGHT-2));
        assertEquals(gameSession.getPlayers().get(2).getPosition(), new Position(WIDTH-2, 1));
        assertEquals(gameSession.getPlayers().get(3).getPosition(), new Position(WIDTH-2, HEIGHT-2));
    }
}
